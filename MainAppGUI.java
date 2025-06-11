import javax.swing.*; // P15 GUI
import java.awt.*;

public class MainAppGUI {
    private static final Antrian antrian = new AntrianBiasa(); // P11 Static Variable
    private static JTextArea outputArea;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistem Antrian");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Selamat Datang di Sistem Antrian");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcomeLabel);

        JButton pengunjungButton = new JButton("Masuk sebagai Pengunjung");
        JButton petugasButton = new JButton("Masuk sebagai Petugas");

        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        pengunjungButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        petugasButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        pengunjungButton.addActionListener(e -> masukSebagaiPengunjung());
        petugasButton.addActionListener(e -> masukSebagaiPetugas());

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(pengunjungButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(petugasButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(scrollPane);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private static void masukSebagaiPengunjung() { // P6 Method
        String nama = JOptionPane.showInputDialog("Masukkan nama Anda:");
        if (nama != null && !nama.isEmpty()) {
            Pengunjung pengunjung = new Pengunjung(nama);
            int pilihan = JOptionPane.showConfirmDialog(null, "Ambil antrian?", "Menu Pengunjung",
                    JOptionPane.YES_NO_OPTION);
            if (pilihan == JOptionPane.YES_OPTION) {
                try {
                    int nomor = pengunjung.ambilAntrian(antrian);
                    outputArea.append(nama + " mendapat nomor antrian: " + nomor + "\n");
                } catch (IllegalStateException e) {
                    outputArea.append("Gagal ambil antrian: " + e.getMessage() + "\n");
                }
            }
        }
    }

    private static void masukSebagaiPetugas() {
        String nama = JOptionPane.showInputDialog("Masukkan nama Petugas:");
        if (nama != null && !nama.isEmpty()) {
            Petugas petugas = new Petugas(nama);
            String[] options = { "Panggil Antrian", "Lihat Nomor Berikutnya", "Sisa Antrian", "Keluar" }; // P5 Array
            while (true) {
                int pilihan = JOptionPane.showOptionDialog(null, "Menu Petugas", "Petugas",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                if (pilihan == 0) {
                    try { // P12 Exception Handling
                        int nomor = petugas.panggilAntrian(antrian);
                        outputArea.append("Memanggil antrian nomor: " + nomor + "\n");
                    } catch (IllegalStateException e) {
                        outputArea.append("Antrian kosong: " + e.getMessage() + "\n");
                    }
                } else if (pilihan == 1) {
                    Integer berikutnya = antrian.lihatNomorBerikutnya();
                    outputArea.append("Nomor berikutnya: " + (berikutnya != null ? berikutnya : "Kosong") + "\n");
                } else if (pilihan == 2) {
                    outputArea.append("Sisa antrian: " + antrian.sisaAntrian() + "\n");
                } else {
                    break;
                }
            }
        }
    }
}
