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

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.fragment.DataKesehatanFragment;
import tiregdev.hi_depok.fragment.DataPendidikanFragment;
import tiregdev.hi_depok.fragment.DataSandangFragment;
import tiregdev.hi_depok.fragment.DataSosialFragment;
import tiregdev.hi_depok.fragment.DataUmumFragment;
import tiregdev.hi_depok.fragment.DataWisataFragment;

public class CariDataActivity extends AppCompatActivity {

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
                CariDataActivity.this.finish();
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

        CariDataActivity.Adapter adapter = new CariDataActivity.Adapter(getSupportFragmentManager());
        adapter.addFragment(DataKesehatanFragment.newInstance("kesehatan"), "KESEHATAN");
        adapter.addFragment(DataPendidikanFragment.newInstance("pendidikan"), "PENDIDIKAN");
        adapter.addFragment(DataSandangFragment.newInstance("sandangpangan"), "SANDANG DAN PANGAN");
        adapter.addFragment(DataWisataFragment.newInstance("wisata"), "WISATA");
        adapter.addFragment(new DataSosialFragment(), "SOSIAL");
        adapter.addFragment(DataUmumFragment.newInstance("umkm"), "UMUM");
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
