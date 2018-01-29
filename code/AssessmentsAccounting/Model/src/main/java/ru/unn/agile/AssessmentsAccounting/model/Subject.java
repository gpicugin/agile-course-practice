package ru.unn.agile.AssessmentsAccounting.model;

import java.util.UUID;

public class Subject {

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Subject(final UUID subjectId, final String subjectName) {
        if (subjectId == null) {
            throw new NullPointerException("Subject ID is null");
        }
        this.validateName(subjectName);
        this.id = subjectId;
        this.name = subjectName;
    }

    public void rename(final String newSubjectName) {
        this.validateName(newSubjectName);
        this.name = newSubjectName;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Subject) {
            return this.getId().equals(((Subject) object).getId());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    private UUID id;

    private String name;

    private void validateName(final String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Subject must have not empty name");
        }
    }
}
