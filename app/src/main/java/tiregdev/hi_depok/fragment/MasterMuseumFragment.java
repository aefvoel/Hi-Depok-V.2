package tiregdev.hi_depok.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import tiregdev.hi_depok.adapter.MasterMuseumAdapter;
import tiregdev.hi_depok.model.MasterpiecePost;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;

/**
 * Created by TiregDev on 23/08/2017.
 */

public class MasterMuseumFragment extends BaseFragment implements MaterialSpinner.OnItemSelectedListener {

    View v;
    SwipeRefreshLayout swipeRefreshRecyclerList;
    RecyclerView rView;
    MasterpiecePost mPost;
    JSONObject jsonObject;
    List<MasterpiecePost> dataAdapter;
    MasterMuseumAdapter rvAdapter;
    GridLayoutManager gridLayoutManager;
    MaterialSpinner spinner;
    String extraLink;

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
        extraLink = AppConfig.DISPLAY_MASTERPIECE + "?status=diterima";
        String searhdata[] = {"Semua Kategori",
                "Teknologi",
                "Kesehatan",
                "Lingkungan",
                "Pendidikan",
                "Umum"};
        spinner.setItems(searhdata);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        switch (position) {
            case 0:
                extraLink = AppConfig.DISPLAY_MASTERPIECE + "?status=diterima";
                break;
            case 1:
                extraLink = AppConfig.DISPLAY_MASTERPIECE + "?kategori=Teknologi";
                break;
            case 2:
                extraLink = AppConfig.DISPLAY_MASTERPIECE + "?kategori=Kesehatan";
                break;
            case 3:
                extraLink = AppConfig.DISPLAY_MASTERPIECE + "?kategori=Lingkungan";
                break;
            case 4:
                extraLink = AppConfig.DISPLAY_MASTERPIECE + "?kategori=Pendidikan";
                break;
            case 5:
                extraLink = AppConfig.DISPLAY_MASTERPIECE + "?kategori=Umum";
                break;
            default:
                break;
        }
        displayData();
    }

    private void displayData(){
        dataAdapter.clear();
        swipeRefreshRecyclerList.setRefreshing(true);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(extraLink, new Response.Listener<JSONArray>() {
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
                rvAdapter = new MasterMuseumAdapter(getContext(), dataAdapter);
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
