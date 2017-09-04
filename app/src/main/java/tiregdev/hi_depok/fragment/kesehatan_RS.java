package tiregdev.hi_depok.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.adapter_karya;
import tiregdev.hi_depok.adapter.adapter_searchData;
import tiregdev.hi_depok.model.itemObject_karya;
import tiregdev.hi_depok.model.itemObject_searchData;

/**
 * Created by Muhammad63 on 9/1/2017.
 */

public class kesehatan_RS extends Fragment {

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_kesehatan_rs,container, false);
        setupAdapter();
        return v;
    }

    public void setupAdapter(){
        List<itemObject_searchData> rowListItem = getAllItemList();
        LinearLayoutManager lLayout = new LinearLayoutManager(getContext());

        RecyclerView rView = (RecyclerView)v.findViewById(R.id.view_rs);
        rView.setLayoutManager(lLayout);

        adapter_searchData rcAdapter = new adapter_searchData(getContext(), rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private List<itemObject_searchData> getAllItemList(){
        List<itemObject_searchData> allItems = new ArrayList<>();
        allItems.add(new itemObject_searchData("RS Permata Depok","Jalan Raya Muchtar No. 22, Sawangan Baru, Sawangan, Depok, Jawa Barat,6511",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("RS Mitra Keluarga","Jl. Margonda Raya Pancoran Mas, Depok",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("RS Setya Bhakti","Jl. Raya Bogor Km. 30 Kelurahan Mekarsari Kecamatan Cimanggis Kota Depok 16952",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("RS Hermina Depok","Jalan Siliwangi No. 50, Pancoran MAS, Kota Depok, Jawa Barat 16436",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("RS Permata Depok","Jalan Raya Muchtar No. 22, Sawangan Baru, Sawangan, Depok, Jawa Barat,6511",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("RS Mitra Keluarga","Jl. Margonda Raya Pancoran Mas, Depok",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("RS Setya Bhakti","Jl. Raya Bogor Km. 30 Kelurahan Mekarsari Kecamatan Cimanggis Kota Depok 16952",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("RS Hermina Depok","Jalan Siliwangi No. 50, Pancoran MAS, Kota Depok, Jawa Barat 16436",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));

        return allItems;
    }
}
