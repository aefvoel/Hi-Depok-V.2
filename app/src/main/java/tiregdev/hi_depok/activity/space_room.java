package tiregdev.hi_depok.activity;

import android.app.DatePickerDialog;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;
import tiregdev.hi_depok.R;

import static tiregdev.hi_depok.R.layout.activity_signup;

public class space_room extends AppCompatActivity {

    BannerSlider banner;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Calendar dateAndTime = Calendar.getInstance();
    Button tglPinjam;
    DatePickerDialog.OnDateSetListener d =
            new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker view, int year, int month,
                                      int day){
                    dateAndTime.set(Calendar.YEAR, year);
                    dateAndTime.set(Calendar.MONTH, month);
                    dateAndTime.set(Calendar.DAY_OF_MONTH, day);
                    updateLabel();
                }
            };

    private void updateLabel(){
        tglPinjam.setText(
                sdf.format(dateAndTime.getTime()));
    }

    private void settingTanggal(){
        new DatePickerDialog(space_room.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void setTglPinjam(){
        tglPinjam = (Button) findViewById(R.id.tgl);
        tglPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingTanggal();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_room);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupBanner();
        setTglPinjam();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                space_room.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setupBanner(){
        banner = (BannerSlider) findViewById(R.id.banner_slide);
        addBanners();
    }

    private void addBanners() {
        List<Banner> banners = new ArrayList<>();
        banners.add(new DrawableBanner(R.drawable.space_1));
        banners.add(new DrawableBanner(R.drawable.space_2));
        banners.add(new DrawableBanner(R.drawable.space_3));
        banner.setBanners(banners);
    }
}
