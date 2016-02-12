package com.jhb.dvt.dvt_store;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.jhb.dvt.dvt_store.Adapters.SimpleItemRecyclerViewAdapter;
import com.jhb.dvt.dvt_store.Fragment.ItemFragment;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.Utils.Json;
import com.jhb.dvt.dvt_store.Utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener,
        NavigationView.OnNavigationItemSelectedListener {

    private SliderLayout mDemoSlider ;
    private List<Item> featuredItems ;
    private int featuredIndex ;

    FragmentTransaction transaction;
    Fragment ItemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Utilities.getBasket(getApplicationContext());
        createSlider();
        addFragments();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void addFragments() {
        ItemFragment = new ItemFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, ItemFragment);
        transaction.commit();
    }

    private void createSlider()
    {
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        featuredItems = new ArrayList<>();
        featuredItems.add(new Item("8","Dell XPS 13",getString(R.string.item3Description),"http://www.notebookcheck.net/uploads/tx_nbc2/dellXPS13-9343_2.jpg",32000));
        featuredItems.add(new Item("9","Dell Latitude",getString(R.string.itemDescription2),"http://liliputing.com/wp-content/uploads/2015/12/dell-12-7000_02.jpg",24000));
        featuredItems.add(new Item("10","Asus  Transformer",getString(R.string.item1Description),"http://4.bp.blogspot.com/-dxEEpqWNetg/VALP0rJZInI/AAAAAAAAAls/ZTr1CzIiA4U/s1600/laptop_tablet_in_one_asus_transformer_book_t100.png",12000));

        for (Item featuredItem : featuredItems){
            TextSliderView demoSlider = new TextSliderView(this);
            demoSlider.description(featuredItem.getName()).image(featuredItem.getImageUrl())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            mDemoSlider.addSlider(demoSlider);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Left_Top);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
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
}
