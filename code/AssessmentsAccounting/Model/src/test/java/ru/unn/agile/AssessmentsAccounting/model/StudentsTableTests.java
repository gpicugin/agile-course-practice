package ru.unn.agile.AssessmentsAccounting.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


public class StudentsTableTests {

    @Before
    public void init() {
        this.studentsTable = new StudentsTable();
    }

    @After
    public void tearDown() {
        this.studentsTable = null;
    }

    @Test
    public void canCreateStudentsTable() {
        assertNotNull(studentsTable);
    }

    @Test
    public void whenStudentsTableIsCreatedItIsEmpty() {
        assertTrue(studentsTable.isEmpty());
    }

    @Test
    public void canGetStudentsList() {
        assertNotNull(studentsTable.getStudentsList());
    }

    @Test
    public void canAddStudentToTable() {
        studentsTable.add("Mike");
        assertFalse(studentsTable.isEmpty());
        assertEquals(1, studentsTable.size());
    }

    @Test
    public void canAddTwoStudentsToTable() {
        studentsTable.add("Bill");
        studentsTable.add("John");
        assertEquals(2, studentsTable.size());
    }

    @Test
    public void canGetStudentByName() {
        studentsTable.add("Bill");
        List<Student> students = studentsTable.getStudentsList();
        Student studentBill = students.get(0);
        Student student = studentsTable.find("Bill");
        assertEquals(studentBill, student);
    }

    @Test
    public void canGetStudentByNameFromTableWithMoreThenOneStudent() {
        studentsTable.add("Bill");
        studentsTable.add("Thomas");
        studentsTable.add("John");
        Student studentThomas = studentsTable.find("Thomas");
        assertEquals("Thomas", studentThomas.getName());
    }

    @Test(expected = StudentDoesNotExistException.class)
    public void canNotGetStudentByNameIfHeIsNotPresentInTable() {
        studentsTable.find("Bill");
    }

    @Test(expected = NameIsNotUniqueException.class)
    public void canNotAddStudentIfStudentWithThisNameAlreadyPresent() {
        studentsTable.add("Bill");
        studentsTable.add("Bill");
    }

    @Test
    public void canGetStudentById() {
        studentsTable.add("Bill");
        Student studentBill = studentsTable.find("Bill");
        Student student = studentsTable.find(studentBill.getId());
        assertEquals(studentBill, student);
    }

    @Test(expected = StudentDoesNotExistException.class)
    public void canNotGetStudentByIdIfStudentWithThisIdIsNotPresent() {
        UUID randomUUID = UUID.randomUUID();
        studentsTable.add("Bill");
        studentsTable.find(randomUUID);
    }

    @Test
    public void studentTableContainsStudentWhichWasAdded() {
        studentsTable.add("John");
        assertTrue(studentsTable.contains("John"));
    }

    @Test
    public void studentTableDoesNotContainStudentWhichWasNotAdded() {
        studentsTable.add("John");
        assertFalse(studentsTable.contains("Bill"));
    }

    @Test
    public void canRenameStudentToNewName() {
        studentsTable.add("Bill");
        Student studentBill = studentsTable.find("Bill");
        studentsTable.renameStudent("Bill", "John");
        Student studentJohn = studentsTable.find("John");
        assertEquals(studentBill.getId(), studentJohn.getId());
    }

    @Test(expected = StudentDoesNotExistException.class)
    public void canNotRenameNonexistentStudent() {
        studentsTable.add("Cris");
        studentsTable.renameStudent("Bill", "John");
    }

    @Test(expected = NameIsNotUniqueException.class)
    public void canNotRenameStudentToNotUniqueName() {
        studentsTable.add("Cris");
        studentsTable.add("Bill");
        studentsTable.renameStudent("Bill", "Cris");
    }

    @Test
    public void canRenameStudentToOldName() {
        studentsTable.add("Cris");
        Student studentCris = studentsTable.find("Cris");
        studentsTable.renameStudent("Cris", "Cris");
        Student student = studentsTable.find("Cris");
        assertEquals(studentCris, student);
    }

    private StudentsTable studentsTable;
}
