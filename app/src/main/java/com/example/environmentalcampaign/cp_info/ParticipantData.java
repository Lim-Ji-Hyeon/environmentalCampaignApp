package com.example.environmentalcampaign.cp_info;

import android.graphics.drawable.Drawable;

public class ParticipantData {
    private Drawable profile;
    String nickname;

    public ParticipantData(){}

    public Drawable getProfile() { return profile; }

    public void setProfile(Drawable profile) { this.profile = profile; }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }
}
