package ru.unn.agile.AssessmentsAccounting.model;

import java.util.UUID;

public class AssessmentsTableRecord {

    public UUID getStudentId() {
        return this.studentId;
    }

    public UUID getSubjectId() {
        return this.subjectId;
    }

    public Assessment getAssessment() {
        return this.assessment;
    }

    public AssessmentsTableRecord(final UUID studentId, final UUID subjectId,
                                  final Assessment assessment) {
        if (studentId == null || subjectId == null || assessment == null) {
            throw new NullPointerException("Can't create assessments table record with null");
        }
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.assessment = assessment;
    }

    private UUID studentId;
    private UUID subjectId;
    private Assessment assessment;
}
