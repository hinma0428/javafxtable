package com.itgroup.foetida.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import static java.lang.Class.forName;

public class SuperDao {
    private String driver;
    private String url = null;
    private String id = null;
    private String password = null;

    public SuperDao() {
        this.driver = "oracle.jdbc.driver.OracleDriver";
        this.url = "jdbc:oracle:thin:@localhost:1521:xe";
        this.id = "foetida";
        this.password = "8941";

        try {
            Class.forName(driver);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    protected Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, id, password);
            if(conn!=null) {
                System.out.println("접속 성공");
            } else {
                System.out.println("접속 실패");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }
}
