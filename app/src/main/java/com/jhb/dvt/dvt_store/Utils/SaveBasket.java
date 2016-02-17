package com.jhb.dvt.dvt_store.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * Created by Cindy on 2/17/2016.
 */
public class SaveBasket {

    public static void saveBasket(Context appContext)
    {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(appContext);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Utilities.basketItems);
        prefsEditor.commit();
    }

}
