package ru.unn.agile.AssessmentsAccounting.model;

public class NameIsNotUniqueException extends IllegalArgumentException {
    public NameIsNotUniqueException(final String message) {
        super(message);
    }
}
