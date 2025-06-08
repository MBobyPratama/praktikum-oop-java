public class Petugas extends User {

    public Petugas(String nama) {
        super(nama);
    }

    @Override
    public void tampilkanMenu() {
        System.out.println("Menu Petugas:");
        System.out.println("1. Panggil Nomor Antrian");
        System.out.println("2. Lihat Nomor Berikutnya");
        System.out.println("3. Sisa Antrian");
        System.out.println("4. Keluar");
    }

    public int panggilAntrian(Antrian antrian) {
        try {
            int dipanggil = antrian.panggilNomor();
            return dipanggil;
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public void lihatBerikutnya(Antrian antrian) {
        Integer berikutnya = antrian.lihatNomorBerikutnya();
        if (berikutnya != null) {
            System.out.println("Nomor berikutnya: " + berikutnya);
        } else {
            System.out.println("Antrian kosong.");
        }
    }

    public void cekSisaAntrian(Antrian antrian) {
        System.out.println("Sisa antrian: " + antrian.sisaAntrian());
    }
}
