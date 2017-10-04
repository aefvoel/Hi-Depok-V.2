package tiregdev.hi_depok.activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.adapter_kos;
import tiregdev.hi_depok.adapter.adapter_museum;
import tiregdev.hi_depok.model.itemObject_kos;
import tiregdev.hi_depok.model.itemObject_museum;

public class sahabat_kos extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshRecyclerList;
    com.github.clans.fab.FloatingActionButton dss, price;
    LayoutInflater rekomen, filter;
    View extDialog;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sahabat_kos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupAdapter();
        floatingButton();
        swipeRefresh();
        setupToolbar();
//        spinnerFilter();
//        spinnerDSS();
    }

    public void spinnerDSS(){
        String dss[] = {"Harga",
                "Jarak",
                "Fasilitas"};
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.p1);
        spinner.setItems(dss);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_SHORT).show();
            }
        });

        String dss2[] = {"Jarak",
                "Harga",
                "Fasilitas"};
        MaterialSpinner spinner2 = (MaterialSpinner) findViewById(R.id.p2);
        spinner2.setItems(dss2);
        spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_SHORT).show();
            }
        });

        String dss3[] = {"Fasilitas",
                "Jarak",
                "Harga"};
        MaterialSpinner spinner3 = (MaterialSpinner) findViewById(R.id.p3);
        spinner3.setItems(dss3);
        spinner3.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void spinnerFilter(){
        String gender[] = {"Semua",
                "Campuran",
                "Putra",
                "Putri"};
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.gender_spinner);
        spinner.setItems(gender);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_SHORT).show();
            }
        });

        String minHarga[] = {"100.000",
                "200.000",
                "300.000",
                "400.000",
                "500.000",
                "600.000",
                "700.000",
                "800.000",
                "900.000",
                "1.000.000",
                "1.100.000",
                "1.200.000",
                "1.300.000",
                "1.400.000",
                "1.500.000",
                "1.600.000",
                "1.700.000",
                "1.800.000",
                "1.900.000",
                "2.000.000"};
        MaterialSpinner spinner2 = (MaterialSpinner) findViewById(R.id.minHarga_spinner);
        spinner2.setItems(minHarga);
        spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_SHORT).show();
            }
        });

        String maxHarga[] = {"100.000",
                "200.000",
                "300.000",
                "400.000",
                "500.000",
                "600.000",
                "700.000",
                "800.000",
                "900.000",
                "1.000.000",
                "1.100.000",
                "1.200.000",
                "1.300.000",
                "1.400.000",
                "1.500.000",
                "1.600.000",
                "1.700.000",
                "1.800.000",
                "1.900.000",
                "2.000.000"};
        MaterialSpinner spinner3 = (MaterialSpinner) findViewById(R.id.maxHarga_spinner);
        spinner3.setItems(maxHarga);
        spinner3.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void floatingButton(){
        dss = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.dss);
        price = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.price);

        dss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final LayoutInflater factory = LayoutInflater.from(sahabat_kos.this);
//                final View exitDialogView = factory.inflate(R.layout.dialog_rekomendasi_kos, null);
//                final AlertDialog exitDialog = new AlertDialog.Builder(sahabat_kos.this).create();
//                exitDialog.setView(exitDialogView);
//                exitDialogView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        exitDialog.dismiss();
//                    }
//                });
//                exitDialog.show();
                Toast.makeText(sahabat_kos.this, "Sukses", Toast.LENGTH_SHORT).show();
            }
        });

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                filter = LayoutInflater.from(sahabat_kos.this);
//                extDialog = filter.inflate(R.layout.dialog_filter_kos, null);
//                alertDialog = new AlertDialog.Builder(sahabat_kos.this).create();
//                alertDialog.setView(extDialog);
//                extDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertDialog.dismiss();
//                    }
//                });
//                alertDialog.show();
                Toast.makeText(sahabat_kos.this, "Bisa", Toast.LENGTH_SHORT).show();
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
