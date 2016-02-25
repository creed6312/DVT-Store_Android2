package com.jhb.dvt.dvt_store.Utils;

import com.jhb.dvt.dvt_store.Models.Item;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by SSardinha on 2/25/2016.
 */
public class ConnectionManager {

    public String JSonCall(HttpURLConnection conn, List<Item> items) throws IOException {
        new JsonRead().readJsonStream(items, conn.getInputStream());
        if (items.size() > 0)
            return "Success";
        else return "Fail";
    }

    public HttpURLConnection HttpConnection(String Url) throws IOException {
        URL url = new URL(Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(1000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        conn.getResponseCode();
        return conn ;
    }
}
