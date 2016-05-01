package com.jhb.dvt.dvt_store.Utils;

import com.jhb.dvt.dvt_store.Constants.Constants;
import com.jhb.dvt.dvt_store.Models.BasketItem;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.Models.Location;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by SSardinha on 2/25/2016.
 */
public class ConnectionManager {

    private String checkSize(List items)
    {
        if (items.size() > 0)
            return "Success";
        else return "Fail";
    }

    public String JSonCall(HttpURLConnection conn, List<Item> items) throws IOException {
        new JsonRead().readJsonStream(items, conn.getInputStream());
        return checkSize(items);
    }

    public String JSonCallLoc(HttpURLConnection conn, List<Location> items) throws IOException {
        new JsonRead().readMapStream(items, conn.getInputStream());
        return checkSize(items);
    }

    public void JsonCall(HttpURLConnection conn, List<String> orders) throws IOException {
        new JsonRead().readOrderStream(orders, conn.getInputStream());
    }

    public HttpURLConnection HttpConnection(String Url) throws IOException {
        URL url = new URL(Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(Constants.CONNECTION_TIMEOUT);
        conn.setReadTimeout(Constants.READ_TIMEOUT);
        conn.setRequestMethod("GET");
        conn.connect();
        conn.getResponseCode();
        return conn ;
    }
}
