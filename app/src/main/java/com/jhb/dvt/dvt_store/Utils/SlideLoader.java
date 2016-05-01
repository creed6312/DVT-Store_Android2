package com.jhb.dvt.dvt_store.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.jhb.dvt.dvt_store.Constants.Constants;
import com.jhb.dvt.dvt_store.ItemDetailActivity;
import com.jhb.dvt.dvt_store.MainActivity;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CreeD on 2016/02/26.
 */
public class SlideLoader {

    private final SliderLayout mDemoSlider;
    private final List<Item> items;
    private int index ;

    public SlideLoader(final SliderLayout slider, View parent)
    {
        mDemoSlider = slider ;
        items = new ArrayList<>();
        final TextView textFeatured = (TextView) parent.findViewById(R.id.idFeatured);
        textFeatured.setText("");
        final ProgressDialog progressSlider = ProgressDialog.show(mDemoSlider.getContext(), "Please wait", "Loading product catalog...", false, false);

        AsyncTaskCompat.executeParallel(new AsyncTask<Void, Void, String>() {

            public void getFeaturedProducts() {
                for (final Item featuredItem : items) {
                    CustomSlider customSlider = new CustomSlider(mDemoSlider.getContext());
                    customSlider.description(featuredItem.getName())
                            .image(featuredItem.getImageUrl())
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(sliderClick);
                    customSlider.setPrice(featuredItem.getPrice());
                    mDemoSlider.addSlider(customSlider);
                }

                mDemoSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
                mDemoSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        index = position ;
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }

            private final BaseSliderView.OnSliderClickListener sliderClick = new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Intent intent = new Intent(mDemoSlider.getContext(), ItemDetailActivity.class);
                    intent.putExtra("itemName", items.get(index).getName());
                    intent.putExtra("itemImage", items.get(index).getImageUrl());
                    intent.putExtra("itemDetails", items.get(index).getDetails());
                    intent.putExtra("itemID", items.get(index).getId());
                    intent.putExtra("itemPrice", Utilities.getCurrency(items.get(index).getPrice()));
                    mDemoSlider.getContext().startActivity(intent);
                }
            };

            public void CheckNetworkConnection() {
                mDemoSlider.stopAutoCycle();
                CustomSlider customSlider = new CustomSlider(mDemoSlider.getContext());
                customSlider.description("Please check your internet connection.")
                        .setScaleType(BaseSliderView.ScaleType.Fit).image(R.drawable.warn);
                mDemoSlider.addSlider(customSlider);
                final TextView textSeeMore = (TextView) mDemoSlider.findViewById(R.id.idseeMoreContent);
                textFeatured.setText("Connection Error!");
                textSeeMore.setText("");
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    return Utilities.webServiceCall(Constants.HostAddress + "/Api/GetFeatured?apiToken=" + Constants.ApiKey,items);
                } catch (Exception e) {
                    return "Failed";
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result.equals("Success")) {
                    getFeaturedProducts();
                    progressSlider.dismiss();
                    textFeatured.setText("Featured Products");
                    slider.startAutoCycle();
                } else
                {
                    CheckNetworkConnection();
                    progressSlider.dismiss();
                }
                super.onPostExecute(result);
            }
        });
    }
}
