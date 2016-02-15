package com.jhb.dvt.dvt_store.Utils;

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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CreeD on 2016/02/12.
 */
public class ViewLoader extends AsyncTask<String, Void, String> implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private ItemRecyclerViewAdapter adapter;
    private List<Item> items;
    private String Call;
    private SliderLayout mDemoSlider;
    private int index = 0;
    private Context context;

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
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return doPost(Utilities.HostAddress + "/Api/" + Call + "?apiToken=" + Utilities.ApiKey);
        } catch (IOException e) {
            return "Unable to retrieve data. URL may be invalid.";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (adapter != null)
            adapter.notifyDataSetChanged();
        else if (mDemoSlider != null) {
            mDemoSlider.stopAutoCycle();
            for (Item featuredItem : items) {
                CustomSlider customSlider = new CustomSlider(mDemoSlider.getContext());
                customSlider.description(featuredItem.getName())
                        .image(featuredItem.getImageUrl())
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);
                customSlider.setPrice(featuredItem.getPrice());
                mDemoSlider.addSlider(customSlider);
            }
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
            mDemoSlider.addOnPageChangeListener(this);
            mDemoSlider.startAutoCycle(8000, 5000, true);
        }
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
            String result = new JsonRead().readJsonStream(items,conn.getInputStream());
            int response = conn.getResponseCode();
            System.out.println("ERROR: " + result);
            return result;
        } catch (Exception es) {
            System.out.println("ERROR: " + es.getMessage());
            return es.getMessage();
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
