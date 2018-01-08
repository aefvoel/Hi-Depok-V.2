package tiregdev.hi_depok.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.github.clans.fab.FloatingActionButton;

import fr.arnaudguyon.smartfontslib.FontButton;
import fr.arnaudguyon.smartfontslib.FontTextView;
import tiregdev.hi_depok.R;

public class detail_search extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_search);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupToolbar();
        findViews();
    }

    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView image;
    private FontTextView namaTempat;
    private FontTextView alamat;
    private FloatingActionButton direct;
    private FontTextView desc;
    private FontTextView jadwal;
    private FontTextView more;
    private FontButton call;


    private void findViews() {
        collapsingToolbar = (CollapsingToolbarLayout)findViewById( R.id.collapsing_toolbar );
        image = (ImageView)findViewById( R.id.image );
        namaTempat = (FontTextView)findViewById( R.id.namaTempat );
        alamat = (FontTextView)findViewById( R.id.alamat );
        direct = (FloatingActionButton) findViewById( R.id.direct );
        desc = (FontTextView)findViewById( R.id.desc );
        jadwal = (FontTextView)findViewById( R.id.jadwal );
        more = (FontTextView)findViewById( R.id.more );
        call = (FontButton)findViewById( R.id.call );

        image.setImageResource(R.drawable.bg_rs);

        collapsingToolbar.setTitle(getIntent().getExtras().getString("NAMA_TEMPAT"));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.transperent));
        collapsingToolbar.setCollapsedTitleTextColor(Color.rgb(255, 255, 255));

        namaTempat.setText(getIntent().getExtras().getString("NAMA_TEMPAT"));
        alamat.setText(getIntent().getExtras().getString("ALAMAT"));
        desc.setText(getIntent().getExtras().getString("DESKRIPSI"));
        jadwal.setText(getIntent().getExtras().getString("JAM_OPERASI"));
        more.setText(getIntent().getExtras().getString("WEBSITE"));

        direct.setOnClickListener( this );
        call.setOnClickListener( this );
    }


    @Override
    public void onClick(View v) {
        if ( v == direct ) {
            // Handle clicks for direct
        } else if ( v == call ) {
            Intent dial = new Intent();
            dial.setAction("android.intent.action.DIAL");
            dial.setData(Uri.parse("tel:" + getIntent().getExtras().getString("NO_TELP")));
            startActivity(dial);
        }
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

                detail_search.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
