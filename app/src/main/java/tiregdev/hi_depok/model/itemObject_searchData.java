package tiregdev.hi_depok.model;

/**
 * Created by Muhammad63 on 9/1/2017.
 */

public class itemObject_searchData {

    private String namaTempat, alamat, jarak;
    private int icon;

    public itemObject_searchData(String namaTempat, String alamat, String jarak, int icon) {
        this.namaTempat = namaTempat;
        this.alamat = alamat;
        this.jarak = jarak;
        this.icon = icon;
    }

    public String getNamaTempat() {
        return namaTempat;
    }

    public void setNamaTempat(String namaTempat) {
        this.namaTempat = namaTempat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
