package com.example.environmentalcampaign.feed;

public class FeedItem {

    private String image;  //이미지 URL 사용한다면 String 사용해도 됨

    public FeedItem(){}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
