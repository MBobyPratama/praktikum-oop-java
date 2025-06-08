import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainAppGUI {
    private JFrame frame;
    private JTextField namaField;
    private JTextArea outputArea;
    private Antrian antrian;

    public MainAppGUI() {
        antrian = new Antrian();
        frame = new JFrame("Sistem Antrian");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelNama = new JLabel("Nama:");
        namaField = new JTextField();

        JButton btnPengunjung = new JButton("Masuk sebagai Pengunjung");
        JButton btnPetugas = new JButton("Masuk sebagai Petugas");
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        btnPengunjung.addActionListener(e -> pengunjungMenu());
        btnPetugas.addActionListener(e -> petugasMenu());

        panel.add(labelNama);
        panel.add(namaField);
        panel.add(btnPengunjung);
        panel.add(btnPetugas);
        panel.add(new JScrollPane(outputArea));

        frame.add(panel);
        frame.setVisible(true);
    }

    private void pengunjungMenu() {
        String nama = namaField.getText();
        if (nama.isEmpty()) {
            showMessage("Masukkan nama terlebih dahulu.");
            return;
        }
        Pengunjung pengunjung = new Pengunjung(nama);
        int nomor = pengunjung.ambilAntrian(antrian);
        showMessage("Halo " + nama + ", nomor antrian Anda: " + nomor);
    }

    private void petugasMenu() {
        String nama = namaField.getText();
        if (nama.isEmpty()) {
            showMessage("Masukkan nama terlebih dahulu.");
            return;
        }
        Petugas petugas = new Petugas(nama);

        Object[] options = {"Panggil Antrian", "Lihat Berikutnya", "Sisa Antrian", "Kembali"};
        int choice;
        do {
            choice = JOptionPane.showOptionDialog(frame, "Pilih aksi:", "Menu Petugas",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    try {
                        int dipanggil = petugas.panggilAntrian(antrian);
                        showMessage("Memanggil nomor antrian: " + dipanggil);
                    } catch (IllegalStateException e) {
                        showMessage(e.getMessage());
                    }
                    break;
                case 1:
                    Integer berikutnya = antrian.lihatNomorBerikutnya();
                    showMessage("Nomor berikutnya: " + (berikutnya != null ? berikutnya : "Tidak ada."));
                    break;
                case 2:
                    showMessage("Sisa antrian: " + antrian.sisaAntrian());
                    break;
            }
        } while (choice != 3);
    }

    private void showMessage(String msg) {
        outputArea.append(msg + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainAppGUI::new);
    }
}