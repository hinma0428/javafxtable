package com.itgroup.foetida.jdbc;

import com.itgroup.foetida.bean.Mybs;
import com.itgroup.foetida.dao.MybsDao;


import java.util.List;

public class SelectAllMain {
    public static void main(String[] args) {
        MybsDao dao = new MybsDao();
        List<Mybs> allMybs = dao.selectAll();
        System.out.println(allMybs.size());

        for(Mybs bean:allMybs){
            ShowData.printBean(bean);
        }
    }
}
