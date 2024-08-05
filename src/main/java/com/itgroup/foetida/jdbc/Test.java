package com.itgroup.foetida.jdbc;

import com.itgroup.foetida.dao.MybsDao;

import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        MybsDao mybsdao = new MybsDao();
        LocalDateTime dateTime = mybsdao.stringtodate("201212121212");

    }
}
