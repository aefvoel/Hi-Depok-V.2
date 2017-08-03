package tiregdev.hi_depok.activity;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.fragment.HomeFragment;
import tiregdev.hi_depok.fragment.NewsFragment;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                Fragment fragment = null;
                Class fragmentClass = null;

                switch (tabId) {
                    case R.id.tab_news:
                        fragmentClass = NewsFragment.class;
                        break;
                    case R.id.tab_lapor:
                        fragmentClass = NewsFragment.class;
                        break;
                    case R.id.tab_home:
                        fragmentClass = HomeFragment.class;
                        break;
                    case R.id.tab_notif:
                        fragmentClass = NewsFragment.class;
                        break;
                    case R.id.tab_profile:
                        fragmentClass = NewsFragment.class;
                        break;
                }

                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().commit();
            }
        });
    }
}
