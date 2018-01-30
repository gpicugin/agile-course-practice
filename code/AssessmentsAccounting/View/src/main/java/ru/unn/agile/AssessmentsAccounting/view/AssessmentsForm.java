package ru.unn.agile.AssessmentsAccounting.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.unn.agile.AssessmentsAccounting.infrastructure.AssessmentsTextLogger;
import ru.unn.agile.AssessmentsAccounting.viewmodel.AssessmentsAccountingViewModel;

import java.time.LocalDate;

public class AssessmentsForm {

    @FXML
    @SuppressWarnings(value = "unchecked")
    void initialize() {
        this.updateStudentsList();
        this.updateSubjectsList();

        this.studentToAdd.textProperty().bindBidirectional(
                this.viewModel.studentToAddProperty()
        );
        this.addStudentButton.setOnAction(event -> {
            this.viewModel.addStudent();
            this.updateStudentsList();
        });

        this.subjectToAdd.textProperty().bindBidirectional(
                this.viewModel.subjectToAddProperty()
        );
        this.addSubjectButton.setOnAction(event -> {
            this.viewModel.addSubject();
            this.updateSubjectsList();
        });

        this.studentToRename.valueProperty().bindBidirectional(
                this.viewModel.studentToRenameProperty()
        );

        this.renameStudentName.textProperty().bindBidirectional(
                this.viewModel.newStudentNameProperty()
        );

        this.renameStudentButton.setOnAction(event -> {
            this.viewModel.renameStudent();
            this.updateStudentsList();
        });

        this.subjectToRename.valueProperty().bindBidirectional(
                this.viewModel.subjectToRenameProperty()
        );
        this.renameSubjectName.textProperty().bindBidirectional(
                this.viewModel.newSubjectNameProperty()
        );

        this.renameSubjectButton.setOnAction(event -> {
            this.viewModel.renameSubject();
            this.updateSubjectsList();
        });

        this.assessmentsForStudent.valueProperty().bindBidirectional(
                this.viewModel.studentToAssessProperty()
        );

        this.assessmentsForSubject.valueProperty().bindBidirectional(
                this.viewModel.subjectToAssessProperty()
        );

        this.assessmentToAdd.valueProperty().bindBidirectional(
                this.viewModel.assessmentProperty()
        );

        this.assessmentToAdd.itemsProperty().bindBidirectional(
                this.viewModel.assessmentsProperty()
        );

        this.addAssessmentButton.setOnAction(event -> this.viewModel.addAssessment());

        this.studentAssessments.textProperty().bindBidirectional(
                this.viewModel.studentAssessmentsProperty()
        );

        this.averageStudentAssessments.textProperty().bindBidirectional(
                this.viewModel.studentAverageAssessmentProperty()
        );

        this.averageSubjectAssessments.textProperty().bindBidirectional(
                this.viewModel.subjectAverageAssessmentProperty()
        );
        this.loggerTextArea.textProperty().bindBidirectional(
                this.viewModel.getLogsProperty()
        );
        this.viewModel.errorMessageIsShownProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue) {
                        showErrorAlertWindow();
                    }
                }
        );
    }

    private void showErrorAlertWindow() {
        Alert alertWindow = new Alert(Alert.AlertType.ERROR);
        alertWindow.setContentText(this.viewModel.getErrorMessage());
        alertWindow.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> this.viewModel.resetError());
    }

    @SuppressWarnings(value = "unchecked")
    private void updateStudentsList() {
        this.studentToRename.setItems(this.viewModel.getStudents());
        this.assessmentsForStudent.setItems(this.viewModel.getStudents());
    }

    @SuppressWarnings(value = "unchecked")
    private void updateSubjectsList() {
        this.subjectToRename.setItems(this.viewModel.getSubjects());
        this.assessmentsForSubject.setItems(this.viewModel.getSubjects());
    }

    @FXML
    private AssessmentsAccountingViewModel viewModel =
            new AssessmentsAccountingViewModel(
                    new AssessmentsTextLogger("./TextLogger_lab3_" + LocalDate.now() + ".log"));

    @FXML
    private TextField studentToAdd;

    @FXML
    private Button addStudentButton;

    @FXML
    private TextField subjectToAdd;

    @FXML
    private Button addSubjectButton;

    @FXML
    private ComboBox studentToRename;

    @FXML
    private TextField renameStudentName;

    @FXML
    private Button renameStudentButton;

    @FXML
    private ComboBox subjectToRename;

    @FXML
    private TextField renameSubjectName;

    @FXML
    private Button renameSubjectButton;

    @FXML
    private ComboBox assessmentsForStudent;

    @FXML
    private ComboBox assessmentsForSubject;

    @FXML
    private ComboBox assessmentToAdd;

    @FXML
    private Button addAssessmentButton;

    @FXML
    private Label studentAssessments;

    @FXML
    private Label averageSubjectAssessments;

    @FXML
    private Label averageStudentAssessments;

    @FXML
    private TextArea loggerTextArea;
}
