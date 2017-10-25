package tiregdev.hi_depok.model;

/**
 * Created by SONY-VAIO on 4/29/2017.
 */

public class DataModel {
    String id;
    String nama;
    String foto;
    String halaman;
    Double persen;

    public String getHalaman() {
        return halaman;
    }

    public void setHalaman(String halaman) {
        this.halaman = halaman;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Double getPersen() { return persen;}

    public void setPersen(Double persen) { this.persen = persen; }
}
