package com.itgroup.foetida.jdbc;
import com.itgroup.foetida.bean.Mybs;
import java.time.LocalDateTime;

public class ShowData {
    public static void printBean(Mybs bean) {
        int dataid = bean.getDataid();
        int bsugar = bean.getBsugar();
        LocalDateTime medate = bean.getMedate();
        String device = bean.getDevice();
        String react = bean.getReact();
        Double hba1c = bean.getHba1c();
        String binsulin = bean.getBinsulin();

        System.out.println("데이터 번호 : " + dataid);
        System.out.println("혈당(mg/dL) : " + bsugar);
        System.out.println("측정일시 : " + medate);
        System.out.println("측정기기 : " + device);
        System.out.println("당화혈색소(%) : " + hba1c);
        System.out.println("대처 : " + react);
        System.out.println("기저 인슐린(base insulin) : " + binsulin);
        System.out.println("==========================");
    }
}
