package com.example.environmentalcampaign;

import android.graphics.drawable.Drawable;

public class CpCompleteData {
    private String name, frequency, logo;
    private int rate, reCp;

    public CpCompleteData(){}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getFrequency() { return frequency; }

    public void setFrequency(String frequency) { this.frequency = frequency; }

    public int getRate() { return rate; }

    public void setRate(int rate) { this.rate = rate; }

    public int getReCp() { return reCp; }

    public void setReCp(int reCp) { this.reCp = reCp; }

    public String getLogo() { return logo; }

    public void setLogo(String logo) { this.logo = logo; }
}
