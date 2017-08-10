package tiregdev.hi_depok.model;

/**
 * Created by Muhammad63 on 8/7/2017.
 */

public class itemObjcect_notif {

    private String user;
    private String notifTxt;
    private String time;
    private int picNotif;

    public itemObjcect_notif(String user, String notifTxt, String time, int picNotif){
        this.user = user;
        this.notifTxt = notifTxt;
        this.time = time;
        this.picNotif = picNotif;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNotifTxt() {
        return notifTxt;
    }

    public void setNotifTxt(String notifTxt) {
        this.notifTxt = notifTxt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPicNotif() {
        return picNotif;
    }

    public void setPicNotif(int picNotif) {
        this.picNotif = picNotif;
    }

}
