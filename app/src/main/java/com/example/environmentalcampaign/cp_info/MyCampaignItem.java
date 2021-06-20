package com.example.environmentalcampaign.cp_info;

public class MyCampaignItem {
    String title, campaignCode, startDate, endDate;
    int reCount, certiCount, certiCompleteCount;
    boolean complete, reviewComplete;

    public MyCampaignItem(){}

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

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

    public boolean isComplete() { return complete; }

    public void setComplete(boolean complete) { this.complete = complete; }

    public boolean isReviewComplete() { return reviewComplete; }

    public void setReviewComplete(boolean reviewComplete) { this.reviewComplete = reviewComplete; }

    public int getCertiCompleteCount() { return certiCompleteCount; }

    public void setCertiCompleteCount(int certiCompleteCount) { this.certiCompleteCount = certiCompleteCount; }
}
