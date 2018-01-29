package ru.unn.agile.MatrixClass.viewmodel;

public enum Status {
    WAITING("Please enter a matrix size"),
    WAITING_MATRIX("Enter a matrix"),
    READY("Press 'Calculate'"),
    BAD_FORMAT("Invalid input data format"),
    SUCCESS("Success");

    Status(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    private final String name;
}
