package tiregdev.hi_depok.fragment;

import android.os.Bundle;
import android.os.Parcelable;
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
import tiregdev.hi_depok.adapter.adapter_modul;
import tiregdev.hi_depok.model.ModulPost;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;

/**
 * Created by TiregDev on 23/08/2017.
 */

public class Master_modul extends BaseFragment implements MaterialSpinner.OnItemSelectedListener {

    View v;
    SwipeRefreshLayout swipeRefreshRecyclerList;
    RecyclerView rView;
    ModulPost mPost;
    JSONObject jsonObject;
    List<ModulPost> dataAdapter;
    adapter_modul rvAdapter;
    GridLayoutManager gridLayoutManager;
    MaterialSpinner spinner;
    String extraLink;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_master_modul, container, false);
        findViews();
        setViews();
        displayData();
        return v;
    }

    private void findViews(){
        swipeRefreshRecyclerList = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_recycler_list);
        rView = (RecyclerView)v.findViewById(R.id.view_modul);
        spinner = (MaterialSpinner)v.findViewById(R.id.spinner_seacrh);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        dataAdapter = new ArrayList<>();
    }

    private void setViews(){
        rView.setNestedScrollingEnabled(false);
        rView.setLayoutManager(gridLayoutManager);
        extraLink = AppConfig.DISPLAY_MODUL;
        String searhdata[] = {"Semua Kategori",
                "Robotic",
                "Android",
                "Website",
                "Database",
                "Sejarah",
                "Biologi",
                "Fisika",
                "Kimia",
                "Umum"};

        spinner.setItems(searhdata);
        spinner.setOnItemSelectedListener(this);
    }

    private void displayData(){
        dataAdapter.clear();
        swipeRefreshRecyclerList.setRefreshing(true);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(extraLink, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    mPost = new ModulPost();
                    jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        mPost.setId_modul(jsonObject.getString("id_modul"));
                        mPost.setJudul(jsonObject.getString("judul"));
                        mPost.setPengarang(jsonObject.getString("pengarang"));
                        mPost.setKategori(jsonObject.getString("kategori"));
                        mPost.setJml_halaman(jsonObject.getString("jml_halaman"));
                        mPost.setFoto(jsonObject.getString("foto"));
                        mPost.setLink(jsonObject.getString("link"));

                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dataAdapter.add(mPost);

                }
                rvAdapter = new adapter_modul(getContext(), dataAdapter);
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
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        switch (position) {
            case 0:
                extraLink = AppConfig.DISPLAY_MODUL;
                break;
            case 1:
                extraLink = AppConfig.DISPLAY_MODUL + "?kategori=Robotic";
                break;
            case 2:
                extraLink = AppConfig.DISPLAY_MODUL + "?kategori=Android";
                break;
            case 3:
                extraLink = AppConfig.DISPLAY_MODUL + "?kategori=Website";
                break;
            case 4:
                extraLink = AppConfig.DISPLAY_MODUL + "?kategori=Database";
                break;
            case 5:
                extraLink = AppConfig.DISPLAY_MODUL + "?kategori=Sejarah";
                break;
            case 6:
                extraLink = AppConfig.DISPLAY_MODUL + "?kategori=Biologi";
                break;
            case 7:
                extraLink = AppConfig.DISPLAY_MODUL + "?kategori=Fisika";
                break;
            case 8:
                extraLink = AppConfig.DISPLAY_MODUL + "?kategori=Kimia";
                break;
            case 9:
                extraLink = AppConfig.DISPLAY_MODUL + "?kategori=Umum";
                break;
            default:
                break;
        }
        displayData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    protected void onSaveState(Bundle outState) {
        super.onSaveState(outState);
        outState.putParcelable("myState", gridLayoutManager.onSaveInstanceState());
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        gridLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable("myState"));
    }

}
