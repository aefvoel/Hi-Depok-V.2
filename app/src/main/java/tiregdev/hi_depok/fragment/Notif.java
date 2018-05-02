package tiregdev.hi_depok.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
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
import tiregdev.hi_depok.activity.ChatActivity;
import tiregdev.hi_depok.adapter.NotifAdapter;
import tiregdev.hi_depok.model.Notifikasi;

import static tiregdev.hi_depok.activity.MenuActivity.results;

/**
 * Created by Muhammad63 on 8/3/2017.
 */

public class Notif extends Fragment {

    RecyclerView rView;
    LinearLayoutManager lLayout;
    ImageView ham;
    SwipeRefreshLayout swipeRefreshRecyclerList;
    View v;

    public static Notif newInstance(){
        Notif fragment = new Notif();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_notif, container, false);
        setupAdapter();
        setHamBtn();
        setPesanLink();
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

    public void setHamBtn(){
        ham = (ImageView) v.findViewById(R.id.menu);
        ham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results.openDrawer();
            }
        });
    }

    public void setupAdapter(){
        List<Notifikasi> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(getContext());

        rView = (RecyclerView)v.findViewById(R.id.view_notif);
        rView.setLayoutManager(lLayout);

        NotifAdapter rcAdapter = new NotifAdapter(getContext(), rowListItem);
        rView.setAdapter(rcAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), lLayout.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.line_view));
        rView.addItemDecoration(dividerItemDecoration);
    }

    public void setPesanLink(){
        final RippleView rippleViews = (RippleView) v.findViewById(R.id.pesan);
        rippleViews.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), ChatActivity.class);
                startActivity(w);
            }
        });
    }

    private List<Notifikasi> getAllItemList(){
        List<Notifikasi> allItems = new ArrayList<>();
        allItems.add(new Notifikasi("JAR", "Menanggapi postingan Anda","2 jam lalu", R.drawable.report_banjir));
        allItems.add(new Notifikasi("HVS", "Menanggapi postingan Anda","2 jam lalu", R.drawable.report_macet));
        allItems.add(new Notifikasi("Aefvoel", "Menyukai postingan Anda","3 jam lalu", R.drawable.report_pohontumbang));
        allItems.add(new Notifikasi("Pandu", "Menanggapi postingan Anda","3 jam lalu", R.drawable.report_banjir));
        allItems.add(new Notifikasi("Tegar", "Menyukai postingan Anda","3 jam lalu", R.drawable.report_banjir));
        allItems.add(new Notifikasi("Fajri", "Menanggapi postingan Anda","4 jam lalu", R.drawable.report_pohontumbang));
        allItems.add(new Notifikasi("Della", "Menyukai postingan Anda","4 jam lalu", R.drawable.report_macet));
        allItems.add(new Notifikasi("Citra", "Menyukai postingan Andaa","5 jam lalu", R.drawable.report_banjir));
        allItems.add(new Notifikasi("Nadiah", "Menanggapi postingan Anda","5 jam lalu", R.drawable.report_macet));
        allItems.add(new Notifikasi("Yessi", "Menanggapi postingan Anda","6 jam lalu", R.drawable.report_pohontumbang));

        return allItems;
    }
}