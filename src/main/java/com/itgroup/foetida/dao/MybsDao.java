package com.itgroup.foetida.dao;

import com.itgroup.foetida.Utility.Paging;
import com.itgroup.foetida.bean.Mybs;

import java.sql.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//dataid, medate, bsugar, device, react, hba1c, binsulin
public class MybsDao extends SuperDao {
    public int insertData(Mybs bean) {
        System.out.println(bean);
        int cnt = -1;
        Connection conn = null;
        PreparedStatement tmt = null;
        String sql = " insert into mybs (dataid, medate, bsugar, device, react, binsulin) ";
        sql += " values(seqmybs.nextval, ?, ?, ?, ?, ?)";

        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);
            tmt = conn.prepareStatement(sql);

            System.out.println(bean.getMedate());

            tmt.setTimestamp(1, Timestamp.valueOf(bean.getMedate()));
            tmt.setInt(2, bean.getBsugar());
            tmt.setString(3, bean.getDevice());
            tmt.setString(4, bean.getReact());
            tmt.setString(5, bean.getBinsulin());

            cnt = tmt.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (tmt != null) {
                    tmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return cnt;
    }

    public List<Mybs> selectAll() {
        Connection conn = null;
        String sql = "select * from mybs order by medate desc ";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Mybs> allData = new ArrayList<>();
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Mybs bean = this.makeBean(rs);
                allData.add(bean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return allData;
    }

    private Mybs makeBean(ResultSet rs) {
        // bean 데이터를 만들어 주는 메소드입니다.
        // 여러 군데서 호출이 되므로 별도로 만들었습니다.
        Mybs bean = new Mybs();
        try {
            bean.setDataid(rs.getInt("dataid"));
            bean.setBsugar(rs.getInt("bsugar"));
            //Timestamp를 LocalDateTime으로 변환
            Timestamp timestamp = rs.getTimestamp("medate");
            LocalDateTime dateTime = timestamp.toLocalDateTime();
            //bean에 LocalDateTime 설정
            bean.setMedate(dateTime);
            bean.setDevice(rs.getString("device"));
            bean.setReact(rs.getString("react"));
            bean.setBinsulin(rs.getString("binsulin"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bean;
    }

    public int updateData(Mybs bean) {
        System.out.println(bean);
        int cnt = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " update mybs set medate = ?, bsugar = ?, device = ?, react = ?, binsulin = ?";
        sql += " where dataid = ?";

        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            pstmt.setTimestamp(1, Timestamp.valueOf(bean.getMedate()));
            pstmt.setInt(2, bean.getBsugar());
            pstmt.setString(3, bean.getDevice());
            pstmt.setString(4, bean.getReact());
            pstmt.setString(5, bean.getBinsulin());
            pstmt.setInt(6, bean.getDataid());

            cnt = pstmt.executeUpdate();
            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return cnt;
    }

    public int deleteData(int dataid) {
        System.out.println("기본 키 : " + dataid);
        int cnt = -1;
        Connection conn = null;
        PreparedStatement tmt = null;
        String sql = "delete from mybs ";
        sql += " where dataid = ?";

        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);
            tmt = conn.prepareStatement(sql);
            tmt.setInt(1, dataid);
            cnt = tmt.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                if (tmt != null) {
                    tmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cnt;

    }

    public LocalDateTime stringtodate(String time) {
        if (time == null || time.trim().isEmpty()) {
            System.out.println("ㅉㅉ");
            return null;
        }

        String pattern = "yyyyMMdd HHmm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        try {
            return LocalDateTime.parse(time, formatter);
        } catch (DateTimeException e) {
            e.printStackTrace();
            return null;
        }

    }


    public String datetostring(LocalDateTime dateTime) {
        if (dateTime == null) {
            System.out.println("ㅉㅉ");
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmm");
        String strTime = dateTime.format(formatter);
        return strTime;
    }


    public List<Mybs> selectedBsugar(String bsugarmode) {
        Connection conn = null;
        String sql = "select * from mybs ";

        if(bsugarmode.equals("고혈당")) {
            sql += " where bsugar > 140";
        } else if (bsugarmode.equals("저혈당")) {
            sql += " where bsugar < 80";
        } else if (bsugarmode.equals("정상 혈당")) {
            sql += " where bsugar between 81 and 139";
        } else {
            System.out.println("똑바로 입력하십쇼.");
            return null;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Mybs> allBsugar = new ArrayList<>();

        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Mybs bean = makeBean(rs);
                allBsugar.add(bean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return allBsugar;
    }

//    public List<String> selectDistinctCategory() {
//        Connection conn = null;
//        String sql = "select bsugar from article";
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//    }














//    public List<Mybs> selectLow(String HL) {
//        Connection conn = null;
//        String sql = "select * from mybs where bsugar < 80";
//
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        List<Mybs> lowsugar = new ArrayList<>();
//
//        try {
//            conn = super.getConnection();
//            pstmt = conn.prepareStatement(sql);
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                Mybs bean = makeBean(rs);
//                lowsugar.add(bean);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        return lowsugar;
//    }
//
//    public List<Mybs> selectNormal(String HL) {
//        Connection conn = null;
//        String sql = "select * from mybs where bsugar BETWEEN 80 AND 140 ";
//
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        List<Mybs> normalsugar = new ArrayList<>();
//
//        try {
//            conn = super.getConnection();
//            pstmt = conn.prepareStatement(sql);
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                Mybs bean = makeBean(rs);
//                normalsugar.add(bean);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        return normalsugar;
//    }

    public List<Mybs> getPaginationData(Paging pageInfo) {
        Connection conn = null ;
        PreparedStatement pstmt = null ;
        ResultSet rs = null ;
        List<Mybs> allData = new ArrayList<>();

        String sql = "select dataid, medate, bsugar, device, react, binsulin ";
        sql += " from ( ";
        sql += " select dataid, medate, bsugar, device, react, binsulin, ";
        sql += " rank() over(order by dataid desc) as ranking ";
        sql += " from mybs ";

        // mode가 'all'이 아니면 where 절이 추가로 필요합니다.
        String mode = pageInfo.getMode() ;
        boolean bool = mode == null|| mode.equals("null") || mode.equals("") || mode.equals("all");

//        if(!bool){
//            sql += " where category = ? " ;
//        }
        sql += " )  ";
        sql += " where ranking between ? and ?  " ;

        try{
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);

//            if(!bool){
//                pstmt.setString(1, mode);
//                pstmt.setInt(2, pageInfo.getBeginRow());
//                pstmt.setInt(3, pageInfo.getEndRow());
//

            pstmt.setInt(1, pageInfo.getBeginRow());
            pstmt.setInt(2, pageInfo.getEndRow());
            rs = pstmt.executeQuery() ;

            while(rs.next()){
                Mybs bean = this.makeBean(rs);
                allData.add(bean);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(rs!=null){rs.close();}
                if(pstmt!=null){pstmt.close();}
                if(conn!=null){conn.close();}
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return allData ;
    }

    public int getTotalCount() {
        int totalCount = 0;

        String sql = "select count(*) as mycnt from mybs";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                totalCount = rs.getInt("mycnt");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            try{
                if(rs!=null){rs.close();}
                if(pstmt!=null){pstmt.close();}
                if(conn!=null){conn.close();}
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return totalCount;
    }
}