package tiregdev.hi_depok.model;

/**
 * A representation of an rss item from the list.
 * 
 * @author Veaceslav Grec
 * 
 */
public class RssItem {

	private final String title;
	private final String link;
	private final String pubDate;
	private final String portal;


	public RssItem(String title, String link, String pubDate, String portal) {
		this.title = title;
		this.link = link;
		this.pubDate = pubDate;
		this.portal = portal;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public String getPubDate() {
		return pubDate;
	}

	public String getPortal() {
		return portal;
	}
}
