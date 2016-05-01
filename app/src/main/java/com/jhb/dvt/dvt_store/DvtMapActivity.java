package com.jhb.dvt.dvt_store;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jhb.dvt.dvt_store.Adapters.MapsPopupAdapter;
import com.jhb.dvt.dvt_store.Constants.Constants;
import com.jhb.dvt.dvt_store.Models.Location;
import com.jhb.dvt.dvt_store.Utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CreeD on 2016/02/26.
 */
public class DvtMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dvt_map);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final List<Location> dvtLocations = new ArrayList<>();

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Please wait", "Fetching Map Locations...", false, false);
        AsyncTaskCompat.executeParallel(new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Utilities.webServiceCallLocs(Constants.HostAddress + "/Api/GetLocations?apiToken=" + Constants.ApiKey, dvtLocations);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(final GoogleMap map) {
                        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.dvt_small);
                        Bitmap scaleBitmap = Bitmap.createScaledBitmap(bm,(int)(bm.getWidth()/1.5),(int)(bm.getHeight()/1.5), false);

                        map.setInfoWindowAdapter(new MapsPopupAdapter(getLayoutInflater()));
                        map.setMyLocationEnabled(true);

                        for (Location loc: dvtLocations )
                            map.addMarker(new MarkerOptions().position(new LatLng(loc.getLat(),loc.getLong()))
                                    .snippet(loc.getAddress().replace("\\n","\n"))
                                    .title(loc.getPlace()).icon(BitmapDescriptorFactory.fromBitmap(scaleBitmap)));

                        map.setOnInfoWindowCloseListener(new GoogleMap.OnInfoWindowCloseListener() {
                            @Override
                            public void onInfoWindowClose(Marker marker) {
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-29.122722, 25.031469), 5.0f));
                            }
                        });


                        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                if(!marker.isInfoWindowShown()) {
                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                            new LatLng(marker.getPosition().latitude, marker.getPosition().longitude), 14.0f));
                                    marker.showInfoWindow();
                                }
                                return true;
                            }
                        });
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-29.122722, 25.031469), 5.0f));
                        progressDialog.dismiss();
                    }
                });
                super.onPostExecute(v);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
