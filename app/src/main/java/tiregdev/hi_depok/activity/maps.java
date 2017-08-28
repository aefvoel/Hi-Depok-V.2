package tiregdev.hi_depok.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jaredrummler.materialspinner.MaterialSpinner;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.fragment.Map;

public class maps extends AppCompatActivity{

//    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
//    static final LatLng KIEL = new LatLng(53.551, 9.993);
//    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String searhdata[] = {"Semua Data",
                "Rumah Sakit",
                "Apotek",
                "Puskesmas",
                "Panti Pijat",
                "Bidan",
                "Panti Asuhan",
                "Tempat Ibadah",
                "Restaurant",
                "Pasar Tradisional",
                "Pasar Modern",
                "PAM",
                "PLN",
                "Ambulance",
                "GOR",
                "Tempat Wisata",
                "Taman Publik",
                "Perguruan Tinggi",
                "Sekolah",
                "Pemadam Kebakaran",
                "Pos Polisi",
                "Terminal dan Stasiun",
                "TPU",
                "Perpustakaan atau Taman Bacaan",
                "UMKM"};
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_seacrh);
        spinner.setItems(searhdata);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_SHORT).show();
            }
        });
        setFragment(new Map());

//        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.maps));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                // todo: goto back activity from here
                maps.this.finish();
                return true;

            case R.id.more:
                Toast.makeText(this, "Selamat datang di Hi Depok!", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}
