package ru.unn.agile.AssessmentsAccounting.model;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class SubjectsTable {
    public int size() {
        return this.subjectsList.size();
    }

    public List<Subject> getSubjectsList() {
        return this.subjectsList;
    }

    public SubjectsTable() {
        this.subjectsList = new LinkedList<>();
    }

    public boolean isEmpty() {
        return this.subjectsList.isEmpty();
    }

    public void add(final String name) {
        if (this.contains(name)) {
            throw new NameIsNotUniqueException(
                    String.format("Subject with name %s already present", name));
        }
        Subject subjectToAdd = new Subject(UUID.randomUUID(), name);
        this.subjectsList.add(subjectToAdd);
    }

    public Subject find(final String subjectName) {
        for (Subject subject : this.subjectsList) {
            if (subjectName.equals(subject.getName())) {
                return subject;
            }
        }
        throw new SubjectDoesNotExistException(
                String.format("Can't find subject with name %s", subjectName));
    }

    public Subject find(final UUID subjectId) {
        for (Subject subject : this.subjectsList) {
            if (subjectId.equals(subject.getId())) {
                return subject;
            }
        }
        throw new SubjectDoesNotExistException(
                String.format("Can't find subject with ID %s", subjectId.toString()));
    }

    public boolean contains(final String subjectName) {
        for (Subject subject : this.subjectsList) {
            if (subjectName.equals(subject.getName())) {
                return true;
            }
        }
        return false;
    }

    public void renameSubject(final String oldName, final String newName) {
        Subject subjectToRename = this.find(oldName);
        if (oldName.equals(newName)) {
            return;
        }
        if (this.contains(newName)) {
            throw new NameIsNotUniqueException(
                    String.format("Subject with name %s already present", newName));
        }
        subjectToRename.rename(newName);
    }

    private List<Subject> subjectsList;
}
