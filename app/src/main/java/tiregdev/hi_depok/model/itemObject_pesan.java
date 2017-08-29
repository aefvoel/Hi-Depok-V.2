package tiregdev.hi_depok.model;

/**
 * Created by TiregDev on 29/08/2017.
 */

public class itemObject_pesan {
    private String user, time, chat;
    private int avatar;

    public itemObject_pesan(String user, String time, String chat, int avatar) {
        this.user = user;
        this.time = time;
        this.chat = chat;
        this.avatar = avatar;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
