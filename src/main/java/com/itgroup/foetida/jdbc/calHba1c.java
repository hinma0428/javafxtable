package com.itgroup.foetida.jdbc;

import com.itgroup.foetida.dao.SuperDao;

import java.sql.*;

public class calHba1c extends SuperDao {
    public static void main(String[] args) {
        try {
            calHba1c calculator = new calHba1c();
            double result = calculator.calHba1c();
            double realResult = 0.0;
            realResult = (result+75)/35;
            System.out.println("당화혈색소 : " + realResult + "%");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public double calHba1c() throws SQLException {

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

            if(cnt==0) {
                throw new ArithmeticException("no data");
            }
            double hba1c =  sum / cnt;
            return hba1c;

        } finally {
            if (rs!=null) {rs.close();};
            if (tmt!=null) {tmt.close();};
            if (conn!=null) {conn.close();};
        }
    }
}
