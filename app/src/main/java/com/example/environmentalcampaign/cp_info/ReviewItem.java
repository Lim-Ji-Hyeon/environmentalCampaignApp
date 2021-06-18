package com.example.environmentalcampaign.cp_info;

public class ReviewItem {
    private String uid, content;
    private String datetime;
    private double rating;

    public ReviewItem(){}

    public String getUid() { return uid; }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
