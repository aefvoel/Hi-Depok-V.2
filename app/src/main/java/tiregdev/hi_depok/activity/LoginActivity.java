package tiregdev.hi_depok.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.utils.AlertDialogUtil;
import tiregdev.hi_depok.utils.ExtraString;
import tiregdev.hi_depok.utils.ProgressDialogUtil;

import static com.google.android.gms.auth.api.Auth.GoogleSignInApi;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    Button login, signup;
    TextView forgot;
    EditText emailFrm, passFrm;
    GoogleApiClient mGoogleApiClient;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private ProgressDialogUtil pDialog;
    private AlertDialog alertDialog;
    private static final int REQUEST_GMAIL = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setDefault();
        checkPlayServices();
        setAuthInstance();
        setDatabaseInstance();
        setSharedPreferences();
        setProgressDialog();



    }

    private void setDefault(){
        emailFrm = (EditText)findViewById(R.id.email);
        passFrm = (EditText)findViewById(R.id.pass);

        login = (Button)findViewById(R.id.btnLogin);
        signup = (Button)findViewById(R.id.btnLoginGoogle);
        forgot = (TextView)findViewById(R.id.lupaPass);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogInUser();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });
    }
    private void googleSignIn(){
        Intent intent = GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, REQUEST_GMAIL);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GMAIL){
            GoogleSignInResult result = GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }
    private void handleResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            String getNama = account.getDisplayName();
            String getEmail = account.getEmail();
            Intent toRegister = new Intent(LoginActivity.this, SignUpActivity.class);
            toRegister.putExtra("getNama", getNama);
            toRegister.putExtra("getEmail", getEmail);
            startActivity(toRegister);
        }
    }



    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            //Any random request code
            int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
            //Google Play Services app is not available or version is not up to date. Error the
            // error condition here
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        //Google Play Services is available. Return true.
        return true;
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
        checkLoginSession();
    }

    private void checkLoginSession() {
        if(pref.getBoolean(ExtraString.EXTRA_LOGIN_SESSION, false)){
            startActivity(new Intent(LoginActivity.this, MenuActivity.class));
            finish();
        }
    }

    private void setProgressDialog() {
        pDialog = new ProgressDialogUtil(this, ProgressDialog.STYLE_SPINNER, true);
    }
    private void onLogInUser() {
        String emailUser = emailFrm.getText().toString().trim();
        String passUser = passFrm.getText().toString().trim();
        if (emailUser.equals("") || passUser.equals("")) {
            showAlertDialog("Data Belum Diiisi!", true);
        } else if (isIncorrectEmail(emailUser)) {
            showAlertDialog("Email Tidak Valid!", true);
        } else {
            logIn(emailUser, passUser);
        }
    }

    private boolean isIncorrectEmail(String userEmail) {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches();
    }

    private void logIn(String email, String password) {
        pDialog.show();
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            checkUserOnDatabase(task.getResult().getUser().getUid());
                        } else {
                            showAlertDialog("Email tidak terdaftar!", true);
                            Log.e("ERROR", task.getException().getMessage());
                        }
                        pDialog.dismiss();
                    }
                }
        );
    }

    private void checkUserOnDatabase(final String userId) {
        mDatabase.child(ExtraString.EXTRA_CHILD_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isNone = false;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String userUid = child.getKey();
                    if(userUid.equals(userId)){
                        editor.putBoolean(ExtraString.EXTRA_LOGIN_SESSION, true);
                        editor.putString(ExtraString.EXTRA_KEY_ID_USER, userId);
                        editor.commit();
                        isNone = false;
                        break;
                    }else{
                        isNone = true;
                    }
                }
                if(isNone){
                    showAlertDialog("Password Salah!", true);
                }else{
                    goToMainActivity();
                }
                pDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showAlertDialog(String message, boolean isCancelable) {
        alertDialog = AlertDialogUtil.buildAlertDialog("Alert!", message,isCancelable,LoginActivity.this);
        alertDialog.show();
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
