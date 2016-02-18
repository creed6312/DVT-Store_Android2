package com.jhb.dvt.dvt_store;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.jhb.dvt.dvt_store.Utils.Utilities;

public class CustomSlider extends BaseSliderView {

    private double mPrice ;

    public CustomSlider(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.custom_slider,null);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        TextView description = (TextView)v.findViewById(R.id.customDescription);
        TextView price = (TextView)v.findViewById(R.id.customPrice);
        description.setText(getDescription());
        price.setText(Utilities.getCurrency(getPrice()));
        bindEventAndShow(v, target);
        return v;
    }

    public void setPrice(double Price){
        mPrice = Price;
    }

    public double getPrice()
    {
        return mPrice;
    }
}
