package tiregdev.hi_depok.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.adapter_museum;
import tiregdev.hi_depok.model.MasterpiecePost;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;

/**
 * Created by TiregDev on 23/08/2017.
 */

public class Master_museum extends Fragment {

    View v;
    SwipeRefreshLayout swipeRefreshRecyclerList;
    RecyclerView rView;
    MasterpiecePost mPost;
    JSONObject jsonObject;
    List<MasterpiecePost> dataAdapter;
    adapter_museum rvAdapter;
    GridLayoutManager gridLayoutManager;
    MaterialSpinner spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_master_museum, container, false);
        findViews();
        setViews();
        displayData();
        return v;
    }

    private void findViews(){
        swipeRefreshRecyclerList = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_recycler_list);
        rView = (RecyclerView)v.findViewById(R.id.view_museum);
        spinner = (MaterialSpinner)v.findViewById(R.id.spinner_seacrh);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        dataAdapter = new ArrayList<>();
    }

    private void setViews(){
        rView.setNestedScrollingEnabled(false);
        rView.setLayoutManager(gridLayoutManager);

        String searhdata[] = {"Semua Kategori",
                "Teknologi",
                "Kesehatan",
                "Lingkungan",
                "Pendidikan",
                "Umum"};
        spinner.setItems(searhdata);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_SHORT).show();
            }
        });
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
                rvAdapter = new adapter_museum(getContext(), dataAdapter);
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

}
