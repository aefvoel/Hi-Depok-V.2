package tiregdev.hi_depok.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import tiregdev.hi_depok.R;

import static tiregdev.hi_depok.activity.MenuActivity.results;

/**
 * Created by Muhammad63 on 8/3/2017.
 */

public class News extends Fragment {

    ImageView ham;
    View v;
    public static News newInstance(){
        News fragment = new News();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_news, container, false);
        ham = (ImageView) v.findViewById(R.id.menu);

        ham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results.openDrawer();
            }
        });

        return v;
    }
}