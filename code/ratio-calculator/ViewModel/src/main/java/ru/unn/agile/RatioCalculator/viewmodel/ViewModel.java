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

import static java.lang.Math.abs;


public class ViewModel {
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
                super.bind(denominatorFirst, denominatorSecond,
                        numeratorFirst, numeratorSecond);
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
    public ObjectProperty<Operation> operationProperty() {
        return operation;
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

    public StringProperty resultNumeratorProperty() {
        return resultNumerator;
    }
    public final String getResultNumerator() {
        return resultNumerator.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final String getStatus() {
        return status.get();
    }

    public final ObservableList<Operation> getOperations() {
        return operations.get();
    }

    public ObjectProperty<ObservableList<Operation>> operationsProperty() {
        return operations;
    }

    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }

    public final boolean isCalculationDisabled() {
        return calculationDisabled.get();
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

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observableValue,
                            final String oldValue, final String newValue) {
            status.set(getInputStatus().toString());
        }
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (denominatorFirst.get().isEmpty() || denominatorSecond.get().isEmpty()
                || numeratorFirst.get().isEmpty() || numeratorSecond.get().isEmpty()) {
            inputStatus = Status.WAITING;
        }
        try {
            if (!denominatorFirst.get().isEmpty()
                    && checkNull(Double.parseDouble(denominatorFirst.get()))) {
                inputStatus = Status.BAD_FORMAT;

            }
            if (!denominatorSecond.get().isEmpty()
                    && checkNull(Double.parseDouble(denominatorSecond.get()))) {
                inputStatus = Status.BAD_FORMAT;
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

    boolean checkNull(final double value) {
        return (abs(value) < NULL_VALUE);
    }

    private final StringProperty denominatorFirst = new SimpleStringProperty();
    private final StringProperty numeratorFirst = new SimpleStringProperty();
    private final StringProperty denominatorSecond = new SimpleStringProperty();
    private final StringProperty numeratorSecond = new SimpleStringProperty();
    private final StringProperty resultDenominator = new SimpleStringProperty();
    private final StringProperty resultNumerator = new SimpleStringProperty();
    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();
    private static final double NULL_VALUE = 10e-6;

    private final ObjectProperty<ObservableList<Operation>> operations =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(Operation.values()));
    private final ObjectProperty<Operation> operation = new SimpleObjectProperty<>();
    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();
    private final StringProperty status = new SimpleStringProperty();


}

enum Status {
    WAITING("Please provide input data"),
    READY("Press 'Calculate'"),
    BAD_FORMAT("Bad input format"),
    SUCCESS("Success");
    Status(final String nameStatus) {
        this.name = nameStatus;
    }
    public String toString() {
        return name;
    }
    private final String name;
}
