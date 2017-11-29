package tiregdev.hi_depok.model;

/**
 * Created by Muhammad63 on 10/6/2017.
 */

public class itemObject_ntpd {

    private String nama, nmr;

    public itemObject_ntpd(String nama, String nmr) {
        this.nama = nama;
        this.nmr = nmr;
    }

    public itemObject_ntpd(){

    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNmr() {
        return nmr;
    }

    public void setNmr(String nmr) {
        this.nmr = nmr;
    }
}
