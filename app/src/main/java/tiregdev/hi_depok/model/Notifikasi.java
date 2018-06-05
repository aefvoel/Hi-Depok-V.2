package tiregdev.hi_depok.model;

/**
 * Created by Muhammad63 on 8/7/2017.
 */

public class Notifikasi {

    private String id;
    private String user;
    private String text;
    private String time;
    private String foto;
    private String type;
    private String goToId;
    private String name;

    public Notifikasi(String id, String user, String text, String time, String foto, String type, String goToId, String name) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.time = time;
        this.foto = foto;
        this.type = type;
        this.goToId = goToId;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoToId() {
        return goToId;
    }

    public void setGoToId(String goToId) {
        this.goToId = goToId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
