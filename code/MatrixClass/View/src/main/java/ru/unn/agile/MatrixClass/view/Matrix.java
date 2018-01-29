package ru.unn.agile.MatrixClass.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import ru.unn.agile.MatrixClass.viewmodel.ViewModel;

public class Matrix {
    @FXML
    void initialize() {
        matrixSize.textProperty().bindBidirectional(viewModel.matrixSizeProperty());
        fieldForMatrix.textProperty().bindBidirectional(viewModel.fieldForMatrixProperty());

        calculate.setOnAction(event -> viewModel.calculate());
    }

    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField matrixSize;
    @FXML
    private TextField fieldForMatrix;
    @FXML
    private Button calculate;
}
