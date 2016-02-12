package com.jhb.dvt.dvt_store.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.jhb.dvt.dvt_store.Adapters.SimpleItemRecyclerViewAdapter;
import com.jhb.dvt.dvt_store.ItemDetailActivity;
import com.jhb.dvt.dvt_store.Models.Item;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CreeD on 2016/02/12.
 */
public class Json extends AsyncTask<String, Void, String>  implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    SimpleItemRecyclerViewAdapter adapter ;
    List<Item> items ;
    String ApiKey = "0354aa02-e294-b0c2-68df-e8d7bc09e1ec";
    String Call ;
    SliderLayout mDemoSlider;
    int index = 0 ;
    Context context;

    public Json(SimpleItemRecyclerViewAdapter adapter, List<Item> items,String call) {
        this.adapter = adapter;
        this.items = items ;
        this.Call = call ;
    }

    public Json(SliderLayout mDemoSlider,Context context, String call)
    {
        this.mDemoSlider = mDemoSlider ;
        this.items = new ArrayList<>();
        this.Call = call ;
        this.context = context ;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return doPost("http://creed.ddns.net:4501/Api/" + Call + "?apiToken=" + ApiKey);
        } catch (IOException e) {
            return "Unable to retrieve data. URL may be invalid.";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (adapter != null)
            adapter.notifyDataSetChanged();
        else if (mDemoSlider != null)
        {
            for (Item featuredItem : items){
                TextSliderView demoSlider = new TextSliderView(mDemoSlider.getContext());
                demoSlider.description(featuredItem.getName()).image(featuredItem.getImageUrl())
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);
                mDemoSlider.addSlider(demoSlider);
            }

            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Left_Top);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener(this);
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

            readJsonStream(conn.getInputStream());

            int response = conn.getResponseCode();
            return "Success";
        } catch (Exception es) {
            System.out.println("ERROR: " + es.getMessage());
            return es.getMessage();
        }
    }

    public void readJsonStream(InputStream in) throws IOException {
        com.google.gson.stream.JsonReader reader = new com.google.gson.stream.JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                items.add(readItem(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    public Item readItem(com.google.gson.stream.JsonReader reader) throws IOException {
        Item i = new Item();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();

            switch (name)
            {
                case "Id": i.setId(reader.nextString()); break;
                case "Name": i.setName(reader.nextString()); break;
                case "Price": i.setPrice(reader.nextDouble()); break;
                case "Description": i.setDetails(reader.nextString()); break;
                case "Url": i.setImageUrl(reader.nextString()); break;
                default: reader.skipValue(); break ;
            }
        }
        reader.endObject();
        return i;
    }
    @Override
    public void onSliderClick(BaseSliderView slider) {
        Intent intent = new Intent(context.getApplicationContext(), ItemDetailActivity.class);
        intent.putExtra("itemName", items.get(index).getName());
        intent.putExtra("itemImage", items.get(index).getImageUrl());
        intent.putExtra("itemDetails", items.get(index).getDetails());
        intent.putExtra("itemID",items.get(index).getId());
        intent.putExtra("itemPrice", Utilities.getCurrency(items.get(index).getPrice()));
        context.startActivity(intent);
    }

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
}
