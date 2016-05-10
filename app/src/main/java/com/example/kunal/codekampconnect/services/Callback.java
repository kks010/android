package com.example.kunal.codekampconnect.services;

import com.example.kunal.codekampconnect.models.*;
import com.example.kunal.codekampconnect.models.Error;

/**
 * Created by Kunal on 10-05-2016.
 */
public interface Callback<T> {

    void onSuccess(T response);


    void onFailure(Error error);

}