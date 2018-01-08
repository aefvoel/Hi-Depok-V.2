package tiregdev.hi_depok.model;

/**
 * Created by SONY-VAIO on 10/31/2017.
 */

public class MasterpieceKomentar {
    private String isiKomentar;
    private String waktu;
    private String idUser;
    private String idPenghargaan;
    private String namaUser;
    private String avatar;

    public MasterpieceKomentar() {
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIsiKomentar() {
        return isiKomentar;
    }

    public void setIsiKomentar(String isiKomentar) {
        this.isiKomentar = isiKomentar;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdPenghargaan() {
        return idPenghargaan;
    }

    public void setIdPenghargaan(String idPenghargaan) {
        this.idPenghargaan = idPenghargaan;
    }
}
