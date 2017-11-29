package tiregdev.hi_depok.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.adapter_ntpd;
import tiregdev.hi_depok.adapter.adapter_searchData;
import tiregdev.hi_depok.model.CariData;
import tiregdev.hi_depok.model.itemObject_ntpd;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;

/**
 * Created by Muhammad63 on 10/6/2017.
 */

public class ListCariDataFragment extends BaseFragment {

    RecyclerView rView;
    View v;
    List<CariData> dataAdapter;
    adapter_searchData rvAdapter;
    CariData mPost;
    JSONObject jsonObject;
    LinearLayoutManager lLayout;

    public static ListCariDataFragment newInstance(String title, String sub) {
        ListCariDataFragment fragment = new ListCariDataFragment();

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("sub", sub);
        fragment.setArguments(args);

        return fragment;
    }

    public String getSub() {
        Bundle args = getArguments();
        return args.getString("sub", "NO TITLE FOUND");
    }

    public String getTitle() {
        Bundle args = getArguments();
        return args.getString("title", "NO TITLE FOUND");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_ntpd_instansi, container, false);
        setUpAdapter();
        displayData();
        return v;
    }

    public void setUpAdapter(){
        lLayout = new LinearLayoutManager(getActivity());

        rView = (RecyclerView)v.findViewById(R.id.rview);
        rView.setLayoutManager(lLayout);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), lLayout.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.line_view));
        rView.addItemDecoration(dividerItemDecoration);

        dataAdapter = new ArrayList<>();
    }


    private void displayData(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.DISPLAY_CARIDATA + "?kategori=" + getTitle() + "&display=" + getSub(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    mPost = new CariData();
                    jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        mPost.setNamaTempat(jsonObject.getString("nama_tempat"));
                        mPost.setDeskripsi(jsonObject.getString("gambaran_umum"));
                        mPost.setAlamat(jsonObject.getString("alamat"));
                        mPost.setKoordinat(jsonObject.getString("koordinat"));
                        mPost.setFoto(jsonObject.getString("foto"));
                        mPost.setWebsite(jsonObject.getString("website"));
                        mPost.setJamOperasi(jsonObject.getString("jam_operasional"));
                        mPost.setKecamatan(jsonObject.getString("kecamatan"));
                        mPost.setNoTelp(jsonObject.getString("no_telp"));

                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dataAdapter.add(mPost);

                }
                rvAdapter = new adapter_searchData(getContext(), dataAdapter);
                rView.setAdapter(rvAdapter);

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

