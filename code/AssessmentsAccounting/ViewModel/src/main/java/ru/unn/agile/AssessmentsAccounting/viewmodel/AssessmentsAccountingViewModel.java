package ru.unn.agile.AssessmentsAccounting.viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import ru.unn.agile.AssessmentsAccounting.model.*;

import java.util.List;
import java.util.UUID;


public class AssessmentsAccountingViewModel {

    public boolean isErrorMessageShown() {
        return errorMessageIsShown.get();
    }

    public BooleanProperty errorMessageIsShownProperty() {
        return this.errorMessageIsShown;
    }

    public StringProperty studentToAddProperty() {
        return studentToAdd;
    }

    public StringProperty subjectToAddProperty() {
        return subjectToAdd;
    }

    public ObjectProperty studentToRenameProperty() {
        return studentToRename;
    }

    public StringProperty newStudentNameProperty() {
        return newStudentName;
    }

    public ObjectProperty subjectToRenameProperty() {
        return subjectToRename;
    }

    public StringProperty newSubjectNameProperty() {
        return newSubjectName;
    }

    public ObservableList<Student> getStudents() {
        return students.get();
    }

    public ObservableList<Subject> getSubjects() {
        return subjects.get();
    }

    public ObjectProperty<ObservableList<Assessment>> assessmentsProperty() {
        return assessments;
    }

    public ObjectProperty<Student> studentToAssessProperty() {
        return studentToAssess;
    }

    public ObjectProperty<Subject> subjectToAssessProperty() {
        return subjectToAssess;
    }

    public ObjectProperty<Assessment> assessmentProperty() {
        return assessment;
    }

    public StringProperty studentAssessmentsProperty() {
        return studentAssessments;
    }

    public StringProperty studentAverageAssessmentProperty() {
        return studentAverageAssessment;
    }

    public StringProperty subjectAverageAssessmentProperty() {
        return subjectAverageAssessment;
    }

