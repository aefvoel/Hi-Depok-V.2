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
import tiregdev.hi_depok.activity.Diagnosa;
import tiregdev.hi_depok.activity.Ensiklopedia;
import tiregdev.hi_depok.activity.MapsActivity;
import tiregdev.hi_depok.activity.rsud;
import tiregdev.hi_depok.activity.sahabat_kos;
import tiregdev.hi_depok.activity.space_room;
import tiregdev.hi_depok.activity.VolunteerActivity;
import tiregdev.hi_depok.activity.ntpd;

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
                Intent w = new Intent(getActivity(), VolunteerActivity.class);
                startActivity(w);
            }
        });

        final RippleView rippleView = (RippleView) v.findViewById(R.id.seacrh);
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), MapsActivity.class);
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

        final RippleView ntpd = (RippleView) v.findViewById(R.id.panicbtn);
        ntpd.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), ntpd.class);
                startActivity(w);
            }
        });

        final RippleView rs = (RippleView) v.findViewById(R.id.rsud);
        rs.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), rsud.class);
                startActivity(w);
            }
        });
        final RippleView ensiklo = (RippleView) v.findViewById(R.id.ensiklopedi);
        ensiklo.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), Ensiklopedia.class);
                startActivity(w);
            }
        });
        final RippleView diagnos = (RippleView) v.findViewById(R.id.diagnosa);
        diagnos.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), Diagnosa.class);
                startActivity(w);
            }
        });

        return v;
    }
}