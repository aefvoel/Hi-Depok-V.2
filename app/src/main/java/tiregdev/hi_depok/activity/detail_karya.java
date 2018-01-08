package tiregdev.hi_depok.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.arnaudguyon.smartfontslib.FontEditText;
import fr.arnaudguyon.smartfontslib.FontTextView;
import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.adapter_komen_karya;
import tiregdev.hi_depok.model.MasterpieceKomentar;

import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;
import tiregdev.hi_depok.utils.MasterpieceFunctions;
import tiregdev.hi_depok.utils.SQLiteHandler;

import static tiregdev.hi_depok.R.id.ava;
import static tiregdev.hi_depok.R.id.view_komentar;

public class detail_karya extends AppCompatActivity implements View.OnClickListener{

    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView placePicture;
    private ImageView likeIcon;
    private FontTextView likeText;
    private ImageView commentIcon;
    private FontTextView commentText;
    private ImageView shareIcon;
    private FontTextView shareText;
    private ImageView avatar;
    private FontTextView username;
    private FontTextView time;
    private FontTextView location;
    private FontTextView postTxt;
    private FontEditText isiKomentar;
    private ImageView btnKirim;
    private JSONObject jsonObject;
    private RecyclerView rView;
    private LinearLayoutManager lLayout;
    private DividerItemDecoration dividerItemDecoration;
    private MasterpieceKomentar mKom;
    private List<MasterpieceKomentar> dataAdapter;
    private adapter_komen_karya rvAdapter;
    private MasterpieceFunctions mFun;
    private SQLiteHandler db;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_karya);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupToolbar();
        findViews();
        setViews();
    }


    private void findViews() {
        likeIcon = (ImageView)findViewById( R.id.likeIcon );
        likeText = (FontTextView)findViewById( R.id.likeText );
        commentIcon = (ImageView)findViewById( R.id.commentIcon );
        commentText = (FontTextView)findViewById( R.id.commentText );
        shareIcon = (ImageView)findViewById( R.id.shareIcon );
        shareText = (FontTextView)findViewById( R.id.shareText );
        avatar = (ImageView)findViewById( R.id.avatar );
        username = (FontTextView)findViewById( R.id.username );
        time = (FontTextView)findViewById( R.id.time );
        location = (FontTextView)findViewById( R.id.location );
        postTxt = (FontTextView)findViewById( R.id.postTxt );
        isiKomentar = (FontEditText)findViewById( R.id.isiKomentar );
        btnKirim = (ImageView)findViewById( R.id.btnKirim );
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        placePicture = (ImageView) findViewById(R.id.image);
        lLayout = new LinearLayoutManager(detail_karya.this);
        rView = (RecyclerView) findViewById(view_komentar);
        dividerItemDecoration = new DividerItemDecoration(detail_karya.this, lLayout.getOrientation());
        dataAdapter = new ArrayList<>();
        mFun = new MasterpieceFunctions(detail_karya.this);
        db = new SQLiteHandler(getApplicationContext());
        pDialog = new ProgressDialog(this);


    }

    public void setViews(){

        collapsingToolbar.setTitle(getIntent().getExtras().getString("NAMA"));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.transperent));
        collapsingToolbar.setCollapsedTitleTextColor(Color.rgb(255, 255, 255));
        username.setText(getIntent().getExtras().getString("PERAIH"));
        time.setText(getIntent().getExtras().getString("TANGGAL"));
        location.setText(getIntent().getExtras().getString("TINGKAT"));
        postTxt.setText(getIntent().getExtras().getString("DESKRIPSI"));

        commentText.setText(getIntent().getExtras().getString("JUMLAH_KOMENTAR"));
        likeText.setText(getIntent().getExtras().getString("JUMLAH_SUKA"));

        if(getIntent().getExtras().getBoolean("ISLIKED")){
            likeIcon.setImageResource(R.drawable.favorite);
        }else {
            likeIcon.setImageResource(R.drawable.like);
        }


        rView.setNestedScrollingEnabled(false);
        rView.setLayoutManager(lLayout);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat Komentar ...");

        Glide.with(this).load(AppConfig.IMG_MASTERPIECE + getIntent().getExtras().getString("IMAGE")).placeholder(R.drawable.no_image).into(placePicture);
        Glide.with(this).load(getIntent().getExtras().getString("AVATAR")).placeholder(R.drawable.no_image).into(avatar);

        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(detail_karya.this, R.drawable.line_view));
        rView.addItemDecoration(dividerItemDecoration);

        commentIcon.setOnClickListener(this);
        likeIcon.setOnClickListener(this);
        commentIcon.setOnClickListener(this);
        btnKirim.setOnClickListener(this);
    }


    public void setupToolbar(){
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                detail_karya.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayData(){
        showDialog();
        dataAdapter.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.DISPLAY_KOMENTAR_MASTERPIECE + "?id=" + getIntent().getExtras().getString("ID_POST"), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    mKom = new MasterpieceKomentar();
                    jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        mKom.setIsiKomentar(jsonObject.getString("isi_komentar"));
                        mKom.setWaktu(jsonObject.getString("waktu"));
                        mKom.setIdPenghargaan(jsonObject.getString("id_penghargaan"));
                        mKom.setIdUser(jsonObject.getString("id_user"));
                        mKom.setAvatar(jsonObject.getString("foto"));
                        mKom.setNamaUser(jsonObject.getString("username"));

                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dataAdapter.add(mKom);

                }
                commentText.setText(String.valueOf(dataAdapter.size()));
                rvAdapter = new adapter_komen_karya(getApplicationContext(), dataAdapter);
                rView.setAdapter(rvAdapter);
                hideDialog();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Belum ada komentar untuk post ini! jadilah yang pertama!", Toast.LENGTH_LONG).show();
                hideDialog();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }




    @Override
    public void onClick(View v) {
        if ( v == likeIcon ) {
            mFun.insertLike(db.getUserDetails().get("uid"), getIntent().getExtras().getString("ID_POST"));
            likeIcon.setImageResource(R.drawable.favorite);
        }else if( v == commentIcon) {
        }else if( v == shareIcon) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Lihat konten ini pada Aplikasi Hi-Depok";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            v.getContext().startActivity(Intent.createChooser(sharingIntent, "Share Via"));
        }else if( v == btnKirim) {
            mFun.insertKomentar(isiKomentar.getText().toString().trim(), db.getUserDetails().get("uid"), getIntent().getExtras().getString("ID_POST"));
            isiKomentar.setText(null);
            displayData();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(rvAdapter != null){
            rvAdapter.notifyDataSetChanged();
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
