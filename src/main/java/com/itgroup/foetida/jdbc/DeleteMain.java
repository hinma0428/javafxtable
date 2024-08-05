package com.itgroup.foetida.jdbc;

import com.itgroup.foetida.bean.Mybs;
import com.itgroup.foetida.dao.MybsDao;

import java.util.Scanner;

public class DeleteMain {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("삭제할 기록 번호 : ");
        int dataid = scan.nextInt();

        MybsDao dao = new MybsDao();
        int cnt = -1;
        cnt = dao.deleteData(dataid);

        if(cnt==-1){
            System.out.println("기록 삭제 실패.");
        }else{
            System.out.println("기록 삭제 성공.");
        }
    }
}
