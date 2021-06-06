package com.kolosov.kolosovtinkofftest.models;

public class DataModel {

    private String description;
    private String gifURL;


    public DataModel(String description, String gifURL) {
        this.description = description;
        this.gifURL = gifURL;
    }

    public String getDescription() {
        return description;
    }

    public String getGifURL() {
        return gifURL;
    }


}
