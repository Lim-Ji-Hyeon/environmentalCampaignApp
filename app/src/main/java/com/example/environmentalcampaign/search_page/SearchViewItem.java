package com.example.environmentalcampaign.search_page;

import android.graphics.drawable.Drawable;

public class SearchViewItem {

    private String campaignCode;
    private String campaignName;
    private String campaignLogo;
    private String frequency;
    private int participantsN;

    public SearchViewItem(){ }

    public String getCampaignCode() { return campaignCode; }

    public void setCampaignCode(String campaignCode) { this.campaignCode = campaignCode; }

    public String getCampaignName() { return campaignName; }

    public void setCampaignName(String campaignName) { this.campaignName = campaignName; }

    public String getCampaignLogo() { return campaignLogo; }

    public void setCampaignLogo(String campaignLogo) { this.campaignLogo = campaignLogo; }

    public String getFrequency() { return frequency; }

    public void setFrequency(String frequency) { this.frequency = frequency; }

    public int getParticipantsN() { return participantsN; }

    public void setParticipantsN(int participantsN) { this.participantsN = participantsN; }
}
