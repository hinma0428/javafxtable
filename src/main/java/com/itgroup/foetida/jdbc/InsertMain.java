package com.itgroup.foetida.jdbc;

import com.itgroup.foetida.bean.Mybs;
import com.itgroup.foetida.dao.MybsDao;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InsertMain {
    public static void main(String[] args) {
        MybsDao dao = new MybsDao();
        Mybs bean = new Mybs();

        Scanner scan = new Scanner(System.in);
        System.out.print("혈당(int) : ");
        int bsugar = scan.nextInt();

        Scanner scan2 = new Scanner(System.in);
        System.out.print("일시(YYYYMMDDHHMM) : ");
        String time = scan2.nextLine();

        //메소드 활용
        LocalDateTime medate = dao.stringtodate(time);
        bean.setMedate(medate);

//
//
//
//
//
//
//        LocalDateTime dateTime = null;
//        String pattern = "yyyyMMddHHmm";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
//
//        try {
//            dateTime = LocalDateTime.parse(inputdate, formatter);
//            bean.setMedate(dateTime);
//        } catch(DateTimeException e) {
//            System.out.println("입력한 날짜 형식이 올바르지 않음.");
//            e.printStackTrace();
//            scan2.close();
//            return;
//        }

        bean.setBsugar(bsugar);
        bean.setDevice("프리스타일 리브레 2");
        bean.setReact("none");
        bean.setHba1c(7.9);
        bean.setBinsulin("투제오 13");

        int cnt = -1;
        cnt = dao.insertData(bean);

        if (cnt == 1) {
            System.out.println("혈당 입력에 성공하였습니다.");
        } else {
            System.out.println("혈당 입력에 실패하였습니다.");
        }
    }
}
