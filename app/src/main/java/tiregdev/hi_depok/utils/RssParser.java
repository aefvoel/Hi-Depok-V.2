package tiregdev.hi_depok.utils;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import tiregdev.hi_depok.model.RssItem;

public class RssParser {

    private static final String TAG_TITLE = "title";
    private static final String TAG_LINK = "link";
    private static final String TAG_RSS = "rss";
    private static final String TAG_DATE = "pubDate";

    // We don't use namespaces
    private final String ns = null;

    public List<RssItem> parse(InputStream inputStream) throws XmlPullParserException, IOException, IllegalArgumentException {

        if(inputStream == null){
            throw new IllegalArgumentException();
        }

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            try{
                inputStream.close();
            }catch(Exception e){
                //do something
            }
        }
    }

    private List<RssItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, TAG_RSS);
        String title = null;
        String link = null;
        String pubDate = null;
        String portal = null;
        List<RssItem> items = new ArrayList<RssItem>();
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TAG_TITLE)) {
                title = readTitle(parser);
            } else if (name.equals(TAG_LINK)) {
                link = readLink(parser);
            } else if (name.equals(TAG_DATE)) {
                pubDate = readDate(parser);
            }
            if (title != null && link != null && pubDate != null) {
                if(link.contains("depoknews")){
                    portal = "Depok News";
                }else if(link.contains("depokpos")){
                    portal = "Depok Pos";
                }else if(link.contains("depokgoid")){
                    portal = "Portal Depok";
                }else if(link.contains("hariandepok")){
                    portal = "Harian Depok";
                }else if(link.contains("radardepok")){
                    portal = "Radar Depok";
                }
                RssItem item = new RssItem(title, link, pubDate, portal);
                items.add(item);
                title = null;
                link = null;
                pubDate = null;
                portal = null;
            }
        }
        return items;
    }

    private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_LINK);
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG_LINK);
        return link;
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_TITLE);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG_TITLE);
        return title;
    }

    private String readDate(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_DATE);
        String pubDate = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG_DATE);
        return pubDate;
    }

    // For the tags title and link, extract their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}
