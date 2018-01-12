package tiregdev.hi_depok.activity;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.adapter_kos;
import tiregdev.hi_depok.model.itemObject_kos;

public class sahabat_kos extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshRecyclerList;
    com.github.clans.fab.FloatingActionButton dss, price;
    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sahabat_kos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupAdapter();
        floatingButton();
        swipeRefresh();
        setupToolbar();
    }

    public void floatingButton(){
        dss = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.dss);
        price = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.price);

        dss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater factory = LayoutInflater.from(sahabat_kos.this);
                final View exitDialogView = factory.inflate(R.layout.dialog_rekomendasi_kos, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(sahabat_kos.this).create();
                exitDialog.setView(exitDialogView);
                exitDialogView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                    }
                });
                exitDialog.show();
//                Toast.makeText(sahabat_kos.this, "Sukses", Toast.LENGTH_SHORT).show();
            }
        });

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater factory = LayoutInflater.from(sahabat_kos.this);
                final View exitDialogView = factory.inflate(R.layout.dialog_filter_kos, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(sahabat_kos.this).create();
                exitDialog.setView(exitDialogView);
                exitDialogView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                    }
                });
                exitDialog.show();
//                Toast.makeText(sahabat_kos.this, "Bisa", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setupToolbar(){
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                sahabat_kos.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void swipeRefresh(){
        swipeRefreshRecyclerList = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_recycler_list);
        swipeRefreshRecyclerList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Do your stuff on refresh
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (swipeRefreshRecyclerList.isRefreshing())
                            swipeRefreshRecyclerList.setRefreshing(false);
                    }
                }, 5000);

            }
        });
    }

    public void setupAdapter(){
        List<itemObject_kos> rowListItem = getAllItemList();

        RecyclerView rView = (RecyclerView) findViewById(R.id.view_kos);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(sahabat_kos.this,2);
        rView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        adapter_kos rcAdapter = new adapter_kos(sahabat_kos.this, rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private List<itemObject_kos> getAllItemList(){
        List<itemObject_kos> allItems = new ArrayList<>();
        allItems.add(new itemObject_kos(getResources().getString(R.string.kos1), getResources().getString(R.string.alamat),getResources().getString(R.string.harga1), R.drawable.no_image));
        allItems.add(new itemObject_kos(getResources().getString(R.string.kos2), getResources().getString(R.string.alamat),getResources().getString(R.string.harga2), R.drawable.wisata));
        allItems.add(new itemObject_kos(getResources().getString(R.string.kos3), getResources().getString(R.string.alamat),getResources().getString(R.string.harga2), R.drawable.no_image));
        allItems.add(new itemObject_kos(getResources().getString(R.string.kos1), getResources().getString(R.string.alamat),getResources().getString(R.string.harga1), R.drawable.wisata));
        allItems.add(new itemObject_kos(getResources().getString(R.string.kos2), getResources().getString(R.string.alamat),getResources().getString(R.string.harga1), R.drawable.no_image));
        allItems.add(new itemObject_kos(getResources().getString(R.string.kos3), getResources().getString(R.string.alamat),getResources().getString(R.string.harga2), R.drawable.wisata));
        allItems.add(new itemObject_kos(getResources().getString(R.string.kos1), getResources().getString(R.string.alamat),getResources().getString(R.string.harga1), R.drawable.wisata));
        allItems.add(new itemObject_kos(getResources().getString(R.string.kos2), getResources().getString(R.string.alamat),getResources().getString(R.string.harga2), R.drawable.no_image));
        allItems.add(new itemObject_kos(getResources().getString(R.string.kos3), getResources().getString(R.string.alamat),getResources().getString(R.string.harga1), R.drawable.no_image));

        return allItems;
    }
}
