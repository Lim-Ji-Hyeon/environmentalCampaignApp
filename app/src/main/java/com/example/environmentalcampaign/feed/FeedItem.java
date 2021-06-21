package com.example.environmentalcampaign.feed;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class FeedItem {

    private String image;  //이미지 URL 사용한다면 String 사용해도 됨
    private String publisher;
    private String date; //피드 올린 날짜
    private String contents; // 인증 내용
    private int heartN; //좋아요 개수
    private String uid; //사용자 아이디
    public Map<String, Boolean> heartTotalN = new HashMap<>();
    private int warningN;
    public Map<String, Boolean> warningTotalN = new HashMap<>();

    public FeedItem(){}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublisher() { return publisher; }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getContents() { return contents; }

    public void setContents(String contents) { this.contents = contents; }

    public int getHeartN() { return heartN; }

    public void setHeartN(int heartN) { this.heartN = heartN; }

    public int getWarningN() { return warningN; }

    public void setWarningN(int warningN) { this.warningN = warningN; }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("publisher", publisher);
        result.put("contents", contents);
        result.put("heartN", heartN);
        result.put("heartTotalN", heartTotalN);
        result.put("warningN", warningN);
        result.put("warningTotalN", warningTotalN);
        return result;
    }
}
