package tiregdev.hi_depok.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.adapter_news;
import tiregdev.hi_depok.model.itemObject_news;

import static tiregdev.hi_depok.activity.MenuActivity.results;

/**
 * Created by Muhammad63 on 8/3/2017.
 */

public class News extends Fragment {
//    private LinearLayoutManager;
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
        View v = inflater.inflate(R.layout.fragment_news, null);

        ham = (ImageView) v.findViewById(R.id.menu);
        ham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results.openDrawer();
            }
        });

        List<itemObject_news> rowListItem = getAllItemList();
        LinearLayoutManager lLayout = new LinearLayoutManager(getContext());

        RecyclerView rView = (RecyclerView)v.findViewById(R.id.view_news);
        rView.setLayoutManager(lLayout);

        adapter_news rcAdapter = new adapter_news(getContext(), rowListItem);
        rView.setAdapter(rcAdapter);

        return v;
    }

    private List<itemObject_news> getAllItemList(){
        List<itemObject_news> allItems = new ArrayList<>();
//        allItems.add(new itemObject_news(R.string.news2, R.string.portal2,R.string.time2, R.drawable.report_banjir));

        return allItems;
    }
}