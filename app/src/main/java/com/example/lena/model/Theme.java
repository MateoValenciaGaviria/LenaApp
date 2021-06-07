package com.example.lena.model;

public class Theme {

    private String name;
    private String image;
    private String description;

    public Theme() {

    }

    public Theme(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public Theme(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
