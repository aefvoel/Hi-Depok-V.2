package tiregdev.hi_depok.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by TiregDev on 30/08/2017.
 */

public class DataWisataFragment extends Fragment {

    ViewPager  pager;
    View v;
    public static DataWisataFragment newInstance(String title) {
        DataWisataFragment fragment = new DataWisataFragment();

        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);

        return fragment;
    }

    public String getTitle() {
        Bundle args = getArguments();
        return args.getString("title", "NO TITLE FOUND");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_data_wisata, container, false);
        pager = (ViewPager) v.findViewById(R.id.pager);
        TabLayout tabs = (TabLayout) v.findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);
        setupViewPager(pager);
        return v;
    }

    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(ListCariDataFragment.newInstance(getTitle(), "tibadah"),"TEMPAT IBADAH");
        adapter.addFragment(ListCariDataFragment.newInstance(getTitle(), "twisata"), "TEMPAT WISATA");
        adapter.addFragment(ListCariDataFragment.newInstance(getTitle(), "tpublik"), "TAMAN PUBLIK");
        adapter.addFragment(ListCariDataFragment.newInstance(getTitle(), "gor"), "GOR");
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
