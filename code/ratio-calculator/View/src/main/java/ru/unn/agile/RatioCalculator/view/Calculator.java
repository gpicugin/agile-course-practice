package ru.unn.agile.RatioCalculator.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.unn.agile.RatioCalculator.viewmodel.ViewModel;
import ru.unn.agile.RatioCalculator.Model.Ratio;
public class Calculator {

    @FXML
    void initialize() {
    numeratorFirst.textProperty().bindBidirectional(viewModel.numeratorFirstProperty());
    numeratorSecond.textProperty().bindBidirectional(viewModel.numeratorSecondProperty());
    denominatorFirst.textProperty().bindBidirectional(viewModel.denominatorFirstProperty());
    denominatorSecond.textProperty().bindBidirectional(viewModel.denominatorSecondProperty());
    cbOperation.valueProperty().bindBidirectional(viewModel.operationProperty());
    calc.setOnAction(event -> viewModel.calculate());
    }

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

}
