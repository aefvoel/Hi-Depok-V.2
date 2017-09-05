package tiregdev.hi_depok.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;
import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.adapter_karya;
import tiregdev.hi_depok.adapter.adapter_news;
import tiregdev.hi_depok.model.itemObject_karya;
import tiregdev.hi_depok.model.itemObject_news;

/**
 * Created by TiregDev on 23/08/2017.
 */

public class Master_karya extends Fragment {

    View v;
    BannerSlider banner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_master_karya, container, false);
        setupBanner();
        setupAdapter();

        return v;
    }

    public void setupAdapter(){
        List<itemObject_karya> rowListItem = getAllItemList();
        LinearLayoutManager lLayout = new LinearLayoutManager(getContext());

        RecyclerView rView = (RecyclerView)v.findViewById(R.id.view_karya);
        rView.setLayoutManager(lLayout);

        adapter_karya rcAdapter = new adapter_karya(getContext(), rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private List<itemObject_karya> getAllItemList(){
        List<itemObject_karya> allItems = new ArrayList<>();
        allItems.add(new itemObject_karya(getResources().getString(R.string.nama), getResources().getString(R.string.location),getResources().getString(R.string.likeTxt),
                getResources().getString(R.string.commentTxt),getResources().getString(R.string.postTxt),getResources().getString(R.string.share1),
                getResources().getString(R.string.time),R.drawable.char_icon,R.drawable.report_banjir,R.drawable.status_waiting));
        allItems.add(new itemObject_karya(getResources().getString(R.string.nama1), getResources().getString(R.string.location),getResources().getString(R.string.likeTxt1),
                getResources().getString(R.string.commentTxt1),getResources().getString(R.string.postTxt3),getResources().getString(R.string.share2),
                getResources().getString(R.string.time2),R.drawable.char_icon,R.drawable.report_pohontumbang,R.drawable.status_accepted));
        allItems.add(new itemObject_karya(getResources().getString(R.string.nama2), getResources().getString(R.string.location),getResources().getString(R.string.likeTxt2),
                getResources().getString(R.string.commentTxt2),getResources().getString(R.string.postTxt2),getResources().getString(R.string.share3),
                getResources().getString(R.string.time1),R.drawable.char_icon,R.drawable.report_macet,R.drawable.status_proses));
        allItems.add(new itemObject_karya(getResources().getString(R.string.nama3), getResources().getString(R.string.location),getResources().getString(R.string.likeTxt3),
                getResources().getString(R.string.commentTxt3),getResources().getString(R.string.postTxt1),getResources().getString(R.string.share1),
                getResources().getString(R.string.time),R.drawable.char_icon,R.drawable.wisata,R.drawable.status_waiting));
        allItems.add(new itemObject_karya(getResources().getString(R.string.nama), getResources().getString(R.string.location),getResources().getString(R.string.likeTxt),
                getResources().getString(R.string.commentTxt),getResources().getString(R.string.postTxt),getResources().getString(R.string.share2),
                getResources().getString(R.string.time2),R.drawable.char_icon,R.drawable.report_banjir,R.drawable.status_proses));

        return allItems;
    }

    public void setupBanner(){
        banner = (BannerSlider) v.findViewById(R.id.banner_karya);
        addBanners();
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(), "Banner with position " + String.valueOf(position) + " clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBanners() {
        List<Banner> banners = new ArrayList<>();
        banners.add(new DrawableBanner(R.drawable.karya_1));
        banners.add(new DrawableBanner(R.drawable.karya_2));
        banners.add(new DrawableBanner(R.drawable.karya_3));
        banners.add(new DrawableBanner(R.drawable.karya_4));

        banner.setBanners(banners);
    }

}
