package com.example.englanguage.model.vocabulary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SuccessVocabulary implements Serializable {

    public SuccessVocabulary(String word, String mean, String example) {
        this.word = word;
        this.mean = mean;
        this.example = example;
    }

    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("mean")
    @Expose
    private String mean;
    @SerializedName("image_path")
    @Expose
    private String imagePath;
    @SerializedName("example")
    @Expose
    private String example;
    @SerializedName("id_wordtype")
    @Expose
    private Integer idWordtype;
    @SerializedName("user_create")
    @Expose
    private Integer userCreate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public Integer getIdWordtype() {
        return idWordtype;
    }

    public void setIdWordtype(Integer idWordtype) {
        this.idWordtype = idWordtype;
    }

    public Integer getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(Integer userCreate) {
        this.userCreate = userCreate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
