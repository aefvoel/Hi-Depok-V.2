package tiregdev.hi_depok.model;

/**
 * Created by TiregDev on 25/08/2017.
 */

public class itemObject_news_Blog {

    private String title, portal, time;
    private int picTitle;

    public itemObject_news_Blog(String title, String portal, String time, int picTitle){
        this.title = title;
        this.portal = portal;
        this.time = time;
        this.picTitle = picTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPicTitle() {
        return picTitle;
    }

    public void setPicTitle(int picTitle) {
        this.picTitle = picTitle;
    }
}
