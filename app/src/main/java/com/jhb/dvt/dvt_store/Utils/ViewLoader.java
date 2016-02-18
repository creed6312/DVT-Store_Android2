package com.jhb.dvt.dvt_store.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.jhb.dvt.dvt_store.Adapters.ItemRecyclerViewAdapter;
import com.jhb.dvt.dvt_store.CustomSlider;
import com.jhb.dvt.dvt_store.ItemDetailActivity;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CreeD on 2016/02/12.
 */
public class ViewLoader extends AsyncTask<Void, Void, String> implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private ItemRecyclerViewAdapter adapter;
    private List<Item> items;
    private String Call;
    private SliderLayout mDemoSlider;
    private int index = 0;
    private Context context;
    private ProgressDialog progressDialog;
    CustomSlider customSlider;

    public ViewLoader(ItemRecyclerViewAdapter adapter, Context context, List<Item> items, String call) {
        progressDialog = ProgressDialog.show(context, "Please wait", "Loading product catalog...", false, false);
        this.adapter = adapter;
        this.items = items;
        this.Call = call;
        this.context = context;
    }

    public ViewLoader(SliderLayout mDemoSlider, Context context, String call) {
        progressDialog = ProgressDialog.show(context, "Please wait", "Loading product catalog...", false, false);
        this.mDemoSlider = mDemoSlider;
        this.items = new ArrayList<>();
        this.Call = call;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            return doPost(Utilities.HostAddress + "/Api/" + Call + "?apiToken=" + Utilities.ApiKey);
        } catch (IOException e) {
            System.out.println("Unable to retrieve data. URL may be invalid.");
        }
        return null;
    }

    public void getFeaturedProducts() {
        for (Item featuredItem : items) {
            customSlider = new CustomSlider(mDemoSlider.getContext());
            customSlider.description(featuredItem.getName())
                    .image(featuredItem.getImageUrl())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            customSlider.setPrice(featuredItem.getPrice());
            mDemoSlider.addSlider(customSlider);
        }
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
        mDemoSlider.setCurrentPosition(0);
        mDemoSlider.startAutoCycle(8000, 5000, true);
    }

    public void CheckNetworkConnection() {
        mDemoSlider.stopAutoCycle();
        customSlider = new CustomSlider(mDemoSlider.getContext());
        customSlider.description("Please check your internet connection.")
                .setScaleType(BaseSliderView.ScaleType.Fit).image(R.drawable.warn);
        mDemoSlider.addSlider(customSlider);
    }

    @Override
    protected void onPostExecute(String result) {
        if (adapter != null && result.equals("Success"))
            adapter.notifyDataSetChanged();
        else if (mDemoSlider != null && result.equals("Success")) {
            getFeaturedProducts();
        } else if (mDemoSlider != null) {
            CheckNetworkConnection();
        }
        progressDialog.dismiss();
        super.onPostExecute(result);
    }

    private String doPost(String myUrl) throws IOException {
        try {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            new JsonRead().readJsonStream(items, conn.getInputStream());
            conn.getResponseCode();
            return "Success";
        } catch (Exception es) {
            return ("ERROR: " + es.getMessage());
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
    public void onPageSelected(int position) {
        index = position;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
