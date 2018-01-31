package ru.unn.agile.AssessmentsAccounting.model;

public class SubjectDoesNotExistException extends IllegalArgumentException {
    public SubjectDoesNotExistException(final String message) {
        super(message);
    }
}
