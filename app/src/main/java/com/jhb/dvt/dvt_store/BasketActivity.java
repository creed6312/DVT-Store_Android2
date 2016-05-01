package com.jhb.dvt.dvt_store;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.jhb.dvt.dvt_store.Fragment.BasketFragment;

/**
 * Created by CreeD on 2016/02/27.
 */
public class BasketActivity extends BaseActivity {

    BasketFragment frag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        frag = new BasketFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, frag);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.action_cart);
        item.setEnabled(false);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_blank).setVisible(true);

        return true;
    }

}
