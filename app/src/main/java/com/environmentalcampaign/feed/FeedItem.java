package com.environmentalcampaign.feed;

public class FeedItem {

    private int image;  //이미지 URL 사용한다면 String 사용해도 됨

    public FeedItem(int image) {
        this.image = image;
    }

    public int getImage(){
        return image;
    }
}
