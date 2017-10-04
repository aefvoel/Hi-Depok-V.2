package tiregdev.hi_depok.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.maps;
import tiregdev.hi_depok.activity.sahabat_kos;
import tiregdev.hi_depok.activity.space_room;
import tiregdev.hi_depok.activity.volunteer;

/**
 * Created by Muhammad63 on 8/3/2017.
 */

public class Home extends Fragment {

    View v;

    public static Home newInstance(){
        Home fragment = new Home();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        final RippleView spaceroom = (RippleView) v.findViewById(R.id.spaceroom);
        spaceroom.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), space_room.class);
                startActivity(w);
            }
        });

        final RippleView volunteers = (RippleView) v.findViewById(R.id.volunteer);
        volunteers.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), volunteer.class);
                startActivity(w);
            }
        });

        final RippleView rippleView = (RippleView) v.findViewById(R.id.seacrh);
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), maps.class);
                startActivity(w);
            }
        });

        final RippleView kos = (RippleView) v.findViewById(R.id.sahabatKos);
        kos.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), sahabat_kos.class);
                startActivity(w);
            }
        });

        return v;
    }
}