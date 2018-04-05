package tiregdev.hi_depok.model;


public class User {

    private String id;
    private String nama;
    private String email;
    private String pass;
    private String alamat;
    private String bio;
    private String noTelp;
    private String tglLahir;
    private String dibuatPada;

    public User() {
    }

    public User(String id, String nama, String email) {
        this.id = id;
        this.nama = nama;
        this.email = email;
    }

    public User(String nama, String email, String pass, String alamat, String bio, String noTelp, String tglLahir, String dibuatPada) {
        this.nama = nama;
        this.email = email;
        this.pass = pass;
        this.alamat = alamat;
        this.bio = bio;
        this.noTelp = noTelp;
        this.tglLahir = tglLahir;
        this.dibuatPada = dibuatPada;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getDibuatPada() {
        return dibuatPada;
    }

    public void setDibuatPada(String dibuatPada) {
        this.dibuatPada = dibuatPada;
    }

}
