package tiregdev.hi_depok.model;

/**
 * Created by SONY-VAIO on 10/19/2017.
 */

public class MasterpiecePost {

    private String id_post;
    private String id_user;
    private String nama_peraih;
    private String instansi;
    private String nama_prestasi;
    private String deskripsi;
    private String tingkat;
    private String kategori;
    private String riwayat;
    private String keterangan;
    private String jumlah_suka;
    private String jumlah_komentar;
    private String status;
    private String tgl_post;
    private String image;
    private String username;
    private String avatar;
    private boolean is_liked;

    public MasterpiecePost(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean is_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }

    public String getId_post() {
        return id_post;
    }

    public void setId_post(String id_post) {
        this.id_post = id_post;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama_peraih() {
        return nama_peraih;
    }

    public void setNama_peraih(String nama_peraih) {
        this.nama_peraih = nama_peraih;
    }

    public String getInstansi() {
        return instansi;
    }

    public void setInstansi(String instansi) {
        this.instansi = instansi;
    }

    public String getNama_prestasi() {
        return nama_prestasi;
    }

    public void setNama_prestasi(String nama_prestasi) {
        this.nama_prestasi = nama_prestasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTingkat() {
        return tingkat;
    }

    public void setTingkat(String tingkat) {
        this.tingkat = tingkat;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getRiwayat() {
        return riwayat;
    }

    public void setRiwayat(String riwayat) {
        this.riwayat = riwayat;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getJumlah_suka() {
        return jumlah_suka;
    }

    public void setJumlah_suka(String jumlah_suka) {
        this.jumlah_suka = jumlah_suka;
    }

    public String getJumlah_komentar() {
        return jumlah_komentar;
    }

    public void setJumlah_komentar(String jumlah_komentar) {
        this.jumlah_komentar = jumlah_komentar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTgl_post() {
        return tgl_post;
    }

    public void setTgl_post(String tgl_post) {
        this.tgl_post = tgl_post;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
