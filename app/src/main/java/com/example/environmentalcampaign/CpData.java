package com.example.environmentalcampaign;

import android.widget.ImageView;

public class CpData {
    private String name, frequency, stime, etime, sdate;
    private int rate, dDay, logo;

    public CpData(int rate, int dDay, String name, String frequency, String stime, String etime, String sdate, int logo) {
        this.rate = rate;
        this.dDay = dDay;
        this.name = name;
        this.frequency = frequency;
        this.stime = stime;
        this.etime = etime;
        this.sdate = sdate;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getdDay() {
        return dDay;
    }

    public void setdDay(int dDay) {
        this.dDay = dDay;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
