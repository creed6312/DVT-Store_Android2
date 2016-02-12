package com.jhb.dvt.dvt_store;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jhb.dvt.dvt_store.Models.BasketItem;
import com.jhb.dvt.dvt_store.Utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailActivity extends AppCompatActivity {

    TextView itemDetailQuantity ;
    FloatingActionButton floatingActionBuy,floatActionMinus,floatActionPlus ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ImageView itemDetailImage  = (ImageView) findViewById(R.id.idItemDetailImage);
        TextView itemDetailName  = (TextView) findViewById(R.id.idItemDetailName);
        TextView itemDetailDetails  = (TextView) findViewById(R.id.idItemDetailDetails);
        TextView itemDetailPrice  = (TextView) findViewById(R.id.idItemDetailPrice);
        itemDetailQuantity  = (TextView) findViewById(R.id.idDetailQuantity);
        floatingActionBuy  = (FloatingActionButton) findViewById(R.id.floatingActionBuy);
        floatActionPlus  = (FloatingActionButton) findViewById(R.id.floatActionPlus);
        floatActionMinus  = (FloatingActionButton) findViewById(R.id.floatActionMinus);

        checkQuantity();

        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("itemImage"))
                .crossFade().into(itemDetailImage);

        itemDetailName.setText(getIntent().getStringExtra("itemName"));
        itemDetailDetails.setText(Html.fromHtml(getIntent().getStringExtra("itemDetails")));
        itemDetailPrice.setText(getIntent().getStringExtra("itemPrice"));

        floatingActionBuy();
        floatActionIncreaseDecrease();
    }

    public void checkQuantity()
    {
        String id = getIntent().getStringExtra("itemID");
        for (int i=0; i< Utilities.basketItems.size();i++) {
            if (Utilities.basketItems.get(i).getId().equals(id))
            {
                showQuantity();
                itemDetailQuantity.setText(String.valueOf(Utilities.basketItems.get(i).getQuantity()));
                return;
            }
        }
        hideQuantity();
    }

    private void hideQuantity()
    {
        floatingActionBuy.setVisibility(View.VISIBLE);
        floatActionMinus.setVisibility(View.INVISIBLE);
        floatActionPlus.setVisibility(View.INVISIBLE);
        itemDetailQuantity.setVisibility(View.INVISIBLE);
    }

    private void showQuantity()
    {
        floatingActionBuy.setVisibility(View.INVISIBLE);
        itemDetailQuantity.setVisibility(View.VISIBLE);
        floatActionMinus.setVisibility(View.VISIBLE);
        floatActionPlus.setVisibility(View.VISIBLE);
    }

    public void floatActionIncreaseDecrease()
    {
        floatActionPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added to Cart!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                for (int i = 0; i < Utilities.basketItems.size(); i++) {
                    if (Utilities.basketItems.get(i).getId().equals(getIntent().getStringExtra("itemID"))) {
                        Utilities.basketItems.get(i).increaseQuantity();
                        itemDetailQuantity.setText(String.valueOf(Utilities.basketItems.get(i).getQuantity()));
                        Utilities.saveBasket(getApplicationContext());
                        return;
                    }
                }
            }
        });

        floatActionMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i< Utilities.basketItems.size();i++) {
                    if ( Utilities.basketItems.get(i).getId().equals(getIntent().getStringExtra("itemID")))
                    {
                        Utilities.basketItems.get(i).decreaseQuantity();
                        itemDetailQuantity.setText(String.valueOf(Utilities.basketItems.get(i).getQuantity()));
                        if (Utilities.basketItems.get(i).getQuantity() < 1)
                        {
                            Snackbar.make(view, "Removed from Cart!", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                            Utilities.basketItems.remove(i);
                            hideQuantity();
                        }
                        Utilities.saveBasket(getApplicationContext());
                        return;
                    }
                }
            }
        });
    }

    public void floatingActionBuy()
    {
        floatingActionBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added to Cart!", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                Utilities.basketItems.add(new BasketItem(getIntent().getStringExtra("itemID")));
                Utilities.saveBasket(getApplicationContext());
                itemDetailQuantity.setText("1");
                showQuantity();
            }
        });
    }
}
