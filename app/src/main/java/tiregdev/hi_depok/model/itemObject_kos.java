package tiregdev.hi_depok.model;

/**
 * Created by TiregDev on 04/10/2017.
 */

public class itemObject_kos {
    private String nama, alamat, harga;
    private int image;

    public itemObject_kos(String nama, String alamat, String harga, int image) {
        this.nama = nama;
        this.alamat = alamat;
        this.harga = harga;
        this.image = image;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
