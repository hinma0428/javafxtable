package com.itgroup.foetida.Utility;

import javafx.scene.control.Alert;

class bsugarRange{
    public String mode;
    public int minbsugar;
    public int maxbsugar;

    public bsugarRange(String mode, int minbsugar, int maxbsugar) {
        this.mode = mode;
        this.minbsugar = minbsugar;
        this.maxbsugar = maxbsugar;
    }
}

public class Utility {
    public static final String FXML_PATH = "/com/itgroup/foetida/fxml/"; //fxml 파일이 있는 경로
    public static final String IMAGE_PATH = "/com/itgroup/foetida/image/"; //이미지 파일이 있는 경로
    public static final String CSS_PATH = "/com/itgroup/foetida/css/"; //css 파일이 있는 경로
    public static final String DATA_PATH = "\\src\\main\\java\\com\\itgroup\\foetida\\data\\";

//    public static final bsugarRange HIGH_bsugar_RANGE = new bsugarRange("고", 140, Integer.MAX_VALUE);


    public static void showAlert(Alert.AlertType alertType, String[] message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(message[0]);
        alert.setHeaderText(message[1]);
        alert.setContentText(message[2]);
        alert.showAndWait();

//        where category = ?
//        where bsugar = BETWEEN ? AND ?
//        bsugar = BETWEEN HIGH_bsugar_RANGE.minbsugar AND HIGH_bsugar_RANGE.maxbsugar
    }
}
