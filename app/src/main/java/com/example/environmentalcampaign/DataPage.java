package com.example.environmentalcampaign;

import android.graphics.drawable.Drawable;

public class DataPage {
    Drawable image;

    public DataPage(Drawable image) {
        this.image = image;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
