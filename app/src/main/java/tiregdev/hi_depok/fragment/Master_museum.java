package tiregdev.hi_depok.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.adapter_modul;
import tiregdev.hi_depok.adapter.adapter_museum;
import tiregdev.hi_depok.model.itemObject_modul;
import tiregdev.hi_depok.model.itemObject_museum;

/**
 * Created by TiregDev on 23/08/2017.
 */

public class Master_museum extends Fragment{

    View v;
    SwipeRefreshLayout swipeRefreshRecyclerList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_master_museum, container, false);
        spinner();
        setupAdapter();
        swipeRefresh();
        return v;
    }

    public void swipeRefresh(){
        swipeRefreshRecyclerList = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_recycler_list);
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

    public void spinner(){
        String searhdata[] = {"Semua Kategori",
                "Teknologi",
                "Kesehatan",
                "Lingkungan",
                "Pendidikan",
                "Umum"};
        MaterialSpinner spinner = (MaterialSpinner) v.findViewById(R.id.spinner_seacrh);
        spinner.setItems(searhdata);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void setupAdapter(){
        List<itemObject_museum> rowListItem = getAllItemList();

        RecyclerView rView = (RecyclerView)v.findViewById(R.id.view_museum);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        adapter_museum rcAdapter = new adapter_museum(getActivity(), rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private List<itemObject_museum> getAllItemList(){
        List<itemObject_museum> allItems = new ArrayList<>();
        allItems.add(new itemObject_museum(getResources().getString(R.string.museum1), getResources().getString(R.string.nama1),getResources().getString(R.string.tahun), R.drawable.wisata));
        allItems.add(new itemObject_museum(getResources().getString(R.string.museum2), getResources().getString(R.string.nama2),getResources().getString(R.string.tahun1), R.drawable.header_bg));
        allItems.add(new itemObject_museum(getResources().getString(R.string.museum3), getResources().getString(R.string.nama3),getResources().getString(R.string.tahun2), R.drawable.header_profile));
        allItems.add(new itemObject_museum(getResources().getString(R.string.museum1), getResources().getString(R.string.nama),getResources().getString(R.string.tahun), R.drawable.report_banjir));
        allItems.add(new itemObject_museum(getResources().getString(R.string.museum2), getResources().getString(R.string.nama1),getResources().getString(R.string.tahun1), R.drawable.report_macet));
        allItems.add(new itemObject_museum(getResources().getString(R.string.museum3), getResources().getString(R.string.nama2),getResources().getString(R.string.tahun2), R.drawable.report_pohontumbang));
        allItems.add(new itemObject_museum(getResources().getString(R.string.museum1), getResources().getString(R.string.nama3),getResources().getString(R.string.tahun), R.drawable.report_banjir));
        allItems.add(new itemObject_museum(getResources().getString(R.string.museum2), getResources().getString(R.string.nama1),getResources().getString(R.string.tahun1), R.drawable.wisata));
        allItems.add(new itemObject_museum(getResources().getString(R.string.museum3), getResources().getString(R.string.nama),getResources().getString(R.string.tahun2), R.drawable.header_profile));

        return allItems;
    }
}
