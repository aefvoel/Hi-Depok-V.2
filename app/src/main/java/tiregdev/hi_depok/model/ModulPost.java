package tiregdev.hi_depok.model;

/**
 * Created by SONY-VAIO on 10/20/2017.
 */

public class ModulPost {
    private String id_modul;
    private String judul;
    private String pengarang;
    private String kategori;
    private String jml_halaman;
    private String foto;
    private String link;

    public ModulPost(String id_modul, String judul, String pengarang, String kategori, String jml_halaman, String foto, String link) {
        this.id_modul = id_modul;
        this.judul = judul;
        this.pengarang = pengarang;
        this.kategori = kategori;
        this.jml_halaman = jml_halaman;
        this.foto = foto;
        this.link = link;
    }

    public  ModulPost(){

    }

    public String getId_modul() {
        return id_modul;
    }

    public void setId_modul(String id_modul) {
        this.id_modul = id_modul;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJml_halaman() {
        return jml_halaman;
    }

    public void setJml_halaman(String jml_halaman) {
        this.jml_halaman = jml_halaman;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
