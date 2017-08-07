package tiregdev.hi_depok.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.model.User;
import tiregdev.hi_depok.utils.AlertDialogUtil;
import tiregdev.hi_depok.utils.ExtraString;
import tiregdev.hi_depok.utils.ProgressDialogUtil;

public class SignUpActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    EditText name, email, pass, confirmPass, alamat, noTelp, bio;
    Button signup, ttl;
    CheckBox validasi;
    SimpleDateFormat sdf;
    Calendar dateAndTime;
    DatePickerDialog.OnDateSetListener d;
    private AlertDialog alertDialog;
    private ProgressDialogUtil pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setDefault();
        setAuthInstance();
        setDatabaseInstance();
        setSharedPreferences();
        setProgressDialog();

    }
    private void setDefault(){
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

        name.setText(getIntent().getExtras().getString("getNama"));
        email.setText(getIntent().getExtras().getString("getEmail"));

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateAndTime = Calendar.getInstance();

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

    private void setAuthInstance() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
    }

    private void setDatabaseInstance() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void setSharedPreferences() {
        pref = getApplicationContext().getSharedPreferences(ExtraString.EXTRA_DATABASE_SESSION, MODE_PRIVATE);
        editor = pref.edit();
    }

    private void setProgressDialog() {
        pDialog = new ProgressDialogUtil(this, ProgressDialog.STYLE_SPINNER, true);
    }

    private void onRegisterUser() {
        String namaUser = name.getText().toString().trim();
        String emailUser = email.getText().toString().trim();
        String passUser = pass.getText().toString().trim();
        String confirmpassUser = confirmPass.getText().toString().trim();
        String alamatUser = alamat.getText().toString().trim();
        String noTelpUser = noTelp.getText().toString().trim();
        String bioUser = bio.getText().toString().trim();
        String tglLahirUser = ttl.getText().toString().trim();

        if(namaUser.equals("") || emailUser.equals("") || passUser.equals("") || alamatUser.equals("") || noTelpUser.equals("") || bioUser.equals("") || tglLahirUser.equals("")){
            showAlertDialog("Harap Semua Data Diisi!",true);
        }else if(isIncorrectEmail(emailUser)) {
            showAlertDialog("Email tidak valid!",true);
        }else if(!passUser.equals(confirmpassUser)) {
            showAlertDialog("Password tidak cocok!",true);
        }else if(passUser.length() <= 6){
            showAlertDialog("Password kurang dari 6 karakter!",true);
        }else if(!validasi.isChecked()) {
            showAlertDialog("Persetujuan belum dicentang!",true);
        }else {
            signUp(emailUser, passUser);
        }
    }

    private boolean isIncorrectEmail(String userEmail) {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches();
    }

    private void onAuthSuccess(FirebaseUser user) {
        createNewUser(user.getUid());
        goToMainActivity();
        editor.putBoolean(ExtraString.EXTRA_LOGIN_SESSION, true);
        editor.putString(ExtraString.EXTRA_KEY_ID_USER,user.getUid());
        editor.commit();
    }

    private void signUp(String email, String password) {
        pDialog.show();
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    onAuthSuccess(task.getResult().getUser());
                }else {
                    showAlertDialog("Email Sudah Ada!",true);
                    Log.e("ERROR", task.getException().getMessage());
                }
                pDialog.dismiss();
            }
        });
    }

    private void createNewUser(String userId){
        User user = buildNewUser();
        mDatabase.child(ExtraString.EXTRA_CHILD_USERS).child(userId).setValue(user);
    }

    private User buildNewUser() {
        SimpleDateFormat sdf = new  SimpleDateFormat("dd/MM/yyyy h:mm:ss a");
        String namaUser = name.getText().toString().trim();
        String emailUser = email.getText().toString().trim();
        String passUser = pass.getText().toString().trim();
        String alamatUser = alamat.getText().toString().trim();
        String noTelpUser = noTelp.getText().toString().trim();
        String bioUser = bio.getText().toString().trim();
        String tglLahirUser = ttl.getText().toString().trim();

        return new User(
                namaUser,
                emailUser,
                passUser,
                alamatUser,
                bioUser,
                noTelpUser,
                tglLahirUser,
                sdf.format(Calendar.getInstance().getTime().getTime())
        );
    }

    private void goToMainActivity() {
        Intent intent = new Intent(SignUpActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
    }
}
