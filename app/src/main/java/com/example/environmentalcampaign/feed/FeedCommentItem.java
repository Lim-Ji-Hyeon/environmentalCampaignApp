package com.example.environmentalcampaign.feed;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class FeedCommentItem {

    private String publisher;//피드 게시자
    private String feedDate; //피드게시 날짜
    private String userImage;
    private String nickname; // 댓글 게시자
    private String comment;
    private String date; // 댓글 쓴 날짜
    private String uid; //사용자 아이디

    public FeedCommentItem(){}

    public String getUserImage() { return userImage; }

    public void setUserImage(String userImage) { this.userImage = userImage; }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getPublisher() { return publisher; }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getFeedDate() { return feedDate; }

    public void setFeedDate(String feedDate) { this.feedDate = feedDate; }

    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid; }

}
