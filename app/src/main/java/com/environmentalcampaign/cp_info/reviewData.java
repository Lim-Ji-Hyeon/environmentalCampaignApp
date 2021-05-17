package com.environmentalcampaign.cp_info;

import android.graphics.drawable.Drawable;

public class reviewData {
    private Drawable profile;
    private String nickname, review;
    private int date;
    private double ratingbar;

    public reviewData() { }

    public Drawable getProfile() {
        return profile;
    }

    public void setProfile(Drawable profile) {
        this.profile = profile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public double getRatingbar() {
        return ratingbar;
    }

    public void setRatingbar(double ratingbar) {
        this.ratingbar = ratingbar;
    }
}
