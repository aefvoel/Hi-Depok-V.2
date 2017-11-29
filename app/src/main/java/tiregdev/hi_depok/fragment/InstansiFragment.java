package tiregdev.hi_depok.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;

/**
 * Created by Muhammad63 on 10/6/2017.
 */

public class InstansiFragment extends Fragment {

    ViewPager pager;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_ntpd_2, container, false);
        setUpPager();
        return v;
    }

    public void setUpPager(){
        pager = (ViewPager) v.findViewById(R.id.pager);

        TabLayout tabs = (TabLayout) v.findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);

        setupViewPager(pager);
    }

    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(ListInstansiFragment.newInstance("kecamatan"), "KECAMATAN/KELURAHAN");
        adapter.addFragment(ListInstansiFragment.newInstance("damkar"), "INSTANSI LAIN");
        adapter.addFragment(ListInstansiFragment.newInstance("puskesmas"), "PUSKESMAS");
        adapter.addFragment(ListInstansiFragment.newInstance("rs"), "UPT");
        adapter.addFragment(ListInstansiFragment.newInstance("polisi"), "KEPOLISIAN/TNI");
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
