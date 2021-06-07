package com.example.lena.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String id;
    private String userName;
    private String email;
    private String photoId;
    private ArrayList<Theme> themes;

    public User(){

    }

    public User(String id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.themes = new ArrayList<>();
        loadFreeThemes();
    }

    public void loadFreeThemes(){
        Theme mountain = new Theme("Mountain", "https://firebasestorage.googleapis.com/v0/b/lenaapp-c1773.appspot.com/o/mountain.png?alt=media&token=e67b7046-ce49-4d17-91d8-3fc21dc23d2e", "");
        Theme sunflower = new Theme("Sunflower", "https://firebasestorage.googleapis.com/v0/b/lenaapp-c1773.appspot.com/o/sunflower.png?alt=media&token=8c8a8a13-59b5-4f98-9661-b46caa14491d", "");
        Theme forest = new Theme("Forest", "https://firebasestorage.googleapis.com/v0/b/lenaapp-c1773.appspot.com/o/forest.png?alt=media&token=96b8f83c-3b9e-4121-9264-ef16b9018d7c", "");
        themes.add(mountain);
        themes.add(sunflower);
        themes.add(forest);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public ArrayList<Theme> getThemes() {
        return themes;
    }

    public void setThemes(ArrayList<Theme> themes) {
        this.themes = themes;
    }
}
