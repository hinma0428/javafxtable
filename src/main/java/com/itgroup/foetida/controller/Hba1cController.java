package com.itgroup.foetida.controller;

import com.itgroup.foetida.bean.Mybs;
import com.itgroup.foetida.dao.SuperDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Hba1cController extends SuperDao implements Initializable {

    @FXML
    TextField textField;

    public void makeH() throws SQLException {

        Connection conn = null;
        PreparedStatement tmt = null;
        ResultSet rs = null;
        String sql = "select bsugar from mybs";
        double sum = 0.0;
        int cnt = 0;

        try {
            conn = super.getConnection();
            tmt = conn.prepareStatement(sql);
            rs = tmt.executeQuery();

            while (rs.next()) {
                sum += rs.getDouble("bsugar");
                cnt++;
            }

            if (cnt == 0) {
                throw new ArithmeticException("no data");
            }
            double mean = sum / cnt;
            double hba1c = (mean+75)/35;
            String result = String.format("%.1f", hba1c);
            textField.setText(result + "%");
        } finally {
            if (rs != null) {
                rs.close();
            }
            ;
            if (tmt != null) {
                tmt.close();
            }
            ;
            if (conn != null) {
                conn.close();
            }
            ;
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
