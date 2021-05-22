package com.example.environmentalcampaign;

// 사용자 계정 정보 모델 클래스

public class UserAccount {

    private String idToken; // Firebase Uid(고유 토큰 정보)
    private String emailId;
    private String password;
    private String nickName;
    private String profileImg;

    // Firebase Realtime 쓸 때 빈 생성자가 필요하다.
    public UserAccount() { }


    // Getter and Setter
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
