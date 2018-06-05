package tiregdev.hi_depok.fragment;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.ChatActivity;
import tiregdev.hi_depok.adapter.NotifAdapter;
import tiregdev.hi_depok.model.Notifikasi;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;
import tiregdev.hi_depok.utils.SQLiteHandler;

import static tiregdev.hi_depok.activity.MenuActivity.results;

/**
 * Created by Muhammad63 on 8/3/2017.
 */

public class Notif extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View v;
    private RecyclerView rView;
    private LinearLayoutManager lLayout;
    private Notif dataObjek;
    private NotifAdapter rcAdapter;
    private JSONObject jsonObject;
    private ImageView ham;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Notifikasi> allItems;

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
        displayData();
        return v;
    }



    public void setupAdapter(){
        ham = (ImageView) v.findViewById(R.id.menu);
        ham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results.openDrawer();
            }
        });
        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh_recycler_list);
        swipeRefreshLayout.setOnRefreshListener(this);
        lLayout = new LinearLayoutManager(getActivity());

        rView = (RecyclerView)v.findViewById(R.id.view_notif);
        rView.setLayoutManager(lLayout);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), lLayout.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.line_view));
        rView.addItemDecoration(dividerItemDecoration);


    }

    private void displayData(){
        swipeRefreshLayout.setRefreshing(true);
        SQLiteHandler db;
        db = new SQLiteHandler(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.NOTIF.replace("_ID_", db.getUserDetails().get("uid")), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                allItems = new ArrayList<>();
                swipeRefreshLayout.setRefreshing(false);
                for (int i = 0; i < response.length(); i++){
                    dataObjek = new Notif();
                    jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        allItems.add(new Notifikasi(
                                jsonObject.getString("id_notif"),
                                jsonObject.getString("username"),
                                jsonObject.getString("isi"),
                                jsonObject.getString("waktu"),
                                jsonObject.getString("foto"),
                                jsonObject.getString("type"),
                                jsonObject.getString("id_type"),
                                jsonObject.getString("name")));


                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                rcAdapter = new NotifAdapter(getContext(), allItems);
                rView.setAdapter(rcAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Belum Ada Data!", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);



    }

    @Override
    public void onRefresh() {
        allItems.clear();
        displayData();
        rcAdapter.notifyDataSetChanged();
    }
}