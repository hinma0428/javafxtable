package com.itgroup.foetida.application;

import com.itgroup.foetida.Utility.Utility;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMybs extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        String fxmlFile = Utility.FXML_PATH + "MainMybs.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));

        Parent container = fxmlLoader.load(); // 승급
        Scene scene = new Scene(container);
        stage.setTitle("bloodsugar management system v 0.1");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }
}
