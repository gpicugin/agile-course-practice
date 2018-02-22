package ru.unn.agile.RatioCalculator.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    public ViewModel() {
        init();
    }

    public ViewModel(final ILogger logger) {
        setLogger(logger);
        init();
    }


    private void init() {
        denominatorFirst.set("");
        numeratorFirst.set("");
        denominatorSecond.set("");
        numeratorSecond.set("");
        resultDenominator.set("");
        resultNumerator.set("");
        operation.set(Operation.ADD);
        status.set(Status.WAITING.toString());

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

        final List<StringProperty> fields = new ArrayList<StringProperty>() {
            {
                add(denominatorFirst);
                add(denominatorSecond);
                add(numeratorFirst);
                add(numeratorSecond);
            }
        };

        valueChangedListeners = new ArrayList<>();
        for (StringProperty val : fields) {
            final ValueCachingChangeListener listener = new ValueCachingChangeListener();
            val.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

        public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
            if (!oldValue && newValue) {
                return;
            }

            for (ValueCachingChangeListener listener : valueChangedListeners) {
                if (listener.isChanged()) {
                    StringBuilder message = new StringBuilder(LogMessages.EDITING_FINISHED);
                    message.append("Input arguments are: [")
                            .append(numeratorFirst.get()).append("/ ")
                            .append(denominatorFirst.get()).append("; ")
                            .append(numeratorSecond.get()).append("; ")
                            .append(denominatorSecond.get()).append("]");
                    logger.log(message.toString());
                    updateLogs();
                    listener.cache();
                    break;
                }
            }
        }

     //   for (StringProperty field : fields) {
      //      final ValueChangeListener listener = new ValueChangeListener();
      //      field.addListener(listener);
      //      valueChangedListeners.add(listener);
     //   }

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
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

        StringBuilder message = new StringBuilder(LogMessages.CALCULATE_WAS_PRESSED);
        message.append("Arguments: first ratio = ").append(numeratorFirst.get())
                .append("/").append(denominatorFirst.get())
                .append("; second ratio = ").append(numeratorSecond.get())
                .append("/").append(denominatorSecond.get())
                .append(" Operation: ").append(operation.get().toString()).append(".");
        logger.log(message.toString());
        updateLogs();

        resultNumerator.
                set(Integer.toString(operation.get().apply(z1, z2).getNumerator()));
        resultDenominator.
                set(Integer.toString(operation.get().apply(z1, z2).getDenominator()));
        status.set(Status.SUCCESS.toString());
    }

    public void onOperationChanged(final Operation oldValue, final Operation newValue) {
        if (oldValue.equals(newValue)) {
            return;
        }
        StringBuilder message = new StringBuilder(LogMessages.OPERATION_WAS_CHANGED);
        message.append(newValue.toString());
        logger.log(message.toString());
        updateLogs();
    }

    private void updateLogs() {
        List<String> fullLog = logger.get();
        String record = new String("");
        for (String log : fullLog) {
            record += log + "\n";
        }
        logs.set(record);
    }

    private class ValueCachingChangeListener implements ChangeListener<String> {
        private String prevValue = new String("");
        private String curValue = new String("");
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            if (oldValue.equals(newValue)) {
                return;
            }
            status.set(getInputStatus().toString());
            curValue = newValue;
        }
        public boolean isChanged() {
            return !prevValue.equals(curValue);
        }
        public void cache() {
            prevValue = curValue;
        }
    }


    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        int divNullCheck = 1;
        if (denominatorFirst.get().isEmpty() || denominatorSecond.get().isEmpty()
                || numeratorFirst.get().isEmpty() || numeratorSecond.get().isEmpty()) {
            inputStatus = Status.WAITING;
        }
        try {
            if (!numeratorSecond.get().isEmpty()) {
                divNullCheck = Integer.parseInt(numeratorSecond.get());
            }
            inputStatus = checkParseFields(inputStatus);
        } catch (NumberFormatException nfe) {
            inputStatus = Status.BAD_FORMAT;
        }
        if (divNullCheck == 0 && operation.get() == Operation.DIV) {
            inputStatus = Status.BAD_FORMAT;
        }
        return inputStatus;
    }

    private Status checkParseFields(final Status inputStatus) throws NumberFormatException {
        Status current = inputStatus;
        if (!denominatorFirst.get().isEmpty()
                && Integer.parseInt(denominatorFirst.get()) == 0) {
            current = Status.BAD_FORMAT;
        }
        if (!denominatorSecond.get().isEmpty()
                && Integer.parseInt(denominatorSecond.get()) == 0) {
            current = Status.BAD_FORMAT;
        }
        if (!numeratorFirst.get().isEmpty()) {
            Integer.parseInt(numeratorFirst.get());
        }
        return current;
    }

    public final List<String> getLog() {
        return logger.get();
    }
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
   // private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();
    private List<ValueCachingChangeListener> valueChangedListeners;
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty logs = new SimpleStringProperty();

    private ILogger logger;
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

final class LogMessages {
    public static final String CALCULATE_WAS_PRESSED = "Calculate. ";
    public static final String OPERATION_WAS_CHANGED = "Operation was changed to ";
    public static final String EDITING_FINISHED = "Updated input. ";

    private LogMessages() { }
}
