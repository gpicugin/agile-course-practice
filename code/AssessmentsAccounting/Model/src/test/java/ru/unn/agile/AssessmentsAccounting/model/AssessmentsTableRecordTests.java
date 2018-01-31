package ru.unn.agile.AssessmentsAccounting.model;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AssessmentsTableRecordTests {
    @Test
    public void canCreateRecord() {
        AssessmentsTableRecord record = new AssessmentsTableRecord(UUID.randomUUID(),
                UUID.randomUUID(), Assessment.EXCELLENT);
        assertNotNull(record);
    }

    @Test
    public void canGetStudentId() {
        UUID studentId = UUID.randomUUID();
        AssessmentsTableRecord record = new AssessmentsTableRecord(studentId,
                UUID.randomUUID(), Assessment.GOOD);
        assertEquals(studentId, record.getStudentId());
    }

    @Test
    public void canGetSubjectId() {
        UUID subjectId = UUID.randomUUID();
        AssessmentsTableRecord record = new AssessmentsTableRecord(UUID.randomUUID(),
                subjectId, Assessment.SATISFACTORY);
        assertEquals(subjectId, record.getSubjectId());
    }

    @Test
    public void canGetAssessment() {
        AssessmentsTableRecord record = new AssessmentsTableRecord(UUID.randomUUID(),
                UUID.randomUUID(), Assessment.UNSATISFACTORY);
        assertEquals(Assessment.UNSATISFACTORY, record.getAssessment());
    }

    @Test(expected = NullPointerException.class)
    public void canNotCreateAssessmentsTableRecordWithNullStudentId() {
        AssessmentsTableRecord record = new AssessmentsTableRecord(null,
                UUID.randomUUID(), Assessment.GOOD);
    }

    @Test(expected = NullPointerException.class)
    public void canNotCreateAssessmentsTableRecordWithNullSubjectId() {
        AssessmentsTableRecord record = new AssessmentsTableRecord(UUID.randomUUID(),
                null, Assessment.GOOD);
    }

    @Test(expected = NullPointerException.class)
    public void canNotCreateAssessmentsTableRecordWithNullAssessment() {
        AssessmentsTableRecord record = new AssessmentsTableRecord(UUID.randomUUID(),
                UUID.randomUUID(), null);
    }

}
