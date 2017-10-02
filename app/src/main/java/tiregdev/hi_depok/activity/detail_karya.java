package tiregdev.hi_depok.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.adapter_komen_karya;
import tiregdev.hi_depok.adapter.adapter_modul;
import tiregdev.hi_depok.adapter.adapter_news_blog;
import tiregdev.hi_depok.model.itemObject_komen_karya;
import tiregdev.hi_depok.model.itemObject_modul;
import tiregdev.hi_depok.model.itemObject_news_Blog;

import static tiregdev.hi_depok.R.id.view_komentar;
import static tiregdev.hi_depok.R.id.view_news;

public class detail_karya extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    ImageView placePicture, share;
    RecyclerView rView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_karya);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setCollaps();
        setupToolbar();
        setupAdapter();
        share();
    }

    public void share(){
        share = (ImageView) findViewById(R.id.shareIcon);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Lihat konten ini pada Aplikasi Hi-Depok";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                view.getContext().startActivity(Intent.createChooser(sharingIntent, "Share Via"));
            }
        });
    }

    public void setCollaps(){
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        placePicture = (ImageView) findViewById(R.id.image);
        placePicture.setImageResource(R.drawable.report_banjir);

        collapsingToolbar.setTitle("Detail Karya");
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

                detail_karya.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setupAdapter(){
        List<itemObject_komen_karya> rowListItem = getAllItemList();
        LinearLayoutManager lLayout = new LinearLayoutManager(detail_karya.this);

        rView = (RecyclerView) findViewById(view_komentar);
        rView.setNestedScrollingEnabled(false);
        rView.setLayoutManager(lLayout);

        adapter_komen_karya rcAdapter = new adapter_komen_karya(detail_karya.this, rowListItem);
        rView.setAdapter(rcAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(detail_karya.this, lLayout.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(detail_karya.this, R.drawable.line_view));
        rView.addItemDecoration(dividerItemDecoration);
    }

    private List<itemObject_komen_karya> getAllItemList(){
        List<itemObject_komen_karya> allItems = new ArrayList<>();
        allItems.add(new itemObject_komen_karya(getResources().getString(R.string.nama1),
                getResources().getString(R.string.time),getResources().getString(R.string.desc), R.drawable.char_icon));
        allItems.add(new itemObject_komen_karya(getResources().getString(R.string.nama2),
                getResources().getString(R.string.time1),getResources().getString(R.string.desc), R.drawable.char_icon));
        allItems.add(new itemObject_komen_karya(getResources().getString(R.string.nama),
                getResources().getString(R.string.time2),getResources().getString(R.string.desc), R.drawable.char_icon));
        allItems.add(new itemObject_komen_karya(getResources().getString(R.string.nama3),
                getResources().getString(R.string.time2),getResources().getString(R.string.desc), R.drawable.char_icon));

        return allItems;
    }
}
