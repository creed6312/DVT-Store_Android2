package com.jhb.dvt.dvt_store.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.jhb.dvt.dvt_store.Adapters.ItemRecyclerViewAdapter;
import com.jhb.dvt.dvt_store.Models.Item;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created by CreeD on 2016/02/12.
 */
public class ViewLoader extends AsyncTask<Void, Void, String> {

    private ItemRecyclerViewAdapter adapter;
    private static List<Item> items;
    private String Call;
    private ProgressDialog progressItems;

    public ViewLoader(ItemRecyclerViewAdapter adapter, Context context, List<Item> items, String call) {
        progressItems = ProgressDialog.show(context, "Please wait", "Loading product catalog...", false, false);
        this.adapter = adapter;
        this.items = items;
        this.Call = call;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            return webServiceCall(Utilities.HostAddress + "/Api/" + Call + "?apiToken=" + Utilities.ApiKey);
        } catch (IOException e) {
            return "Failed";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        progressItems.cancel();
        adapter.notifyDataSetChanged();
        super.onPostExecute(s);
    }

    public static String webServiceCall(String myUrl) throws IOException
    {
        ConnectionManager con = new ConnectionManager();
        return con.JSonCall(con.HttpConnection(myUrl),items);
    }
}
