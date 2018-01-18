package ru.unn.agile.PrimeNumber.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import ru.unn.agile.PrimeNumber.Model.PrimeNumber;
import ru.unn.agile.PrimeNumber.Model.PrimeNumber.Methods;

public class ViewModel {
    public ViewModel() {
        rangeFrom.set("");
        rangeTo.set("");
        maxCountPrimes.set("");
        currentAnswer.set("");
        status.set(Status.WAITING.toString());
        method.set(Methods.SIMPLE);
        answersList.set(FXCollections.observableArrayList());
        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(rangeFrom, rangeTo, maxCountPrimes);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        calculationDisabled.bind(couldCalculate.not());

        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(rangeFrom);
            add(rangeTo);
            add(maxCountPrimes);
        } };

        for (StringProperty field : fields) {
            final PropertyChangeListener listener = new PropertyChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void calculate() {
        if (calculationDisabled.get()) {
            return;
        }
        Integer left = Integer.parseInt(rangeFrom.get());
        Integer right = Integer.parseInt(rangeTo.get());
        Integer countPrimes = Integer.parseInt(maxCountPrimes.get());

        long startTime = System.nanoTime();

        PrimeNumber primes = new PrimeNumber(left, right);
        primes.findPrimeNumberFromRange(method.get());
        List<Integer> primesList = primes.getPrimeList();

        long elapsedTime = System.nanoTime() - startTime;
        final long numberOfNanosecondsInSecond = 1000000000;
        Double elapsedTimeInSec = (double) elapsedTime / numberOfNanosecondsInSecond;

        Integer numberOfOutputPrimes = Integer.min(countPrimes, primesList.size());
        setCalculationMessages(left, right, elapsedTimeInSec, numberOfOutputPrimes, primesList);

        status.set(Status.SUCCESS.toString());
    }

    public void chooseAnswerById(final Integer id) {
        currentAnswer.set(answersList.get(id).getAnswerMessage());
    }

    public String getRangeFrom() {
        return rangeFrom.get();
    }
    public String getRangeTo() {
        return rangeTo.get();
    }
    public String getMaxCountPrimes() {
        return maxCountPrimes.get();
    }
    public String getCurrentAnswer() {
        return currentAnswer.get();
    }
    public final String getStatus() {
        return status.get();
    }

    public final boolean isCalculationDisabled() {
        return calculationDisabled.get();
    }
    public final ObservableList<Methods> getMethods() {
        return methods.get();
    }

    public StringProperty rangeFromProperty() {
        return rangeFrom;
    }
    public StringProperty rangeToProperty() {
        return rangeTo;
    }
    public StringProperty maxCountPrimesProperty() {
        return maxCountPrimes;
    }
    public StringProperty currentAnswerProperty() {
        return currentAnswer;
    }
    public StringProperty statusProperty() {
        return status;
    }
    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }
    public ObjectProperty<Methods> methodProperty() {
        return method;
    }
    public ListProperty<Query> answersListProperty() {
        return answersList;
    }

    private void setCalculationMessages(final Integer left, final Integer right,
                                        final Double elapsedTimeInSec,
                                        final Integer numberOfOutputPrimes,
                                        final List<Integer> primesList) {
        String answerMessage, shortMessage = String.format("%d. ", answersList.size() + 1);
        if (!primesList.isEmpty()) {
            answerMessage = String.format("Found %d primes ", primesList.size());
            shortMessage += String.format("%d primes ", primesList.size());
        } else {
            answerMessage = "There are no primes ";
            shortMessage += "No primes ";
        }

        answerMessage += String.format("in the range from %d to %d in %f seconds.\n",
                left, right, elapsedTimeInSec);
        shortMessage += String.format("in [%d; %d] and printed %d",
                left, right, numberOfOutputPrimes);

        if (numberOfOutputPrimes > 0) {
            answerMessage += String.format("Here are %d of them:\n", numberOfOutputPrimes);
            answerMessage += listOfPrimesToString(primesList, numberOfOutputPrimes) + "\n";
        }

        currentAnswer.set(answerMessage);
        answersList.add(new Query(shortMessage, answerMessage));
    }

    private String listOfPrimesToString(final List<Integer> primesList,
                                        final Integer numberOfOutputPrimes) {
        String result = listToString(primesList.subList(0, (numberOfOutputPrimes + 1) / 2));
        if (numberOfOutputPrimes > 1) {
            result += DELIMITER;
            if (numberOfOutputPrimes != primesList.size()) {
                result += "..." + DELIMITER;
            }
        }
        result += listToString(
                primesList.subList(primesList.size() - numberOfOutputPrimes / 2, primesList.size())
        );
        return result;
    }

    private static String listToString(final List<Integer> collection) {
        return collection.stream().map(Object::toString).collect(Collectors.joining(DELIMITER));
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (rangeFrom.get().isEmpty()
                || rangeTo.get().isEmpty()
                || maxCountPrimes.get().isEmpty()) {
            inputStatus = Status.WAITING;
        }
        try {
            if (!maxCountPrimes.get().isEmpty()) {
                Integer maxCount = Integer.parseInt(maxCountPrimes.get());
                if (maxCount < 0) {
                    throw new NumberFormatException("Negative max count");
                }
            }
            if (!rangeFrom.get().isEmpty()) {
                Integer.parseInt(rangeFrom.get());
            }
            if (!rangeTo.get().isEmpty()) {
                Integer.parseInt(rangeTo.get());
            }
        } catch (NumberFormatException nfe) {
            inputStatus = Status.BAD_FORMAT;
        }

        return inputStatus;
    }

    private static final String DELIMITER = ", ";

    private final StringProperty rangeFrom = new SimpleStringProperty();
    private final StringProperty rangeTo = new SimpleStringProperty();
    private final StringProperty maxCountPrimes = new SimpleStringProperty();
    private final StringProperty currentAnswer = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();
    private final List<PropertyChangeListener> valueChangedListeners = new ArrayList<>();
    private final ObjectProperty<Methods> method = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<Methods>> methods =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(Methods.values()));
    private final ListProperty<Query> answersList = new SimpleListProperty<>();

    private class PropertyChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            status.set(getInputStatus().toString());
        }
    }
}
