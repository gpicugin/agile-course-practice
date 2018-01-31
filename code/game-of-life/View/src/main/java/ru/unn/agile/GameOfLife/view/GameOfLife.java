package ru.unn.agile.GameOfLife.view;

//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
//import ru.unn.agile.GameOfLife.model.GameOfLife;
import ru.unn.agile.GameOfLife.viewmodel.ViewModel;

public class GameOfLife {
    @FXML
    void initialize() {
        txtWidth.textProperty().bindBidirectional(viewModel.widthProperty());
        txtHeight.textProperty().bindBidirectional(viewModel.heightProperty());
        txtTable.textProperty().bindBidirectional(viewModel.tableProperty());

        btnGetNext.setOnAction(event -> viewModel.getNextGeneration());
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
}
