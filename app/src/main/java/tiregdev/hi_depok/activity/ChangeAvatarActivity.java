package tiregdev.hi_depok.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.ChangeAvatarAdapter;
import tiregdev.hi_depok.model.AvatarList;

public class ChangeAvatarActivity extends AppCompatActivity {

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
                ChangeAvatarActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUpAdapter(){
        List<AvatarList> rowListItem = getAllItemList();

        RecyclerView rView = (RecyclerView) findViewById(R.id.rview);
        rView.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ChangeAvatarActivity.this,3);
        rView.setLayoutManager(gridLayoutManager);// set LayoutManager to RecyclerView

        ChangeAvatarAdapter rcAdapter = new ChangeAvatarAdapter(ChangeAvatarActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private List<AvatarList> getAllItemList(){
        List<AvatarList> allItems = new ArrayList<>();
        allItems.add(new AvatarList("Children",R.drawable.char_icon));
        allItems.add(new AvatarList("Children",R.drawable.char_icon));
        allItems.add(new AvatarList("Children",R.drawable.char_icon));
        allItems.add(new AvatarList("Children",R.drawable.char_icon));
        allItems.add(new AvatarList("Children",R.drawable.char_icon));
        allItems.add(new AvatarList("Children",R.drawable.char_icon));
        allItems.add(new AvatarList("Children",R.drawable.char_icon));
        allItems.add(new AvatarList("Children",R.drawable.char_icon));
        allItems.add(new AvatarList("Children",R.drawable.char_icon));
        allItems.add(new AvatarList("Children",R.drawable.char_icon));
        allItems.add(new AvatarList("Children",R.drawable.char_icon));
        allItems.add(new AvatarList("Children",R.drawable.char_icon));

        return allItems;
    }
}
