public class Pengunjung extends User {
    private int nomorAntrian;

    public Pengunjung(String nama) {
        super(nama);
        this.nomorAntrian = -1; // -1 means belum ambil antrian
    }

    @Override
    public void tampilkanMenu() {
        System.out.println("Menu Pengunjung:");
        System.out.println("1. Ambil Nomor Antrian");
        System.out.println("2. Keluar");
    }

    public int ambilAntrian(Antrian antrian) {
        this.nomorAntrian = antrian.ambilNomor();
        return this.nomorAntrian;
    }

    public int getNomorAntrian() {
        return nomorAntrian;
    }
}
