package tiregdev.hi_depok.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.utils.AppConfig;

public class DetailModulActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_modul);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViews();
    }

    private ImageView cover;
    private TextView kategori;
    private TextView judul;
    private TextView pengarang;
    private TextView page;
    private TextView viewer;
    private ImageView share;
    private Button download;


    private void findViews() {
        cover = (ImageView)findViewById( R.id.cover );
        kategori = (TextView)findViewById( R.id.kategori );
        judul = (TextView)findViewById( R.id.judul );
        pengarang = (TextView)findViewById( R.id.pengarang );
        page = (TextView)findViewById( R.id.page );
        viewer = (TextView)findViewById( R.id.viewer );
        share = (ImageView)findViewById( R.id.share );
        download = (Button)findViewById( R.id.download );

        kategori.setText(getIntent().getExtras().getString("KATEGORI"));
        judul.setText(getIntent().getExtras().getString("JUDUL"));
        pengarang.setText(getIntent().getExtras().getString("PENGARANG"));
        page.setText(getIntent().getExtras().getString("PAGE"));
        viewer.setText(getIntent().getExtras().getString("VIEWER"));

        download.setOnClickListener( this );
        share.setOnClickListener( this );

        Glide.with(getApplicationContext()).load(AppConfig.IMG_LINK + "modul/" + getIntent().getExtras().getString("COVER")).placeholder(R.drawable.no_image).into(cover);
    }


    @Override
    public void onClick(View v) {
        if ( v == download ) {
            Uri uri = Uri.parse(getIntent().getExtras().getString("LINK"));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if( v == share ) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Lihat konten ini pada Aplikasi Hi-Depok";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share Via"));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                DetailModulActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
