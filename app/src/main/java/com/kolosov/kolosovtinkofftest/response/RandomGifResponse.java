package com.kolosov.kolosovtinkofftest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kolosov.kolosovtinkofftest.models.DataModel;


public class RandomGifResponse {

    @SerializedName("description")
    @Expose()
    private String description;

    @SerializedName("gifURL")
    @Expose()
    private String gifURL;

    private DataModel dataModel;

    public DataModel getDataModel() {
        return new DataModel(description , gifURL);
    }


}
