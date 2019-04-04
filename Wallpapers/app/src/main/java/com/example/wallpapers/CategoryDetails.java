package com.example.wallpapers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryDetails implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("images")
    @Expose
    private String images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }


}


