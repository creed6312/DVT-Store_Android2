package com.jhb.dvt.dvt_store.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhb.dvt.dvt_store.Adapters.ItemRecyclerViewAdapter;
import com.jhb.dvt.dvt_store.Constants.Constants;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.R;
import com.jhb.dvt.dvt_store.Utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SSardinha on 2016-02-12.
 */
public class ItemFragment extends Fragment {

    private final List<Item> itemList = new ArrayList<>();
    private ItemRecyclerViewAdapter itemRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_list, container, false);
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Please wait", "Loading product catalog...", false, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.items_List);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemRecyclerViewAdapter = new ItemRecyclerViewAdapter(itemList);
        recyclerView.setAdapter(itemRecyclerViewAdapter);

        AsyncTaskCompat.executeParallel(new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return Utilities.webServiceCall(Constants.HostAddress + "/Api/GetAllProducts?apiToken=" + Constants.ApiKey,itemList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                itemRecyclerViewAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
                super.onPostExecute(s);
            }
        });

        return recyclerView;
    }
}
