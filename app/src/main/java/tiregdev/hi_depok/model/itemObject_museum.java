package tiregdev.hi_depok.model;

/**
 * Created by TiregDev on 29/08/2017.
 */

public class itemObject_museum {
    private String judul, nama, tahun;
    private int picMuseum;

    public itemObject_museum(String judul, String nama, String tahun, int picMuseum) {
        this.judul = judul;
        this.nama = nama;
        this.tahun = tahun;
        this.picMuseum = picMuseum;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public int getPicMuseum() {
        return picMuseum;
    }

    public void setPicMuseum(int picMuseum) {
        this.picMuseum = picMuseum;
    }
}
