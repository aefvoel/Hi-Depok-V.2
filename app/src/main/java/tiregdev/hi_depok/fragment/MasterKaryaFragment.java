package tiregdev.hi_depok.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import tiregdev.hi_depok.activity.SubmitKaryaActivity;
import tiregdev.hi_depok.adapter.MasterKaryaAdapter;
import tiregdev.hi_depok.model.MasterpiecePost;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;
import tiregdev.hi_depok.utils.MasterpieceFunctions;
import tiregdev.hi_depok.utils.SQLiteHandler;

/**
 * Created by TiregDev on 23/08/2017.
 */

public class MasterKaryaFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    View v;
    BannerSlider banner;
    SwipeRefreshLayout swipeRefreshRecyclerList;
    RippleView rippleViews;
    RecyclerView rView;
    MasterpiecePost mPost;
    JSONObject jsonObject;
    List<MasterpiecePost> dataAdapter;
    MasterKaryaAdapter rvAdapter;
    LinearLayoutManager lLayout;
    MasterpieceFunctions mFun;
    SQLiteHandler db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_master_karya, container, false);
        findViews();
        setViews();
        return v;
    }

    private void findViews(){
        rippleViews = (RippleView) v.findViewById(R.id.submit);
        swipeRefreshRecyclerList = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_recycler_list);
        rView = (RecyclerView)v.findViewById(R.id.view_karya);
        banner = (BannerSlider) v.findViewById(R.id.banner_karya);
        lLayout = new LinearLayoutManager(getContext());
        dataAdapter = new ArrayList<>();
        mFun = new MasterpieceFunctions(getContext());
        db = new SQLiteHandler(getContext());
    }

    private void setViews(){
        rView.setNestedScrollingEnabled(false);
        rView.setLayoutManager(lLayout);

        rippleViews.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), SubmitKaryaActivity.class);
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
        dataAdapter.clear();
        swipeRefreshRecyclerList.setRefreshing(true);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.DISPLAY_MASTERPIECE, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    mPost = new MasterpiecePost();
                    jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        mPost.setId_post(jsonObject.getString("id_penghargaan"));
                        mPost.setId_user(jsonObject.getString("id_user"));
                        mPost.setDeskripsi(jsonObject.getString("deskripsi"));
                        mPost.setInstansi(jsonObject.getString("instansi"));
                        mPost.setKategori(jsonObject.getString("kategori"));
                        mPost.setImage(jsonObject.getString("image"));
                        mPost.setNama_peraih(jsonObject.getString("nama_peraih"));
                        mPost.setNama_prestasi(jsonObject.getString("nama_prestasi"));
                        mPost.setKeterangan(jsonObject.getString("keterangan"));
                        mPost.setTingkat(jsonObject.getString("bio"));
                        mPost.setJumlah_komentar(jsonObject.getString("jumlah_komentar"));
                        mPost.setJumlah_suka(jsonObject.getString("jumlah_suka"));
                        mPost.setTgl_post(jsonObject.getString("tgl_post"));
                        mPost.setStatus(jsonObject.getString("status"));
                        mPost.setRiwayat(jsonObject.getString("riwayat"));
                        mPost.setUsername(jsonObject.getString("username"));
                        mPost.setAvatar(jsonObject.getString("foto"));

                        if(!jsonObject.getString("id_user_suka").equals("null")){
                            if (jsonObject.getString("id_user_suka").contains(db.getUserDetails().get("uid"))) {
                                mPost.setIs_liked(true);
                            } else {
                                mPost.setIs_liked(false);
                            }
                        }else{
                            mPost.setIs_liked(false);
                        }

                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dataAdapter.add(mPost);


                }
                rvAdapter = new MasterKaryaAdapter(getContext(), dataAdapter);
                rvAdapter.notifyDataSetChanged();
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

    @Override
    public void onStart() {
        super.onStart();
        displayData();
    }

    @Override
    public void onPause() {
        super.onPause();
        swipeRefreshRecyclerList.setRefreshing(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        swipeRefreshRecyclerList.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        displayData();
        rvAdapter.notifyDataSetChanged();
    }
}
