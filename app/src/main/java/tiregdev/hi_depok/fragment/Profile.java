package tiregdev.hi_depok.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andexert.library.RippleView;
import com.bumptech.glide.Glide;

import fr.arnaudguyon.smartfontslib.FontTextView;
import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.ChatActivity;
import tiregdev.hi_depok.activity.EditProfileActivity;
import tiregdev.hi_depok.activity.PesanActivity;
import tiregdev.hi_depok.utils.SQLiteHandler;

import static tiregdev.hi_depok.activity.MenuActivity.results;

/**
 * Created by Muhammad63 on 8/3/2017.
 */

public class Profile extends Fragment {

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
        findViews();
        setViews();
        return v;
    }

    private ImageView menu;
    private RippleView pesan;
    private ImageView avatar;
    private FontTextView user;
    private FontTextView jointxt;
    private FontTextView jointgl;
    private RippleView btnEdtProfile;
    private FontTextView bio;
    private ImageView mail;
    private FontTextView email;
    private ImageView earth;
    private FontTextView alamat;
    private ImageView phone;
    private FontTextView tlp;
    private ImageView calender;
    private FontTextView ttl;
    private SQLiteHandler db;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-10-12 12:57:05 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        menu = (ImageView)v.findViewById( R.id.menu );
        pesan = (RippleView)v.findViewById( R.id.pesan );
        avatar = (ImageView)v.findViewById( R.id.avatar );
        user = (FontTextView)v.findViewById( R.id.user );
        jointxt = (FontTextView)v.findViewById( R.id.jointxt );
        jointgl = (FontTextView)v.findViewById( R.id.jointgl );
        btnEdtProfile = (RippleView)v.findViewById( R.id.btnEdtProfile );
        bio = (FontTextView)v.findViewById( R.id.bio );
        mail = (ImageView)v.findViewById( R.id.mail );
        email = (FontTextView)v.findViewById( R.id.email );
        earth = (ImageView)v.findViewById( R.id.earth );
        alamat = (FontTextView)v.findViewById( R.id.alamat );
        phone = (ImageView)v.findViewById( R.id.phone );
        tlp = (FontTextView)v.findViewById( R.id.tlp );
        calender = (ImageView)v.findViewById( R.id.calender );
        ttl = (FontTextView)v.findViewById( R.id.ttl );

        db = new SQLiteHandler(getContext());

        btnEdtProfile.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(w);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results.openDrawer();
            }
        });

        pesan.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent w = new Intent(getActivity(), ChatActivity.class);
                startActivity(w);
            }
        });
    }

    private void setViews(){
        user.setText(db.getUserDetails().get("name"));
        email.setText(db.getUserDetails().get("email"));
        alamat.setText(db.getUserDetails().get("alamat"));
        tlp.setText(db.getUserDetails().get("no_telp"));
        ttl.setText(db.getUserDetails().get("tanggal_lahir"));
        bio.setText(db.getUserDetails().get("bio"));
        jointgl.setText(db.getUserDetails().get("created_at"));

        Glide.with(getContext()).load(db.getUserDetails().get("foto")).placeholder(R.drawable.no_image).into(avatar);
    }

}