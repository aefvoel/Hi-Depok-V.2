package tiregdev.hi_depok.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.DetailNewsActivity;
import tiregdev.hi_depok.adapter.CariDataAdapter;
import tiregdev.hi_depok.adapter.RSSAdapter;
import tiregdev.hi_depok.model.CariData;
import tiregdev.hi_depok.model.RssItem;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;
import tiregdev.hi_depok.utils.RssService;


public class Home extends BaseFragment implements LocationListener{

    private RecyclerView rView, rViewData;
    private LinearLayoutManager lLayout, dataLayout;
    private double latitudeA, longitudeA, latitudeB, longitudeB;
    private LocationManager locationManager;
    private String provider;
    private Location location;
    private List<CariData> dataAdapter;
    private CariDataAdapter rvAdapter;
    private RSSAdapter adapter;
    private CariData mPost;
    private JSONObject jsonObject;
    private String TAG = "HomeFragment";
    private AVLoadingIndicatorView avLoadingIndicatorView, avLoadingIndicatorView2;
    private CircleImageView btnData, btnLoadNews;
    private TextView txtLoad, txtLoadNews;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    public double deviceLat, deviceLng;

    RssReceiver resultReceiver = null;

    public static Home newInstance(){
        Home fragment = new Home();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        startService();
        getLocationPermission();
        getDeviceLocation();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lLayout = new LinearLayoutManager(getActivity());
        rView = (RecyclerView)view.findViewById(R.id.view_news);
        rView.setLayoutManager(lLayout);
        rView.setNestedScrollingEnabled(false);
        avLoadingIndicatorView = view.findViewById(R.id.avi);
        avLoadingIndicatorView2 = view.findViewById(R.id.avi2);
        btnData = view.findViewById(R.id.btnLoadData);
        btnLoadNews = view.findViewById(R.id.btnLoadNews);
        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvAdapter.setDisplayCount(rvAdapter.getItemCount() + 5);
            }
        });
        btnLoadNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setDisplayCount(adapter.getItemCount() + 5);
            }
        });
        txtLoadNews = view.findViewById(R.id.txtLoadNews);
        txtLoad = view.findViewById(R.id.txtLoadMore);
        dataLayout = new LinearLayoutManager(getActivity());
        rViewData = (RecyclerView)view.findViewById(R.id.view_data);
        rViewData.setLayoutManager(dataLayout);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), dataLayout.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.line_view));
        rViewData.addItemDecoration(dividerItemDecoration);

        dataAdapter = new ArrayList<>();

        MaterialSpinner spinner = (MaterialSpinner) view.findViewById(R.id.spinner);
        spinner.setItems("Filter by...", "apotek", "bank", "bidan", "damkar", "industri", "jasa_pengiriman",
                "klinik", "mall", "olahraga", "pdam", "perkantoran", "pln", "pos_polisi", "spbu",
                "supermarket", "taman", "tni", "tpu", "universitas");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if(item.equals("Filter by...")){
                    dataAdapter.clear();
                    rViewData.removeAllViews();
                    txtLoad.setVisibility(View.GONE);
                    btnData.setVisibility(View.GONE);
                }else {
                    dataAdapter.clear();
                    displayData(item);
                }

            }
        });

    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getActivity(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getActivity(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
            }else{
                ActivityCompat.requestPermissions(this.getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this.getActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                }
            }
        }
    }


    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        try{
            if(mLocationPermissionsGranted){
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            Log.d(TAG, "origin latlng" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() );
                            deviceLat = currentLocation.getLatitude();
                            deviceLng = currentLocation.getLongitude();
                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(getActivity(), "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void displayData(String namaTempat){
        avLoadingIndicatorView2.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.CARI_DATA + namaTempat + "s", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                avLoadingIndicatorView2.setVisibility(View.GONE);
                txtLoad.setVisibility(View.VISIBLE);
                btnData.setVisibility(View.VISIBLE);
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

                        if(jsonObject.getString("koordinat").contains(",")){
                            String[] kordinatA = jsonObject.getString("koordinat").split(",");
                            latitudeA = Double.parseDouble(kordinatA[0]);
                            longitudeA = Double.parseDouble(kordinatA[1].trim());
                        }
                        else{
                            latitudeA = 0.0;
                            longitudeA = 0.0;
                        }

                        latitudeB = deviceLat;
                        longitudeB = deviceLng;

                        Double jarak = BigDecimal.valueOf(CalculationByDistance(latitudeB, longitudeB, latitudeA, longitudeA))
                                .setScale(3, RoundingMode.HALF_UP)
                                .doubleValue();
                        mPost.setJarak(String.valueOf(jarak));

                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dataAdapter.add(mPost);

                }
                Collections.sort(dataAdapter, new Comparator<CariData>() {
                    @Override
                    public int compare(CariData data1, CariData data2) {
                        Double f1 = Double.parseDouble(data1.getJarak());
                        Double f2 = Double.parseDouble(data2.getJarak());
                        return f1.compareTo(f2);
                    }
                });
                rvAdapter = new CariDataAdapter(getContext(), dataAdapter);
                rvAdapter.notifyDataSetChanged();
                rvAdapter.setDisplayCount(5);
                rViewData.setAdapter(rvAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                avLoadingIndicatorView2.setVisibility(View.GONE);
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    public double CalculationByDistance(double lat1, double lon1, double lat2, double lon2) {
        int Radius = 6371;// radius of earth in Km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }



    private void startService() {

        Intent intent = new Intent(getActivity(), RssService.class);
        getContext().startService(intent);

    }

    /**
     * Once the {@link RssService} finishes its task, the result is sent to this BroadcastReceiver
     */
    private class RssReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            avLoadingIndicatorView.setVisibility(View.VISIBLE);
            final List<RssItem> items = (List<RssItem>) intent.getSerializableExtra(RssService.ITEMS);
            final List<RssItem> positifItems = new ArrayList<>();
            if (items != null) {
                for(int i=0;i<items.size();i++){
                    int finalI = i;
                    StringRequest strReq = new StringRequest(Request.Method.POST,
                            AppConfig.SENTIMENT, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "Response: " + response.toString());

                            try {
                                JSONObject jObj = new JSONObject(response);
                                boolean error = jObj.getBoolean("error");
                                if (!error) {
                                    String result = jObj.getString("result");
                                    if(result.equals("positif")){
                                        positifItems.add(items.get(finalI));
                                    }

                                } else {

                                    // Error occurred in registration. Get the error
                                    // message
                                    String errorMsg = jObj.getString("error_msg");
                                    Toast.makeText(getActivity(),
                                            errorMsg, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getActivity(),
                                    error.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Failed with error msg:\t" + error.getMessage());
                            Log.d(TAG, "Error StackTrace: \t" + error.getStackTrace());
                            // edited here
                            try {
                                byte[] htmlBodyBytes = error.networkResponse.data;
                                Log.e(TAG, new String(htmlBodyBytes), error);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() {
                            // Posting params to register url
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("text", items.get(finalI).getTitle());

                            return params;
                        }

                    };
                    AppController.getInstance().addToRequestQueue(strReq);


                }

                Collections.sort(positifItems, new Comparator<RssItem>() {
                    @Override
                    public int compare(RssItem data1, RssItem data2) {
                        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
                        try {
                            Date date1 = formatter.parse(data1.getPubDate());
                            Date date2 = formatter.parse(data2.getPubDate());
                            return date2.compareTo(date1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });

                adapter = new RSSAdapter(getActivity(), positifItems);
                adapter.setDisplayCount(5);
                rView.setAdapter(adapter);
                avLoadingIndicatorView.setVisibility(View.GONE);
                txtLoadNews.setVisibility(View.VISIBLE);
                btnLoadNews.setVisibility(View.VISIBLE);

            } else {
                Toast.makeText(getActivity(), "An error occurred while downloading the rss feed.",
                        Toast.LENGTH_LONG).show();
                avLoadingIndicatorView.setVisibility(View.GONE);


            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        resultReceiver = new RssReceiver();
        IntentFilter intentFilter = new IntentFilter(RssService.ACTION_RSS_PARSED);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(resultReceiver, intentFilter);

    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(resultReceiver);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    protected void onSaveState(Bundle outState) {
        super.onSaveState(outState);
        outState.putParcelable("myState", lLayout.onSaveInstanceState());
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        lLayout.onRestoreInstanceState(savedInstanceState.getParcelable("myState"));
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}

