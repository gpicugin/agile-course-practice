package ru.unn.agile.AssessmentsAccounting.model;

public class StudentDoesNotExistException extends IllegalArgumentException {
    public StudentDoesNotExistException(final String message) {
        super(message);
    }
}
