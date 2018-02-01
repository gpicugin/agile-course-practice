package ru.unn.agile.GameOfLife.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.unn.agile.GameOfLife.viewmodel.ViewModel;

public class GameOfLife {
    @FXML
    void initialize() {
        txtWidth.textProperty().bindBidirectional(viewModel.widthProperty());
        txtHeight.textProperty().bindBidirectional(viewModel.heightProperty());
        txtTable.textProperty().bindBidirectional(viewModel.tableProperty());

        btnGetNext.setOnAction(event -> viewModel.getNextGeneration());
        btnDefault.setOnAction(event -> viewModel.setDefaultTable());
        btnSetPrevious.setOnAction(event -> viewModel.setPreviousGeneration());
    }

    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtWidth;
    @FXML
    private TextField txtHeight;
    @FXML
    private TextArea txtTable;
    @FXML
    private Button btnGetNext;
    @FXML
    private Button btnDefault;
    @FXML
    private Button btnSetPrevious;
}
