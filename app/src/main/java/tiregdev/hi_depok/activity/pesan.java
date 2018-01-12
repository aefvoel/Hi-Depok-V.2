package tiregdev.hi_depok.activity;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.adapter_pesan;
import tiregdev.hi_depok.model.itemObject_pesan;

public class pesan extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshRecyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setUpAdapter();
        setUpToolbar();
        swipeRefresh();
    }

    public void swipeRefresh(){
        swipeRefreshRecyclerList = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_recycler_list);
        swipeRefreshRecyclerList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Do your stuff on refresh
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (swipeRefreshRecyclerList.isRefreshing())
                            swipeRefreshRecyclerList.setRefreshing(false);
                    }
                }, 5000);

            }
        });
    }

    public void setUpToolbar(){
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                pesan.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUpAdapter(){
        List<itemObject_pesan> rowListItem = getAllItemList();
        LinearLayoutManager lLayout = new LinearLayoutManager(getBaseContext());

        RecyclerView rView = (RecyclerView) findViewById(R.id.view_pesan);
        rView.setLayoutManager(lLayout);

        adapter_pesan rcAdapter = new adapter_pesan(getBaseContext(), rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private List<itemObject_pesan> getAllItemList(){
        List<itemObject_pesan> allItems = new ArrayList<>();
        allItems.add(new itemObject_pesan(getResources().getString(R.string.nama), getResources().getString(R.string.jam3),getResources().getString(R.string.pesan),R.drawable.char_icon));
        allItems.add(new itemObject_pesan(getResources().getString(R.string.nama1), getResources().getString(R.string.jam),getResources().getString(R.string.pesan2),R.drawable.char_icon));
        allItems.add(new itemObject_pesan(getResources().getString(R.string.nama2), getResources().getString(R.string.jam1),getResources().getString(R.string.pesan3),R.drawable.char_icon));
        allItems.add(new itemObject_pesan(getResources().getString(R.string.nama3), getResources().getString(R.string.jam1),getResources().getString(R.string.pesan4),R.drawable.char_icon));
        allItems.add(new itemObject_pesan(getResources().getString(R.string.nama), getResources().getString(R.string.jam2),getResources().getString(R.string.pesan),R.drawable.char_icon));

        return allItems;
    }
}
