package com.itgroup.foetida.jdbc;


import com.itgroup.foetida.bean.Mybs;
import com.itgroup.foetida.dao.MybsDao;

import java.sql.SQLOutput;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

public class UpdateMain {
    public static void main(String[] args) {
        MybsDao dao = new MybsDao();
        Mybs bean = new Mybs();

        Scanner scan = new Scanner(System.in);
        System.out.print("데이터 번호 : ");
        int id = scan.nextInt();

        Scanner scan2 = new Scanner(System.in);
        System.out.print("수정할 일시(202011221212) : ");
        String time = scan2.nextLine();

        //공용 메소드 활용?
        LocalDateTime medate = dao.stringtodate(time);
        bean.setMedate(medate);

//        String inputdate = scan2.nextLine();
//        String pattern = "yyyyMMddHHmm";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

//        try {
//            LocalDateTime medate = LocalDateTime.parse(inputdate, formatter);
//            bean.setMedate(medate);
//        } catch(DateTimeParseException e) {
//            System.out.println("야 걍 스트링으로 바까라");
//            e.getMessage();
//            e.getErrorIndex();
//            e.printStackTrace();
//            scan2.close();
//            return;
//        }

        bean.setDataid(id);
        bean.setBsugar(150);
        bean.setDevice("프리스타일 리브레 2");
        bean.setHba1c(7.9);
        bean.setReact("없음");
        bean.setBinsulin("투제오 13");

        int cnt = -1 ; // -1을 실패한 경우라고 가정합니다.
        cnt = dao.updateData(bean);

        if(cnt==-1){
            System.out.println("기록 수정에 실패하였습니다.");
        }else{
            System.out.println("기록 수정에 성공하였습니다.");
        }
    }
}
