package ru.unn.agile.AssessmentsAccounting.model;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;

public class SubjectTests {
    @Test
    public void canCreateSubjectWithName() {
        UUID subjectId = UUID.randomUUID();
        Subject subject = new Subject(subjectId, "Math");
        assertNotNull(subject);
    }

    @Test(expected = IllegalArgumentException.class)
    public void canNotCreateSubjectWithoutName() {
        UUID subjectId = UUID.randomUUID();
        Subject subject = new Subject(subjectId, "");
    }

    @Test(expected = NullPointerException.class)
    public void canNotCreateSubjectWithNullAsName() {
        UUID subjectId = UUID.randomUUID();
        Subject subject = new Subject(subjectId, null);
    }

    @Test
    public void canGetSubjectName() {
        UUID subjectId = UUID.randomUUID();
        Subject subject = new Subject(subjectId, "Math");
        assertEquals("Math", subject.getName());
    }

    @Test
    public void canGetSubjectId() {
        UUID subjectId = UUID.randomUUID();
        Subject subject = new Subject(subjectId, "Algebra");
        assertEquals(subjectId, subject.getId());
    }

    @Test
    public void canRenameSubject() {
        UUID subjectId = UUID.randomUUID();
        Subject subject = new Subject(subjectId, "Math");
        subject.rename("Algebra");
        assertEquals("Algebra", subject.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void canNotRenameSubjectToEmptyName() {
        UUID subjectId = UUID.randomUUID();
        Subject subject = new Subject(subjectId, "History");
        subject.rename("");
    }

    @Test(expected = NullPointerException.class)
    public void canNotRenameSubjectToNullAsName() {
        UUID subjectId = UUID.randomUUID();
        Subject subject = new Subject(subjectId, "History");
        subject.rename(null);
    }

    @Test
    public void subjectsWithSameIdAreSame() {
        UUID subjectId = UUID.randomUUID();
        Subject subjectMath = new Subject(subjectId, "Math");
        Subject subjectAlgebra = new Subject(subjectId, "Algebra");
        assertEquals(subjectMath, subjectAlgebra);
    }

    @Test
    public void subjectsWithSameNamesAreNotSame() {
        UUID subjectHistoryId = UUID.randomUUID();
        Subject subjectHistory = new Subject(subjectHistoryId, "History");
        UUID anotherSubjectHistoryId = UUID.randomUUID();
        Subject anotherSubjectHistory = new Subject(anotherSubjectHistoryId, "History");
        assertNotEquals(subjectHistory, anotherSubjectHistory);
    }

    @Test
    public void subjectsWithDifferentNamesAreNotSame() {
        UUID subjectMathId = UUID.randomUUID();
        Subject subjectMath = new Subject(subjectMathId, "Math");
        UUID subjectHistoryId = UUID.randomUUID();
        Subject subjectHistory = new Subject(subjectHistoryId, "History");
        assertNotEquals(subjectHistory, subjectMath);
    }

    @Test
    public void subjectIsNotEqualsToObjectOfOtherType() {
        UUID subjectId = UUID.randomUUID();
        Subject subject = new Subject(subjectId, "Algebra");
        assertNotEquals("Algebra", subject);
    }
}
