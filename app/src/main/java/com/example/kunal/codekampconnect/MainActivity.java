package com.example.kunal.codekampconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.kunal.codekampconnect.R;
import com.example.kunal.codekampconnect.models.*;
import com.example.kunal.codekampconnect.models.Error;
import com.example.kunal.codekampconnect.services.Callback;
import com.example.kunal.codekampconnect.services.CodekampService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CodekampService service = new CodekampService("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIsImlzcyI6Imh0dHA6XC9cL2FwaS5jb2Rla2FtcC5pblwvYXV0aGVudGljYXRlIiwiaWF0IjoxNDYyODc1Mzc1LCJleHAiOjE0NjQwODQ5NzUsIm5iZiI6MTQ2Mjg3NTM3NSwianRpIjoiZmM1NDI5MWVmN2FhZDlhM2MzNTdhNTE1NGRjZDc1YWYifQ.gIR6O0RhlQ8ciaKeXvomh3ZoZ8WhLTRE7S-Ol9NvZKo");

        service.listContacts(1, new Callback<ListResponse<Contact>>() {
            @Override
            public void onSuccess(ListResponse<Contact> response) {
                List<Contact> contacts = response.getData();
                Log.d("codekamp", " list contact " + contacts.get(0).getFirstName());
                response.getMeta();

            }

            @Override
            public void onFailure(com.example.kunal.codekampconnect.models.Error error) {
                Log.d("codekamp", "list contact " +error.getMessage());
            }
        });



        service.getContact(2000, new Callback<ItemResponse<Contact>>() {
            @Override
            public void onSuccess(ItemResponse<Contact> response) {
                Contact contact = response.getData();

                Log.d("codekamp", contact.getFirstName() + contact.getEmail());
            }

            @Override
            public void onFailure(Error error) {
                Log.d("codekamp", error.getMessage());
            }
        });


//        service.createContact("Prashant", new Callback<ItemResponse<Contact>>() {
//            @Override
//            public void onSuccess(ItemResponse<Contact> response) {
//
//            }
//            @Override
//            public void onFailure(Error error) {
//
//            }
//        });
    }
}