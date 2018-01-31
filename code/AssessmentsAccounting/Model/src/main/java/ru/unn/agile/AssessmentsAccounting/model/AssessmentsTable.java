package ru.unn.agile.AssessmentsAccounting.model;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class AssessmentsTable {

    public List<AssessmentsTableRecord> getAllRecords() {
        return this.records;
    }

    public int size() {
        return this.records.size();
    }

    public AssessmentsTable() {
        this.records = new LinkedList<>();
    }

    public void add(final Student student, final Subject subject, final Assessment assessment) {
        AssessmentsTableRecord record = new AssessmentsTableRecord(student.getId(),
                subject.getId(), assessment);
        this.records.add(record);
    }

    public List<Pair<UUID, Assessment>> getAssessments(final Student student) {
        List<Pair<UUID, Assessment>> studentAssessments = new LinkedList<>();
        UUID studentId = student.getId();
        for (AssessmentsTableRecord record : this.records) {
            if (studentId.equals(record.getStudentId())) {
                studentAssessments.add(new Pair<>(record.getSubjectId(), record.getAssessment()));
            }
        }
        return studentAssessments;
    }

    public List<Pair<UUID, Assessment>> getAssessments(final Subject subject) {
        List<Pair<UUID, Assessment>> subjectAssessments = new LinkedList<>();
        UUID subjectId = subject.getId();
        for (AssessmentsTableRecord record : this.records) {
            if (subjectId.equals(record.getSubjectId())) {
                subjectAssessments.add(new Pair<>(record.getStudentId(), record.getAssessment()));
            }
        }
        return subjectAssessments;
    }

    public List<Assessment> getAssessments(final Student student, final Subject subject) {
        List<Pair<UUID, Assessment>> studentAssessments = this.getAssessments(student);
        UUID subjectId = subject.getId();
        List<Assessment> assessmentsList = new LinkedList<>();
        for (Pair<UUID, Assessment> studentAssessment : studentAssessments) {
            if (subjectId.equals(studentAssessment.getKey())) {
                assessmentsList.add(studentAssessment.getValue());
            }
        }
        return assessmentsList;
    }

    private List<AssessmentsTableRecord> records;

}
