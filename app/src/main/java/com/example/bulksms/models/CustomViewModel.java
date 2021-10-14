package com.example.bulksms.models;

public class CustomViewModel {
    private  int layoutImage;
    private  int  description;

    public CustomViewModel(int layoutImage, int description) {
        this.layoutImage = layoutImage;
        this.description = description;
}

    public int getLayoutImage() {
        return layoutImage;
    }

    public void setLayoutImage(int layoutImage) {
        this.layoutImage = layoutImage;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }
}
