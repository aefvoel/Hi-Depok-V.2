package tiregdev.hi_depok.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fr.arnaudguyon.smartfontslib.FontButton;
import fr.arnaudguyon.smartfontslib.FontEditText;
import tiregdev.hi_depok.R;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;
import tiregdev.hi_depok.utils.SQLiteHandler;


public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = EditProfileActivity.class.getSimpleName();
    private ImageView editavatar;
    private FontEditText username;
    private FontEditText email;
    private FontEditText alamat;
    private FontEditText nmrTlp;
    private FontButton tglLahir;
    private FontButton saveChanges;
    private FontEditText bio;
    private ImageView avatar;
    private Calendar dateAndTime;
    private SQLiteHandler db;
    private ProgressDialog pDialog;
    private SimpleDateFormat sdf;
    private RadioGroup jenisKel;



    private void settingTanggal(){
        dateAndTime = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener d =
                new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month,
                                          int day){
                        dateAndTime.set(Calendar.YEAR, year);
                        dateAndTime.set(Calendar.MONTH, month);
                        dateAndTime.set(Calendar.DAY_OF_MONTH, day);

                        tglLahir.setText(sdf.format(dateAndTime.getTime()));
                    }
                };

        new DatePickerDialog(EditProfileActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();
        setViews();
    }
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-10-12 14:45:19 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        avatar = (ImageView)findViewById( R.id.avatar );
        username = (FontEditText)findViewById( R.id.username );
        email = (FontEditText)findViewById( R.id.email );
        alamat = (FontEditText)findViewById( R.id.alamat );
        nmrTlp = (FontEditText)findViewById( R.id.nmrTlp );
        tglLahir = (FontButton)findViewById( R.id.tglLahir );
        saveChanges = (FontButton)findViewById( R.id.saveChanges );
        bio = (FontEditText)findViewById( R.id.bio );
        jenisKel = (RadioGroup)findViewById(R.id.jenisKel);
        editavatar = (ImageView) findViewById(R.id.edit);
        pDialog = new ProgressDialog(this);
        db = new SQLiteHandler(getApplicationContext());
        sdf = new SimpleDateFormat("dd-MM-yyyy");

    }

    private void setViews() {
        username.setText(db.getUserDetails().get("name"));
        email.setText(db.getUserDetails().get("email"));
        alamat.setText(db.getUserDetails().get("alamat"));
        nmrTlp.setText(db.getUserDetails().get("no_telp"));
        tglLahir.setText(db.getUserDetails().get("tanggal_lahir"));
        bio.setText(db.getUserDetails().get("bio"));
        pDialog.setCancelable(false);
        tglLahir.setOnClickListener( this );
        saveChanges.setOnClickListener( this );
    }

    private void sendData(){
        final String uid = db.getUserDetails().get("uid");
        final String namaUser = username.getText().toString().trim();
        final String emailUser = email.getText().toString().trim();
        final String alamatUser = alamat.getText().toString().trim();
        final String noTelpUser = nmrTlp.getText().toString().trim();
        final String bioUser = bio.getText().toString().trim();
        final String tglLahirUser = tglLahir.getText().toString().trim();

        Date now = new Date();
        final String tglUpdate = new SimpleDateFormat("dd-MM-yyyy").format(now);
        final String jenisKelamin = ((RadioButton)findViewById(jenisKel.getCheckedRadioButtonId())).getText().toString();

        String tag_string_req = "req_edit";

        pDialog.setMessage("Saving Changes ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE, new Response.Listener<String>() {

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
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String alamat = user.getString("alamat");
                        String jenis_kel = user.getString("jenis_kelamin");
                        String no_telp = user.getString("no_telp");
                        String tanggal_lahir = user.getString("tanggal_lahir");
                        String bio = user.getString("bio");
                        String created_at = user.getString("created_at");
                        String updated_at = user.getString("updated_at");
                        String foto_user = user.getString("foto");

                        // Inserting row in users table
                        db.updateUser(name, email, uid, alamat, no_telp, tanggal_lahir, bio, foto_user, jenis_kel, created_at, updated_at);

                        Toast.makeText(getApplicationContext(), "Data changed successfully!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                EditProfileActivity.this,
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
                params.put("uid", uid);
                params.put("name", namaUser);
                params.put("email", emailUser);
                params.put("alamat", alamatUser);
                params.put("no_telp", noTelpUser);
                params.put("bio", bioUser);
                params.put("tanggal_lahir", tglLahirUser);
                params.put("jenis_kelamin", jenisKelamin);
                params.put("updated_at", tglUpdate);

                return params;
            }

        };

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-10-12 14:45:19 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == tglLahir ) {
            // Handle clicks for tglLahir
            settingTanggal();
        }else if( v == saveChanges) {
            if(username.getText().toString().isEmpty() || email.getText().toString().isEmpty() ||
                    alamat.getText().toString().isEmpty() || nmrTlp.getText().toString().isEmpty() ||
                    bio.getText().toString().isEmpty() || tglLahir.getText().toString().isEmpty() ||
                    jenisKel.getCheckedRadioButtonId() == -1){
                Toast.makeText(this, "Error harap isi semua opsi!", Toast.LENGTH_SHORT).show();
            }else{
                sendData();
            }
        }else if( v == editavatar) {
            Intent i = new Intent(EditProfileActivity.this, change_avatar.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                EditProfileActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
}
