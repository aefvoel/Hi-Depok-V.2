package tiregdev.hi_depok.model;

/**
 * Created by TiregDev on 26/09/2017.
 */

public class EventList {

    private String title, sumber, time;
    private int image;

    public EventList(String title, String sumber, String time, int image) {
        this.title = title;
        this.sumber = sumber;
        this.time = time;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSumber() {
        return sumber;
    }

    public void setSumber(String sumber) {
        this.sumber = sumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
