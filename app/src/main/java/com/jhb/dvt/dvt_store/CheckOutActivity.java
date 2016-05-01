package com.jhb.dvt.dvt_store;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jhb.dvt.dvt_store.Utils.Utilities;

import org.w3c.dom.Text;

/**
 * Created by SSardinha on 3/8/2016.
 */
public class CheckOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_activity);

        TextView checkoutID = (TextView) findViewById(R.id.txtCheckOutID);
        TextView checkoutCount = (TextView) findViewById(R.id.txtCheckOutCount);
        TextView checkoutTotal = (TextView) findViewById(R.id.txtCheckOutTotal);

        Button btnCheckoutDone = (Button) findViewById(R.id.btnCheckOutDone);
        btnCheckoutDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String ID = "Order ID: <font color='blue'>"+getIntent().getStringExtra("id")+"</font>";
        String Total = "Total cost: <font color='blue'>R"+getIntent().getStringExtra("total")+".00</font>";
        String Count = "Total amount of items: <font color='blue'>"+ getIntent().getStringExtra("count")+"</font>";

        Utilities.saveBasket(getApplicationContext());
        checkoutID.setText(Html.fromHtml(ID), TextView.BufferType.SPANNABLE);
        checkoutTotal.setText(Html.fromHtml(Total), TextView.BufferType.SPANNABLE);
        checkoutCount.setText(Html.fromHtml(Count), TextView.BufferType.SPANNABLE);
    }
}
