package tiregdev.hi_depok.model;

/**
 * Created by SONY-VAIO on 10/24/2017.
 */

public class CariData {
    private String namaTempat;
    private String koordinat;
    private String deskripsi;
    private String alamat;
    private String noTelp;
    private String jamOperasi;
    private String kecamatan;
    private String website;
    private String foto;
    private String jarak;

    public CariData(String namaTempat, String koordinat, String deskripsi, String alamat, String noTelp, String jamOperasi, String kecamatan, String website, String foto, String jarak) {
        this.namaTempat = namaTempat;
        this.koordinat = koordinat;
        this.deskripsi = deskripsi;
        this.alamat = alamat;
        this.noTelp = noTelp;
        this.jamOperasi = jamOperasi;
        this.kecamatan = kecamatan;
        this.website = website;
        this.foto = foto;
        this.jarak = jarak;
    }

    public CariData() {
    }

    public String getNamaTempat() {
        return namaTempat;
    }

    public void setNamaTempat(String namaTempat) {
        this.namaTempat = namaTempat;
    }

    public String getKoordinat() {
        return koordinat;
    }

    public void setKoordinat(String koordinat) {
        this.koordinat = koordinat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getJamOperasi() {
        return jamOperasi;
    }

    public void setJamOperasi(String jamOperasi) {
        this.jamOperasi = jamOperasi;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }
}
