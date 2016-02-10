package com.jhb.dvt.dvt_store.Utils;

import java.text.NumberFormat;

/**
 * Created by CreeD on 2016/02/10.
 */
public class Utilities {

    public static String getCurrency(double value)
    {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(value);
    }
}
