package com.jhb.dvt.dvt_store.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jhb.dvt.dvt_store.Adapters.BasketRecyclerViewAdapter;
import com.jhb.dvt.dvt_store.CheckOutActivity;
import com.jhb.dvt.dvt_store.Constants.Constants;
import com.jhb.dvt.dvt_store.Models.BasketItem;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.R;
import com.jhb.dvt.dvt_store.Utils.ConnectionManager;
import com.jhb.dvt.dvt_store.Utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CreeD on 2016/02/27.
 */
public class BasketFragment extends Fragment {

    private View view;
    private BasketRecyclerViewAdapter basketRecyclerViewAdapter;
    private List<Item> items ;
    private Snackbar snackbar  ;
    private Button btnCheckOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.basket_activity, container, false);
        RelativeLayout thisContainer = (RelativeLayout) view.findViewById(R.id.basket_container);
        TextView textCartTotal = (TextView) view.findViewById(R.id.basketTotal);
        btnCheckOut = (Button) view.findViewById(R.id.btnCheckOut);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkout();
            }
        });
        textCartTotal.setText("");

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.basket_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        items = new ArrayList<>();
        basketRecyclerViewAdapter = new BasketRecyclerViewAdapter(items,textCartTotal,getActivity(),btnCheckOut);
        recyclerView.setAdapter(basketRecyclerViewAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        String ids = "";
        for (int i = 0 ; i < Utilities.basketItems.size();i++)
        {
            ids += Utilities.basketItems.get(i).getId() ;
            if (i != Utilities.basketItems.size()-1)
                ids += ",";
        }

        final String finalIds = ids;
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Please wait", "Loading your basket...", false, false);
        AsyncTaskCompat.executeParallel(new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                     Utilities.webServiceCall(Constants.HostAddress + "/Api/GetCart?ids=" + finalIds + "&apiToken=" + Constants.ApiKey, items);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                basketRecyclerViewAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
                super.onPostExecute(v);
            }
        });

        return thisContainer;
    }

    private void Checkout()
    {
        final List<String> orders = new ArrayList<String>();
        final ProgressDialog progressCheckOut = ProgressDialog.show(getContext(), "Please wait", "Placing Order...", false, false);
        AsyncTaskCompat.executeParallel(new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String ids = "";
                    String qtys = "";
                    for (int i = 0 ; i < Utilities.basketItems.size();i++)
                    {
                        ids += Utilities.basketItems.get(i).getId();
                        qtys += Utilities.basketItems.get(i).getQuantity();

                        if (i != Utilities.basketItems.size()-1)
                        {
                            ids += ",";
                            qtys += ",";
                        }
                    }
                    Utilities.webServiceCallOrder(Constants.HostAddress + "/Api/CheckOut?ids=" + ids + "&qtys=" + qtys + "&apiToken=" + Constants.ApiKey, orders);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                progressCheckOut.dismiss();
                Utilities.basketItems.clear();
                Intent i = new Intent(getContext(), CheckOutActivity.class);
                i.putExtra("id", orders.get(0));
                i.putExtra("total",  orders.get(1));
                i.putExtra("count", orders.get(2));
                startActivity(i);
                getActivity().finish();
                super.onPostExecute(v);
            }
        });
    }

    private void Undo(Item itemTemp, BasketItem BasketTemp)
    {
        btnCheckOut.setEnabled(true);
        Snackbar snackbar1 = Snackbar.make(view, "Item restored.", Snackbar.LENGTH_SHORT);
        snackbar1.show();
        items.add(itemTemp);
        basketRecyclerViewAdapter.notifyDataSetChanged();
        Utilities.basketItems.add(BasketTemp);
        Utilities.saveBasket(getContext());
    }

    public boolean CheckSnack()
    {
       // if (snackbar != null)
       //     return !snackbar.isShownOrQueued();
        return true ;
    }

    private final ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
            final BasketItem BasketTemp = Utilities.basketItems.get(viewHolder.getLayoutPosition());
            final Item itemTemp = items.get(viewHolder.getLayoutPosition());

            btnCheckOut.setEnabled(false);
            System.out.println(itemTemp.getId() + " " + BasketTemp.getId());
            items.remove(itemTemp);
            Utilities.basketItems.remove(BasketTemp);
            Utilities.saveBasket(getContext());
            basketRecyclerViewAdapter.notifyDataSetChanged();

            snackbar = Snackbar
                    .make(view, "Item removed from cart.", Snackbar.LENGTH_LONG).setActionTextColor(ContextCompat.getColor(getContext(),R.color.colorDvtYellow))
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Undo(itemTemp,BasketTemp);
                        }
                    }).setCallback(new Snackbar.Callback() {

                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            if (items.size() == 0 && !snackbar.isShownOrQueued() && getActivity() != null)
                            {
                                btnCheckOut.setEnabled(true);
                                Utilities.basketItems.clear();
                                Utilities.saveBasket(getContext());
                                getActivity().finish();
                            }
                            super.onDismissed(snackbar, event);
                        }
                    });
            snackbar.show();
        }
    };
}
