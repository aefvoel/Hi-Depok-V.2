package tiregdev.hi_depok.fragment;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;

import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.pesan;

import static tiregdev.hi_depok.activity.MenuActivity.results;

/**
 * Created by Muhammad63 on 8/3/2017.
 */

public class Masterpiece extends Fragment {

    ViewPager pager;
    ImageView ham;
    View v;
    public static Masterpiece newInstance(){
        Masterpiece fragment = new Masterpiece();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public Masterpiece(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_masterpiece, container, false);
        ham = (ImageView) v.findViewById(R.id.menu);

        ham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results.openDrawer();
            }
        });

        pager = (ViewPager) v.findViewById(R.id.pager);

        TabLayout tabs = (TabLayout) v.findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);

        setupViewPager(pager);
        setPesanLink();

        return v;
    }

    public void setPesanLink(){
        final RippleView rippleViews = (RippleView) v.findViewById(R.id.pesan);
        rippleViews.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), pesan.class);
                startActivity(w);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new Master_karya(), "KARYA");
        adapter.addFragment(new Master_modul(), "MODUL");
        adapter.addFragment(new Master_museum(), "MUSEUM");
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
