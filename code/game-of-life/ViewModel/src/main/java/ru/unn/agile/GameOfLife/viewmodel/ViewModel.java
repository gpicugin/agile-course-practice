package ru.unn.agile.GameOfLife.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

import ru.unn.agile.GameOfLife.Model.GameOfLife;

public class ViewModel {

    public ViewModel() {
        height.set("");
        width.set("");
        table.set("");
        result.set("");
        status.set(Status.WAITING.toString());
        BooleanBinding couldGetNextGeneration = new BooleanBinding() {
            {
                super.bind(height, width, table);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        gettingNextGenerationDisabled.bind(couldGetNextGeneration.not());

        BooleanBinding couldSetDefaultTable = new BooleanBinding() {
            {
                super.bind(height, width);
            }
            @Override
            protected boolean computeValue() {
                return heightAndWidthIsCorrect();
            }
        };
        settingDefaultTableDisabled.bind(couldSetDefaultTable.not());

        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(height);
            add(width);
            add(table);
        } };

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void setDefaultTable() {
        String defaultTable = "";
        for (int i = 0; i < heightInt(); i++) {
            for (int j = 0; j < widthInt(); j++) {
                defaultTable = defaultTable + ".";
            }
            if (i < heightInt() - 1) {
                defaultTable = defaultTable + "\n";
            }
        }
        table.set(defaultTable);
    }

    public void setPreviousGeneration() {
        table.set(result.get());
    }

    public String[] translateTableToInputArray() {
        String[] translatedTable = new String[heightInt() + 1];
        translatedTable[0] = width.get() + " " + height.get();
        String currentString = "";
        for (int i = 0; i < heightInt(); i++) {
            for (int j = 0; j < widthInt(); j++) {
                currentString = currentString + table.get().charAt(i * (widthInt() + 1) + j);
            }
            translatedTable[i + 1] = currentString;
            currentString = "";
        }
        return  translatedTable;
    }

    public String translateNextGenerationArrayToOutputString(final String[] stringArray) {
        String outputString = "";
        for (int i = 1; i < stringArray.length - 1; i++) {
            outputString = outputString + stringArray[i] + "\n";
        }
        outputString = outputString + stringArray[stringArray.length - 1];
        return outputString;
    }

    public void getNextGeneration() {
        GameOfLife testGame = new GameOfLife();
        testGame.readCurrentGeneration(translateTableToInputArray());
        testGame.buildNextGeneration();
        result.set(translateNextGenerationArrayToOutputString(testGame.writeNextGeneration()));
    }

    public StringProperty heightProperty() {
        return height;
    }
    public StringProperty widthProperty() {
        return width;
    }
    public StringProperty tableProperty() {
        return table;
    }
    public StringProperty resultProperty() {
        return result;
    }
    public final String getResult() {
        return result.get();
    }
    public StringProperty statusProperty() {
        return status;
    }
    public final String getStatus() {
        return status.get();
    }

    public BooleanProperty gettingNextGenerationDisabledProperty() {
        return gettingNextGenerationDisabled;
    }
    public final boolean isGettingNextGenerationDisabled() {
        return gettingNextGenerationDisabled.get();
    }

    public BooleanProperty settingDefaultTableDisabledProperty() {
        return settingDefaultTableDisabled;
    }
    public final boolean isSettingDefaultTableDisabled() {
        return settingDefaultTableDisabled.get();
    }

    public int heightInt() {
        return Integer.parseInt(height.get());
    }
    public int widthInt() {
        return Integer.parseInt(width.get());
    }
    public char currentSymbol(final int i) {
        return table.get().charAt(i);
    }

    private final StringProperty height = new SimpleStringProperty();
    private final StringProperty width = new SimpleStringProperty();
    private final StringProperty table = new SimpleStringProperty();

    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    private final BooleanProperty gettingNextGenerationDisabled = new SimpleBooleanProperty();
    private final BooleanProperty settingDefaultTableDisabled = new SimpleBooleanProperty();
    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            status.set(getInputStatus().toString());
        }
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        try {
            if (!height.get().isEmpty()) {
                Integer.parseInt(height.get());
            }
            if (!width.get().isEmpty()) {
                Integer.parseInt(width.get());
            }
        } catch (NumberFormatException numberFormatException) {
            inputStatus = Status.BAD_FORMAT;
            return inputStatus;
        }
        if (height.get().isEmpty() || width.get().isEmpty()
                || table.get().isEmpty()) {
            inputStatus = Status.WAITING;
        } else if (!tableIsCorrect()) {
            inputStatus = Status.BAD_FORMAT;
        } else if (table.get().length() != ((widthInt() + 1) * heightInt() - 1)) {
            inputStatus = Status.WAITING;
        }
        return inputStatus;
    }

    private boolean tableIsCorrect() {
        int j = 0;
        for (int i = 0; i < table.get().length(); i++) {
            if (j < widthInt()) {
                if ((currentSymbol(i) != ".".charAt(0)) && (currentSymbol(i) != "*".charAt(0))) {
                    return false;
                }
                j++;
            } else {
                if (currentSymbol(i) != "\n".charAt(0)) {
                    return false;
                }
                j = 0;
            }
        }
        return true;
    }

    private boolean heightAndWidthIsCorrect() {
        try {
            if (!height.get().isEmpty()) {
                Integer.parseInt(height.get());
            }
            if (!width.get().isEmpty()) {
                Integer.parseInt(width.get());
            }
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
        return !(height.get().isEmpty() || width.get().isEmpty());
    }
}

enum Status {
    WAITING("Please provide input data"),
    READY("Press 'Get next generation' or Enter"),
    BAD_FORMAT("Bad format"),
    SUCCESS("Success");

    Status(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    private final String name;
}
