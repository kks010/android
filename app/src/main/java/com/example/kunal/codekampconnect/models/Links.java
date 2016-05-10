package com.example.kunal.codekampconnect.models;

/**
 * Created by Kunal on 10-05-2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("next")
    @Expose
    private String next;

    /**
     *
     * @return
     *     The next
     */
    public String getNext() {
        return next;
    }

    /**
     *
     * @param next
     *     The next
     */
    public void setNext(String next) {
        this.next = next;
    }

}
