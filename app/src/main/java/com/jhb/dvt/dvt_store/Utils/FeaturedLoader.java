package com.jhb.dvt.dvt_store.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.jhb.dvt.dvt_store.CustomSlider;
import com.jhb.dvt.dvt_store.Fragment.ItemFragment;
import com.jhb.dvt.dvt_store.ItemDetailActivity;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SSardinha on 2/25/2016.
 */
public class FeaturedLoader extends AsyncTask<Void, Void, String>implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private List<Item> items;
    private String Call;
    private SliderLayout mDemoSlider;
    private int index = 0;
    private Context context;
    private ProgressDialog progressSlider;
    private CustomSlider customSlider;
    FragmentManager supportFragmentManager ;

    public FeaturedLoader(SliderLayout mDemoSlider, Context context, String call, FragmentManager supportFragmentManager) {
        progressSlider = ProgressDialog.show(context, "Please wait", "Loading product catalog...", false, false);
        this.mDemoSlider = mDemoSlider;
        items = new ArrayList<>();
        this.Call = call;
        this.context = context;
        this.supportFragmentManager = supportFragmentManager ;
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
    }

    public void CheckNetworkConnection() {
        mDemoSlider.stopAutoCycle();
        customSlider = new CustomSlider(mDemoSlider.getContext());
        customSlider.description("Please check your internet connection.")
                .setScaleType(BaseSliderView.ScaleType.Fit).image(R.drawable.warn);
        mDemoSlider.addSlider(customSlider);
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            return webServiceCall(Utilities.HostAddress + "/Api/" + Call + "?apiToken=" + Utilities.ApiKey);
        } catch (Exception e) {
            return "Failed";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("Success")) {
            getFeaturedProducts();
            progressSlider.cancel();
            ItemFragment ItemFragment = new ItemFragment();
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            transaction.replace(R.id.container, ItemFragment);
            transaction.commit();
        } else
        {
            CheckNetworkConnection();
            progressSlider.cancel();
        }
        super.onPostExecute(result);
    }

    public String webServiceCall(String myUrl) throws IOException
    {
        ConnectionManager con = new ConnectionManager();
        return con.JSonCall(con.HttpConnection(myUrl), items);
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

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
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
