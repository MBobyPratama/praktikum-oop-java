
import java.util.ArrayList;

public interface Antrian { // P13 Abstract and Interface (Interface)
    int ambilNomor();

    int panggilNomor();

    Integer lihatNomorBerikutnya();

    int sisaAntrian();
}

/*
 * public class Antrian {
 * private ArrayList<Integer> daftarAntrian; // P14 Java Collection (ArrayList)
 * private int nomorTerakhir;
 * 
 * public Antrian() {
 * daftarAntrian = new ArrayList<>();
 * nomorTerakhir = 0;
 * }
 * 
 * public int ambilNomor() {
 * nomorTerakhir++;
 * daftarAntrian.add(nomorTerakhir);
 * return nomorTerakhir;
 * }
 * 
 * public int panggilNomor() {
 * if (!daftarAntrian.isEmpty()) {
 * return daftarAntrian.remove(0);
 * } else {
 * throw new
 * IllegalStateException("Antrian kosong. Tidak ada nomor untuk dipanggil.");
 * }
 * }
 * 
 * public Integer lihatNomorBerikutnya() {
 * if (!daftarAntrian.isEmpty()) {
 * return daftarAntrian.get(0);
 * } else {
 * return null;
 * }
 * }
 * 
 * public int sisaAntrian() {
 * return daftarAntrian.size();
 * }
 * }
 * 
 */