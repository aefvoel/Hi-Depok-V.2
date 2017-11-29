package tiregdev.hi_depok.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.model.CariData;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerClickListener {

    private CariData mPost;
    private JSONObject jsonObject;
    private MaterialSpinner spinner;
    private GoogleMap mMap;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private double lat;
    private double lng;
    private HashMap<String,Marker> markers = new HashMap<>();
    private HashMap<String, String> mHashMap = new HashMap<>();
    private HashMap<String, HashMap> extraHashMap = new HashMap<>();

    private static final String TAG = MapsActivity.class.getSimpleName();
    private String extraLink;

    private List<Marker> originMarkers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViews();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        // Zoom to device's current location
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            String provider = locationManager.getBestProvider(new Criteria(), true);
            Location myLocation = locationManager.getLastKnownLocation(provider);
            if (myLocation != null) {
                LatLng currentLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
            } else {
                Log.v("ERROR", "Can't figure out current location :(");
                Toast.makeText(getApplicationContext(), "GPS Anda tidak berfungsi, mohon untuk melakukan pengaturan terlebih dahulu", Toast.LENGTH_LONG).show();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(29.9648943, -90.1095941)));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
            }
        }
    }
    private void findViews(){
        MapFragment mapFragment = MapFragment.newInstance();
        mapFragment.getMapAsync(this);
        getFragmentManager()
                .beginTransaction()
                .add(R.id.frame, mapFragment)
                .commit();

        String searhdata[] = {"Semua Data",
                "Rumah Sakit",
                "Apotek",
                "Puskesmas",
                "Panti Pijat",
                "Bidan",
                "Ambulance",
                "SD",
                "SMP",
                "SMA",
                "Perguruan Tinggi",
                "Perpustakaan",
                "Restaurant",
                "Pasar Tradisional",
                "Pasar Modern",
                "UMKM",
                "Panti Asuhan",
                "Tempat Ibadah",
                "Tempat Wisata",
                "Taman Publik",
                "GOR",
                "PAM",
                "PLN",
                "Pemadam Kebakaran",
                "Pos Polisi",
                "Ambulance",
                "Terminal dan Stasiun",
                "TPU"};
        spinner = (MaterialSpinner) findViewById(R.id.spinner_seacrh);
        spinner.setItems(searhdata);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        extraLink = "?kategori=kesehatan&display=rs";
                        break;
                    case 2:
                        extraLink = "?kategori=kesehatan&display=apotek";
                        break;
                    case 3:
                        extraLink = "?kategori=kesehatan&display=puskesmas";
                        break;
                    case 4:
                        extraLink = "?kategori=kesehatan&display=pantipijat";
                        break;
                    case 5:
                        extraLink = "?kategori=kesehatan&display=bidan";
                        break;
                    case 6:
                        extraLink = "?kategori=kesehatan&display=ambulans";
                        break;
                    case 7:
                        extraLink = "?kategori=pendidikan&display=sd";
                        break;
                    case 8:
                        extraLink = "?kategori=pendidikan&display=smp";
                        break;
                    case 9:
                        extraLink = "?kategori=pendidikan&display=sma";
                        break;
                    case 10:
                        extraLink = "?kategori=pendidikan&display=pt";
                        break;
                    case 11:
                        extraLink = "?kategori=pendidikan&display=perpus";
                        break;
                    case 12:
                        extraLink = "?kategori=sandangpangan&display=restoran";
                        break;
                    case 13:
                        extraLink = "?kategori=sandangpangan&display=ptradisional";
                        break;
                    case 14:
                        extraLink = "?kategori=sandangpangan&display=pmodern";
                        break;
                    case 15:
                        extraLink = "?kategori=sandangpangan&display=umkm";
                        break;
                    case 16:
                        extraLink = "?kategori=wisata&display=tibadah";
                        break;
                    case 17:
                        extraLink = "?kategori=wisata&display=twisata";
                        break;
                    case 18:
                        extraLink = "?kategori=wisata&display=tpublik";
                        break;
                    case 19:
                        extraLink = "?kategori=wisata&display=gor";
                        break;
                    case 20:
                        extraLink = "?kategori=umum&display=pam";
                        break;
                    case 21:
                        extraLink = "?kategori=umum&display=pln";
                        break;
                    case 22:
                        extraLink = "?kategori=umum&display=damkar";
                        break;
                    case 23:
                        extraLink = "?kategori=umum&display=pospol";
                        break;
                    case 24:
                        extraLink = "?kategori=umum&display=terminal";
                        break;
                    case 25:
                        extraLink = "?kategori=umum&display=tpu";
                        break;
                    default:
                        break;
                }
                displayData();

            }
        });
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
                MapsActivity.this.finish();
                return true;

            case R.id.more:
                Intent in = new Intent(MapsActivity.this, search_list.class);
                startActivity(in);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayData(){
        mMap.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.DISPLAY_CARIDATA + extraLink, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    mPost = new CariData();
                    jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        mPost.setNamaTempat(jsonObject.getString("nama_tempat"));
                        mPost.setDeskripsi(jsonObject.getString("gambaran_umum"));
                        mPost.setAlamat(jsonObject.getString("alamat"));
                        mPost.setKoordinat(jsonObject.getString("koordinat"));
                        mPost.setFoto(jsonObject.getString("foto"));
                        mPost.setWebsite(jsonObject.getString("website"));
                        mPost.setJamOperasi(jsonObject.getString("jam_operasional"));
                        mPost.setKecamatan(jsonObject.getString("kecamatan"));
                        mPost.setNoTelp(jsonObject.getString("no_telp"));

                        if(mPost.getKoordinat().contains(",")) {
                            String[] kordinat = mPost.getKoordinat().split(",");
                            lat = Double.parseDouble(kordinat[0]);
                            lng = Double.parseDouble(kordinat[1].trim());
                        }
                        else{
                            lat = 0.0;
                            lng = 0.0;
                        }

                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lat, lng))
                                .title(mPost.getNamaTempat())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                        mHashMap.put("nama_tempat", mPost.getNamaTempat());
                        mHashMap.put("gambaran_umum", mPost.getDeskripsi());
                        mHashMap.put("alamat", mPost.getAlamat());
                        mHashMap.put("koordinat", mPost.getKoordinat());
                        mHashMap.put("foto", mPost.getFoto());
                        mHashMap.put("website", mPost.getWebsite());
                        mHashMap.put("jam_operasional", mPost.getJamOperasi());
                        mHashMap.put("kecamatan", mPost.getKecamatan());
                        mHashMap.put("no_telp", mPost.getNoTelp());

                        extraHashMap.put(marker.getId(), mHashMap);



                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        HashMap<String, String> sendData = extraHashMap.get(marker.getId());

        Intent intent = new Intent(getApplicationContext(), detail_search.class);
        intent.putExtra("NAMA_TEMPAT", sendData.get("nama_tempat"));
        intent.putExtra("KOORDINAT", sendData.get("koordinat"));
        intent.putExtra("DESKRIPSI", sendData.get("gambaran_umum"));
        intent.putExtra("ALAMAT", sendData.get("alamat"));
        intent.putExtra("NO_TELP", sendData.get("no_telp"));
        intent.putExtra("JAM_OPERASI", sendData.get("jam_operasional"));
        intent.putExtra("KECAMATAN", sendData.get("kecamatan"));
        intent.putExtra("WEBSITE", sendData.get("website"));
        intent.putExtra("FOTO", sendData.get("foto"));
        startActivity(intent);
    }

}
