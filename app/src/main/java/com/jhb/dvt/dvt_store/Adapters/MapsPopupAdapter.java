package com.jhb.dvt.dvt_store.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.jhb.dvt.dvt_store.R;

/**
 * Created by CreeD on 2016/02/26.
 */
public class MapsPopupAdapter implements GoogleMap.InfoWindowAdapter {
    private View popup = null;
    private LayoutInflater inflater = null;

    public MapsPopupAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return(null);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getInfoContents(Marker marker) {
        if (popup == null)
            popup = inflater.inflate(R.layout.popup, null);

        TextView tv= (TextView) popup.findViewById(R.id.title);
        tv.setText(marker.getTitle());
        tv= (TextView) popup.findViewById(R.id.snippet);
        tv.setText(marker.getSnippet());

        return(popup);
    }
}