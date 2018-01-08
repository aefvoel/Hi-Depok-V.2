package tiregdev.hi_depok.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import fr.arnaudguyon.smartfontslib.FontTextView;
import tiregdev.hi_depok.R;
import tiregdev.hi_depok.utils.AppConfig;

public class detail_museum extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    ImageView placePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_museum);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setCollaps();
        setupToolbar();
        findViews();
    }

    private FontTextView namaPenghargaan;
    private FontTextView descPenghargaan;
    private FontTextView peraih;
    private FontTextView tgl;
    private FontTextView kategori;
    private FontTextView tingkat;


    private void findViews() {
        namaPenghargaan = (FontTextView)findViewById( R.id.namaPenghargaan );
        descPenghargaan = (FontTextView)findViewById( R.id.desc_penghargaan );
        peraih = (FontTextView)findViewById( R.id.peraih );
        tgl = (FontTextView)findViewById( R.id.tgl );
        kategori = (FontTextView)findViewById( R.id.kategori );
        tingkat = (FontTextView)findViewById( R.id.tingkat );

        namaPenghargaan.setText(getIntent().getExtras().getString("NAMA"));
        descPenghargaan.setText(getIntent().getExtras().getString("DESKRIPSI"));
        peraih.setText(getIntent().getExtras().getString("PERAIH"));
        tgl.setText(getIntent().getExtras().getString("TANGGAL"));
        kategori.setText(getIntent().getExtras().getString("KATEGORI"));
        tingkat.setText(getIntent().getExtras().getString("TINGKAT"));

    }


    public void setCollaps(){

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        placePicture = (ImageView) findViewById(R.id.image);
        Glide.with(this).load(AppConfig.IMG_MASTERPIECE + getIntent().getExtras().getString("IMAGE")).centerCrop().placeholder(R.drawable.no_image).into(placePicture);
        collapsingToolbar.setTitle(getIntent().getExtras().getString("NAMA"));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.transperent));
        collapsingToolbar.setCollapsedTitleTextColor(Color.rgb(255, 255, 255));
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

                detail_museum.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
