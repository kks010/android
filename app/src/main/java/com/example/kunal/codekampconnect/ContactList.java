package com.example.kunal.codekampconnect;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kunal.codekampconnect.models.*;
import com.example.kunal.codekampconnect.services.Callback;
import com.example.kunal.codekampconnect.services.CodekampService;

import java.util.ArrayList;
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
    private List<Contact> emptyList;
    private List<String> items;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 1;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private Menu menu;

    private int mPageNumberToFetch=1;
    public String mAccessToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIsImlzcyI6Imh0dHA6XC9cL2FwaS5jb2Rla2FtcC5pblwvYXV0aGVudGljYXRlIiwiaWF0IjoxNDYzMTI1Njk5LCJleHAiOjE0NjQzMzUyOTksIm5iZiI6MTQ2MzEyNTY5OSwianRpIjoiZGE0N2YzYWVhMmM3YjA5YzhiODI1Mzc2ODM5ZGM3OGEifQ.fiBzCqamNBSJOXJlSOjYQRrwerLjK6tDI_iU6LvuEnE";
    private LinearLayoutManager mLayoutManager;
    List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_contact);

//        ActionBar ab = getSupportActionBar();
//            ab.setDisplayShowHomeEnabled(true);
//            ab.setIcon(R.drawable.codekampconnectlogo);
//            ab.setDisplayUseLogoEnabled(true);


        ButterKnife.bind(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddContactActivity.class);
                startActivity(intent);
            }
        });

        mLayoutManager= new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        emptyList = new ArrayList<>();
        mAdapter = new MyAdapter(getApplicationContext(), emptyList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {

                    mPageNumberToFetch++;
                    fetchContactsList(mAccessToken, mPageNumberToFetch);
                    loading = true;
                }
            }
        });

        CodekampService service = new CodekampService(mAccessToken);

        service.listContacts(mPageNumberToFetch, new Callback<ListResponse<Contact>>() {
            @Override
            public void onSuccess(ListResponse<Contact> response) {
                contacts = response.getData();
//                Log.d("codekamp", contacts.get(0).getFirstName());
//                response.getMeta();

                mAdapter = new MyAdapter(getApplicationContext(),contacts);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(com.example.kunal.codekampconnect.models.Error error) {
                Log.d("codekamp failure", error.getMessage());
                Toast.makeText(getApplicationContext(),"No internet Connection",Toast.LENGTH_SHORT);
            }
        });


        list.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, leftDrawerOptions));
        list.setOnItemClickListener(this);
    }

    private void fetchContactsList(String accessToken, int pageNumber) {
        CodekampService service = new CodekampService(accessToken);

        service.listContacts(pageNumber, new Callback<ListResponse<Contact>>() {
            @Override
            public void onSuccess(ListResponse<Contact> response) {
                contacts.addAll(response.getData());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(com.example.kunal.codekampconnect.models.Error error) {
                Toast.makeText(ContactList.this, "Failure!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
//        this.menu=menu;
//
//        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//
//        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
//
//        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
//
//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                loadHistory(newText);
//                return false;
//            }
//        });
//
//        return true;
//    }
//    private void loadHistory(String query) {
//
//            String[] columns = new String[] { "_id", "text" };
//            Object[] temp = new Object[] { 0, "default" };
//
//            MatrixCursor cursor = new MatrixCursor(columns);
//
//            for(int i = 0; i < items.size(); i++) {
//
//                temp[0] = i;
//                temp[1] = items.get(i);
//
//                cursor.addRow(temp);
//
//            }
//
//             SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//
//            final SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
//
//           search.setSuggestionsAdapter(new ExampleAdapter(this, cursor, items));

        return super.onCreateOptionsMenu(menu);
        }




        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<Contact> mDataset;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public Integer mContactId;
            TextView mTextView;
            FrameLayout starLayout;
            int i=0;
            public ViewHolder(View v) {
                super(v);
                mTextView=(TextView)v.findViewById(R.id.text_view_adapter);
                starLayout=(FrameLayout)v.findViewById(R.id.star);
                mTextView.setOnClickListener(this);
                starLayout.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                if(v.getId()==R.id.text_view_adapter) {
                    Intent i = new Intent(getApplicationContext(), ProfileDisplayActivity.class);
                    i.putExtra("contactId", mContactId);
                    startActivity(i);
                }
                else if(v.getId()==R.id.star){

                    if(i%2==0) {
                        starLayout.setBackgroundResource(R.drawable.star_blue_128);
                    }else {
                        starLayout.setBackgroundResource(R.drawable.whitestar);
                    }
                    i++;
                }
            }
        }

        public MyAdapter(Context context,List<Contact> contact) {
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

            holder.mTextView.setText(" " + mDataset.get(position).getFirstName());

            holder.mContactId = mDataset.get(position).getId();


        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

    }
//    public class ExampleAdapter extends CursorAdapter {
//
//        private List<String> items;
//
//        private TextView text;
//
//        public ExampleAdapter(Context context, Cursor cursor, List<String> items) {
//
//            super(context, cursor, false);
//
//            this.items = items;
//
//        }
//
//        @Override
//        public void bindView(View view, Context context, Cursor cursor) {
//
//            text.setText(items.get(cursor.getPosition()));
//
//        }
//
//        @Override
//        public View newView(Context context, Cursor cursor, ViewGroup parent) {
//
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            View view = inflater.inflate(R.layout.item, parent, false);
//
//            text = (TextView) view.findViewById(R.id.text);
//
//            return view;
//
//        }
//
//    }
}

