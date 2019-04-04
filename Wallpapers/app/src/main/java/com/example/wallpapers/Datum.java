package com.example.wallpapers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cat_name")
    @Expose
    private String catName;
    @SerializedName("cat_image")
    @Expose
    private String catImage;


    public Datum(String id, String catName, String catImage) {
        this.id = id;
        this.catName = catName;
        this.catImage = catImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

}