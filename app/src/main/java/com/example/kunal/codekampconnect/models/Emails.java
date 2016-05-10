package com.example.kunal.codekampconnect.models;

/**
 * Created by Kunal on 10-05-2016.
 */

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Emails {

    @SerializedName("data")
    @Expose
    private List<Object> data = new ArrayList<Object>();

    /**
     *
     * @return
     * The data
     */
    public List<Object> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Object> data) {
        this.data = data;
    }

}