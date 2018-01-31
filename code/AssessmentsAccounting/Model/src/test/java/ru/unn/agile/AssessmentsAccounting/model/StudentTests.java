package ru.unn.agile.AssessmentsAccounting.model;

import java.util.UUID;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;


public class StudentTests {
    @Test
    public void canCreateStudentWithName() {
        UUID studentId = UUID.randomUUID();
        Student student = new Student(studentId, "Vasya");
        assertNotNull(student);
    }

    @Test(expected = IllegalArgumentException.class)
    public void canNotCreateStudentWithoutName() {
        UUID studentId = UUID.randomUUID();
        Student student = new Student(studentId, "");
    }

    @Test(expected = NullPointerException.class)
    public void canNotCreateStudentWithNullAsName() {
        UUID studentId = UUID.randomUUID();
        Student student = new Student(studentId, null);
    }

    @Test(expected = NullPointerException.class)
    public void canNotCreateStudentWithNullAsId() {
        Student student = new Student(null, "Jack");
    }

    @Test
    public void canGetStudentName() {
        UUID studentId = UUID.randomUUID();
        Student student = new Student(studentId, "Petya");
        assertEquals("Petya", student.getName());
    }

    @Test
    public void canGetStudentId() {
        UUID studentId = UUID.randomUUID();
        Student student = new Student(studentId, "Christian");
        assertEquals(studentId, student.getId());
    }

    @Test
    public void canRenameStudent() {
        UUID studentId = UUID.randomUUID();
        Student student = new Student(studentId, "John");
        student.rename("Jack");
        assertEquals("Jack", student.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void canNotRenameStudentToEmptyName() {
        UUID studentId = UUID.randomUUID();
        Student student = new Student(studentId, "Cristian");
        student.rename("");
    }

    @Test(expected = NullPointerException.class)
    public void canNotRenameStudentToNullAsName() {
        UUID studentId = UUID.randomUUID();
        Student student = new Student(studentId, "Bill");
        student.rename(null);
    }

    @Test
    public void studentsWithSameIdAreSame() {
        UUID studentId = UUID.randomUUID();
        Student studentBill = new Student(studentId, "Bill");
        Student studentPetr = new Student(studentId, "Petr");
        assertEquals(studentBill, studentPetr);
    }

    @Test
    public void studentsWithSameNamesAreNotSame() {
        UUID studentKolyaId = UUID.randomUUID();
        Student studentKolya = new Student(studentKolyaId, "Kolya");
        UUID anotherStudentKolyaId = UUID.randomUUID();
        Student anotherStudentKolya = new Student(anotherStudentKolyaId, "Kolya");
        assertNotEquals(studentKolya, anotherStudentKolya);
    }

    @Test
    public void studentsWithDifferentNamesAreNotSame() {
        UUID studentKolyaId = UUID.randomUUID();
        Student studentKolya = new Student(studentKolyaId, "Kolya");
        UUID studentBillId = UUID.randomUUID();
        Student studentBill = new Student(studentBillId, "Bill");
        assertNotEquals(studentBill, studentKolya);
    }

    @Test
    public void studentIsNotEqualsToObjectOfOtherType() {
        UUID studentId = UUID.randomUUID();
        Student student = new Student(studentId, "Bill");
        assertNotEquals("Bill", student);
    }
}
