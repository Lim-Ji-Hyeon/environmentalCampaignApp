package com.example.environmentalcampaign.cp_info;

import java.util.ArrayList;

public class CampaignItem {
    // CampaignInformation
    String logo, title, frequency, period;
    int participantsN, reCampaignN;
    ArrayList<ParticipantItem> participants=null;

    // FragmentInfo
    String cpInfo, infoImage1=null, infoImage2=null, infoImage3=null, infoImage4=null, infoImage5=null;

    // FragmentWay ( + frequency, period )
    String wayInfo, rightPhoto1=null, rightPhoto2=null, rightPhotoInfo1=null, rightPhotoInfo2=null,
            wrongPhoto1=null, wrongPhoto2=null, wrongPhotoInfo1=null, wrongPhotoInfo2=null;

    // FragmentReview
    ArrayList<ReviewItem> reviews=null;

    // 생성날짜로 캠페인 구분 + 신규 캠페인 확인에 사용
    String datetime; // YYYYmmDDHHMMSSsss 식으로 생성

    public CampaignItem(){}

    public String getLogo() { return logo; }

    public void setLogo(String logo) { this.logo = logo; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getFrequency() { return frequency; }

    public void setFrequency(String frequency) { this.frequency = frequency; }

    public String getPeriod() { return period; }

    public void setPeriod(String period) { this.period = period; }

    public int getParticipantsN() { return participantsN; }

    public void setParticipantsN(int participantsN) { this.participantsN = participantsN; }

    public int getReCampaignN() { return reCampaignN; }

    public void setReCampaignN(int reCampaignN) { this.reCampaignN = reCampaignN; }

    public ArrayList<ParticipantItem> getParticipants() { return participants; }

    public void setParticipants(ArrayList<ParticipantItem> participants) { this.participants = participants; }

    public String getCpInfo() { return cpInfo; }

    public void setCpInfo(String cpInfo) { this.cpInfo = cpInfo; }

    public String getInfoImage1() { return infoImage1; }

    public void setInfoImage1(String infoImage1) { this.infoImage1 = infoImage1; }

    public String getInfoImage2() { return infoImage2; }

    public void setInfoImage2(String infoImage2) { this.infoImage2 = infoImage2; }

    public String getInfoImage3() { return infoImage3; }

    public void setInfoImage3(String infoImage3) { this.infoImage3 = infoImage3; }

    public String getInfoImage4() { return infoImage4; }

    public void setInfoImage4(String infoImage4) { this.infoImage4 = infoImage4; }

    public String getInfoImage5() { return infoImage5; }

    public void setInfoImage5(String infoImage5) { this.infoImage5 = infoImage5; }

    public String getWayInfo() { return wayInfo; }

    public void setWayInfo(String wayInfo) { this.wayInfo = wayInfo; }

    public String getRightPhoto1() { return rightPhoto1; }

    public void setRightPhoto1(String rightPhoto1) { this.rightPhoto1 = rightPhoto1; }

    public String getRightPhoto2() { return rightPhoto2; }

    public void setRightPhoto2(String rightPhoto2) { this.rightPhoto2 = rightPhoto2; }

    public String getRightPhotoInfo1() { return rightPhotoInfo1; }

    public void setRightPhotoInfo1(String rightPhotoInfo1) { this.rightPhotoInfo1 = rightPhotoInfo1; }

    public String getRightPhotoInfo2() { return rightPhotoInfo2; }

    public void setRightPhotoInfo2(String rightPhotoInfo2) { this.rightPhotoInfo2 = rightPhotoInfo2; }

    public String getWrongPhoto1() { return wrongPhoto1; }

    public void setWrongPhoto1(String wrongPhoto1) { this.wrongPhoto1 = wrongPhoto1; }

    public String getWrongPhoto2() { return wrongPhoto2; }

    public void setWrongPhoto2(String wrongPhoto2) { this.wrongPhoto2 = wrongPhoto2; }

    public String getWrongPhotoInfo1() { return wrongPhotoInfo1; }

    public void setWrongPhotoInfo1(String wrongPhotoInfo1) { this.wrongPhotoInfo1 = wrongPhotoInfo1; }

    public String getWrongPhotoInfo2() { return wrongPhotoInfo2; }

    public void setWrongPhotoInfo2(String wrongPhotoInfo2) { this.wrongPhotoInfo2 = wrongPhotoInfo2; }

    public ArrayList<ReviewItem> getReviews() { return reviews; }

    public void setReviews(ArrayList<ReviewItem> reviews) { this.reviews = reviews; }

    public String getDatetime() { return datetime; }

    public void setDatetime(String datetime) { this.datetime = datetime; }
}
