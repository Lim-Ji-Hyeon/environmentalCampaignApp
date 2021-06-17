package com.example.environmentalcampaign;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class CpData {
    private String name, period, frequency, edate, logo;
    private int rate, reCp;
    private boolean complete;

    public CpData(){}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPeriod() { return period; }

    public void setPeriod(String period) { this.period = period; }

    public String getFrequency() { return frequency; }

    public void setFrequency(String frequency) { this.frequency = frequency; }

    public String getEdate() { return edate; }

    public void setEdate(String edate) { this.edate = edate; }

    public String getLogo() { return logo; }

    public void setLogo(String logo) { this.logo = logo; }

    public int getRate() { return rate; }

    public void setRate(int rate) { this.rate = rate; }

    public int getReCp() { return reCp; }

    public void setReCp(int reCp) { this.reCp = reCp; }

    public boolean isComplete() { return complete; }

    public void setComplete(boolean complete) { this.complete = complete; }
}
