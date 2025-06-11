import java.util.ArrayList;

public class AntrianBiasa implements Antrian {
    private final ArrayList<Integer> daftarAntrian;
    private int nomorTerakhir;

    private static final String PESAN_ANTRIAN_KOSONG = "Antrian kosong. Tidak ada nomor untuk dipanggil."; // P11 Final
                                                                                                           // Static
                                                                                                           // Variable
    private static final String PESAN_ANTRIAN_PENUH = "Antrian penuh. Tidak bisa menambah nomor lagi.";
    private static final int MAKSIMAL_ANTRIAN = 5;

    public AntrianBiasa() {
        this.daftarAntrian = new ArrayList<>();
        this.nomorTerakhir = 0;
    }

    @Override
    public int ambilNomor() {
        if (daftarAntrian.size() >= MAKSIMAL_ANTRIAN) {
            throw new IllegalStateException(PESAN_ANTRIAN_PENUH);
        }
        nomorTerakhir++;
        daftarAntrian.add(nomorTerakhir);
        return nomorTerakhir;
    }

    @Override
    public int panggilNomor() {
        if (daftarAntrian.isEmpty()) {
            throw new IllegalStateException(PESAN_ANTRIAN_KOSONG);
        }
        return daftarAntrian.remove(0);
    }

    @Override
    public Integer lihatNomorBerikutnya() {
        return daftarAntrian.isEmpty() ? null : daftarAntrian.get(0);
    }

    @Override
    public int sisaAntrian() {
        return daftarAntrian.size();
    }
}
