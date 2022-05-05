package com.example.englanguage.model.vocabulary;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vocabulary {

    @SerializedName("success")
    @Expose
    private List<SuccessVocabulary> success = null;

    public List<SuccessVocabulary> getSuccess() {
        return success;
    }

    public void setSuccess(List<SuccessVocabulary> success) {
        this.success = success;
    }

}
