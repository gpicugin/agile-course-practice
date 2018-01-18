package ru.unn.agile.Polynomial.viewmodel;

public enum Operation {
    ADD("Add"),
    SUB("Sub"),
    MULTIPLY("Mul");

    Operation(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    private final String name;
}
