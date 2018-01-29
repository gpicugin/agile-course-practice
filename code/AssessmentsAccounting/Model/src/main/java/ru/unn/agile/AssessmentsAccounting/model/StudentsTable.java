package ru.unn.agile.AssessmentsAccounting.model;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class StudentsTable {

    public int size() {
        return this.studentsList.size();
    }

    public List<Student> getStudentsList() {
        return this.studentsList;
    }

    public StudentsTable() {
        this.studentsList = new LinkedList<>();
    }

    public boolean isEmpty() {
        return this.studentsList.isEmpty();
    }

    public void add(final String name) {
        if (this.contains(name)) {
            throw new NameIsNotUniqueException(
                    String.format("Student with name %s already present", name));
        }
        Student studentToAdd = new Student(UUID.randomUUID(), name);
        this.studentsList.add(studentToAdd);
    }

    public Student find(final String studentName) {
        for (Student student : this.studentsList) {
            if (studentName.equals(student.getName())) {
                return student;
            }
        }
        throw new StudentDoesNotExistException(
                String.format("Can't find student with name %s", studentName));
    }

    public Student find(final UUID studentId) {
        for (Student student : this.studentsList) {
            if (studentId.equals(student.getId())) {
                return student;
            }
        }
        throw new StudentDoesNotExistException(
                String.format("Can't find student with ID %s", studentId.toString()));
    }

    public boolean contains(final String studentName) {
        for (Student student : this.studentsList) {
            if (studentName.equals(student.getName())) {
                return true;
            }
        }
        return false;
    }

    public void renameStudent(final String oldName, final String newName) {
        Student studentToRename = this.find(oldName);
        if (oldName.equals(newName)) {
            return;
        }
        if (this.contains(newName)) {
            throw new NameIsNotUniqueException(
                    String.format("Student with name %s already present", newName));
        }
        studentToRename.rename(newName);
    }

    private List<Student> studentsList;
}
