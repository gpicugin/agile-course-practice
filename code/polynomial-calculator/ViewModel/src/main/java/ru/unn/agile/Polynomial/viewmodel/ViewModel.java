package ru.unn.agile.Polynomial.viewmodel;

import ru.unn.agile.Polynomial.model.Polynomial;

public class ViewModel {
    public ViewModel() {
        isCalculateButtonEnabled = false;
        firstPolynomial = "";
        secondPolynomial = "";
        operation = Operation.ADD;
        result = "";
        status = Status.WAITING;
    }

    public boolean isButtonCalculateEnabled() {
        return isCalculateButtonEnabled;
    }

    public String getResult() {
        return result;
    }

    public Status getStatus() {
        return status;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(final Operation operation) {
        this.operation = operation;
    }

    public String getFirstPolynomial() {
        return firstPolynomial;
    }

    public void setFirstPolynomial(final String txt) {
        firstPolynomial = txt;
    }

    public String getSecondPolynomial() {
        return secondPolynomial;
    }

    public void setSecondPolynomial(final String txt) {
        secondPolynomial = txt;
    }

    public void processTextChanged() {
        parseInput();
    }

    public void calculate() {
        if (parseInput()) {
            Polynomial firstOperand = Parser.getPolynomial(firstPolynomial);
            Polynomial secondOperand = Parser.getPolynomial(secondPolynomial);
            Polynomial resultOfOperation;
            switch (operation) {
                case ADD:
                    resultOfOperation = firstOperand.add(secondOperand);
                    break;
                case SUB:
                    resultOfOperation = firstOperand.sub(secondOperand);
                    break;
                case MULTIPLY:
                    resultOfOperation = firstOperand.multiply(secondOperand);
                    break;
                default:
                    throw new IllegalStateException("No such operation!");
            }
            result = resultOfOperation.toString();
            status = Status.SUCCESS;
        }
    }

    private boolean isInputAvailable() {
        return !firstPolynomial.isEmpty() && !secondPolynomial.isEmpty();
    }

    private boolean parseInput() {
        isCalculateButtonEnabled = false;
        if (!isInputAvailable()) {
            status = Status.WAITING;
        } else {
            if (Parser.getPolynomial(firstPolynomial) != null
                    && Parser.getPolynomial(secondPolynomial) != null) {
                isCalculateButtonEnabled = true;
                status = Status.READY;
            } else {
                status = Status.BAD_FORMAT;
            }
        }
        return isCalculateButtonEnabled;
    }

    private boolean isCalculateButtonEnabled;
    private String firstPolynomial;
    private String secondPolynomial;
    private Operation operation;
    private Status status;
    private String result;
}
