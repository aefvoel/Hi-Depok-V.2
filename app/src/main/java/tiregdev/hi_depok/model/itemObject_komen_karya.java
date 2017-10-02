package tiregdev.hi_depok.model;

/**
 * Created by Muhammad63 on 9/30/2017.
 */

public class itemObject_komen_karya {

    private String uname, time, komen;
    private int avatar;

    public itemObject_komen_karya(String uname, String time, String komen, int avatar) {
        this.uname = uname;
        this.time = time;
        this.komen = komen;
        this.avatar = avatar;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKomen() {
        return komen;
    }

    public void setKomen(String komen) {
        this.komen = komen;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
