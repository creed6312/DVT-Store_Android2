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
    public static final String ApiKey = "0ac44d4d-eb43-3e0e-fb86-ba427bee5eb4";
    public static final String HostAddress = "http://dvtstorage.cloudapp.net";

    public static String getCurrency(double value)
    {
        NumberFormat formatString = NumberFormat.getInstance();
        formatString.setMinimumFractionDigits(2);
        return "R" + formatString.format(value);
    }

    public static void getBasket(Context appContext)
    {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(appContext);
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("myBasket", "");
        Type type = new TypeToken<List<BasketItem>>(){}.getType();
        basketItems = gson.fromJson(json, type);

        for (int i = 0 ; i < basketItems.size(); i++)
        {
            System.out.println(basketItems.get(i).getQuantity());
        }

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
