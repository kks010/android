package com.example.kunal.codekampconnect;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.kunal.codekampconnect.models.Contact;
import com.example.kunal.codekampconnect.models.ItemResponse;
import com.example.kunal.codekampconnect.models.ListResponse;
import com.example.kunal.codekampconnect.services.Callback;
import com.example.kunal.codekampconnect.services.CodekampService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kunal on 12-05-2016.
 */
public class ProfileDisplayActivity extends MainActivity {

    Integer i;
    @BindView(R.id.first_name_user_icon) TextView FirstName;
    @BindView(R.id.last_name_user_icon) TextView LastName;
    @BindView(R.id.dob_user_icon) TextView dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_display);

        ButterKnife.bind(this);

        i=getIntent().getIntExtra("contactId", -1);

        CodekampService service = new CodekampService("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIsImlzcyI6Imh0dHA6XC9cL2FwaS5jb2Rla2FtcC5pblwvYXV0aGVudGljYXRlIiwiaWF0IjoxNDYyODc1Mzc1LCJleHAiOjE0NjQwODQ5NzUsIm5iZiI6MTQ2Mjg3NTM3NSwianRpIjoiZmM1NDI5MWVmN2FhZDlhM2MzNTdhNTE1NGRjZDc1YWYifQ.gIR6O0RhlQ8ciaKeXvomh3ZoZ8WhLTRE7S-Ol9NvZKo");

        service.getContact(i, new Callback<ItemResponse<Contact>>() {
            @Override
            public void onSuccess(ItemResponse<Contact> response) {
                Contact contactDetails = response.getData();
                FirstName.setText(contactDetails.getFirstName());
                LastName.setText(contactDetails.getLastName());
                dob.setText(contactDetails.getBirthday());
            }

            @Override
            public void onFailure(com.example.kunal.codekampconnect.models.Error error) {
                Log.d("codekamp", "contact " + error.getMessage());
            }
        });
    }
}
