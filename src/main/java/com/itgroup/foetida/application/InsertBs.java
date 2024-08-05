package com.itgroup.foetida.application;

import com.itgroup.foetida.Utility.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InsertBs  extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        String fxmlFile = Utility.FXML_PATH + "InsertBs.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));

        Parent container = fxmlLoader.load(); // 승급
        Scene scene = new Scene(container);
        stage.setScene(scene);
        stage.setTitle("need more cash");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
