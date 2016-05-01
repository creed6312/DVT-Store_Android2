package com.jhb.dvt.dvt_store.Utils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.jhb.dvt.dvt_store.R;
import com.jhb.dvt.dvt_store.Utils.Utilities;

class CustomSlider extends BaseSliderView {

    private double mPrice;

    public CustomSlider(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_slider, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.daimajia_slider_image);
        TextView textViewDescription = (TextView) view.findViewById(R.id.customDescription);
        TextView textViewPrice = (TextView) view.findViewById(R.id.customPrice);
        textViewDescription.setText(getDescription());
        if (getPrice() == 0)
            textViewPrice.setText("Connection Timed Out!");
        else
            textViewPrice.setText(Utilities.getCurrency(getPrice()));
        bindEventAndShow(view, imageView);
        return view;
    }
    public void setPrice(double Price) {
        mPrice = Price;
    }
    private double getPrice() {
        return mPrice;
    }
}
