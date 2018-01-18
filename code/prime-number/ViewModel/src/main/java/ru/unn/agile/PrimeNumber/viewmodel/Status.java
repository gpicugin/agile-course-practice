package ru.unn.agile.PrimeNumber.viewmodel;

public enum Status {
    WAITING("Please provide input data"),
    READY("Press 'Find' or Enter to find prime numbers"),
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

