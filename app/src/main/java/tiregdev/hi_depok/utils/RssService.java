package tiregdev.hi_depok.utils;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.model.RssItem;

public class RssService extends IntentService {

    private static final String[] RSS_LINK = {
            "http://www.depoknews.id/feed/",
            "http://www.depokpos.com/feed/",
            "http://feeds.feedburner.com/depokgoid",
            "http://www.hariandepok.com/feed/",
            "http://radardepok.com/feed/"
    };
    public static final String ITEMS = "items";
    public static final String ACTION_RSS_PARSED = "tiregdev.hi_depok.ACTION_RSS_PARSED";

    public RssService() {
        super("RssService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(ExtraString.TAG, "Service started");
        List<RssItem> rssItems;
        List<RssItem> listNews = new ArrayList<>();
        for(int i=0;i<RSS_LINK.length;i++){
            try {
                RssParser parser = new RssParser();
                rssItems = parser.parse(getInputStream(RSS_LINK[i]));
                listNews.addAll(rssItems);

            } catch (XmlPullParserException | IOException e) {
                Log.w(e.getMessage(), e);
            } catch (IllegalArgumentException e){
                Log.w(e.getMessage(), e);
            }
            // Send result
        }

        Intent resultIntent = new Intent(ACTION_RSS_PARSED);
        resultIntent.putExtra(ITEMS, (Serializable) listNews);
        LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);

    }

    public InputStream getInputStream(String link) {
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            Log.w(ExtraString.TAG, "Exception while retrieving the input stream", e);
            return null;
        }
    }
}
