package com.example.environmentalcampaign.home;

public class RecyclerViewItem {

    private String campaignCode, image, title;
    private double reCampaignN;

    public RecyclerViewItem(){}

    public String getCampaignCode() { return campaignCode; }

    public void setCampaignCode(String campaignCode) { this.campaignCode = campaignCode; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public double getReCampaignN() { return reCampaignN; }

    public void setReCampaignN(double reCampaignN) { this.reCampaignN = reCampaignN; }
}
