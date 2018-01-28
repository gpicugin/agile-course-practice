package ru.unn.agile.RatioCalculator.viewmodel;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.RatioCalculator.Model.Ratio;
import ru.unn.agile.RatioCalculator.Model.Ratio.Operation;
import javafx.beans.binding.BooleanBinding;

import java.util.ArrayList;
import java.util.List;


public class ViewModel {
     private final StringProperty denominatorFirst = new SimpleStringProperty();
     private final StringProperty numeratorFirst = new SimpleStringProperty();
     private final StringProperty denominatorSecond = new SimpleStringProperty();
     private final StringProperty numeratorSecond = new SimpleStringProperty();
     private final StringProperty resultDenominator = new SimpleStringProperty();
     private final StringProperty resultNumerator = new SimpleStringProperty();
     private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();

    private final ObjectProperty<ObservableList<Operation>> operations =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(Operation.values()));
    private final ObjectProperty<Operation> operation = new SimpleObjectProperty<>();
    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    public ObjectProperty<Operation> operationProperty() {
        return operation;
    }

    public ViewModel() {
        denominatorFirst.set("");
        numeratorFirst.set("");
        denominatorSecond.set("");
        numeratorSecond.set("");
        resultDenominator.set("");
        resultNumerator.set("");
        operation.set(Operation.ADD);
        status.set(Status.WAITING.toString());

        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(denominatorFirst);
            add(denominatorSecond);
            add(numeratorFirst);
            add(numeratorSecond);
        }
        };

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(denominatorFirst);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };

        calculationDisabled.bind(couldCalculate.not());

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public StringProperty denominatorFirstProperty() {
        return denominatorFirst;
    }
    public StringProperty numeratorFirstProperty() {
        return numeratorFirst;
    }
    public StringProperty denominatorSecondProperty() {
        return denominatorSecond;
    }
    public StringProperty numeratorSecondProperty() {
        return numeratorSecond;
    }

    public StringProperty resultDenominatorProperty() {
        return resultDenominator;
    }

    public final String getResultDenominator() {
        return resultDenominator.get();
    }

    public final String getResultNumerator() {
        return resultNumerator.get();
    }

    public StringProperty resultNumeratorProperty() {
        return resultNumerator;
    }


    private final StringProperty status = new SimpleStringProperty();

    public StringProperty statusProperty() {
        return status;
    }
    public final String getStatus() {
        return status.get();
    }
    public ObjectProperty<ObservableList<Operation>> operationsProperty() {
        return operations;
    }
    public final ObservableList<Operation> getOperations() {
        return operations.get();
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            status.set(getInputStatus().toString());
        }
    }
    public void calculate() {
        if (calculationDisabled.get()) {
            return;
        }

        Ratio z1 = new Ratio(Integer.parseInt(numeratorFirst.get()),
                Integer.parseInt(denominatorFirst.get()));
        Ratio z2 = new Ratio(Integer.parseInt(numeratorSecond.get()),
                Integer.parseInt(denominatorSecond.get()));

        resultNumerator.
                set(Integer.toString(operation.get().apply(z1, z2).getNumerator()));
        resultDenominator.
                set(Integer.toString(operation.get().apply(z1, z2).getDenominator()));
        status.set(Status.SUCCESS.toString());
    }


    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (denominatorFirst.get().isEmpty() || denominatorSecond.get().isEmpty()
                || numeratorFirst.get().isEmpty() || numeratorSecond.get().isEmpty()) {
            inputStatus = Status.WAITING;
        }
        try {
            if (!denominatorFirst.get().isEmpty()) {
                Double.parseDouble(denominatorFirst.get());
            }
            if (!denominatorSecond.get().isEmpty()) {
                Double.parseDouble(denominatorSecond.get());
            }
            if (!numeratorFirst.get().isEmpty()) {
                Double.parseDouble(numeratorFirst.get());
            }
            if (!numeratorSecond.get().isEmpty()) {
                Double.parseDouble(numeratorSecond.get());
            }
        } catch (NumberFormatException nfe) {
            inputStatus = Status.BAD_FORMAT;
        }

        return inputStatus;
    }


}

enum Status {
    WAITING("Please provide input data"),
    READY("Press 'Calculate' or Enter"),
    BAD_FORMAT("Bad format"),
    SUCCESS("Success");

    private final String name;
    Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
