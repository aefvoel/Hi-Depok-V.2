package tiregdev.hi_depok.activity;

import android.content.pm.ActivityInfo;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.viewpagerindicator.CirclePageIndicator;

import tiregdev.hi_depok.R;

public class detail_search extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    ViewPager viewPager;
    PagerAdapter adapter;
    CirclePageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_search);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        viewPager = (ViewPager) findViewById(R.id.pager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);

        collapsingToolbar.setTitle("Detail Data");

        setupToolbar();
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
