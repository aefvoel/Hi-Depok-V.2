package tiregdev.hi_depok.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.utils.AlertDialogUtil;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;
import tiregdev.hi_depok.utils.SQLiteHandler;
import tiregdev.hi_depok.utils.SessionManager;

public class SignUpActivity extends AppCompatActivity {

    private EditText name, email, pass, confirmPass, alamat, noTelp, bio;
    private RadioGroup jenisKel;
    private Button signup, ttl;
    private CheckBox validasi;
    private SimpleDateFormat sdf;
    private Calendar dateAndTime;
    private DatePickerDialog.OnDateSetListener d;
    private AlertDialog alertDialog;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private static final String TAG = SignUpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setInit();

    }
    private void setInit(){
        name = (EditText)findViewById(R.id.username);
        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.pass);
        confirmPass = (EditText)findViewById(R.id.confirm_pass);
        alamat = (EditText)findViewById(R.id.alamat);
        noTelp = (EditText)findViewById(R.id.nmrTlp);
        bio = (EditText)findViewById(R.id.bio);
        signup = (Button)findViewById(R.id.btnSignUp);
        ttl = (Button)findViewById(R.id.ttl);
        validasi = (CheckBox)findViewById(R.id.validasi);
        jenisKel = (RadioGroup)findViewById(R.id.jenisKel);
        name.setText(getIntent().getExtras().getString("getNama"));
        email.setText(getIntent().getExtras().getString("getEmail"));

        sdf = new SimpleDateFormat("dd-MM-yyyy");
        dateAndTime = Calendar.getInstance();
// Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SignUpActivity.this,
                    MenuActivity.class);
            startActivity(intent);
            finish();
        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterUser();
            }
        });
        ttl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingTanggal();
            }
        });

        d = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day){
                dateAndTime.set(Calendar.YEAR, year);
                dateAndTime.set(Calendar.MONTH, month);
                dateAndTime.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };
    }


    private void onRegisterUser() {
        String namaUser = name.getText().toString().trim();
        String emailUser = email.getText().toString().trim();
        String passUser = pass.getText().toString().trim();
        String confirmpassUser = confirmPass.getText().toString().trim();
        String alamatUser = alamat.getText().toString().trim();
        String jenisKelamin = ((RadioButton)findViewById(jenisKel.getCheckedRadioButtonId())).getText().toString().trim();
        String noTelpUser = noTelp.getText().toString().trim();
        String bioUser = bio.getText().toString().trim();
        String tglLahirUser = ttl.getText().toString().trim();

        Date now = new Date();
        String tglRegister = new SimpleDateFormat("dd-MM-yyyy").format(now);
        String tglUpdate = new SimpleDateFormat("dd-MM-yyyy").format(now);
        String fotoUser = getIntent().getExtras().getString("getPhoto");

        if(namaUser.equals("") || emailUser.equals("") || passUser.equals("") || alamatUser.equals("") || jenisKelamin.equals("") || noTelpUser.equals("") || bioUser.equals("") || tglLahirUser.equals("")){
            showAlertDialog("Harap Semua Data Diisi!",true);
        }else if(isIncorrectEmail(emailUser)) {
            showAlertDialog("Email tidak valid!",true);
        }else if(!passUser.equals(confirmpassUser)) {
            showAlertDialog("Password tidak cocok!",true);
        }else if(passUser.length() < 6){
            showAlertDialog("Password kurang dari 6 karakter!",true);
        }else if(!validasi.isChecked()) {
            showAlertDialog("Persetujuan belum dicentang!",true);
        }else {
            registerUser(namaUser, emailUser, passUser, alamatUser, jenisKelamin, noTelpUser, bioUser, tglLahirUser, tglRegister, tglUpdate, fotoUser);
        }
    }

    private boolean isIncorrectEmail(String userEmail) {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches();
    }


    private void showAlertDialog(String message, boolean isCancelable) {
        alertDialog = AlertDialogUtil.buildAlertDialog("Alert!", message,isCancelable, SignUpActivity.this);
        alertDialog.show();
    }
    private void updateLabel(){
        ttl.setText(
                sdf.format(dateAndTime.getTime()));
    }

    private void settingTanggal(){
        new DatePickerDialog(SignUpActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }


    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email,
                              final String password, final String alamat, final String jenisKelamin, final String noTelp,
                              final String bio, final String tglLahir, final String tglRegister, final String tglUpdate, final String fotoUser) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

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
                        db.addUser(name, email, uid, alamat, no_telp, tanggal_lahir, bio, foto_user, jenis_kel, created_at, updated_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                SignUpActivity.this,
                                LoginActivity.class);
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
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("alamat", alamat);
                params.put("no_telp", noTelp);
                params.put("bio", bio);
                params.put("tanggal_lahir", tglLahir);
                params.put("foto", fotoUser);
                params.put("jenis_kelamin", jenisKelamin);
                params.put("created_at", tglRegister);
                params.put("updated_at", tglUpdate);

                return params;
            }

        };

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
