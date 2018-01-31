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
        height.addListener(inputStringChangedListener);
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

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        try {
            if (!height.get().isEmpty()) {
                Integer.parseInt(height.get());
            }
            if (!width.get().isEmpty()) {
                Integer.parseInt(width.get());
            }
        } catch (NumberFormatException nfe) {
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

    public String[] translateTableToInputArray() {
        String[] translatedTable = new String[heightInt() + 1];
        translatedTable[0] = width.get() + " " + height.get();
        String currentString = "";
        int i = 0;
        int j = 0;
        while (i < heightInt()) {
            while (j < widthInt()) {
                currentString = currentString + table.get().charAt(i * (widthInt() + 1) + j);
                j++;
            }
            translatedTable[i + 1] = currentString;
            currentString = "";
            j = 0;
            i++;
        }
        return  translatedTable;
    }

    public String translateNextGenerationArrayToOutputString(final String[] stringArray) {
        String outputString = "";
        int i = 1;
        while (i < stringArray.length - 1) {
            outputString = outputString + stringArray[i] + "\n";
            i++;
        }
        outputString = outputString + stringArray[i];
        return outputString;
    }

    public void getNextGeneration() {
        GameOfLife testGame = new GameOfLife();
        testGame.readCurrentGeneration(translateTableToInputArray());
        testGame.buildNextGeneration();
        //String[] nextGeneration = testGame.writeNextGeneration();
        result.set(translateNextGenerationArrayToOutputString(testGame.writeNextGeneration()));
        //return translateNextGenerationArrayToOutputString(testGame.writeNextGeneration());
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
    private final ValueChangeListener inputStringChangedListener = new ValueChangeListener();
    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            status.set(getInputStatus().toString());
        }
    }

    private boolean tableIsCorrect() {
        int i = 0, j = 0;
        while (i < table.get().length()) {
            if (j < widthInt()) {
                if ((currentSymbol(i) != ".".charAt(0)) && (currentSymbol(i) != "*".charAt(0))) {
                    return false;
                }
                i++;
                j++;
            } else {
                if (currentSymbol(i) != "\n".charAt(0)) {
                    return false;
                }
                i++;
                j = 0;
            }
        }
        return true;
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
