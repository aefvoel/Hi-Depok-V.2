package tiregdev.hi_depok.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.adapter_news_blog;
import tiregdev.hi_depok.model.itemObject_news_Blog;

import static android.support.v7.recyclerview.R.attr.layoutManager;
import static tiregdev.hi_depok.R.id.rview;
import static tiregdev.hi_depok.R.id.view_news;

/**
 * Created by TiregDev on 26/09/2017.
 */

public class news_blog extends android.support.v4.app.Fragment {

    RecyclerView rView;
    SwipeRefreshLayout swipeRefreshRecyclerList;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_news_blog, container, false);
        setUpAdapter();
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

    public void setUpAdapter(){
        List<itemObject_news_Blog> rowListItem = getAllItemList();
        LinearLayoutManager lLayout = new LinearLayoutManager(getActivity());

        rView = (RecyclerView)v.findViewById(view_news);
        rView.setNestedScrollingEnabled(false);
        rView.setLayoutManager(lLayout);

        adapter_news_blog rcAdapter = new adapter_news_blog(getContext(), rowListItem);
        rView.setAdapter(rcAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), lLayout.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.line_view));
        rView.addItemDecoration(dividerItemDecoration);
    }

    private List<itemObject_news_Blog> getAllItemList(){
        List<itemObject_news_Blog> allItems = new ArrayList<>();
        allItems.add(new itemObject_news_Blog(getResources().getString(R.string.news2), getResources().getString(R.string.portal2),getResources().getString(R.string.time1), R.drawable.report_banjir));
        allItems.add(new itemObject_news_Blog(getResources().getString(R.string.news3), getResources().getString(R.string.portal3),getResources().getString(R.string.time1), R.drawable.report_macet));
        allItems.add(new itemObject_news_Blog(getResources().getString(R.string.news4), getResources().getString(R.string.portal1),getResources().getString(R.string.time2), R.drawable.report_pohontumbang));
        allItems.add(new itemObject_news_Blog(getResources().getString(R.string.news5), getResources().getString(R.string.portal2),getResources().getString(R.string.time1), R.drawable.report_banjir));
        allItems.add(new itemObject_news_Blog(getResources().getString(R.string.news2), getResources().getString(R.string.portal3),getResources().getString(R.string.time1), R.drawable.report_macet));
        allItems.add(new itemObject_news_Blog(getResources().getString(R.string.news1), getResources().getString(R.string.portal2),getResources().getString(R.string.time2), R.drawable.wisata));

        return allItems;
    }
}
