package tiregdev.hi_depok.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.adapter_ntpd;
import tiregdev.hi_depok.model.itemObject_ntpd;

/**
 * Created by Muhammad63 on 10/6/2017.
 */

public class ntpd_puskesmas extends Fragment {

    RecyclerView rView;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_ntpd_puskesmas, container, false);
        setUpAdapter();
        return v;
    }

    public void setUpAdapter(){
        List<itemObject_ntpd> rowListItem = getAllItemList();
        LinearLayoutManager lLayout = new LinearLayoutManager(getActivity());

        rView = (RecyclerView)v.findViewById(R.id.rview);
        rView.setLayoutManager(lLayout);

        adapter_ntpd rcAdapter = new adapter_ntpd(getActivity(), rowListItem);
        rView.setAdapter(rcAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), lLayout.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.line_view));
        rView.addItemDecoration(dividerItemDecoration);
    }

    private List<itemObject_ntpd> getAllItemList(){
        List<itemObject_ntpd> allItems = new ArrayList<>();
        allItems.add(new itemObject_ntpd("Kecamatan Limo","02177880516"));
        allItems.add(new itemObject_ntpd("Kecamatan Limo","02177880516"));
        allItems.add(new itemObject_ntpd("Kecamatan Limo","02177880516"));
        allItems.add(new itemObject_ntpd("Kecamatan Limo","02177880516"));
        allItems.add(new itemObject_ntpd("Kecamatan Limo","02177880516"));
        allItems.add(new itemObject_ntpd("Kecamatan Limo","02177880516"));
        allItems.add(new itemObject_ntpd("Kecamatan Limo","02177880516"));
        allItems.add(new itemObject_ntpd("Kecamatan Limo","02177880516"));

        return allItems;
    }
}
