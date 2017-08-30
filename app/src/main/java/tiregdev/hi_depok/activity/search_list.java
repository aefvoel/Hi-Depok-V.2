package tiregdev.hi_depok.activity;

import android.content.pm.ActivityInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.fragment.Master_karya;
import tiregdev.hi_depok.fragment.Master_modul;
import tiregdev.hi_depok.fragment.Master_museum;
import tiregdev.hi_depok.fragment.Masterpiece;
import tiregdev.hi_depok.fragment.data_kesehatan;
import tiregdev.hi_depok.fragment.data_pendidikan;
import tiregdev.hi_depok.fragment.data_sandang;
import tiregdev.hi_depok.fragment.data_sosial;
import tiregdev.hi_depok.fragment.data_umum;
import tiregdev.hi_depok.fragment.data_wisata;

public class search_list extends AppCompatActivity {

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setUpToolbar();
        setUpPager();
    }

    public void setUpToolbar(){
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                // todo: goto back activity from here
                search_list.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUpPager(){
        pager = (ViewPager) findViewById(R.id.pager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);

        setupViewPager(pager);
    }

    private void setupViewPager(ViewPager viewPager) {

        search_list.Adapter adapter = new search_list.Adapter(getSupportFragmentManager());
        adapter.addFragment(new data_kesehatan(), "KESEHATAN");
        adapter.addFragment(new data_pendidikan(), "PENDIDIKAN");
        adapter.addFragment(new data_sandang(), "SANDANG DAN PANGAN");
        adapter.addFragment(new data_wisata(), "WISATA");
        adapter.addFragment(new data_sosial(), "SOSIAL");
        adapter.addFragment(new data_umum(), "UMUM");
        viewPager.setAdapter(adapter);

    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
