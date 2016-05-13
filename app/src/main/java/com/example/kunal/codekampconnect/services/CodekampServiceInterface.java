package com.example.kunal.codekampconnect.services;

import com.example.kunal.codekampconnect.models.Contact;
import com.example.kunal.codekampconnect.models.ItemResponse;
import com.example.kunal.codekampconnect.models.ListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Kunal on 10-05-2016.
 */
public interface CodekampServiceInterface {

    @GET("contacts")
    Call<ListResponse<Contact>> listContacts(@Header("Authorization") String accessToken, @Query("page") int page);

    @GET("contacts/{id}")
    Call<ItemResponse<Contact>> getContact(@Header("Authorization") String Token, @Path("id") int contactId);

    @POST("contacts")
    Call<ItemResponse<Contact>> createContact(@Header("Authorization") String Token, @Field("first_name") String FirstName);


}
