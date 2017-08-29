package tiregdev.hi_depok.model;

/**
 * Created by TiregDev on 28/08/2017.
 */

public class itemObject_modul {

    private String judul, pengarang, page, viewer, kategori;
    private int cover;

    public itemObject_modul(String judul, String pengarang, String page, String viewer, String kategori, int cover) {
        this.judul = judul;
        this.pengarang = pengarang;
        this.page = page;
        this.viewer = viewer;
        this.kategori = kategori;
        this.cover = cover;
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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getViewer() {
        return viewer;
    }

    public void setViewer(String viewer) {
        this.viewer = viewer;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }
}
