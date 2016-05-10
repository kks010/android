package com.example.kunal.codekampconnect.models;

/**
 * Created by Kunal on 10-05-2016.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListResponse<T> {

    @SerializedName("data")
    @Expose
    private List<T> data = new ArrayList<T>();
    @SerializedName("meta")
    @Expose
    private Meta meta;

    /**
     *
     * @return
     *     The data
     */
    public List<T> getData() {
        return data;
    }

    /**
     *
     * @param data
     *     The data
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     *
     * @return
     *     The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     *
     * @param meta
     *     The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
