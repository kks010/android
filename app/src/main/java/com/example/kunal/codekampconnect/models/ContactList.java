package com.example.kunal.codekampconnect.models;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kunal.codekampconnect.R;
import com.example.kunal.codekampconnect.MainActivity;
import com.example.kunal.codekampconnect.services.Callback;
import com.example.kunal.codekampconnect.services.CodekampService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kunal on 09-05-2016.
 */
public class ContactList extends MainActivity implements AdapterView.OnItemClickListener {

    private String[] leftDrawerOptions={"Actions","Contacts","Batches"};
    @BindView(R.id.left_drawer) ListView list;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager =  new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_contact);

        ButterKnife.bind(this);

        CodekampService service = new CodekampService("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIsImlzcyI6Imh0dHA6XC9cL2FwaS5jb2Rla2FtcC5pblwvYXV0aGVudGljYXRlIiwiaWF0IjoxNDYxNzcxNDcxLCJleHAiOjE0NjI5ODEwNzEsIm5iZiI6MTQ2MTc3MTQ3MSwianRpIjoiMzFjYzcwMjJmZmM2MTM5Y2Y5YzhmMjkwM2YxMjlmYmQifQ.dMdaFF0KIy7Qe4dVEDWG_82Pwq6MOBcshO3f-TakH3w");

        service.listContacts(1, new Callback<ListResponse<Contact>>() {
            @Override
            public void onSuccess(ListResponse<Contact> response) {
                List<Contact> contacts = response.getData();
//                Log.d("codekamp", contacts.get(0).getFirstName());
//                response.getMeta();
                ButterKnife.bind(mRecyclerView);

                mRecyclerView.setHasFixedSize(true);

                mRecyclerView.setLayoutManager(mLayoutManager);

                mAdapter = new MyAdapter(contacts);
                mRecyclerView.setAdapter(mAdapter);


            }

            @Override
            public void onFailure(com.example.kunal.codekampconnect.models.Error error) {
                Log.d("codekamp", error.getMessage());
            }
        });

        list.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, leftDrawerOptions));
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

 class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Contact> mDataset;

    public static  class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_adapter)TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(mTextView);
        }
    }

    public MyAdapter(List<Contact> contact) {
        mDataset = contact;
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTextView.setText(mDataset.get(position).getFirstName());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}