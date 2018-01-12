package tiregdev.hi_depok.fragment;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.adapter.CariDataAdapter;
import tiregdev.hi_depok.model.CariData;
import tiregdev.hi_depok.utils.AppConfig;
import tiregdev.hi_depok.utils.AppController;

/**
 * Created by Muhammad63 on 10/6/2017.
 */

public class ListCariDataFragment extends BaseFragment implements LocationListener{

    RecyclerView rView;
    View v;
    List<CariData> dataAdapter;
    CariDataAdapter rvAdapter;
    CariData mPost;
    JSONObject jsonObject;
    LinearLayoutManager lLayout;
    SearchView searchView;
    double latitudeA, longitudeA, latitudeB, longitudeB;
    LocationManager locationManager;
    String provider;
    Location location;

    public static ListCariDataFragment newInstance(String title, String sub) {
        ListCariDataFragment fragment = new ListCariDataFragment();

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("sub", sub);
        fragment.setArguments(args);

        return fragment;
    }

    public String getSub() {
        Bundle args = getArguments();
        return args.getString("sub", "NO TITLE FOUND");
    }

    public String getTitle() {
        Bundle args = getArguments();
        return args.getString("title", "NO TITLE FOUND");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_ntpd_instansi, container, false);
        setUpAdapter();
        displayData();
        // Getting LocationManager object
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Creating an empty criteria object
        Criteria criteria = new Criteria();

        // Getting the name of the provider that meets the criteria
        provider = locationManager.getBestProvider(criteria, false);

        if(provider!=null && !provider.equals("")){

            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                location = locationManager.getLastKnownLocation(provider);

                locationManager.requestLocationUpdates(provider, 20000, 1, this);
                if(location!=null)
                    onLocationChanged(location);
                else
                    Toast.makeText(getActivity(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
            }
            // Get the location from the given provider


        }else{
            Toast.makeText(getActivity(), "No Provider Found", Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    public void setUpAdapter(){
        lLayout = new LinearLayoutManager(getActivity());

        rView = (RecyclerView)v.findViewById(R.id.rview);
        rView.setLayoutManager(lLayout);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), lLayout.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.line_view));
        rView.addItemDecoration(dividerItemDecoration);

        dataAdapter = new ArrayList<>();
    }


    private void displayData(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.DISPLAY_CARIDATA + "?kategori=" + getTitle() + "&display=" + getSub(), new Response.Listener<JSONArray>() {
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

                        if(jsonObject.getString("koordinat").contains(",")){
                            String[] kordinatA = jsonObject.getString("koordinat").split(",");
                            latitudeA = Double.parseDouble(kordinatA[0]);
                            longitudeA = Double.parseDouble(kordinatA[1].trim());
                        }
                        else{
                            latitudeA = 0.0;
                            longitudeA = 0.0;
                        }

                        latitudeB = location.getLatitude();
                        longitudeB = location.getLongitude();

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
                rView.setAdapter(rvAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if(savedInstanceState == null) {
            setHasOptionsMenu(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);

        if (searchItem != null) {
            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    //some operation
                    dataAdapter.clear();
                    displayData();
                    return false;
                }
            });
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //some operation
                }
            });
            EditText searchPlate = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchPlate.setHint("Search");
            View searchPlateView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            searchPlateView.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
            // use this method for search process
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // use this method when query submitted
                    dataAdapter.clear();
                    displayData();
                    Toast.makeText(getContext(), "Hasil pencarian untuk: " + query, Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // use this method for auto complete search process
                    dataAdapter.clear();
                    displayData();
                    return false;
                }
            });
            SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        }
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

