package tiregdev.hi_depok.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.adapter_avatar;
import tiregdev.hi_depok.model.itemObject_avatar;

public class change_avatar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setUpToolbar();
        setUpAdapter();
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
                change_avatar.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUpAdapter(){
        List<itemObject_avatar> rowListItem = getAllItemList();

        RecyclerView rView = (RecyclerView) findViewById(R.id.rview);
        rView.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(change_avatar.this,3);
        rView.setLayoutManager(gridLayoutManager);// set LayoutManager to RecyclerView

        adapter_avatar rcAdapter = new adapter_avatar(change_avatar.this, rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private List<itemObject_avatar> getAllItemList(){
        List<itemObject_avatar> allItems = new ArrayList<>();
        allItems.add(new itemObject_avatar("Children",R.drawable.char_icon));
        allItems.add(new itemObject_avatar("Children",R.drawable.char_icon));
        allItems.add(new itemObject_avatar("Children",R.drawable.char_icon));
        allItems.add(new itemObject_avatar("Children",R.drawable.char_icon));
        allItems.add(new itemObject_avatar("Children",R.drawable.char_icon));
        allItems.add(new itemObject_avatar("Children",R.drawable.char_icon));
        allItems.add(new itemObject_avatar("Children",R.drawable.char_icon));
        allItems.add(new itemObject_avatar("Children",R.drawable.char_icon));
        allItems.add(new itemObject_avatar("Children",R.drawable.char_icon));
        allItems.add(new itemObject_avatar("Children",R.drawable.char_icon));
        allItems.add(new itemObject_avatar("Children",R.drawable.char_icon));
        allItems.add(new itemObject_avatar("Children",R.drawable.char_icon));

        return allItems;
    }
}