    public StringProperty getLogsProperty() {
        return assesmentLogs;
    }

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null!");
        }
        this.logger = logger;
    }

    public final List<String> getLog() {
        return logger.getLog();
    }

    public AssessmentsAccountingViewModel(final ILogger logger) {
        setLogger(logger);
        init();
    }

    public void resetError() {
        this.errorMessageText.set("");
        this.errorMessageIsShown.set(false);
    }

    public String getErrorMessage() {
        return errorMessageText.get();
    }

    public void addStudent() {
        try {
            final String newStudentName = this.studentToAdd.get();
            this.studentsTable.add(newStudentName);
            this.updateStudentsList();
            updateLoggerInfo(createStudentAddingLog(newStudentName));
        } catch (IllegalArgumentException exception) {
            prepareForError(exception.getMessage());
        }

    }

    public void addSubject() {
        try {
            final String newSubjectName = this.subjectToAdd.get();
            this.subjectsTable.add(newSubjectName);
            this.updateSubjectsList();
            updateLoggerInfo(createSubjectAddingLog(newSubjectName));
        } catch (IllegalArgumentException exception) {
            prepareForError(exception.getMessage());
        }
    }

    public void renameStudent() {
        try {
            final Student studentToRename = this.studentToRename.get();
            if (studentToRename == null) {
                prepareForError("You should select student for renaming!");
                return;
            }
            final String newName = this.newStudentName.get();
            final String oldName = studentToRename.getName();
            this.studentsTable.renameStudent(oldName, newName);
            this.updateStudentsList();
            updateLoggerInfo(createStudentRenameLog(oldName, newName));
        } catch (IllegalArgumentException exception) {
            prepareForError(exception.getMessage());
        }
    }

    public void renameSubject() {
        try {
            final Subject subjectToRename = this.subjectToRename.get();
            if (subjectToRename == null) {
                prepareForError("You should select subject for renaming!");
                return;
            }
            final String newName = this.newSubjectName.get();
            final String oldName = subjectToRename.getName();
            this.subjectsTable.renameSubject(oldName, newName);
            this.updateSubjectsList();
            updateLoggerInfo(createSubjectRenameLog(oldName, newName));
        } catch (IllegalArgumentException exception) {
            prepareForError(exception.getMessage());
        }
    }

    public void addAssessment() {
        try {
            String item = null;
            final Student student = this.studentToAssess.get();
            final Subject subject = this.subjectToAssess.get();
            final Assessment assessment = this.assessment.get();

            if (student == null) {
                item = "student";
            } else if (subject == null) {
                item = "subject";
            } else if (assessment == null) {
                item = "assessment";
            }
            if (item != null) {
                prepareForError("You should select " + item + "!");
                return;
            }
            this.assessmentsTable.add(student, subject, assessment);
            this.updateAssessmentsInformation(student, subject);
            updateLoggerInfo(createAssessmentAddingLog(
                    student.getName(), subject.getName(), assessment.toString()));
        } catch (Exception exc) {
            prepareForError("Error! Check input values!");
        }
    }

    private void init() {
        this.errorMessageText.set("");
        this.errorMessageIsShown.set(false);
        this.studentToAdd.set("");
        this.subjectToAdd.set("");
        this.newStudentName.set("");
        this.newSubjectName.set("");
        this.assesmentLogs.set("");
        this.studentToAssess.addListener((observable, previousStudent, currentStudent) -> {
            this.updateAssessmentsInformation(currentStudent, this.subjectToAssess.get());
        });
        this.subjectToAssess.addListener((observable, previousSubject, currentSubject) -> {
            this.updateAssessmentsInformation(this.studentToAssess.get(), currentSubject);
        });
    }

    private void updateAssessmentsInformation(final Student student, final Subject subject) {
        if (student != null && subject != null) {
            this.updateStudentAssessmentsInformation(student, subject);
            this.updateSubjectAssessmentsInformation(subject);
        }
    }

    private void updateStudentAssessmentsInformation(final Student student, final Subject subject) {
        List<Assessment> studentAssessments = this.assessmentsTable.getAssessments(student,
                subject);
        this.studentAssessments.set(studentAssessments.toString());
        if (studentAssessments.isEmpty()) {
            this.studentAverageAssessment.set("0");
        } else {
            long assessmentsPoints = 0;
            for (Assessment assessment : studentAssessments) {
                assessmentsPoints += assessment.getAssessment();
            }
            final long assessmentsCount = studentAssessments.size();
            final float averageStudentAssessment = (float) assessmentsPoints / assessmentsCount;
            this.studentAverageAssessment.set(Float.toString(averageStudentAssessment));
        }

    }

    private void updateSubjectAssessmentsInformation(final Subject subject) {
        List<Pair<UUID, Assessment>> subjectAssessments = this.assessmentsTable.getAssessments(
                subject);
        if (subjectAssessments.isEmpty()) {
            this.subjectAverageAssessment.set("0");
        } else {
            long assessmentsPoints = 0;
            for (Pair<UUID, Assessment> assessmentPair : subjectAssessments) {
                Assessment assessment = assessmentPair.getValue();
                assessmentsPoints += assessment.getAssessment();
            }
            final long assessmentsCount = subjectAssessments.size();
            final float averageSubjectAssessment = (float) assessmentsPoints / assessmentsCount;
            this.subjectAverageAssessment.set(Float.toString(averageSubjectAssessment));
        }
    }

    private void updateStudentsList() {
        this.students.set(FXCollections.observableArrayList(this.studentsList()));
    }

    private void updateSubjectsList() {
        this.subjects.set(FXCollections.observableArrayList(this.subjectsList()));
    }

    private List<Student> studentsList() {
        return this.studentsTable.getStudentsList();
    }

    private List<Subject> subjectsList() {
        return this.subjectsTable.getSubjectsList();
    }

    private StudentsTable studentsTable = new StudentsTable();
    private SubjectsTable subjectsTable = new SubjectsTable();
    private AssessmentsTable assessmentsTable = new AssessmentsTable();

    private void prepareForError(final String message) {
        updateLoggerInfo(createErrorLog(message));
        errorMessageText.set(message);
        errorMessageIsShown.set(true);
    }

    private String createErrorLog(final String errorMessage) {
        return "Error:\t" + errorMessage;
    }

    private String createStudentRenameLog(final String oldName, final String newName) {
        return "Event:\tStudent " + oldName + " changed name to " + newName + ".";
    }

    private String createSubjectRenameLog(final String oldName, final String newName) {
        return "Event:\tSubject " + oldName + " changed name to " + newName + ".";
    }

    private String createStudentAddingLog(final String name) {
        return "Event:\tAdded new student " + name + ".";
    }

    private String createSubjectAddingLog(final String name) {
        return "Event:\tAdded new subject " + name + ".";
    }

    private String createAssessmentAddingLog(final String studentName, final String subjectName,
                                             final String assessment) {
        return "Event:\tAdded new assessment " + assessment
                + " to student " + studentName
                + " by subject " + subjectName + ".";
    }

    public void updateLoggerInfo(final String message) {
        logger.log(message);
        List<String> currentLog = logger.getLog();
        String record = new String("");
        for (String log : currentLog) {
            record += log + "\n";
        }
        assesmentLogs.set(record);
    }

    private ILogger logger;

    private final StringProperty assesmentLogs = new SimpleStringProperty();

    private final StringProperty studentToAdd = new SimpleStringProperty();

    private final StringProperty subjectToAdd = new SimpleStringProperty();

    private final ObjectProperty<Student> studentToRename = new SimpleObjectProperty<>();

    private final StringProperty newStudentName = new SimpleStringProperty();

    private final ObjectProperty<Subject> subjectToRename = new SimpleObjectProperty<>();

    private final StringProperty newSubjectName = new SimpleStringProperty();

    private final ObjectProperty<Student> studentToAssess = new SimpleObjectProperty<>();

    private final ObjectProperty<Subject> subjectToAssess = new SimpleObjectProperty<>();

    private final ObjectProperty<Assessment> assessment = new SimpleObjectProperty<>();

    private final StringProperty studentAssessments = new SimpleStringProperty();

    private final StringProperty studentAverageAssessment = new SimpleStringProperty();

    private final StringProperty subjectAverageAssessment = new SimpleStringProperty();

    private final BooleanProperty errorMessageIsShown = new SimpleBooleanProperty();
    private final StringProperty errorMessageText = new SimpleStringProperty();

    private final ObjectProperty<ObservableList<Student>> students =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(this.studentsList()));

    private final ObjectProperty<ObservableList<Subject>> subjects =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(this.subjectsList()));

    private final ObjectProperty<ObservableList<Assessment>> assessments =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(Assessment.values()));
}
