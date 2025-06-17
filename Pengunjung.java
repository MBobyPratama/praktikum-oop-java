public class Pengunjung extends User { // P9 Inheritance
    private int nomorAntrian; // P7 Encapsulation

    public Pengunjung(String nama) {
        super(nama);
        this.nomorAntrian = -1;
    }

    @Override // P10 Polimorfisme
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