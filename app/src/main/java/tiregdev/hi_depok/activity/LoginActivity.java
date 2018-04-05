package tiregdev.hi_depok.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import io.fabric.sdk.android.Fabric;
import tiregdev.hi_depok.R;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;
import tiregdev.hi_depok.utils.Application;
import tiregdev.hi_depok.utils.SQLiteHandler;
import tiregdev.hi_depok.utils.SessionManager;

import static com.google.android.gms.auth.api.Auth.GoogleSignInApi;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    private SignInButton btnGoogle;
    private LoginButton btnFacebook;
    private TwitterLoginButton btnTwitter;
    private TextView forgot;
    private EditText emailFrm, passFrm;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;
    private CallbackManager mFacebookCallbackManager;
    private String full_name;
    private String email_id;
    private String photo_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mFacebookCallbackManager = CallbackManager.Factory.create();
        TwitterAuthConfig authConfig = new TwitterAuthConfig("EcXjy4S0dDcPfcSfq8cloDDps",
                "D2b77118vTt4n1kH3m1QTgfrwbZcPEY3IDgKyJ25y5FcXWmFey");
        Fabric.with(this, new TwitterCore(authConfig));

        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setInit();
    }


    private void setInit() {

        emailFrm = (EditText) findViewById(R.id.email);
        passFrm = (EditText) findViewById(R.id.pass);

        login = (Button) findViewById(R.id.btnLogin);
        btnGoogle = (SignInButton) findViewById(R.id.btnLoginGoogle);
        btnFacebook = (LoginButton) findViewById(R.id.btnLoginFacebook);
        btnTwitter = (TwitterLoginButton) findViewById(R.id.btnLoginTwitter);
        forgot = (TextView) findViewById(R.id.lupaPass);
        login.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        forgot.setOnClickListener(this);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }


