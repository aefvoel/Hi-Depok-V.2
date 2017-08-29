package tiregdev.hi_depok.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import tiregdev.hi_depok.adapter.adapter_karya;
import tiregdev.hi_depok.adapter.adapter_modul;
import tiregdev.hi_depok.model.itemObject_karya;
import tiregdev.hi_depok.model.itemObject_modul;

/**
 * Created by TiregDev on 23/08/2017.
 */

public class Master_modul extends Fragment {

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_master_modul, container, false);
        spinner();
        setupAdapter();
        return v;
    }

    public void spinner(){
        String searhdata[] = {"Semua Kategori",
                "Robotic",
                "Android",
                "Website",
                "Database",
                "Sejarah",
                "Biologi",
                "Fisika",
                "Kimia",
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
        List<itemObject_modul> rowListItem = getAllItemList();
        LinearLayoutManager lLayout = new LinearLayoutManager(getContext());

        RecyclerView rView = (RecyclerView)v.findViewById(R.id.view_modul);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        adapter_modul rcAdapter = new adapter_modul(getContext(), rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private List<itemObject_modul> getAllItemList(){
        List<itemObject_modul> allItems = new ArrayList<>();
        allItems.add(new itemObject_modul(getResources().getString(R.string.judul), getResources().getString(R.string.pengarang),getResources().getString(R.string.page),
                getResources().getString(R.string.viewer),getResources().getString(R.string.kategori),R.drawable.header_bg));
        allItems.add(new itemObject_modul(getResources().getString(R.string.judul1), getResources().getString(R.string.pengarang1),getResources().getString(R.string.page1),
                getResources().getString(R.string.viewer1),getResources().getString(R.string.kategori1),R.drawable.report_pohontumbang));
        allItems.add(new itemObject_modul(getResources().getString(R.string.judul2), getResources().getString(R.string.pengarang2),getResources().getString(R.string.page2),
                getResources().getString(R.string.viewer2),getResources().getString(R.string.kategori2),R.drawable.report_macet));
        allItems.add(new itemObject_modul(getResources().getString(R.string.judul3), getResources().getString(R.string.pengarang),getResources().getString(R.string.page1),
                getResources().getString(R.string.viewer),getResources().getString(R.string.kategori3),R.drawable.report_banjir));
        allItems.add(new itemObject_modul(getResources().getString(R.string.judul), getResources().getString(R.string.pengarang1),getResources().getString(R.string.page1),
                getResources().getString(R.string.viewer1),getResources().getString(R.string.kategori),R.drawable.header_profile));
        allItems.add(new itemObject_modul(getResources().getString(R.string.judul1), getResources().getString(R.string.pengarang2),getResources().getString(R.string.page2),
                getResources().getString(R.string.viewer2),getResources().getString(R.string.kategori1),R.drawable.report_banjir));
        allItems.add(new itemObject_modul(getResources().getString(R.string.judul2), getResources().getString(R.string.pengarang),getResources().getString(R.string.page1),
                getResources().getString(R.string.viewer),getResources().getString(R.string.kategori2),R.drawable.header_bg));

        return allItems;
    }
}
