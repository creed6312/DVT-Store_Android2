package com.jhb.dvt.dvt_store.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Xml;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jhb.dvt.dvt_store.Adapters.SimpleItemRecyclerViewAdapter;
import com.jhb.dvt.dvt_store.MainActivity;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.R;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SSardinha on 2016-02-12.
 */
public class Json extends AsyncTask<String, Void, String> {

    SimpleItemRecyclerViewAdapter adapter ;
    List<Item> items ;

    public Json(SimpleItemRecyclerViewAdapter adapter, List<Item> items) {
        this.adapter = adapter;
        this.items = items ;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return doPost("http://192.168.1.132/Api/GetOrderById?id=1&apiToken=40dccc93-b3d0-86d5-5efe-efd387306fcd");
        } catch (IOException e) {
            return "Unable to retrieve data. URL may be invalid.";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        adapter.notifyDataSetChanged();
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

            new JsonRead().readJsonStream(items,conn.getInputStream());

            int response = conn.getResponseCode();
            return "Success";
        } catch (Exception es) {
            System.out.println("ERROR: " + es.getMessage());
            return es.getMessage();
        }
    }
}