//        checkForInstagramData();

        // The private key that follows should never be public
        // (consider this when deploying the application)


        btnFacebook.registerCallback(mFacebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        //By Profile Class

                        Profile profile = Profile.getCurrentProfile();
                        if (profile != null) {
                            String facebook_id = profile.getId();
                            full_name = profile.getFirstName();
                            String m_name = profile.getMiddleName();
                            String l_name = profile.getLastName();
                            String full_name = profile.getName();
                            photo_url = profile.getProfilePictureUri(400, 400).toString();
                        }
                        //Toast.makeText(FacebookLogin.this,"Wait...",Toast.LENGTH_SHORT).show();
                        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            email_id = object.getString("email");
                                            String gender = object.getString("gender");
                                            String full_name = object.getString("name");
                                            long fb_id = object.getLong("id"); //use this for logout

                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            //  e.printStackTrace();
                                        }

                                    }

                                });

                        handleSignInResult(new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                LoginManager.getInstance().logOut();
                                return null;
                            }
                        }, full_name, email_id, photo_url);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        handleSignInResult(null, null, null, null);
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(LoginActivity.class.getCanonicalName(), error.getMessage());
                        handleSignInResult(null, null, null, null);
                    }
                }
        );

        btnTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(final Result<TwitterSession> result) {
                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
                twitterApiClient.getAccountService().verifyCredentials(false, false, new Callback<com.twitter.sdk.android.core.models.User>() {
                    @Override
                    public void success(Result<com.twitter.sdk.android.core.models.User> userResult) {
                        full_name = userResult.data.name;
//                        email_id = userResult.data.email;
                        photo_url = userResult.data.profileImageUrl;

                        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                        TwitterAuthClient authClient = new TwitterAuthClient();
                        authClient.requestEmail(session, new Callback<String>() {
                            @Override
                            public void success(Result<String> result) {
                                // Do something with the result, which provides the email address
                                // the email is saved in the result variable 'result.data'
                                email_id = result.data;
                                handleSignInResult(new Callable<Void>() {
                                    @Override
                                    public Void call() throws Exception {
                                        TwitterCore.getInstance().logOut();
                                        return null;
                                    }
                                }, full_name, email_id, photo_url);
                            }

                            @Override
                            public void failure(TwitterException exception) {
                                // Do something on failure
                            }
                        });
                    }

                    @Override
                    public void failure(TwitterException exc) {
                        Log.d("TwitterKit", "Verify Credentials Failure", exc);
                    }
                });

            }

            @Override
            public void failure(TwitterException e) {
                Log.d(LoginActivity.class.getCanonicalName(), e.getMessage());
                handleSignInResult(null, null, null, null);
            }
        });

    }

    private void signInWithGoogle() {
        if(mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        final Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

//    private void signInWithInstagram() {
//        final Uri.Builder uriBuilder = new Uri.Builder();
//        uriBuilder.scheme("https")
//                .authority("api.instagram.com")
//                .appendPath("oauth")
//                .appendPath("authorize")
//                .appendQueryParameter("client_id", "18a8b74da9644bd7a9294caef1c5e76c")
//                .appendQueryParameter("redirect_uri", "sociallogin://redirect")
//                .appendQueryParameter("response_type", "token");
//        final Intent browser = new Intent(Intent.ACTION_VIEW, uriBuilder.build());
//        startActivity(browser);
//    }
//
//    private void checkForInstagramData() {
//        final Uri data = this.getIntent().getData();
//        if(data != null && data.getScheme().equals("sociallogin") && data.getFragment() != null) {
//            final String accessToken = data.getFragment().replaceFirst("access_token=", "");
//            if (accessToken != null) {
//                handleSignInResult(new Callable<Void>() {
//                    @Override
//                    public Void call() throws Exception {
//                        // Do nothing, just throw the access token away.
//                        return null;
//                    }
//                });
//            } else {
//                handleSignInResult(null);
//            }
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                final GoogleApiClient client = mGoogleApiClient;
                full_name = result.getSignInAccount().getDisplayName();
                email_id = result.getSignInAccount().getEmail();

                Uri uri= result.getSignInAccount().getPhotoUrl();

                if(uri != null){
                    photo_url = uri.toString();
                }else{
                    photo_url = "http://hi.depok.go.id/storage/default/no%20image.jpg";
                }

                handleSignInResult(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        if (client != null) {

                            Auth.GoogleSignInApi.signOut(client).setResultCallback(
                                    new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(Status status) {
                                            Log.d(LoginActivity.class.getCanonicalName(),
                                                    status.getStatusMessage());

                                        /* TODO: handle logout failures */
                                        }
                                    }
                            );

                        }

                        return null;
                    }
                }, full_name, email_id, photo_url);

            } else {
                handleSignInResult(null, null, null, null);
            }
        } else if (TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE == requestCode) {
            btnTwitter.onActivityResult(requestCode, resultCode, data);
        } else {
            mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Callable<Void> logout, String fullname, String email, String photo) {
        if (logout == null) {
            /* Login error */
            Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
        } else {
            /* Login success */

            Application.getInstance().setLogoutCallable(logout);
            Intent toRegister = new Intent(LoginActivity.this, SignUpActivity.class);
            toRegister.putExtra("getNama", fullname);
            toRegister.putExtra("getEmail", email);
            toRegister.putExtra("getPhoto", photo);
            startActivity(toRegister);
        }
    }


    private void onLogInUser() {
        String emailUser = emailFrm.getText().toString().trim();
        String passUser = passFrm.getText().toString().trim();
        // Check for empty data in the form
        if (!emailUser.isEmpty() && !passUser.isEmpty()) {
            // login user
            checkLogin(emailUser, passUser);
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext(),
                    "Please enter the credentials!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
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

                        tiregdev.hi_depok.model.User gcmUser = new tiregdev.hi_depok.model.User(jObj.getString("uid"),
                                user.getString("name"),
                                user.getString("email"));

                        // storing user in shared preferences
                        AppController.getInstance().getPrefManager().storeUser(gcmUser);

                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this,
                                MenuActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

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


    @Override
    public void onClick(View view) {
        if (view == login) {
            onLogInUser();
        } else if (view == btnGoogle) {
            signInWithGoogle();
        } else if (view == forgot) {
            Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
            startActivity(intent);
        }
    }

}
