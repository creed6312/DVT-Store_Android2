package com.jhb.dvt.dvt_store.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jhb.dvt.dvt_store.Models.BasketItem;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.Models.Location;
import com.jhb.dvt.dvt_store.R;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CreeD on 2016/02/10.
 */
public class Utilities {
    public static List<BasketItem> basketItems;

    public static String getCurrency(double value)
    {
        NumberFormat formatString = NumberFormat.getInstance();
        formatString.setMinimumFractionDigits(2);
        return "R" + formatString.format(value);
    }

    public static String webServiceCall(String myUrl, List<Item> items) throws IOException
    {
        ConnectionManager con = new ConnectionManager();
        return con.JSonCall(con.HttpConnection(myUrl), items);
    }

    public static String webServiceCallLocs(String myUrl, List<Location> items) throws IOException
    {
        ConnectionManager con = new ConnectionManager();
        return con.JSonCallLoc(con.HttpConnection(myUrl), items);
    }

    public static void webServiceCallOrder(String myUrl, List<String> orders) throws IOException
    {
        ConnectionManager con = new ConnectionManager();
        con.JsonCall(con.HttpConnection(myUrl), orders);
    }

    public static void CheckCart(Menu menu)
    {
        if (menu != null)
        {
            MenuItem item = menu.findItem(R.id.action_cart);
            if (Utilities.basketItems.size() == 0 )
                item.setVisible(false);
            else item.setVisible(true);
        }
    }

    public static void getBasket(Context appContext)
    {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(appContext);
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("myBasket", "");
        Type type = new TypeToken<List<BasketItem>>(){}.getType();
        basketItems = gson.fromJson(json, type);

        if (json.equals(""))
            basketItems = new ArrayList<>();
    }

    public static void saveBasket(Context appContext)
    {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(appContext);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(basketItems);
        prefsEditor.putString("myBasket", json);
        prefsEditor.commit();
    }
}
