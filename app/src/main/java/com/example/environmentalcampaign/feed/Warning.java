package com.example.environmentalcampaign.feed;

public class Warning {
    private String feedPublisher;
    private String feedDate;
    private String warningReason;


    public Warning(){}

    public String getFeedPublisher() { return feedPublisher; }

    public void setFeedPublisher(String feedPublisher) { this.feedPublisher = feedPublisher; }

    public String getFeedDate() { return feedDate; }

    public void setFeedDate(String feedDate) { this.feedDate = feedDate; }

    public String getWarningReason() { return warningReason; }

    public void setWarningReason(String warningReason) { this.warningReason = warningReason; }

}
