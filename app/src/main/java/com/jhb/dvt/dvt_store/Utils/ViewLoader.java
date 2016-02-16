package com.jhb.dvt.dvt_store.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.jhb.dvt.dvt_store.Adapters.ItemRecyclerViewAdapter;
import com.jhb.dvt.dvt_store.CustomSlider;
import com.jhb.dvt.dvt_store.ItemDetailActivity;
import com.jhb.dvt.dvt_store.Models.Item;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CreeD on 2016/02/12.
 */
public class ViewLoader extends AsyncTask<Void, Void, Void> implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private ItemRecyclerViewAdapter adapter;
    private List<Item> items;
    private String Call;
    private SliderLayout mDemoSlider;
    private int index = 0;
    private Context context;

    public int itemCounter = 0;

    public ViewLoader(ItemRecyclerViewAdapter adapter, Context context, List<Item> items, String call) {
        this.adapter = adapter;
        this.items = items;
        this.Call = call;
        this.context = context;
    }

    public ViewLoader(SliderLayout mDemoSlider, Context context, String call) {
        this.mDemoSlider = mDemoSlider;
        this.items = new ArrayList<>();
        this.Call = call;
        this.context = context;
        progressDialog = ProgressDialog.show(context, "", "Loading Resources", false, false);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            doPost(Utilities.HostAddress + "/Api/" + Call + "?apiToken=" + Utilities.ApiKey);
        } catch (IOException e) {
            System.out.println("Unable to retrieve data. URL may be invalid.");
        }
        return null;
    }

    ProgressDialog progressDialog;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //this is the

    @Override
    protected void onPostExecute(Void aVoid) {

        if (adapter != null)
            adapter.notifyDataSetChanged();
        else if (mDemoSlider != null) {
            mDemoSlider.stopAutoCycle();
            for (Item featuredItem : items) {
                itemCounter++;

                CustomSlider customSlider = new CustomSlider(mDemoSlider.getContext());
                customSlider.description(featuredItem.getName())
                        .image(featuredItem.getImageUrl())
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);


                customSlider.setPrice(featuredItem.getPrice());
                mDemoSlider.addSlider(customSlider);
                if(featuredItem.equals(items.get(items.size()-1))){

                }

            }
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
            mDemoSlider.addOnPageChangeListener(this);
            mDemoSlider.startAutoCycle(8000, 5000, true);
            progressDialog.dismiss();
        }
        super.onPostExecute(aVoid);
    }

    private void doPost(String myUrl) throws IOException {
        try {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            new JsonRead().readJsonStream(items,conn.getInputStream());
            conn.getResponseCode();
        } catch (Exception es) {
            System.out.println("ERROR: " + es.getMessage());
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Intent intent = new Intent(context.getApplicationContext(), ItemDetailActivity.class);
        intent.putExtra("itemName", items.get(index).getName());
        intent.putExtra("itemImage", items.get(index).getImageUrl());
        intent.putExtra("itemDetails", items.get(index).getDetails());
        intent.putExtra("itemID", items.get(index).getId());
        intent.putExtra("itemPrice", Utilities.getCurrency(items.get(index).getPrice()));
        context.startActivity(intent);
    }

    @Override
    public void onPageSelected(int position) { index = position; }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageScrollStateChanged(int state) { }
}
