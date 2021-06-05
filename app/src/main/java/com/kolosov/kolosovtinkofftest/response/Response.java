package com.kolosov.kolosovtinkofftest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Response {

    @SerializedName("description")
    @Expose()
    private String description;

    @SerializedName("gifURL")
    @Expose()
    private String gifURL;

    public String getDescription() {
        return description;
    }

    public String getGifURL() {
        return gifURL;
    }

    @Override
    public String toString() {
        return "Response{" +
                "description='" + description + '\'' +
                ", gifURL='" + gifURL + '\'' +
                '}';
    }
}
