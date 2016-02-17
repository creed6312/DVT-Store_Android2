package com.jhb.dvt.dvt_store.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhb.dvt.dvt_store.Adapters.ItemRecyclerViewAdapter;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.R;
import com.jhb.dvt.dvt_store.Utils.ViewLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SSardinha on 2016-02-12.
 */
public class ItemFragment extends Fragment {

    List<Item> itemList = new ArrayList<>();
    View view;
    private RecyclerView recyclerView;
    private ItemRecyclerViewAdapter itemRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.items_List);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemRecyclerViewAdapter = new ItemRecyclerViewAdapter(itemList);
        recyclerView.setAdapter(itemRecyclerViewAdapter);
        new ViewLoader(itemRecyclerViewAdapter, container.getContext(), itemList, "GetAllProducts").execute();

        return recyclerView;
    }
}
