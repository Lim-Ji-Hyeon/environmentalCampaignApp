package com.example.environmentalcampaign.cp_info;

public class MyCampaignItem {
    String campaignCode, startDate, endDate;
    int reCount, certiCount;

    public MyCampaignItem(){}

    public String getCampaignCode() { return campaignCode; }

    public void setCampaignCode(String campaignCode) { this.campaignCode = campaignCode; }

    public String getStartDate() { return startDate; }

    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }

    public void setEndDate(String endDate) { this.endDate = endDate; }

    public int getReCount() { return reCount; }

    public void setReCount(int reCount) { this.reCount = reCount; }

    public int getCertiCount() { return certiCount; }

    public void setCertiCount(int certiCount) { this.certiCount = certiCount; }
}
