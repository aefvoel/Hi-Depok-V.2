package tiregdev.hi_depok.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.fragment.ChatFragment;
import tiregdev.hi_depok.fragment.Home;
import tiregdev.hi_depok.fragment.Home_2;
import tiregdev.hi_depok.fragment.News;
import tiregdev.hi_depok.fragment.Notif;
import tiregdev.hi_depok.fragment.Profile;
import tiregdev.hi_depok.fragment.Masterpiece;
import tiregdev.hi_depok.utils.SQLiteHandler;
import tiregdev.hi_depok.utils.SessionManager;

public class MenuActivity extends AppCompatActivity {

    private boolean backPressedToExitOnce = false;
    private Toast toast = null;
    int abc;
    private Drawer result = null;
    private AccountHeader headerResult = null;
    public static Drawer results = null;
    ImageView ham;
    BottomBar bottomBar;
    private SQLiteHandler db;
    private SessionManager session;

    FragmentManager mFragmentManager;
    Fragment mFragment;
    Fragment newsFragment = News.newInstance();
    Fragment notifFragment = Notif.newInstance();
    Fragment homeFragment = Home.newInstance();
    Fragment chatFragment = ChatFragment.newInstance();
    Fragment profileFragment = Profile.newInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.tab_home);

        mFragmentManager = getSupportFragmentManager();
        createFragments();
        setUIListeners();

        drawer();
    }

    private void setUIListeners() {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_news:
                        hideShowFragment(mFragment, newsFragment);
                        mFragment = newsFragment;
                        break;
                    case R.id.tab_discussion:
                        hideShowFragment(mFragment, chatFragment);
                        mFragment = chatFragment;
                        break;
                    case R.id.tab_home:
                        hideShowFragment(mFragment, homeFragment);
                        mFragment = homeFragment;
                        break;
                    case R.id.tab_notification:
                        hideShowFragment(mFragment, notifFragment);
                        mFragment = notifFragment;
                        break;
                    case R.id.tab_profile:
                        hideShowFragment(mFragment, profileFragment);
                        mFragment = profileFragment;
                        break;
                }
            }
        });
    }

    //Method to add and hide all of the fragments you need to. In my case I hide 4 fragments, while 1 is visible, that is the first one.
    private void addHideFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().add(R.id.frameLayout, fragment).hide(fragment).commit();
    }

    //Method to hide and show the fragment you need. It is called in the BottomBar click listener.
    private void hideShowFragment(Fragment hide, Fragment show) {
        mFragmentManager.beginTransaction().hide(hide).show(show).commit();
    }

    //Add all the fragments that need to be added and hidden. Also, add the one that is supposed to be the starting one, that one is not hidden.
    private void createFragments() {
        addHideFragment(newsFragment);
        addHideFragment(chatFragment);
        addHideFragment(notifFragment);
        addHideFragment(profileFragment);
        mFragmentManager.beginTransaction().add(R.id.frameLayout, homeFragment).commit();
        mFragment = homeFragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        super.onSaveInstanceState(outState);
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
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        int selectedItemId = bottomBar.getCurrentTabId();
        if (results != null && results.isDrawerOpen()) {
            results.closeDrawer();
        } else {
            if (R.id.tab_home != selectedItemId) {
                bottomBar.selectTabAtPosition(2, true);
            } else if (backPressedToExitOnce) {
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
        final IProfile profile = new ProfileDrawerItem().withName(db.getUserDetails().get("name")).
                withEmail(db.getUserDetails().get("email")).withIcon(Uri.parse(db.getUserDetails().get("foto")));

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                Glide.with(imageView.getContext()).load(db.getUserDetails().get("foto")).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Glide.clear(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx, String tag) {
                //define different placeholders for different imageView targets
                //default tags are accessible via the DrawerImageLoader.Tags
                //custom ones can be checked via string. see the CustomUrlBasePrimaryDrawerItem LINE 111
                if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
                    return DrawerUIUtils.getPlaceHolder(ctx);
                } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(com.mikepenz.materialdrawer.R.color.primary).sizeDp(56);
                } else if ("customUrlItem".equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.md_red_500).sizeDp(56);
                }

                //we use the default one for
                //DrawerImageLoader.Tags.PROFILE_DRAWER_ITEM.name()

                return super.placeholder(ctx, tag);
            }
        });

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
                .withSelectedItem(-1)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(getResources().getString(R.string.app_name_bold))
                                .withDescription("We Share, We Care")
                                .withEnabled(false)
                                .withDisabledTextColor(Color.parseColor("#50aacd")),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem()
                                .withName("Kritik dan Saran")
                                .withDescription("Memberikan kritik atau saran untuk Kota Depok atau untuk pengembangan aplikasi ini")
                                .withIcon(R.drawable.ic_format_quote_grey_600_24dp)
                                .withIdentifier(8),
                        new PrimaryDrawerItem()
                                .withName("FAQ")
                                .withDescription("Frequently Asked Question")
                                .withIcon(R.drawable.ic_help_grey_600_24dp)
                                .withIdentifier(5),
                        new PrimaryDrawerItem()
                                .withName("About")
                                .withDescription("Tentang aplikasi")
                                .withIcon(R.drawable.ic_info_grey_600_24dp)
                                .withIdentifier(6),
                        new PrimaryDrawerItem()
                                .withName("Logout")
                                .withDescription("Keluar akun")
                                .withIcon(R.drawable.ic_exit_to_app_grey_600_24dp)
                                .withIdentifier(7)
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
                        if (drawerItem != null) {
                            if (drawerItem.getIdentifier() == 1){
                                Toast.makeText(MenuActivity.this, "bisa", Toast.LENGTH_SHORT).show();
                            } else if (drawerItem.getIdentifier() == 2){
                                Toast.makeText(MenuActivity.this, "ini bisa", Toast.LENGTH_SHORT).show();
                            } else if (drawerItem.getIdentifier() == 3){
                                Toast.makeText(MenuActivity.this, "bisa juga", Toast.LENGTH_SHORT).show();
                            } else if (drawerItem.getIdentifier() == 4){
                                Toast.makeText(MenuActivity.this, "bisa banget", Toast.LENGTH_SHORT).show();
                            } else if (drawerItem.getIdentifier() == 5){
                                Intent i = new Intent(MenuActivity.this, FAQActivity.class);
                                startActivity(i);
                            } else if (drawerItem.getIdentifier() == 6){
                                Intent i = new Intent(MenuActivity.this, AboutActivity.class);
                                startActivity(i);
                            } else if (drawerItem.getIdentifier() == 7){
                                logoutUser();
                                Toast.makeText(MenuActivity.this, "Logout Berhasil!", Toast.LENGTH_SHORT).show();
                            } else if (drawerItem.getIdentifier() == 8) {
                                Intent i = new Intent(MenuActivity.this, KritikSaranActivity.class);
                                startActivity(i);
                            }
                        }

                        return false;
                    }
                })
                .withDrawerGravity(Gravity.END)
                .build();
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
