package ru.unn.agile.RatioCalculator.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.unn.agile.RatioCalculator.viewmodel.ViewModel;
import ru.unn.agile.RatioCalculator.Model.*;
public class Calculator {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField numeratorFirst;
    @FXML
    private TextField numeratorSecond;
    @FXML
    private TextField denominatorFirst;
    @FXML
    private TextField denominatorSecond;
    @FXML
    private ComboBox<Ratio.Operation> cbOperation;
    @FXML
    private Button calc;

    @FXML
    void initialize() {

        // Two-way binding hasn't supported by FXML yet, so place it in code-behind
        numeratorFirst.textProperty().bindBidirectional(viewModel.numeratorFirstProperty());
        numeratorSecond.textProperty().bindBidirectional(viewModel.numeratorSecondProperty());
        denominatorFirst.textProperty().bindBidirectional(viewModel.denominatorFirstProperty());
        denominatorSecond.textProperty().bindBidirectional(viewModel.denominatorSecondProperty());

        cbOperation.valueProperty().bindBidirectional(viewModel.operationProperty());

        calc.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(final ActionEvent event) {
            viewModel.calculate();
             }
           });
          }
}
