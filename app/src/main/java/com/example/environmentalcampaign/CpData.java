package com.example.environmentalcampaign;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class CpData {
    private String name, period, frequency;
    private int rate, reCp, edate;
    private Drawable logo;
    private boolean complete;

    public CpData(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() { return period; }

    public void setPeriod(String period) { this.period = period; }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getEdate() {
        return edate;
    }

    public void setEdate(int edate) {
        this.edate = edate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Drawable getLogo() {
        return logo;
    }

    public void setLogo(Drawable logo) {
        this.logo = logo;
    }

    public int getReCp() { return reCp; }

    public void setReCp(int reCp) { this.reCp = reCp; }

    public boolean getComplete() { return complete; }

    public void setComplete(boolean complete) { this.complete = complete; }
}
