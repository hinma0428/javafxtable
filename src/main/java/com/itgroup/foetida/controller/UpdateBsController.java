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
import java.util.Date;
import java.util.ResourceBundle;

public class UpdateBsController implements Initializable {

    @FXML
    TextField fxmlBsugar;
    @FXML
    TextField fxmlMedate;
    @FXML
    TextField fxmlBinsulin;
    @FXML
    TextField fxmlReact;
    @FXML
    TextField fxmlDevice;
    @FXML
    Button btnUpdate;

    private Mybs oldBean = null;
    private Mybs newBean = null;

    public void setBean(Mybs bean) {
        this.oldBean = bean;
        fillPreviousData();

    }

    private void fillPreviousData() {

        fxmlBsugar.setText(String.valueOf(this.oldBean.getBsugar()));
        fxmlMedate.setText(String.valueOf(this.oldBean.getMedate()));
//        LocalDateTime dateTime;
//        dateTime = this.oldBean.getMedate();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyymmdd HHmm");
//        String strTime = dateTime.format(formatter);
//        fxmlMedate.setText(strTime);



        fxmlDevice.setText(this.oldBean.getDevice());
        fxmlBinsulin.setText(this.oldBean.getBinsulin());
        fxmlReact.setText(this.oldBean.getReact());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("수정할 거야");
    }

    public void onUpdate(ActionEvent event) {
        MybsDao dao = new MybsDao();
        Mybs bean = new Mybs();
        int cnt = -1;

        bean.setBsugar(Integer.parseInt(fxmlBsugar.getText()));

        String medate = fxmlMedate.getText();
        String pattern = "yyyyMMdd HHmm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime dateTime = LocalDateTime.parse(medate, formatter);
        bean.setMedate(dateTime);

        bean.setDevice(fxmlDevice.getText());
        bean.setBinsulin(fxmlBinsulin.getText());
        bean.setReact(fxmlReact.getText());


        cnt = dao.updateData(bean);
          if(cnt==-1) {
            System.out.println("수정 실패...");
        }else{
            System.out.println("기록을 수정하였습니다.");
            Node source = (Node)event.getSource();
            Stage stage = (Stage)source.getScene().getWindow();
            stage.close();
        }
    }

}