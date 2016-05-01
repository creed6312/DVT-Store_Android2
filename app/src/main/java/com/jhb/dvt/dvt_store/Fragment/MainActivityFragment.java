package com.jhb.dvt.dvt_store.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.jhb.dvt.dvt_store.R;
import com.jhb.dvt.dvt_store.Utils.SlideLoader;

/**
 * Created by CreeD on 2016/02/27.
 */
public class MainActivityFragment extends Fragment {

    private View view;
    SliderLayout mDemoSlider ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_main, container, false);
        createSlider();
        LoadNonFeaturedItems();
        return view;
    }

    private void createSlider() {
        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
        mDemoSlider.stopAutoCycle();
        mDemoSlider.setCustomIndicator((PagerIndicator) view.findViewById(R.id.custom_indicator));
        new SlideLoader(mDemoSlider, view);
    }

    public void startSlider()
    {
        mDemoSlider.startAutoCycle();
    }

    private void LoadNonFeaturedItems()
    {
        ItemFragment frag = new ItemFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, frag);
        transaction.commit();
    }
}
