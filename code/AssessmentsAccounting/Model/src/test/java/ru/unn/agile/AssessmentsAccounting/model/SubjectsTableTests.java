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


public class SubjectsTableTests {

    @Before
    public void init() {
        this.subjectsTable = new SubjectsTable();
    }

    @After
    public void tearDown() {
        this.subjectsTable = null;
    }

    @Test
    public void canCreateSubjectsTable() {
        assertNotNull(subjectsTable);
    }

    @Test
    public void whenSubjectsTableIsCreatedItIsEmpty() {
        assertTrue(subjectsTable.isEmpty());
    }

    @Test
    public void canGetSubjectsList() {
        assertNotNull(subjectsTable.getSubjectsList());
    }

    @Test
    public void canAddSubjectToTable() {
        subjectsTable.add("History");
        assertFalse(subjectsTable.isEmpty());
        assertEquals(1, subjectsTable.size());
    }

    @Test
    public void canAddTwoSubjectsToTable() {
        subjectsTable.add("Math");
        subjectsTable.add("Algebra");
        assertEquals(2, subjectsTable.size());
    }

    @Test
    public void canGetSubjectByName() {
        subjectsTable.add("Math");
        List<Subject> subjects = subjectsTable.getSubjectsList();
        Subject subjectMath = subjects.get(0);
        Subject subject = subjectsTable.find("Math");
        assertEquals(subjectMath, subject);
    }

    @Test
    public void canGetSubjectByNameFromTableWithMoreThenOneSubject() {
        subjectsTable.add("Math");
        subjectsTable.add("Physics");
        subjectsTable.add("Algebra");
        Subject subjectPhysics = subjectsTable.find("Physics");
        assertEquals("Physics", subjectPhysics.getName());
    }

    @Test(expected = SubjectDoesNotExistException.class)
    public void canNotGetSubjectByNameIfHeIsNotPresentInTable() {
        subjectsTable.find("Math");
    }

    @Test(expected = NameIsNotUniqueException.class)
    public void canNotAddSubjectIfSubjectWithThisNameAlreadyPresent() {
        subjectsTable.add("Math");
        subjectsTable.add("Math");
    }

    @Test
    public void canGetSubjectById() {
        subjectsTable.add("Math");
        Subject subjectMath = subjectsTable.find("Math");
        Subject subject = subjectsTable.find(subjectMath.getId());
        assertEquals(subjectMath, subject);
    }

    @Test(expected = SubjectDoesNotExistException.class)
    public void canNotGetSubjectByIdIfSubjectWithThisIdIsNotPresent() {
        UUID randomUUID = UUID.randomUUID();
        subjectsTable.add("Math");
        subjectsTable.find(randomUUID);
    }

    @Test
    public void subjectTableContainsSubjectWhichWasAdded() {
        subjectsTable.add("Algebra");
        assertTrue(subjectsTable.contains("Algebra"));
    }

    @Test
    public void subjectTableDoesNotContainSubjectWhichWasNotAdded() {
        subjectsTable.add("Algebra");
        assertFalse(subjectsTable.contains("Math"));
    }

    @Test
    public void canRenameSubjectToNewName() {
        subjectsTable.add("Math");
        Subject subjectMath = subjectsTable.find("Math");
        subjectsTable.renameSubject("Math", "Algebra");
        Subject subjectAlgebra = subjectsTable.find("Algebra");
        assertEquals(subjectMath.getId(), subjectAlgebra.getId());
    }

    @Test(expected = SubjectDoesNotExistException.class)
    public void canNotRenameNonexistentSubject() {
        subjectsTable.add("Biology");
        subjectsTable.renameSubject("Math", "Algebra");
    }

    @Test(expected = NameIsNotUniqueException.class)
    public void canNotRenameSubjectToNotUniqueName() {
        subjectsTable.add("Biology");
        subjectsTable.add("Math");
        subjectsTable.renameSubject("Math", "Biology");
    }

    @Test
    public void canRenameSubjectToOldName() {
        subjectsTable.add("Biology");
        Subject subjectBiology = subjectsTable.find("Biology");
        subjectsTable.renameSubject("Biology", "Biology");
        Subject subject = subjectsTable.find("Biology");
        assertEquals(subjectBiology, subject);
    }

    private SubjectsTable subjectsTable;
}
