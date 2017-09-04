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

public class kesehatan_pijat extends Fragment {

    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_kesehatan_pijat,container, false);
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
        allItems.add(new itemObject_searchData("Natural Refleksi","Jl. Raya Pondok Duta No. 17A Kel. Bakti Jaya Kec. Sukmajaya, Kota Depok, Jawa Barat 16418",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("Panti Pijat Tuna Netra Berijazah Sida Mukti","Jl. Putri Tunggal Kel. Harjamukti Kec. Cimanggis, Kota Depok, Jawa Barat 16454",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("Pengobatan Tradisional Cimande","Jl. Studio Alam TVRI Gg. Tower Kec. Sukmajaya, Kota Depok, Jawa Barat",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("Shu'ka Sehat Reflexy","\tJl. Raya Bogor KM 31 No. 9 A Kel. Cisalak Ps. Kec. Cimanggis, Kota Depok, Jawa Barat 16452",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("Natural Refleksi","Jl. Raya Pondok Duta No. 17A Kel. Bakti Jaya Kec. Sukmajaya, Kota Depok, Jawa Barat 16418",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("Panti Pijat Tuna Netra Berijazah Sida Mukti","Jl. Putri Tunggal Kel. Harjamukti Kec. Cimanggis, Kota Depok, Jawa Barat 16454",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("Pengobatan Tradisional Cimande","Jl. Studio Alam TVRI Gg. Tower Kec. Sukmajaya, Kota Depok, Jawa Barat",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));
        allItems.add(new itemObject_searchData("Shu'ka Sehat Reflexy","\tJl. Raya Bogor KM 31 No. 9 A Kel. Cisalak Ps. Kec. Cimanggis, Kota Depok, Jawa Barat 16452",
                getResources().getString(R.string.jarak), R.drawable.massege_icon));

        return allItems;
    }
}
