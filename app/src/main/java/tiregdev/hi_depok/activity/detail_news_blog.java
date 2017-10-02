package tiregdev.hi_depok.activity;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.utils.AdBlocker;

import static java.security.AccessController.getContext;
import static tiregdev.hi_depok.R.id.share;

public class detail_news_blog extends AppCompatActivity {

    WebView webView;
    TextView link;
    String url = "https://www.depoknews.id/pemutaran-film-pengkhianatan-g30spki-begini-tanggapan-anak-depok/";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news_blog);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        AdBlocker.init(this);
        webView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_blog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Lihat konten ini pada Aplikasi Hi-Depok";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share Via"));
                return true;

            case R.id.copylink:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("link has been copy to clipboard", url);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(detail_news_blog.this, "link has been copy to clipboard", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.open:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void webView() {
        mProgressBar = (ProgressBar) findViewById(R.id.pb);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new WebViewClient() {
            private Map<String, Boolean> loadedUrls = new HashMap<>();

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                boolean ad;
                if (!loadedUrls.containsKey(url)) {
                    ad = AdBlocker.isAd(url);
                    loadedUrls.put(url, ad);
                } else {
                    ad = loadedUrls.get(url);
                }
                return ad ? AdBlocker.createEmptyResource() :
                        super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                // Do something on page loading started
                // Visible the progressbar
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int newProgress){
                // Update the progress bar with page loading progress
                mProgressBar.setProgress(newProgress);
                if(newProgress == 100){
                    // Hide the progressbar
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });

        webView.loadUrl(url);

        link = (TextView) findViewById(R.id.link);
        link.setText(url);
    }
}
