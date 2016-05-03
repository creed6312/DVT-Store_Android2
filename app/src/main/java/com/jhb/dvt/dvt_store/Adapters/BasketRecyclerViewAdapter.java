package com.jhb.dvt.dvt_store.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jhb.dvt.dvt_store.ItemDetailActivity;
import com.jhb.dvt.dvt_store.Models.BasketItem;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.R;
import com.jhb.dvt.dvt_store.Utils.Utilities;

import java.util.List;

/**
 * Created by CreeD on 2016/02/27.
 */
public class BasketRecyclerViewAdapter extends RecyclerView.Adapter<BasketRecyclerViewAdapter.ViewHolder> {

    private final List<Item> mBasket;
    private final TextView textCart;
    private final Activity context ;
    private final Button btnCheckOut ;

    public BasketRecyclerViewAdapter(List<Item> items, TextView cart, Activity context, Button btnCheckOut )  {
        mBasket = items;
        this.textCart = cart;
        this.context = context;
        this.btnCheckOut = btnCheckOut ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_item, parent, false);
        return new ViewHolder(view);
    }

    private void CalculateCost()
    {
        int cartCount = 0 ;
        double cartCost = 0 ;
        for (int i = 0 ; i < mBasket.size();i++)
        {
            for (int j = 0 ; j < Utilities.basketItems.size();j++)
            {
                if (Utilities.basketItems.get(j).getId().equals(mBasket.get(i).getId()))
                {
                    cartCount += Utilities.basketItems.get(j).getQuantity() ;
                    cartCost += (mBasket.get(i).getPrice() * Utilities.basketItems.get(j).getQuantity());
                }
            }
        }
        if (cartCount > 1)
            textCart.setText(cartCount + " items in Cart. (Total: " + Utilities.getCurrency(cartCost)  + ")");
        else
            textCart.setText(cartCount + " item in Cart. (Total: " + Utilities.getCurrency(cartCost)  + ")");
    }

    private BasketItem GetBasketItem(String ID)
    {
        for (BasketItem basketitem: Utilities.basketItems)
        {
            if (basketitem.getId().equals(ID))
                return basketitem;
        }
        return null ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Item item = mBasket.get(position);
        holder.mListTitle.setText(item.getName());
        holder.mPrice.setText(Utilities.getCurrency(item.getPrice()));
        final BasketItem basketItem = GetBasketItem(item.getId());

        if (basketItem != null)
            holder.mBasketQuantity.setText(String.valueOf(basketItem.getQuantity()));
        CalculateCost();

        holder.mBasketDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basketItem.decreaseQuantity();
                holder.mBasketQuantity.setText(String.valueOf(basketItem.getQuantity()));
                if (basketItem.getQuantity() < 1 )
                {
                    final BasketItem tempBasketItem = basketItem ;
                    Utilities.basketItems.remove(basketItem);
                    mBasket.remove(item);
                    notifyDataSetChanged();
                    Utilities.saveBasket(context);
                    btnCheckOut.setEnabled(false);

                    Snackbar snackbar = Snackbar
                            .make(holder.mView, "Item removed from cart.", Snackbar.LENGTH_LONG)
                            .setActionTextColor(ContextCompat.getColor(context,R.color.colorDvtYellow))
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    btnCheckOut.setEnabled(true);
                                    Snackbar snackbar1 = Snackbar.make(view, "Item restored.", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                    tempBasketItem.increaseQuantity();
                                    Utilities.basketItems.add(tempBasketItem);
                                    mBasket.add(item);
                                    notifyDataSetChanged();
                                    Utilities.saveBasket(context);
                                    CalculateCost();
                                }
                            }).setCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    if (Utilities.basketItems.size() == 0)
                                    {
                                        btnCheckOut.setEnabled(true);
                                        context.finish();
                                    }
                                    super.onDismissed(snackbar, event);
                                }
                            });
                    snackbar.show();
                }
                CalculateCost();
            }
        });

        holder.mBasketIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basketItem.increaseQuantity();
                holder.mBasketQuantity.setText(String.valueOf(basketItem.getQuantity()));
                Utilities.saveBasket(context);
                CalculateCost();
            }
        });

        Glide.with(holder.mListImage.getContext())
                .load(mBasket.get(position).getImageUrl())
                .crossFade()
                .into(new GlideDrawableImageViewTarget(holder.mListImage) {

                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                        holder.mProgress.setVisibility(View.INVISIBLE);
                        super.onResourceReady(drawable, anim);
                    }
                });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra("itemName", item.getName());
                intent.putExtra("itemImage", item.getImageUrl());
                intent.putExtra("itemDetails", item.getDetails());
                intent.putExtra("itemPrice", Utilities.getCurrency(item.getPrice()));
                intent.putExtra("itemID", item.getId());
                context.finish();
                context.startActivity(intent);
            }
        });
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mBasket.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mListImage ;
        public final TextView mListTitle, mPrice, mBasketQuantity;
        public final ProgressBar mProgress;
        public final FloatingActionButton mBasketIncrease,mBasketDecrease;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mListImage = (ImageView) view.findViewById(R.id.idBasketItem);
            mListTitle = (TextView) view.findViewById(R.id.basket_item_Title);
            mPrice = (TextView) view.findViewById(R.id.basket_item_Price);
            mProgress = (ProgressBar) view.findViewById(R.id.basket_loading_bar);
            mBasketIncrease = (FloatingActionButton) view.findViewById(R.id.basket_Increase);
            mBasketDecrease = (FloatingActionButton) view.findViewById(R.id.basket_Decrease);
            mBasketQuantity = (TextView) view.findViewById(R.id.basketQuantity);
        }
    }
}