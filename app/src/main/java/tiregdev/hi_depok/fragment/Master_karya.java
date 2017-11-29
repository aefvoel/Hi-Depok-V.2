package tiregdev.hi_depok.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;
import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.submit_master_karya;
import tiregdev.hi_depok.adapter.adapter_karya;
import tiregdev.hi_depok.model.MasterpiecePost;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;

/**
 * Created by TiregDev on 23/08/2017.
 */

public class Master_karya extends BaseFragment {

    View v;
    BannerSlider banner;
    SwipeRefreshLayout swipeRefreshRecyclerList;
    RippleView rippleViews;
    RecyclerView rView;
    MasterpiecePost mPost;
    JSONObject jsonObject;
    List<MasterpiecePost> dataAdapter;
    adapter_karya rvAdapter;
    LinearLayoutManager lLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_master_karya, container, false);
        findViews();
        setViews();
        displayData();
        return v;
    }

    private void findViews(){
        rippleViews = (RippleView) v.findViewById(R.id.submit);
        swipeRefreshRecyclerList = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_recycler_list);
        rView = (RecyclerView)v.findViewById(R.id.view_karya);
        banner = (BannerSlider) v.findViewById(R.id.banner_karya);
        lLayout = new LinearLayoutManager(getContext());
        dataAdapter = new ArrayList<>();
    }

    private void setViews(){
        rView.setNestedScrollingEnabled(false);
        rView.setLayoutManager(lLayout);

        rippleViews.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), submit_master_karya.class);
                startActivity(w);
            }
        });

        List<Banner> banners = new ArrayList<>();
        banners.add(new DrawableBanner(R.drawable.karya_1));
        banners.add(new DrawableBanner(R.drawable.karya_2));
        banners.add(new DrawableBanner(R.drawable.karya_3));
        banners.add(new DrawableBanner(R.drawable.karya_4));

        banner.setBanners(banners);
    }

    private void displayData(){
        swipeRefreshRecyclerList.setRefreshing(true);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.DISPLAY_MASTERPIECE, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    mPost = new MasterpiecePost();
                    jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        mPost.setId_user(jsonObject.getString("id_user"));
                        mPost.setDeskripsi(jsonObject.getString("deskripsi"));
                        mPost.setInstansi(jsonObject.getString("instansi"));
                        mPost.setKategori(jsonObject.getString("kategori"));
                        mPost.setImage(jsonObject.getString("image"));
                        mPost.setNama_peraih(jsonObject.getString("nama_peraih"));
                        mPost.setNama_prestasi(jsonObject.getString("nama_prestasi"));
                        mPost.setKeterangan(jsonObject.getString("keterangan"));
                        mPost.setTingkat(jsonObject.getString("tingkat"));
                        mPost.setJumlah_komentar(jsonObject.getString("jumlah_komentar"));
                        mPost.setJumlah_suka(jsonObject.getString("jumlah_suka"));
                        mPost.setTgl_post(jsonObject.getString("tgl_post"));
                        mPost.setStatus(jsonObject.getString("status"));
                        mPost.setRiwayat(jsonObject.getString("riwayat"));

                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dataAdapter.add(mPost);

                }
                rvAdapter = new adapter_karya(getContext(), dataAdapter);
                rView.setAdapter(rvAdapter);
                swipeRefreshRecyclerList.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    protected void onSaveState(Bundle outState) {
        super.onSaveState(outState);
        outState.putParcelable("myState", lLayout.onSaveInstanceState());
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        lLayout.onRestoreInstanceState(savedInstanceState.getParcelable("myState"));
    }

}
