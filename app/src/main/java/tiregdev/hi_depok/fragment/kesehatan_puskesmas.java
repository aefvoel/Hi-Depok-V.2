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

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.adapter_searchData;
import tiregdev.hi_depok.model.itemObject_searchData;

/**
 * Created by Muhammad63 on 9/1/2017.
 */

public class kesehatan_puskesmas extends Fragment {

    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_kesehatan_puskesmas,container, false);
        setupAdapter();
        return v;
    }

    public void setupAdapter(){
        List<itemObject_searchData> rowListItem = getAllItemList();
        LinearLayoutManager lLayout = new LinearLayoutManager(getContext());

        RecyclerView rView = (RecyclerView)v.findViewById(R.id.rview);
        rView.setLayoutManager(lLayout);

        adapter_searchData rcAdapter = new adapter_searchData(getContext(), rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private List<itemObject_searchData> getAllItemList(){
        List<itemObject_searchData> allItems = new ArrayList<>();
        allItems.add(new itemObject_searchData("Puskesmas Harjamukti","Komp. Deppen, Kel. Harjamukti Kec. Cimanggis, Kota Depok, Jawa Barat",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("Puskesmas Kalimulya","Jl. Kampung Jati RT 04 RW 04 Kel. Kalimulya Kec. Cilodong, Kota Depok, Jawa Barat",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("Puskesmas Kemiri Muka","Jl. Ciliwung Kel. Kemiri Muka Kec. Beji, Kota Depok, Jawa Barat 16423",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("Puskesmas Mekarsari","Jl. Tipar Raya 1 RT 04 RW 09 Kel. Mekarsari Kel. Cimanggis, Kota Depok, Jawa Barat",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("Puskesmas Harjamukti","Komp. Deppen, Kel. Harjamukti Kec. Cimanggis, Kota Depok, Jawa Barat",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("Puskesmas Kalimulya","Jl. Kampung Jati RT 04 RW 04 Kel. Kalimulya Kec. Cilodong, Kota Depok, Jawa Barat",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("Puskesmas Kemiri Muka","Jl. Ciliwung Kel. Kemiri Muka Kec. Beji, Kota Depok, Jawa Barat 16423",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("Puskesmas Mekarsari","Jl. Tipar Raya 1 RT 04 RW 09 Kel. Mekarsari Kel. Cimanggis, Kota Depok, Jawa Barat",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));

        return allItems;
    }
}
