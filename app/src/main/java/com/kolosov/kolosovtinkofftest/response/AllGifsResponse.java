package com.kolosov.kolosovtinkofftest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kolosov.kolosovtinkofftest.models.DataModel;

import java.util.List;

public class AllGifsResponse {

    @SerializedName("result")
    @Expose()
    private List<DataModel> dataModelList;

    @SerializedName("totalCount")
    @Expose
    private int totalCount;

    public int getTotalCount(){
        return totalCount;
    }

    public List<DataModel> getDataModelList() {
        return dataModelList;
    }
}
