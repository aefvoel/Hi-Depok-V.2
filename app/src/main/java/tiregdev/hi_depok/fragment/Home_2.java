package tiregdev.hi_depok.fragment;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.wang.avi.AVLoadingIndicatorView;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.activity.CariDataActivity;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by Muhammad63 on 8/3/2017.
 */

public class Home_2 extends Fragment {

    private AVLoadingIndicatorView pDialog;
    private LinearLayout wrap;
    private WebView webView;
    private Button list;
    private String url = "http://hi.depok.go.id/maps";
    private View v;

    public static Home_2 newInstance(){
        Home_2 fragment = new Home_2();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home_2, container, false);
        setWebView();
        aviLoader();
        setIntent();
        return v;
    }

    public void aviLoader() {
        wrap = (LinearLayout) v.findViewById(R.id.wrap);
        pDialog = (AVLoadingIndicatorView) v.findViewById(R.id.avi);
    }

    public void setIntent(){
        list = (Button) v.findViewById(R.id.searchList);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CariDataActivity.class);
                startActivity(i);
            }
        });
    }

    public void setWebView(){
        webView = (WebView) v.findViewById(R.id.maps);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
        webView.getSettings().setAppCachePath( getContext().getCacheDir().getAbsolutePath() );
        webView.getSettings().setAllowFileAccess( true );
        webView.getSettings().setAppCacheEnabled( true );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);

        if ( !isNetworkAvailable() ) { // loading offline
            webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        }
        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int newProgress){
                // Update the progress bar with page loading progress
                pDialog.setVisibility(View.VISIBLE);
                wrap.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);
                if(newProgress == 100){
                    // Hide the progressbar
                    pDialog.setVisibility(View.GONE);
                    wrap.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                }
            }
        });
        webView.loadUrl(url);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}