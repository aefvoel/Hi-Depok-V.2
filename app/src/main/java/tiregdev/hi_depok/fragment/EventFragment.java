package tiregdev.hi_depok.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.DetailEventActivity;
import tiregdev.hi_depok.adapter.EventAdapter;
import tiregdev.hi_depok.model.EventList;
import tiregdev.hi_depok.utils.GridMarginDecoration;

/**
 * Created by TiregDev on 26/09/2017.
 */

public class EventFragment extends android.support.v4.app.Fragment {

    View v;
    SwipeRefreshLayout swipeRefreshRecyclerList;
    private RecyclerView recyclerView;
    private EventAdapter mAdapter;
    private ArrayList<EventList> modelList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_news_event, container, false);
        swipeRefresh();
        findViews();
        setAdapter();
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

    private void findViews() {
        recyclerView = (RecyclerView) v.findViewById(R.id.view_event);
    }

    private void setAdapter() {


        modelList.add(new EventList(getResources().getString(R.string.judul), getResources().getString(R.string.nama), getResources().getString(R.string.time), R.drawable.wisata));
        modelList.add(new EventList(getResources().getString(R.string.judul1), getResources().getString(R.string.nama1), getResources().getString(R.string.time), R.drawable.report_banjir));
        modelList.add(new EventList(getResources().getString(R.string.judul2), getResources().getString(R.string.nama2), getResources().getString(R.string.time), R.drawable.report_macet));
        modelList.add(new EventList(getResources().getString(R.string.judul3), getResources().getString(R.string.nama3), getResources().getString(R.string.time1), R.drawable.report_pohontumbang));
        modelList.add(new EventList(getResources().getString(R.string.judul1), getResources().getString(R.string.nama), getResources().getString(R.string.time1), R.drawable.wisata));
        modelList.add(new EventList(getResources().getString(R.string.judul), getResources().getString(R.string.nama2), getResources().getString(R.string.time1), R.drawable.wisata));
        modelList.add(new EventList(getResources().getString(R.string.judul2), getResources().getString(R.string.nama1), getResources().getString(R.string.time1), R.drawable.report_macet));
        modelList.add(new EventList(getResources().getString(R.string.judul3), getResources().getString(R.string.nama3), getResources().getString(R.string.time2), R.drawable.report_pohontumbang));
        modelList.add(new EventList(getResources().getString(R.string.judul), getResources().getString(R.string.nama), getResources().getString(R.string.time2), R.drawable.report_banjir));
        modelList.add(new EventList(getResources().getString(R.string.judul1), getResources().getString(R.string.nama1), getResources().getString(R.string.time2), R.drawable.wisata));
        modelList.add(new EventList(getResources().getString(R.string.judul2), getResources().getString(R.string.nama2), getResources().getString(R.string.time2), R.drawable.report_pohontumbang));
        modelList.add(new EventList(getResources().getString(R.string.judul3), getResources().getString(R.string.nama3), getResources().getString(R.string.time2), R.drawable.wisata));


        mAdapter = new EventAdapter(getActivity(), modelList);

        recyclerView.setHasFixedSize(true);


        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.addItemDecoration(new GridMarginDecoration(getActivity(), 2, 2, 2, 2));
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, EventList model) {
                Intent i = new Intent(getContext(), DetailEventActivity.class);
                startActivity(i);
            }
        });

    }

}
