package com.jhb.dvt.dvt_store;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jhb.dvt.dvt_store.Models.BasketItem;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.Utils.Utilities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener,
        NavigationView.OnNavigationItemSelectedListener {

    private SliderLayout mDemoSlider ;
    private List<Item> featuredItems ;
    private int featuredIndex ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Utilities.getBasket(getApplicationContext());
        createSlider();
        setupRecyclerView();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void createSlider()
    {
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        featuredItems = new ArrayList<>();
        featuredItems.add(new Item("1","Dell XPS 13",getString(R.string.item3Description),"http://www.notebookcheck.net/uploads/tx_nbc2/dellXPS13-9343_2.jpg",32000));
        featuredItems.add(new Item("2","Dell Latitude",getString(R.string.itemDescription2),"http://liliputing.com/wp-content/uploads/2015/12/dell-12-7000_02.jpg",24000));
        featuredItems.add(new Item("3","Asus  Transformer",getString(R.string.item1Description),"http://4.bp.blogspot.com/-dxEEpqWNetg/VALP0rJZInI/AAAAAAAAAls/ZTr1CzIiA4U/s1600/laptop_tablet_in_one_asus_transformer_book_t100.png",12000));

        for (Item featuredItem : featuredItems){
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.description(featuredItem.getName()).image(featuredItem.getImageUrl())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Left_Top);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    private void setupRecyclerView() {
        View recyclerView = findViewById(R.id.item_list);
        ((RecyclerView)recyclerView).setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        List<Item> items = new ArrayList<>();
        items.add(new Item("1","Very Cheap Mug, big and black in color","This is the Cheapest mug you can buy","https://larvalsubjects.files.wordpress.com/2010/04/450x450std-cobalt-blue-mug.jpg",4));
        items.add(new Item("2","Cheap Mug round and only comes in white", "This Mug is Cheap","http://www.9andthreequarters.co.za/media/com_hikashop/upload/standard_coffee_mug.jpg",12));
        items.add(new Item("3","Fallout Mug if fan of fall out the game","This is a Sweet Mug","http://store.bethsoft.com/media/catalog/product/b/a/barware-mug-fo-thumbsup-full_1.jpg",180));
        items.add(new Item("4","Crazy Eyes comes with free cookies and usb","This Mug comes with free cookies","https://ak-hdl.buzzfed.com/static/enhanced/webdr01/2013/3/8/14/enhanced-buzz-5070-1362770931-3.jpg",40));
        items.add(new Item("5","The Doughnut special shaped mug","This is the Awesome Doughnut Mug","http://cdn.home-designing.com/wp-content/uploads/2015/10/donut-mug-600x600.jpg",60));
        items.add(new Item("6","Cow Mug in many different colors","This mug is perfect if you into cows","http://www.cowdepot.com/bostmugrubotsm.jpg",35));
        items.add(new Item("7","Asus  Transformer, This mug is perfect if you into cows, Asus  Transformer, This mug is perfect if you into cows , Asus  Transformer, This mug is perfect if you into cows",getString(R.string.item1Description),"http://4.bp.blogspot.com/-dxEEpqWNetg/VALP0rJZInI/AAAAAAAAAls/ZTr1CzIiA4U/s1600/laptop_tablet_in_one_asus_transformer_book_t100.png",12000));

        assert recyclerView != null;
        ((RecyclerView) recyclerView).setAdapter(new SimpleItemRecyclerViewAdapter(items));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_category1) {
        } else if (id == R.id.nav_category2) {

        } else if (id == R.id.nav_category2) {

        } else if (id == R.id.nav_extra1) {

        } else if (id == R.id.nav_extra2) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Intent intent = new Intent(getApplicationContext(), ItemDetailActivity.class);
        intent.putExtra("itemName", featuredItems.get(featuredIndex).getName());
        intent.putExtra("itemImage", featuredItems.get(featuredIndex).getImageUrl());
        intent.putExtra("itemDetails", featuredItems.get(featuredIndex).getDetails());
        intent.putExtra("itemID",featuredItems.get(featuredIndex).getId());
        intent.putExtra("itemPrice", Utilities.getCurrency(featuredItems.get(featuredIndex).getPrice()));
        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        featuredIndex = position ;
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            doSearch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void doSearch()
    {

    }

    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Item> mValues;

        public SimpleItemRecyclerViewAdapter(List<Item> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mListTitle.setText(mValues.get(position).getName());
            holder.mPrice.setText(Utilities.getCurrency(mValues.get(position).getPrice()));

            Glide.with(holder.mListImage.getContext())
                    .load(mValues.get(position).getImageUrl())
                    .crossFade()
                    .into(holder.mListImage);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra("itemName", holder.mItem.getName());
                    intent.putExtra("itemImage", holder.mItem.getImageUrl());
                    intent.putExtra("itemDetails", holder.mItem.getDetails());
                    intent.putExtra("itemPrice", Utilities.getCurrency(holder.mItem.getPrice()));
                    intent.putExtra("itemID", holder.mItem.getId());
                    context.startActivity(intent);
                }
            });
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
            public Item mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mListImage = (ImageView) view.findViewById(R.id.idListImage);
                mListTitle = (TextView) view.findViewById(R.id.idListTitle);
                mPrice = (TextView)view.findViewById(R.id.idListPrice);
            }

        }
    }


}
