package tiregdev.hi_depok.activity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.SugestiAdapter;
import tiregdev.hi_depok.model.DataModel;

public class Sugesti extends AppCompatActivity {
    String JSON_URL = "http://hi.depok.go.id/android_api/ensiklopedia/sikepokensiklopedia_json.php";
    String idgejala;
    RecyclerView rView;
    TextView emptyview;
    List<DataModel> dataAdapter;
    DataModel data;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosa_sugesti);
//        super.onCreateDrawer();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
//        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rView = (RecyclerView) findViewById(R.id.list_sugesti);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);
        emptyview = (TextView) findViewById(R.id.empty_view);
        dataAdapter = new ArrayList<>();
        SharedPreferences pref = this.getSharedPreferences("MyPref", 0);
        idgejala = pref.getString("idgejala", "0");
        JSON_URL = "http://hi.depok.go.id/android_api/diagnosa/sikepokdiagnosa_json.php?sugesti=" + idgejala;
        getDataFromJSON(JSON_URL);
        if (idgejala=="0") {
            rView.setVisibility(View.GONE);
            emptyview.setVisibility(View.VISIBLE);
        }
        else {
            rView.setVisibility(View.VISIBLE);
            emptyview.setVisibility(View.GONE);
        }
    }

    public void getDataFromJSON(String url){
        JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    data = new DataModel();
                    JSONObject json = null;
                    try{

                        json = response.getJSONObject(i);
                        data.setId(json.getString("id_penyakit"));
                        data.setNama(json.getString("nama_penyakit"));
                        data.setHalaman(json.getString("halaman"));
                        data.setPersen(json.getDouble("persentase"));

                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                    dataAdapter.add(data);
                }
                RecyclerView.Adapter rViewAdapter = new SugestiAdapter(dataAdapter, getBaseContext());
                rView.setAdapter(rViewAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue reqQueue = Volley.newRequestQueue(this);
        reqQueue.add(req);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Sugesti.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

