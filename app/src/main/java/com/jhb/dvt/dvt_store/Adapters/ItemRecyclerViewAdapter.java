package com.jhb.dvt.dvt_store.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jhb.dvt.dvt_store.ItemDetailActivity;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.R;
import com.jhb.dvt.dvt_store.Utils.Utilities;

import java.util.List;

/**
 * Created by SSardinha on 2016-02-12.
 */
public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {
    private final List<Item> mValues;

    public ItemRecyclerViewAdapter(List<Item> items)  {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Item item = mValues.get(position);
        holder.mListTitle.setText(item.getName());
        holder.mPrice.setText(Utilities.getCurrency(item.getPrice()));
        Glide.with(holder.mListImage.getContext())
                .load(mValues.get(position).getImageUrl())
                .crossFade()
                .into(new GlideDrawableImageViewTarget(holder.mListImage) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                    }

                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                        holder.mProgress.setVisibility(View.INVISIBLE);
                        super.onResourceReady(drawable, anim);
                    }
                });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra("itemName", item.getName());
                intent.putExtra("itemImage", item.getImageUrl());
                intent.putExtra("itemDetails", item.getDetails());
                intent.putExtra("itemPrice", Utilities.getCurrency(item.getPrice()));
                intent.putExtra("itemID", item.getId());
                context.startActivity(intent);
            }
        });
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mListImage;
        public final TextView mListTitle;
        public final TextView mPrice;
        public final ProgressBar mProgress;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mListImage = (ImageView) view.findViewById(R.id.idListImage);
            mListTitle = (TextView) view.findViewById(R.id.idListTitle);
            mPrice = (TextView) view.findViewById(R.id.idListPrice);
            mProgress = (ProgressBar) view.findViewById(R.id.item_loading_bar);
        }
    }
}
