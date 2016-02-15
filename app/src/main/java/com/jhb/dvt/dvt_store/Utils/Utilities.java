package com.jhb.dvt.dvt_store.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jhb.dvt.dvt_store.Models.BasketItem;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CreeD on 2016/02/10.
 */
public class Utilities {

    public static List<BasketItem> basketItems;
    public static int featuredIndex ;
    public static final String ApiKey = "40dccc93-b3d0-86d5-5efe-efd387306fcd";
    public static final String HostAddress = "http://192.168.88.10:4501";

    public static String getCurrency(double value)
    {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(value);
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
}
