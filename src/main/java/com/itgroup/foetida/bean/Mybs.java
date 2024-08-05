package com.itgroup.foetida.bean;

import java.time.LocalDateTime;

public class Mybs {
    private int dataid;
    private LocalDateTime medate;
    private int bsugar;
    private String device;
    private String react;
    private double hba1c;
    private String binsulin;

    public Mybs() {}

    public Mybs(int dataid, LocalDateTime medate, int bsugar, String device, String react, double hba1c, String binsulin) {
        this.dataid = dataid;
        this.medate = medate;
        this.bsugar = bsugar;
        this.device = device;
        this.react = react;
        this.hba1c = hba1c;
        this.binsulin = binsulin;
    }

    @Override
    public String toString() {
        return "Mybs{" +
                "dataid=" + dataid +
                ", medate='" + medate + '\'' +
                ", bsugar=" + bsugar +
                ", device='" + device + '\'' +
                ", react='" + react + '\'' +
                ", hba1c=" + hba1c +
                ", binsulin='" + binsulin + '\'' +
                '}';
    }

    public int getDataid() {
        return dataid;
    }

    public void setDataid(int dataid) {
        this.dataid = dataid;
    }

    public LocalDateTime getMedate() {
        return medate;
    }

    public void setMedate(LocalDateTime medate) {
        this.medate = medate;
    }

    public int getBsugar() {
        return bsugar;
    }

    public void setBsugar(int bsugar) {
        this.bsugar = bsugar;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getReact() {
        return react;
    }

    public void setReact(String react) {
        this.react = react;
    }

    public double getHba1c() {
        return hba1c;
    }

    public void setHba1c(double hba1c) {
        this.hba1c = hba1c;
    }

    public String getBinsulin() {
        return binsulin;
    }

    public void setBinsulin(String binsulin) {
        this.binsulin = binsulin;
    }
}
