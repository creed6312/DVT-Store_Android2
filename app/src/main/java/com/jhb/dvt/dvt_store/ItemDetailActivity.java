package com.jhb.dvt.dvt_store;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ImageView itemDetailImage  = (ImageView) findViewById(R.id.idItemDetailImage);
        TextView itemDetailName  = (TextView) findViewById(R.id.idItemDetailName);
        TextView itemDetailDetails  = (TextView) findViewById(R.id.idItemDetailDetails);
        TextView itemDetailPrice  = (TextView) findViewById(R.id.idItemDetailPrice);

        Glide.with(getApplicationContext())  .load(getIntent().getStringExtra("itemImage"))
                .crossFade().into(itemDetailImage);

        itemDetailName.setText(getIntent().getStringExtra("itemName"));
        itemDetailDetails.setText(getIntent().getStringExtra("itemDetails"));
        itemDetailPrice.setText(getIntent().getStringExtra("itemPrice"));

        floatingActionBuy();
    }

    public void floatingActionBuy()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "BUY NOW!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
