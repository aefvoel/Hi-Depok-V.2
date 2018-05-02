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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.ChatActivity;
import tiregdev.hi_depok.activity.EditProfileActivity;
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
        setStatistik();
        return v;
    }

    private ImageView menu;
    private RippleView pesan;
    private ImageView avatar;
    private TextView user;
    private TextView jointxt;
    private TextView jointgl;
    private RippleView btnEdtProfile;
    private TextView bio;
    private ImageView mail;
    private TextView email;
    private ImageView earth;
    private TextView alamat;
    private ImageView phone;
    private TextView tlp;
    private ImageView calender;
    private TextView ttl;
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
        user = (TextView)v.findViewById( R.id.user );
        jointxt = (TextView)v.findViewById( R.id.jointxt );
        jointgl = (TextView)v.findViewById( R.id.jointgl );
        btnEdtProfile = (RippleView)v.findViewById( R.id.btnEdtProfile );
        bio = (TextView)v.findViewById( R.id.bio );
        mail = (ImageView)v.findViewById( R.id.mail );
        email = (TextView)v.findViewById( R.id.email );
        earth = (ImageView)v.findViewById( R.id.earth );
        alamat = (TextView)v.findViewById( R.id.alamat );
        phone = (ImageView)v.findViewById( R.id.phone );
        tlp = (TextView)v.findViewById( R.id.tlp );
        calender = (ImageView)v.findViewById( R.id.calender );
        ttl = (TextView)v.findViewById( R.id.ttl );

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

    private void setStatistik(){
        int maxMessage = 0;
        BarChart hBarChart = v.findViewById(R.id.horbarchart);
        List<BarEntry> hEntries = new ArrayList<>();
        for(int i=0; i < db.getUserActiveInRoom(db.getUserDetails().get("uid")).get("total_post").size(); i++){
            hEntries.add(new BarEntry(i, Integer.valueOf(db.getUserActiveInRoom(db.getUserDetails().get("uid")).get("total_post").get(i))));
            maxMessage = maxMessage + Integer.valueOf(db.getUserActiveInRoom(db.getUserDetails().get("uid")).get("total_post").get(i));
        }

        BarDataSet hSet = new BarDataSet(hEntries, "");
        hSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData hBarData = new BarData(hSet);
        hBarData.setValueFormatter(new LargeValueFormatter());
        hBarData.setBarWidth(0.8f);

        hBarChart.getDescription().setEnabled(false);
        hBarChart.setData(hBarData);
        hBarChart.setFitBars(true);
        hBarChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return db.getUserActiveInRoom(db.getUserDetails().get("uid")).get("room_name").get((int) value);
            }
        });
        hBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        hBarChart.getXAxis().setGranularity(1f);
        hBarChart.getAxisRight().setEnabled(false);
        hBarChart.getAxisLeft().setEnabled(false);
        hBarChart.getXAxis().setDrawGridLines(false);
        hBarChart.getXAxis().setDrawAxisLine(false);
        hBarChart.getAxisLeft().setAxisMaximum(maxMessage+1);
        hBarChart.getAxisLeft().setAxisMinimum(0);
        hBarChart.getLegend().setEnabled(false);
        hBarChart.setDrawGridBackground(false);
        hBarChart.setPinchZoom(false);
        hBarChart.setDoubleTapToZoomEnabled(false);
        hBarChart.animateXY(1400, 1400);
        hBarChart.invalidate();



        PieChart pieChart = (PieChart) v.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        int maxPost = 0;
        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        for(int i=0; i < db.getSentimentPostByUser(db.getUserDetails().get("uid")).get("total_post").size(); i++){
            maxPost = maxPost + Integer.valueOf(db.getSentimentPostByUser(db.getUserDetails().get("uid")).get("total_post").get(i));
        }
        for(int i=0; i < db.getSentimentPostByUser(db.getUserDetails().get("uid")).get("total_post").size(); i++){
            yvalues.add(new PieEntry(
                    Integer.valueOf(db.getSentimentPostByUser(db.getUserDetails().get("uid")).get("total_post").get(i)) * 100 / maxPost,
                    db.getSentimentPostByUser(db.getUserDetails().get("uid")).get("sentiment_name").get(i)));
        }

        PieDataSet dataSet = new PieDataSet(yvalues, "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setDrawHoleEnabled(false);


        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setData(data);
        pieChart.animateXY(1400, 1400);
        pieChart.invalidate();
    }

}