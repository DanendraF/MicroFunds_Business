package DashboardPengusaha.Loan;

public class LoanData {
    private String nama;
    private String nik;
    private String alamat;
    private String telp;
    private String pinjaman;
    private String pendapatanbln;

    public LoanData() {}

    public LoanData(String nama, String nik, String alamat, String telp, String pinjaman, String pendapatanbln) {
        this.nama = nama;
        this.nik = nik;
        this.alamat = alamat;
        this.telp = telp;
        this.pinjaman = pinjaman;
        this.pendapatanbln = pendapatanbln;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getPinjaman() {
        return pinjaman;
    }

    public void setPinjaman(String pinjaman) {
        this.pinjaman = pinjaman;
    }

    public String getPendapatanbln() {
        return pendapatanbln;
    }

    public void setPendapatanbln(String pendapatanbln) {
        this.pendapatanbln = pendapatanbln;
    }
}
