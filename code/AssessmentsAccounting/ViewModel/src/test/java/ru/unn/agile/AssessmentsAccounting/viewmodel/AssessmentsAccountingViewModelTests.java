package ru.unn.agile.AssessmentsAccounting.viewmodel;

import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.AssessmentsAccounting.model.Assessment;
import ru.unn.agile.AssessmentsAccounting.model.Student;
import ru.unn.agile.AssessmentsAccounting.model.Subject;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class AssessmentsAccountingViewModelTests {
    public void setAnotherlViewModel(final AssessmentsAccountingViewModel anotherViewModel) {
        this.viewModel = anotherViewModel;
    }

    @Before
    public void setUp() {
        viewModel = new AssessmentsAccountingViewModel(new FakeLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canCreateViewModel() {
        assertNotNull(viewModel);
    }

    @Test
    public void canSetDefaultValues() {
        assertFalse(viewModel.isErrorMessageShown());
        assertEquals(0, viewModel.getStudents().size());
        assertEquals(0, viewModel.getSubjects().size());
        assertEquals("", viewModel.getErrorMessage());
        assertEquals("", viewModel.newStudentNameProperty().get());
        assertEquals("", viewModel.newSubjectNameProperty().get());
        assertEquals("", viewModel.studentToAddProperty().get());
        assertEquals("", viewModel.subjectToAddProperty().get());
        assertEquals(0, viewModel.getLog().size());
    }

    @Test
    public void canAddNewStudent() {
        viewModel.studentToAddProperty().set("Tom");
        viewModel.addStudent();
        ObservableList<Student> students = viewModel.getStudents();
        assertEquals(1, students.size());
        assertEquals("Tom", students.get(0).getName());
    }

    @Test
    public void canNotCreateStudentWithoutName() {
        viewModel.addStudent();
        ObservableList<Student> students = viewModel.getStudents();
        assertEquals(0, students.size());
        assertTrue(viewModel.isErrorMessageShown());
    }

    @Test
    public void canResetError() {
        viewModel.addStudent();
        assertTrue(viewModel.isErrorMessageShown());
        assertNotEquals("", viewModel.getErrorMessage());
        viewModel.resetError();
        assertFalse(viewModel.isErrorMessageShown());
        assertEquals("", viewModel.getErrorMessage());
    }

    @Test
    public void canNotCreateStudentWithNotUniqueName() {
        viewModel.studentToAddProperty().set("Tom");
        viewModel.addStudent();
        viewModel.studentToAddProperty().set("Tom");
        viewModel.addStudent();
        ObservableList<Student> students = viewModel.getStudents();
        assertEquals(1, students.size());
        assertTrue(viewModel.isErrorMessageShown());
    }

    @Test
    public void canAddNewSubject() {
        viewModel.subjectToAddProperty().set("Math");
        viewModel.addSubject();
        ObservableList<Subject> subjects = viewModel.getSubjects();
        assertEquals(1, subjects.size());
        assertEquals("Math", subjects.get(0).getName());
    }

    @Test
    public void canNotCreateSubjectWithoutName() {
        viewModel.addSubject();
        ObservableList<Subject> subjects = viewModel.getSubjects();
        assertEquals(0, subjects.size());
        assertTrue(viewModel.isErrorMessageShown());
    }

    @Test
    public void canNotCreateSubjectWithNotUniqueName() {
        viewModel.subjectToAddProperty().set("Math");
        viewModel.addSubject();
        viewModel.subjectToAddProperty().set("Math");
        viewModel.addSubject();
        ObservableList<Subject> subjects = viewModel.getSubjects();
        assertEquals(1, subjects.size());
        assertTrue(viewModel.isErrorMessageShown());
    }

    @Test
    @SuppressWarnings(value = "unchecked")
    public void canRenameStudent() {
        addStudent("Tom");
        Student student = getStudentAtIndex(0);
        viewModel.newStudentNameProperty().set("Mark");
        viewModel.studentToRenameProperty().set(student);
        viewModel.renameStudent();
        assertEquals("Mark", student.getName());
    }

    @Test
    @SuppressWarnings(value = "unchecked")
    public void canRenameSubject() {
        addSubject("Math");
        Subject subject = getSubjectAtIndex(0);
        viewModel.newSubjectNameProperty().set("Algebra");
        viewModel.subjectToRenameProperty().set(subject);
        viewModel.renameSubject();
        assertEquals("Algebra", subject.getName());
    }

    @Test
    @SuppressWarnings(value = "unchecked")
    public void canNotRenameStudentToEmptyString() {
        addStudent("Tom");
        Student student = getStudentAtIndex(0);
        viewModel.newStudentNameProperty().set("");
        viewModel.studentToRenameProperty().set(student);
        viewModel.renameStudent();
        assertTrue(viewModel.isErrorMessageShown());
    }

    @Test
    @SuppressWarnings(value = "unchecked")
    public void canNotRenameNullStudent() {
        viewModel.newStudentNameProperty().set("Pete");
        viewModel.studentToRenameProperty().set(null);
        viewModel.renameStudent();
        assertTrue(viewModel.isErrorMessageShown());
    }

    @Test
    @SuppressWarnings(value = "unchecked")
    public void canNotRenameSubjectToEmptyString() {
        addSubject("Physics");
        Subject subject = getSubjectAtIndex(0);
        viewModel.newSubjectNameProperty().set("");
        viewModel.subjectToRenameProperty().set(subject);
        viewModel.renameSubject();
        assertTrue(viewModel.isErrorMessageShown());
    }

    @Test
    @SuppressWarnings(value = "unchecked")
    public void canNotRenameNullSubject() {
        viewModel.newSubjectNameProperty().set("Physics");
        viewModel.subjectToRenameProperty().set(null);
        viewModel.renameSubject();
        assertTrue(viewModel.isErrorMessageShown());
    }

    @Test
    @SuppressWarnings(value = "unchecked")
    public void canNotRenameStudentToNotUniqueName() {
        addStudent("Tom");
        addStudent("Mark");
        Student student = getStudentAtIndex(0);
        String newName = getStudentAtIndex(1).getName();
        viewModel.newStudentNameProperty().set(newName);
        viewModel.studentToRenameProperty().set(student);
        viewModel.renameStudent();
        assertTrue(viewModel.isErrorMessageShown());
    }

    @Test
    @SuppressWarnings(value = "unchecked")
    public void canNotRenameSubjectToNotUniqueName() {
        addSubject("Math");
        addSubject("History");
        Subject subject = getSubjectAtIndex(0);
        String newName = getSubjectAtIndex(1).getName();
        viewModel.newSubjectNameProperty().set(newName);
        viewModel.subjectToRenameProperty().set(subject);
        viewModel.renameSubject();
        assertTrue(viewModel.isErrorMessageShown());
    }

    @Test
    public void canAddAssessment() {
        addStudent("Mark");
        addSubject("History");
        Student student = getStudentAtIndex(0);
        Subject subject = getSubjectAtIndex(0);

        viewModel.studentToAssessProperty().set(student);
        viewModel.subjectToAssessProperty().set(subject);
        viewModel.assessmentProperty().set(Assessment.GOOD);
        viewModel.addAssessment();
        assertEquals("[4]", viewModel.studentAssessmentsProperty().get());
        assertEquals("4.0", viewModel.studentAverageAssessmentProperty().get());
        assertEquals("4.0", viewModel.subjectAverageAssessmentProperty().get());
    }

    @Test
    public void canNotAddNullAssessment() {
        addStudent("Pete");
        addSubject("History");
        Student student = getStudentAtIndex(0);
        Subject subject = getSubjectAtIndex(0);

        viewModel.studentToAssessProperty().set(student);
        viewModel.subjectToAssessProperty().set(subject);
        viewModel.assessmentProperty().set(null);
        viewModel.addAssessment();
        assertTrue(viewModel.isErrorMessageShown());
    }

    @Test
    public void canNotAddAssessmentToNullStudent() {
        addSubject("Math");
        Subject subject = getSubjectAtIndex(0);

        viewModel.studentToAssessProperty().set(null);
        viewModel.subjectToAssessProperty().set(subject);
        viewModel.assessmentProperty().set(Assessment.SATISFACTORY);
        viewModel.addAssessment();
        assertTrue(viewModel.isErrorMessageShown());
    }

    @Test
    public void canNotAddAssessmentToNullSubject() {
        addStudent("John");
        Student student = getStudentAtIndex(0);

        viewModel.studentToAssessProperty().set(student);
        viewModel.subjectToAssessProperty().set(null);
        viewModel.assessmentProperty().set(Assessment.EXCELLENT);
        viewModel.addAssessment();
        assertTrue(viewModel.isErrorMessageShown());
    }

    @Test
    public void canLogAddingStudent() {
        addStudent("Tom");
        List<String> currentLog = viewModel.getLog();
        assertEquals(1, currentLog.size());
        assertTrue(currentLog.get(0).contains("Event"));
        assertTrue(currentLog.get(0).contains("Tom"));
    }

    @Test
    public void canLogErrorForAddingEmptyStudentName() {
        addStudent("");
        List<String> currentLog = viewModel.getLog();
        assertEquals(1, currentLog.size());
        assertFalse(currentLog.get(0).contains("Event"));
        assertTrue(currentLog.get(0).contains("Error"));
    }

    @Test
    public void canLogAddingSubject() {
        addSubject("Math");
        List<String> currentLog = viewModel.getLog();
        assertEquals(1, currentLog.size());
        assertTrue(currentLog.get(0).contains("Event"));
        assertTrue(currentLog.get(0).contains("Math"));
    }

    @Test
    @SuppressWarnings(value = "unchecked")
    public void canLogRenamingStudent() {
        addStudent("John");
        Student student = getStudentAtIndex(0);
        viewModel.newStudentNameProperty().set("Ivan");
        viewModel.studentToRenameProperty().set(student);
        viewModel.renameStudent();
        List<String> currentLog = viewModel.getLog();
        assertEquals(2, currentLog.size());
        assertTrue(currentLog.get(1).contains("John"));
        assertTrue(currentLog.get(1).contains("Ivan"));
    }

    @Test
    @SuppressWarnings(value = "unchecked")
    public void canLogRenamingSubject() {
        addSubject("Math");
        Subject subject = getSubjectAtIndex(0);
        viewModel.newSubjectNameProperty().set("Algebra");
        viewModel.subjectToRenameProperty().set(subject);
        viewModel.renameSubject();
        List<String> currentLog = viewModel.getLog();
        assertEquals(2, currentLog.size());
        assertTrue(currentLog.get(1).contains("Math"));
        assertTrue(currentLog.get(1).contains("Algebra"));
    }

    @Test
    @SuppressWarnings(value = "unchecked")
    public void canLogErrorRenamingNullStudent() {
        viewModel.newStudentNameProperty().set("Ivan");
        viewModel.studentToRenameProperty().set(null);
        viewModel.renameStudent();
        List<String> currentLog = viewModel.getLog();
        assertEquals(1, currentLog.size());
        assertTrue(currentLog.get(0).contains("Error"));
    }

    @Test
    @SuppressWarnings(value = "unchecked")
    public void canLogErrorRenamingStudentToEmptyName() {
        addStudent("John");
        Student student = getStudentAtIndex(0);
        viewModel.newStudentNameProperty().set("");
        viewModel.studentToRenameProperty().set(student);
        viewModel.renameStudent();
        List<String> currentLog = viewModel.getLog();
        assertEquals(2, currentLog.size());
        assertTrue(currentLog.get(1).contains("Error"));
    }

    @Test
    @SuppressWarnings(value = "unchecked")
    public void canLogErrorRenamingNullSubject() {
        viewModel.newSubjectNameProperty().set("Math");
        viewModel.subjectToRenameProperty().set(null);
        viewModel.renameSubject();
        List<String> currentLog = viewModel.getLog();
        assertEquals(1, currentLog.size());
        assertTrue(currentLog.get(0).contains("Error"));
    }

    @Test
    @SuppressWarnings(value = "unchecked")
    public void canLogErrorRenamingSubjectToEmptyName() {
        addSubject("Math");
        Subject subject = getSubjectAtIndex(0);
        viewModel.newStudentNameProperty().set("");
        viewModel.subjectToRenameProperty().set(subject);
        viewModel.renameSubject();
        List<String> currentLog = viewModel.getLog();
        assertEquals(2, currentLog.size());
        assertTrue(currentLog.get(1).contains("Error"));
    }

    @Test
    public void canLogAddingAssessment() {
        addSubject("Math");
        addStudent("Mark");
        Student student = getStudentAtIndex(0);
        Subject subject = getSubjectAtIndex(0);

        viewModel.studentToAssessProperty().set(student);
        viewModel.subjectToAssessProperty().set(subject);
        viewModel.assessmentProperty().set(Assessment.EXCELLENT);
        viewModel.addAssessment();
        List<String> currentLog = viewModel.getLog();
        assertEquals(3, currentLog.size());
        assertTrue(currentLog.get(2).contains("Event"));
        assertTrue(currentLog.get(2).contains("Math"));
        assertTrue(currentLog.get(2).contains("Mark"));
        assertTrue(currentLog.get(2).contains("5"));

    }

    @Test
    public void canLogErrorAddingAssessmentToNullStudent() {
        addSubject("Math");
        Subject subject = getSubjectAtIndex(0);

        viewModel.studentToAssessProperty().set(null);
        viewModel.subjectToAssessProperty().set(subject);
        viewModel.assessmentProperty().set(Assessment.GOOD);
        viewModel.addAssessment();
        List<String> currentLog = viewModel.getLog();
        assertEquals(2, currentLog.size());
        assertTrue(currentLog.get(1).contains("Error"));
        assertTrue(currentLog.get(1).contains("student"));
    }

    @Test
    public void canLogErrorAddingAssessmentToNullSubject() {
        addStudent("Mark");
        Student student = getStudentAtIndex(0);

        viewModel.studentToAssessProperty().set(student);
        viewModel.subjectToAssessProperty().set(null);
        viewModel.assessmentProperty().set(Assessment.SATISFACTORY);
        viewModel.addAssessment();
        List<String> currentLog = viewModel.getLog();
        assertEquals(2, currentLog.size());
        assertTrue(currentLog.get(1).contains("subject"));
        assertTrue(currentLog.get(1).contains("Error"));
    }

    @Test
    public void canLogErrorAddingAssessmentNull() {
        addStudent("Mark");
        addSubject("Math");
        Student student = getStudentAtIndex(0);
        Subject subject = getSubjectAtIndex(0);

        viewModel.subjectToAssessProperty().set(subject);
        viewModel.studentToAssessProperty().set(student);
        viewModel.assessmentProperty().set(null);
        viewModel.addAssessment();
        List<String> currentLog = viewModel.getLog();
        assertEquals(3, currentLog.size());
        assertTrue(currentLog.get(2).contains("Error"));
        assertTrue(currentLog.get(2).contains("assessment"));
    }

    private AssessmentsAccountingViewModel viewModel;

    private void addStudent(final String studentName) {
        viewModel.studentToAddProperty().set(studentName);
        viewModel.addStudent();
    }

    private void addSubject(final String subjectName) {
        viewModel.subjectToAddProperty().set(subjectName);
        viewModel.addSubject();
    }

    private Student getStudentAtIndex(final int index) {
        return viewModel.getStudents().get(index);
    }

    private Subject getSubjectAtIndex(final int index) {
        return viewModel.getSubjects().get(index);
    }

}
