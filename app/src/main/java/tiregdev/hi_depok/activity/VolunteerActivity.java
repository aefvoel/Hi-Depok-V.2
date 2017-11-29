package tiregdev.hi_depok.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fr.arnaudguyon.smartfontslib.FontButton;
import fr.arnaudguyon.smartfontslib.FontEditText;
import tiregdev.hi_depok.R;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;
import tiregdev.hi_depok.utils.SQLiteHandler;

public class volunteer extends AppCompatActivity implements View.OnClickListener {



    private FontEditText jenisVolunteer;
    private FontEditText nama;
    private FontEditText nmrTlp;
    private FontEditText keahlian;
    private FontEditText status;
    private FontEditText umur;
    private FontEditText bio;
    private FontButton kirim;
    private SQLiteHandler db;
    private ProgressDialog pDialog;
    private static final String TAG = volunteer.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViews();
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-10-17 13:34:08 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        jenisVolunteer = (FontEditText)findViewById( R.id.jenisVolunteer );
        nama = (FontEditText)findViewById( R.id.nama );
        nmrTlp = (FontEditText)findViewById( R.id.nmrTlp );
        keahlian = (FontEditText)findViewById( R.id.keahlian );
        status = (FontEditText)findViewById( R.id.status );
        umur = (FontEditText)findViewById( R.id.umur );
        bio = (FontEditText)findViewById( R.id.bio );
        kirim = (FontButton)findViewById( R.id.kirim );

        pDialog = new ProgressDialog(this);
        db = new SQLiteHandler(getApplicationContext());
        kirim.setOnClickListener( this );
    }

    private void sendData(){

        final String nama_volunter = nama.getText().toString().trim();
        final String jenis_volunter = jenisVolunteer.getText().toString().trim();
        final String _keahlian = keahlian.getText().toString().trim();
        final String _status = status.getText().toString().trim();
        final String no_telp_volunter = nmrTlp.getText().toString().trim();
        final String _umur = umur.getText().toString().trim();
        final String alasan = bio.getText().toString().trim();
        final String id_user = db.getUserDetails().get("uid");

        String tag_string_req = "req_volunteer";

        pDialog.setMessage("Mengirim Permintaan ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.REG_VOLUNTEER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
//                        String uid = jObj.getString("uid");
//
//                        JSONObject user = jObj.getJSONObject("user");
//                        String name = user.getString("name");
//                        String email = user.getString("email");
//                        String alamat = user.getString("alamat");
//                        String no_telp = user.getString("no_telp");
//                        String tanggal_lahir = user.getString("tanggal_lahir");
//                        String bio = user.getString("bio");
//                        String foto_user = user.getString("foto");
//
//                        // Inserting row in users table
//                        db.updateUser(name, email, uid, alamat, no_telp, tanggal_lahir, bio, foto_user);

                        Toast.makeText(getApplicationContext(), "Data berhasil terkirim!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(getBaseContext(),
                                MenuActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                Log.d(TAG, "Failed with error msg:\t" + error.getMessage());
                Log.d(TAG, "Error StackTrace: \t" + error.getStackTrace());
                // edited here
                try {
                    byte[] htmlBodyBytes = error.networkResponse.data;
                    Log.e(TAG, new String(htmlBodyBytes), error);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama_volunter", nama_volunter);
                params.put("jenis_volunter", jenis_volunter);
                params.put("keahlian", _keahlian);
                params.put("status", _status);
                params.put("no_telp_volunter", no_telp_volunter);
                params.put("umur", _umur);
                params.put("alasan", alasan);
                params.put("id_user", id_user);

                return params;
            }

        };

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-10-17 13:34:08 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == kirim ) {
            // Handle clicks for kirim
            sendData();
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                volunteer.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
