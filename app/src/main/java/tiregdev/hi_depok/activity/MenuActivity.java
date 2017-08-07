package tiregdev.hi_depok.activity;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.fragment.Home;
import tiregdev.hi_depok.fragment.News;
import tiregdev.hi_depok.fragment.Notif;
import tiregdev.hi_depok.fragment.Profile;
import tiregdev.hi_depok.fragment.Report;

public class MenuActivity extends AppCompatActivity {

    private boolean backPressedToExitOnce = false;
    private Toast toast = null;
    int abc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.tab_home);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int item) {
                Fragment selectedFragment = null;
                switch (item) {
                    case R.id.tab_news:
                        abc = 0;
                        selectedFragment = News.newInstance();
                        break;
                    case R.id.tab_lapor:
                        abc = 1;
                        selectedFragment = Report.newInstance();
                        break;
                    case R.id.tab_home:
                        abc = 2;
                        selectedFragment = Home.newInstance();
                        break;
                    case R.id.tab_notif:
                        abc = 3;
                        selectedFragment = Notif.newInstance();
                        break;
                    case R.id.tab_profile:
                        abc = 4;
                        selectedFragment = Profile.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(bottomBar.getCurrentTabPosition() > abc){
                    transaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slide_to_right);
                }
                else if(bottomBar.getCurrentTabPosition() < abc) {
                    transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                transaction.replace(R.id.frameLayout, selectedFragment);
                transaction.commit();
            }
        });
        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, Home.newInstance());
        transaction.commit();

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        BottomBar bottomBar = (BottomBar)findViewById(R.id.bottomBar);
        int selectedItemId = bottomBar.getCurrentTabId();
        if (R.id.tab_home != selectedItemId){
            bottomBar.selectTabAtPosition(2,true);
        } else if (backPressedToExitOnce){
            finishAffinity();
        } else {
            this.backPressedToExitOnce = true;
            showToast("Press again to exit");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressedToExitOnce = false;
                }
            }, 2000);
        }
    }

    private void showToast(String message) {
        if (this.toast == null) {
            // Create toast if found null, it would he the case of first call only
            this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

        } else if (this.toast.getView() == null) {
            // Toast not showing, so create new one
            this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

        } else {
            // Updating toast message is showing
            this.toast.setText(message);
        }
        // Showing toast finally
        this.toast.show();
    }

    private void killToast() {
        if (this.toast != null) {
            this.toast.cancel();
        }
    }

    @Override
    protected void onPause() {
        killToast();
        super.onPause();
    }

}
