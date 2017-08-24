package tiregdev.hi_depok.activity;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.fragment.Home;
import tiregdev.hi_depok.fragment.News;
import tiregdev.hi_depok.fragment.Notif;
import tiregdev.hi_depok.fragment.Profile;
import tiregdev.hi_depok.fragment.Masterpiece;

public class MenuActivity extends AppCompatActivity {

    private boolean backPressedToExitOnce = false;
    private Toast toast = null;
    int abc;
    private Drawer result = null;
    private AccountHeader headerResult = null;
    public static Drawer results = null;
    ImageView ham;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        drawer();

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
                        selectedFragment = Masterpiece.newInstance();
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

                BottomBarTab nearby = bottomBar.getTabWithId(R.id.tab_notif);
                nearby.setBadgeCount(5);
            }
        });
        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, Home.newInstance());
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
//        outState = result.saveInstanceState(outState);
//        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        BottomBar bottomBar = (BottomBar)findViewById(R.id.bottomBar);
        int selectedItemId = bottomBar.getCurrentTabId();
        if (results != null && results.isDrawerOpen()) {
            results.closeDrawer();
        } else {
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



    private void drawer() {
        // Create a few sample profile
        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(R.drawable.char_icon);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header_bg)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(
                        profile
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
//                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withIdentifier(PROFILE_SETTING),
//                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                )
//                .withSavedInstance(savedInstanceState)
                .build();

        //first create the main drawer (this one will be used to add the second drawer on the other side)
        results = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withDisplayBelowStatusBar(true)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName("Ok")
                                .withSetSelected(false),
                        new SecondaryDrawerItem()
                                .withName("Hai")
                                .withSelectable(false)
                                .withDescription("Halo")
                                .withEnabled(false),
                        new DividerDrawerItem()
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
//                        Toast.makeText(MainActivity.this, "onDrawerOpened", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
//                        Toast.makeText(MainActivity.this, "onDrawerClosed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

                        if (drawerItem != null) {
                            if (drawerItem instanceof Nameable) {
                                Toast.makeText(MenuActivity.this, ((Nameable) drawerItem).getName().getText(MenuActivity.this), Toast.LENGTH_SHORT).show();
                            }

                            if (drawerItem instanceof Badgeable) {
                                Badgeable badgeable = (Badgeable) drawerItem;
                                if (badgeable.getBadge() != null) {
                                    //note don't do this if your badge contains a "+"
                                    //only use toString() if you set the test as String
                                    int badge = Integer.valueOf(badgeable.getBadge().toString());
                                    if (badge > 0) {
                                        badgeable.withBadge(String.valueOf(badge - 1));
                                        results.updateItem(drawerItem);
                                    }
                                }
                            }
                        }

                        return false;
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof SecondaryDrawerItem) {
                            Toast.makeText(MenuActivity.this, ((SecondaryDrawerItem) drawerItem).getName().getText(MenuActivity.this), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .withDrawerGravity(Gravity.END)
                .build();

    }

}
