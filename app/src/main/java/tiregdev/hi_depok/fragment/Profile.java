package tiregdev.hi_depok.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.MenuActivity;
import tiregdev.hi_depok.activity.edit_profile;

import static tiregdev.hi_depok.activity.MenuActivity.results;

/**
 * Created by Muhammad63 on 8/3/2017.
 */

public class Profile extends Fragment {

    ImageView ham;
    View v;

    public static Profile newInstance(){
        Profile fragment = new Profile();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        ham = (ImageView) v.findViewById(R.id.menu);
        final RippleView edt  = (RippleView) v.findViewById(R.id.btnEdtProfile);
        edt.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), edit_profile.class);
                startActivity(w);
            }
        });

        ham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results.openDrawer();
            }
        });

        return v;
    }
}