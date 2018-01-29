package ru.unn.agile.AssessmentsAccounting.model;

import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

public class AssessmentsTableTests {

    @Before
    public void init() {
        this.assessmentsTable = new AssessmentsTable();
    }

    @After
    public void tearDown() {
        this.assessmentsTable = null;
    }

    @Test
    public void canCreateAssessmentsTable() {
        assertNotNull(assessmentsTable);
    }

    @Test
    public void canGetListOfAssessmentsRecords() {
        assertNotNull(assessmentsTable.getAllRecords());
    }

    @Test
    public void canAddNewRecord() {
        Student studentMile = new Student(UUID.randomUUID(), "Mile");
        Subject subjectMath = new Subject(UUID.randomUUID(), "Math");
        assessmentsTable.add(studentMile, subjectMath, Assessment.GOOD);
        assertEquals(1, assessmentsTable.size());
    }

    @Test(expected = NullPointerException.class)
    public void canNotAddRecordIfStudentNull() {
        Subject subjectMath = new Subject(UUID.randomUUID(), "Math");
        assessmentsTable.add(null, subjectMath, Assessment.GOOD);
    }

    @Test(expected = NullPointerException.class)
    public void canNotAddRecordIfSubjectIsNull() {
        Student studentMike = new Student(UUID.randomUUID(), "Mike");
        assessmentsTable.add(studentMike, null, Assessment.GOOD);
    }

    @Test(expected = NullPointerException.class)
    public void canNotAddRecordIfAssessmentIsNull() {
        Student studentMike = new Student(UUID.randomUUID(), "Mike");
        Subject subjectMath = new Subject(UUID.randomUUID(), "Math");
        assessmentsTable.add(studentMike, subjectMath, null);
    }

    @Test
    public void listOfAssessmentsIsEmptyIfStudentHasNotAnyAssessment() {
        Student studentMike = new Student(UUID.randomUUID(), "Mike");
        List<Pair<UUID, Assessment>> mikeAssessments =
                assessmentsTable.getAssessments(studentMike);
        assertTrue(mikeAssessments.isEmpty());
    }

    @Test
    public void canGetListOfAssessmentsForStudent() {
        Student studentMike = new Student(UUID.randomUUID(), "Mike");
        Subject subjectMath = new Subject(UUID.randomUUID(), "Math");
        assessmentsTable.add(studentMike, subjectMath, Assessment.SATISFACTORY);
        Pair<UUID, Assessment> mikeAssessmentForMath = new Pair<>(subjectMath.getId(),
                Assessment.SATISFACTORY);
        List<Pair<UUID, Assessment>> mikeAssessments = assessmentsTable.getAssessments(studentMike);
        assertEquals(1, mikeAssessments.size());
        assertEquals(mikeAssessmentForMath, mikeAssessments.get(0));
    }

    @Test
    public void assessmentsListForStudentContainsAssessmentsFromAllSubjects() {
        Student studentMike = new Student(UUID.randomUUID(), "Mike");
        Subject subjectMath = new Subject(UUID.randomUUID(), "Math");
        Subject subjectHistory = new Subject(UUID.randomUUID(), "History");
        assessmentsTable.add(studentMike, subjectMath, Assessment.SATISFACTORY);
        assessmentsTable.add(studentMike, subjectMath, Assessment.GOOD);
        assessmentsTable.add(studentMike, subjectHistory, Assessment.GOOD);
        List<Pair<UUID, Assessment>> mikeAssessments = assessmentsTable.getAssessments(studentMike);
        assertEquals(3, mikeAssessments.size());
    }

    @Test
    public void canGetListOfAssessmentsForSubject() {
        Student studentBill = new Student(UUID.randomUUID(), "Bill");
        Subject subjectMath = new Subject(UUID.randomUUID(), "Math");
        assessmentsTable.add(studentBill, subjectMath, Assessment.EXCELLENT);
        Pair<UUID, Assessment> billAssessmentForMath = new Pair<>(studentBill.getId(),
                Assessment.EXCELLENT);
        List<Pair<UUID, Assessment>> mathAssessments = assessmentsTable.getAssessments(subjectMath);
        assertEquals(1, mathAssessments.size());
        assertEquals(billAssessmentForMath, mathAssessments.get(0));
    }

    @Test
    public void listOfAssessmentsForSubjectContainsAssessmentsOfAllStudents() {
        Student studentMike = new Student(UUID.randomUUID(), "Mike");
        Student studentBill = new Student(UUID.randomUUID(), "Bill");
        Student studentJack = new Student(UUID.randomUUID(), "Jack");
        Subject subjectMath = new Subject(UUID.randomUUID(), "Math");
        assessmentsTable.add(studentMike, subjectMath, Assessment.SATISFACTORY);
        assessmentsTable.add(studentBill, subjectMath, Assessment.EXCELLENT);
        assessmentsTable.add(studentJack, subjectMath, Assessment.GOOD);
        List<Pair<UUID, Assessment>> mathAssessments = assessmentsTable.getAssessments(subjectMath);
        assertEquals(3, mathAssessments.size());
    }

    @Test
    public void canGetAssessmentsListForStudentAndSubject() {
        Student studentMike = new Student(UUID.randomUUID(), "Mike");
        Subject subjectMath = new Subject(UUID.randomUUID(), "Math");
        assessmentsTable.add(studentMike, subjectMath, Assessment.GOOD);
        assessmentsTable.add(studentMike, subjectMath, Assessment.EXCELLENT);
        List<Assessment> mikeAssessmentForMath =
                assessmentsTable.getAssessments(studentMike, subjectMath);
        assertEquals(2, mikeAssessmentForMath.size());
        assertTrue(mikeAssessmentForMath.contains(Assessment.GOOD));
        assertTrue(mikeAssessmentForMath.contains(Assessment.EXCELLENT));
    }

    private AssessmentsTable assessmentsTable;

}
