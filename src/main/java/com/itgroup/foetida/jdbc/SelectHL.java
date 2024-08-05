package com.itgroup.foetida.jdbc;

import com.itgroup.foetida.bean.Mybs;
import com.itgroup.foetida.dao.MybsDao;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class SelectHL {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("고/저/정상 중 하나 : ");
        String bsugarmode = scan.next();

        MybsDao dao = new MybsDao();
        List<Mybs> allBsugar = dao.selectedBsugar(bsugarmode);

        for(Mybs bean:allBsugar) {
            ShowData.printBean(bean);
        }

        System.out.println("기록 개수 : "  + allBsugar.size());
    }
}
