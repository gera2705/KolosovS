package com.kolosov.kolosovtinkofftest.models;

public class DataModel {

    private String description;
    private String url;


    public DataModel(String description, String url) {
        this.description = description;
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }


}
