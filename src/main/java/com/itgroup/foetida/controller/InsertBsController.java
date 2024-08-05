package com.itgroup.foetida.controller;

import com.itgroup.foetida.Utility.Utility;
import com.itgroup.foetida.bean.Mybs;
import com.itgroup.foetida.dao.MybsDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class InsertBsController implements Initializable {

    @FXML TextField fxmlBsugar;
    @FXML TextField fxmlMedate;
    @FXML TextField fxmlBinsulin;
    @FXML TextField fxmlReact;
    @FXML TextField fxmlDevice;
    @FXML Button btnInsert;

    MybsDao dao = null;
    Mybs bean = null;

    public void onInsertBs(ActionEvent event) {
        System.out.println(event);
        int cnt = -1;
        cnt = insertBs();
        if (cnt == 1) {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("잠 좀 자라");
        }
    }

    private int insertBs() {
        int cnt = -1;
        this.bean = new Mybs();

        bean.setDataid(0);

        int bsuagr = Integer.parseInt(fxmlBsugar.getText().trim());
        bean.setBsugar(bsuagr);

        String medate = fxmlMedate.getText().trim();
        String pattern = "yyyyMMdd HHmm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime dateTime = LocalDateTime.parse(medate, formatter);
        bean.setMedate(dateTime);

        String device = fxmlDevice.getText().trim();
        bean.setDevice(device);

        String binsulin = fxmlBinsulin.getText().trim();
        bean.setBinsulin(binsulin);

        String react = fxmlReact.getText().trim();
        bean.setReact(react);

        cnt = dao.insertData(this.bean);
        if (cnt == -1) {
            String[] message = new String[]{"새로운 기록", "뭐하니?", "새로운 기록 등록에 실패하였습니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
        }
        return cnt;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dao = new MybsDao();
    }
}



