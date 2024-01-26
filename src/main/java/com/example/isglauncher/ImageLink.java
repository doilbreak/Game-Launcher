package com.example.isglauncher;

public class ImageLink {
    private String imagePath;
    private String link;

    public ImageLink(String imagePath, String link) {
        this.imagePath = imagePath;
        this.link = link;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

