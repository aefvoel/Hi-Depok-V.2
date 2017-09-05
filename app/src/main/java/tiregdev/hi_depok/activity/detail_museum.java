package tiregdev.hi_depok.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import tiregdev.hi_depok.R;

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
    }

    public void setCollaps(){
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        placePicture = (ImageView) findViewById(R.id.image);
        placePicture.setImageResource(R.drawable.header_bg);

        collapsingToolbar.setTitle("Detail Museum");
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
