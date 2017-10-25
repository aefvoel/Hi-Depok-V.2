package tiregdev.hi_depok.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import tiregdev.hi_depok.adapter.GejalaAdapter;
import tiregdev.hi_depok.model.DataModel;

public class Gejala extends AppCompatActivity {

    String JSON_URL = "http://hi.depok.go.id/android_api/diagnosa/sikepokdiagnosa_json.php";
    String idgejala;
    String idg;
    Integer idgej;
    RecyclerView rView;
    TextView emptyview;
    List<DataModel> dataAdapter;
    DataModel data;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosa_gejala);
//        super.onCreateDrawer();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
//        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rView = (RecyclerView) findViewById(R.id.list_ensiklopedia);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);
        emptyview = (TextView) findViewById(R.id.empty_view);
        dataAdapter = new ArrayList<>();
        SharedPreferences pref = this.getSharedPreferences("MyPref", 0);
        idgejala = pref.getString("idgejala", "0");
        JSON_URL = "http://hi.depok.go.id/android_api/diagnosa/sikepokdiagnosa_json.php?gejala=" + idgejala;
        getDataFromJSON(JSON_URL);
        if (idgejala=="0") {
            rView.setVisibility(View.GONE);
            emptyview.setVisibility(View.VISIBLE);
        }
        else {
            rView.setVisibility(View.VISIBLE);
            emptyview.setVisibility(View.GONE);
        }
        Button tombol_sugesti = (Button) findViewById(R.id.tombol_sugesti);
        tombol_sugesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Sugesti.class);
                startActivity(intent);
            }
        });

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
                        data.setId(json.getString("id_gejala"));
                        data.setNama(json.getString("nama_gejala"));

                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                    dataAdapter.add(data);
                }
                RecyclerView.Adapter rViewAdapter = new GejalaAdapter(dataAdapter, getBaseContext());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hapus_gejala, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Gejala.this.finish();
                return true;
            case R.id.action_hapus_gejala:
                dataAdapter.clear();
                SharedPreferences pref = getBaseContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                idgejala = pref.getString("idgejala", "0");
                JSON_URL = "http://hi.depok.go.id/android_api/diagnosa/sikepokdiagnosa_json.php?gejala=" + pref.getString("idgejala", "0");
                getDataFromJSON(JSON_URL);
                if (idgejala=="0") {
                    rView.setVisibility(View.GONE);
                    emptyview.setVisibility(View.VISIBLE);
                }
                else {
                    rView.setVisibility(View.VISIBLE);
                    emptyview.setVisibility(View.GONE);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
