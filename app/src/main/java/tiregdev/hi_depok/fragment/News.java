package tiregdev.hi_depok.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andexert.library.RippleView;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.maps;
import tiregdev.hi_depok.activity.pesan;
import tiregdev.hi_depok.adapter.adapter_news;
import tiregdev.hi_depok.model.itemObject_news;

import static tiregdev.hi_depok.activity.MenuActivity.results;

/**
 * Created by Muhammad63 on 8/3/2017.
 */

public class News extends Fragment {
    View v;
    ImageView ham;
    public static News newInstance(){
        News fragment = new News();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_news, null);
        setUpAdapter();
        setMenuHamburger();
        setPesanLink();

        return v;
    }

    public void setPesanLink(){
        final RippleView rippleViews = (RippleView) v.findViewById(R.id.pesan);
        rippleViews.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), pesan.class);
                startActivity(w);
            }
        });
    }

    public void setMenuHamburger(){
        ham = (ImageView) v.findViewById(R.id.menu);
        ham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results.openDrawer();
            }
        });
    }

    public void setUpAdapter(){
        List<itemObject_news> rowListItem = getAllItemList();
        LinearLayoutManager lLayout = new LinearLayoutManager(getContext());

        RecyclerView rView = (RecyclerView)v.findViewById(R.id.view_news);
        rView.setLayoutManager(lLayout);

        adapter_news rcAdapter = new adapter_news(getContext(), rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private List<itemObject_news> getAllItemList(){
        List<itemObject_news> allItems = new ArrayList<>();
        allItems.add(new itemObject_news(getResources().getString(R.string.news2), getResources().getString(R.string.portal2),getResources().getString(R.string.time1), R.drawable.report_banjir));
        allItems.add(new itemObject_news(getResources().getString(R.string.news3), getResources().getString(R.string.portal3),getResources().getString(R.string.time1), R.drawable.report_macet));
        allItems.add(new itemObject_news(getResources().getString(R.string.news4), getResources().getString(R.string.portal1),getResources().getString(R.string.time2), R.drawable.report_pohontumbang));
        allItems.add(new itemObject_news(getResources().getString(R.string.news5), getResources().getString(R.string.portal2),getResources().getString(R.string.time1), R.drawable.report_banjir));
        allItems.add(new itemObject_news(getResources().getString(R.string.news2), getResources().getString(R.string.portal3),getResources().getString(R.string.time1), R.drawable.report_macet));
        allItems.add(new itemObject_news(getResources().getString(R.string.news1), getResources().getString(R.string.portal2),getResources().getString(R.string.time2), R.drawable.wisata));

        return allItems;
    }
}