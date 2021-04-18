package com.example.environmentalcampaign;

import android.graphics.drawable.Drawable;

public class CpCompleteData {
    private String name, frequency;
    private int rate, reCp;
    private Drawable logo;

    public CpCompleteData(){}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getFrequency() { return frequency; }

    public void setFrequency(String frequency) { this.frequency = frequency; }

    public int getRate() { return rate; }

    public void setRate(int rate) { this.rate = rate; }

    public int getReCp() { return reCp; }

    public void setReCp(int reCp) { this.reCp = reCp; }

    public Drawable getLogo() { return logo; }

    public void setLogo(Drawable logo) { this.logo = logo; }
}
