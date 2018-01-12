package tiregdev.hi_depok.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fr.arnaudguyon.smartfontslib.FontEditText;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;
import tiregdev.hi_depok.utils.SQLiteHandler;
import tiregdev.hi_depok.utils.VolleyMultipartRequest;

public class SubmitKaryaActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_master_karya);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViews();
        requestStoragePermission();
    }


    private ImageView attachFile;
    private ImageView camera;
    private ImageView imgPost;
    private TextView posting;
    private FontEditText textPos;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private SimpleDateFormat sdf;
    private boolean isPosted;

    private static final String TAG = SubmitKaryaActivity.class.getSimpleName();
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;



    private void findViews() {
        attachFile = (ImageView)findViewById( R.id.attachFile );
        camera = (ImageView)findViewById( R.id.camera );
        imgPost = (ImageView)findViewById( R.id.imgPost );
        posting = (TextView)findViewById( R.id.posting );
        textPos = (FontEditText)findViewById( R.id.textPos );

        pDialog = new ProgressDialog(this);
        db = new SQLiteHandler(getApplicationContext());
        sdf = new SimpleDateFormat("dd-MM-yyyy h:mm:ss");

        isPosted = false;
        camera.setOnClickListener(this);
        attachFile.setOnClickListener(this);
        posting.setOnClickListener(this);
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
    public void onBackPressed() {
        if(!isPosted){
            LayoutInflater factory = LayoutInflater.from(this);
            final View exitDialogView = factory.inflate(R.layout.alert_submit, null);
            final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
            exitDialog.setView(exitDialogView);
            exitDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SubmitKaryaActivity.this.finish();
                }
            });
            exitDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exitDialog.dismiss();
                }
            });
            exitDialog.show();
        } else{
            super.onBackPressed();
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgPost.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if ( v == posting ) {
            // Handle clicks for tglLahir
            uploadBitmap(bitmap);
        }else if( v == camera) {
        }else if( v == attachFile) {
            showFileChooser();
        }
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) {

        final String id_user = db.getUserDetails().get("uid");
        final String nama_peraih = db.getUserDetails().get("name");
        final String instansi = "-";
        final String nama_prestasi = textPos.getText().toString().trim();
        final String deskripsi = textPos.getText().toString().trim();
        final String tingkat = "-";
        final String kategori = "-";
        final String riwayat = "-";
        final String keterangan = "-";
        final String jumlah_suka = "0";
        final String jumlah_komentar = "0";
        final String status = "diproses";
        final String tgl_post = sdf.format(Calendar.getInstance().getTime().getTime());

        String tag_string_req = "req_submit_masterpiece";

        pDialog.setMessage("Saving Changes ...");
        showDialog();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, AppConfig.SUBMIT_MASTERPIECE,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.d(TAG, "Register Response: " + response.toString());
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(new String(response.data));
                            boolean error = jObj.getBoolean("error");
                            if (!error) {

                                Toast.makeText(getApplicationContext(), "Post added successfully!", Toast.LENGTH_LONG).show();
                                isPosted = true;

                                // Launch login activity
                                Intent intent = new Intent(
                                        getApplicationContext(),
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
                },
                new Response.ErrorListener() {
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

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id_user);
                params.put("nama_peraih", nama_peraih);
                params.put("jumlah_suka", jumlah_suka);
                params.put("jumlah_komentar", jumlah_komentar);
                params.put("tgl_post", tgl_post);
                params.put("deskripsi", deskripsi);
                params.put("instansi", instansi);
                params.put("tingkat", tingkat);
                params.put("kategori", kategori);
                params.put("riwayat", riwayat);
                params.put("keterangan", keterangan);
                params.put("nama_prestasi", nama_prestasi);
                params.put("status", status);

                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        AppController.getInstance().addToRequestQueue(volleyMultipartRequest, tag_string_req);
    }
}
