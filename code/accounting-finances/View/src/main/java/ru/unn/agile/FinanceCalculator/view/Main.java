package ru.unn.agile.FinanceCalculator.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Parent load = FXMLLoader.load(getClass().getResource("Accaunting.fxml"));
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(new Scene(load));
        primaryStage.show();
    }

}
