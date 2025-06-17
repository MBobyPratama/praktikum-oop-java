import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class MainAppGUI {
    private static final Antrian antrian = new AntrianBiasa();
    private static JTextArea outputArea;
    private static JLabel statusLabel;
    private static JFrame mainFrame;
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color SECONDARY_COLOR = new Color(176, 196, 222);
    private static final Color ACCENT_COLOR = new Color(255, 165, 0);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public static void main(String[] args) {
        try {
            // mengatur Look and Feel ke Nimbus jika tersedia
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // jika Nimbus tidak tersedia, gunakan Look and Feel default
        }

        // buat frame utama
        mainFrame = new JFrame("Sistem Antrian Modern");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(650, 500);
        mainFrame.setLocationRelativeTo(null);
        
        // buat panel utama dengan latar belakang gradien
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, Color.WHITE, 0, h, SECONDARY_COLOR);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        
        // buat panel header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // buat panel konten
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout(15, 15));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // buat panel tombol
        JPanel buttonsPanel = createButtonsPanel();
        contentPanel.add(buttonsPanel, BorderLayout.NORTH);
        
        // buat panel output
        JPanel outputPanel = createOutputPanel();
        contentPanel.add(outputPanel, BorderLayout.CENTER);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // buat panel status
        JPanel statusPanel = createStatusPanel();
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        
        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setVisible(true);
        
        // update status awal
        updateStatus("Sistem siap digunakan. Silakan pilih mode masuk.");
    }
    
    private static JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        
        JLabel titleLabel = new JLabel("SISTEM MANAJEMEN ANTRIAN");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        return headerPanel;
    }
    
    private static JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(1, 2, 20, 0));
        
        int iconWidth = 24;
        int iconHeight = 24;
        
        Icon userIcon = null;
        Icon adminIcon = null;

        try {
            // muat URL untuk ikon user
            URL userIconUrl = MainAppGUI.class.getResource("/assets/user_icon.png");
            if (userIconUrl != null) {
                ImageIcon originalUserIcon = new ImageIcon(userIconUrl);
                Image userImg = originalUserIcon.getImage().getScaledInstance(
                    iconWidth, iconHeight, Image.SCALE_SMOOTH);
                userIcon = new ImageIcon(userImg);
            }

            // muat URL untuk ikon admin
            URL adminIconUrl = MainAppGUI.class.getResource("/assets/admin_icon.png");
            if (adminIconUrl != null) {
                ImageIcon originalAdminIcon = new ImageIcon(adminIconUrl);
                Image adminImg = originalAdminIcon.getImage().getScaledInstance(
                    iconWidth, iconHeight, Image.SCALE_SMOOTH);
                adminIcon = new ImageIcon(adminImg);
            }
        } catch (Exception e) {
            // jika terjadi kesalahan saat memuat ikon, tampilkan pesan di konsol
            System.err.println("Error loading icons: " + e.getMessage());
        }
        
        JButton pengunjungButton = createStyledButton("Masuk sebagai Pengunjung", userIcon);
        JButton petugasButton = createStyledButton("Masuk sebagai Petugas", adminIcon);
        
        // jika ikon tidak tersedia, gunakan teks default
        if (pengunjungButton.getIcon() == null) {
            pengunjungButton.setText("👤 Masuk sebagai Pengunjung");
        }
        
        if (petugasButton.getIcon() == null) {
            petugasButton.setText("🔑 Masuk sebagai Petugas");
        }
        
        pengunjungButton.addActionListener(e -> masukSebagaiPengunjung());
        petugasButton.addActionListener(e -> masukSebagaiPetugas());
        
        buttonsPanel.add(pengunjungButton);
        buttonsPanel.add(petugasButton);
        
        return buttonsPanel;
    }
    
    private static JButton createStyledButton(String text, Icon icon) {
        JButton button = new JButton(text, icon);
        button.setFont(BUTTON_FONT);
        button.setForeground(new Color(50, 50, 50));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY_COLOR, 1, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        // menambahkan efek hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(SECONDARY_COLOR);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });
        
        return button;
    }
    
    private static JPanel createOutputPanel() {
        JPanel outputPanel = new JPanel();
        outputPanel.setOpaque(false);
        outputPanel.setLayout(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true),
            "Log Aktivitas",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            PRIMARY_COLOR
        ));
        
        outputArea = new JTextArea();
        outputArea.setFont(TEXT_FONT);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBackground(new Color(250, 250, 250));
        outputArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        outputPanel.add(scrollPane, BorderLayout.CENTER);
        
        return outputPanel;
    }
    
    private static JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(PRIMARY_COLOR);
        statusPanel.setLayout(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        statusLabel = new JLabel("Status: Siap");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(Color.WHITE);
        
        statusPanel.add(statusLabel, BorderLayout.WEST);
        
        JLabel infoLabel = new JLabel("Antrian Tersisa: " + antrian.sisaAntrian());
        infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        infoLabel.setForeground(Color.WHITE);
        
        statusPanel.add(infoLabel, BorderLayout.EAST);
        
        // timer untuk memperbarui informasi antrian
        Timer timer = new Timer(1000, e -> {
            infoLabel.setText("Antrian Tersisa: " + antrian.sisaAntrian());
        });
        timer.start();
        
        return statusPanel;
    }

    private static void updateStatus(String message) {
        statusLabel.setText("Status: " + message);
    }

    private static void masukSebagaiPengunjung() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel label = new JLabel("Masukkan nama Anda:");
        label.setFont(TEXT_FONT);
        panel.add(label);
        
        JTextField textField = new JTextField();
        textField.setFont(TEXT_FONT);
        panel.add(textField);
        
        int result = JOptionPane.showConfirmDialog(mainFrame, panel, "Data Pengunjung", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String nama = textField.getText();
            if (nama != null && !nama.trim().isEmpty()) {
                Pengunjung pengunjung = new Pengunjung(nama);
                
                int pilihan = JOptionPane.showConfirmDialog(mainFrame, 
                    "Halo " + nama + "!\nApakah Anda ingin mengambil nomor antrian?", 
                    "Menu Pengunjung", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                if (pilihan == JOptionPane.YES_OPTION) {
                    try {
                        int nomor = pengunjung.ambilAntrian(antrian);
                        
                        // menampilkan dialog sukses
                        JDialog successDialog = new JDialog(mainFrame, "Berhasil", true);
                        successDialog.setLayout(new BorderLayout());
                        
                        JPanel successPanel = new JPanel();
                        successPanel.setLayout(new BoxLayout(successPanel, BoxLayout.Y_AXIS));
                        successPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
                        successPanel.setBackground(Color.WHITE);
                        
                        JLabel nomorLabel = new JLabel(String.valueOf(nomor));
                        nomorLabel.setFont(new Font("Arial", Font.BOLD, 48));
                        nomorLabel.setForeground(ACCENT_COLOR);
                        nomorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        
                        JLabel messageLabel = new JLabel(nama + ", nomor antrian Anda adalah:");
                        messageLabel.setFont(TEXT_FONT);
                        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        
                        JButton okButton = new JButton("OK");
                        okButton.setFont(BUTTON_FONT);
                        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                        okButton.addActionListener(e -> successDialog.dispose());
                        
                        successPanel.add(messageLabel);
                        successPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                        successPanel.add(nomorLabel);
                        successPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                        successPanel.add(okButton);
                        
                        successDialog.add(successPanel);
                        successDialog.pack();
                        successDialog.setLocationRelativeTo(mainFrame);
                        successDialog.setVisible(true);
                        
                        outputArea.append("✓ " + nama + " mendapat nomor antrian: " + nomor + "\n");
                        updateStatus(nama + " telah mendapat nomor antrian " + nomor);
                    } catch (IllegalStateException e) {
                        outputArea.append("⚠ Gagal ambil antrian: " + e.getMessage() + "\n");
                        updateStatus("Gagal mengambil antrian");
                        JOptionPane.showMessageDialog(mainFrame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Nama tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void masukSebagaiPetugas() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel label = new JLabel("Masukkan nama Petugas:");
        label.setFont(TEXT_FONT);
        panel.add(label);
        
        JTextField textField = new JTextField();
        textField.setFont(TEXT_FONT);
        panel.add(textField);
        
        int result = JOptionPane.showConfirmDialog(mainFrame, panel, "Login Petugas", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String nama = textField.getText();
            if (nama != null && !nama.trim().isEmpty()) {
                Petugas petugas = new Petugas(nama);
                updateStatus("Petugas " + nama + " sedang aktif");
                
                JDialog petugasDialog = new JDialog(mainFrame, "Panel Petugas: " + nama, false);
                petugasDialog.setSize(400, 300);
                petugasDialog.setLocationRelativeTo(mainFrame);
                
                JPanel petugasPanel = new JPanel();
                petugasPanel.setLayout(new BorderLayout(10, 10));
                petugasPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                
                JLabel welcomeLabel = new JLabel("Selamat datang, " + nama + "!");
                welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
                welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
                
                JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 0, 10));
                
                JButton panggilButton = createStyledButton("Panggil Antrian", null);
                JButton lihatButton = createStyledButton("Lihat Nomor Berikutnya", null);
                JButton sisaButton = createStyledButton("Sisa Antrian", null);
                JButton keluarButton = createStyledButton("Keluar", null);
                
                panggilButton.addActionListener(e -> {
                    try {
                        int nomor = petugas.panggilAntrian(antrian);
                        outputArea.append("➤ Petugas " + nama + " memanggil antrian nomor: " + nomor + "\n");
                        updateStatus("Memanggil nomor " + nomor);
                        
                        JDialog callDialog = new JDialog(petugasDialog, "Memanggil", true);
                        callDialog.setLayout(new BorderLayout());
                        
                        JPanel callPanel = new JPanel();
                        callPanel.setLayout(new BoxLayout(callPanel, BoxLayout.Y_AXIS));
                        callPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
                        callPanel.setBackground(new Color(240, 255, 240));
                        
                        JLabel callingLabel = new JLabel("MEMANGGIL");
                        callingLabel.setFont(new Font("Arial", Font.BOLD, 18));
                        callingLabel.setForeground(new Color(0, 100, 0));
                        callingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        
                        JLabel numberLabel = new JLabel(String.valueOf(nomor));
                        numberLabel.setFont(new Font("Arial", Font.BOLD, 48));
                        numberLabel.setForeground(new Color(0, 100, 0));
                        numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        
                        JButton doneButton = new JButton("Selesai");
                        doneButton.setFont(BUTTON_FONT);
                        doneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                        doneButton.addActionListener(event -> callDialog.dispose());
                        
                        callPanel.add(callingLabel);
                        callPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                        callPanel.add(numberLabel);
                        callPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                        callPanel.add(doneButton);
                        
                        callDialog.add(callPanel);
                        callDialog.pack();
                        callDialog.setLocationRelativeTo(petugasDialog);
                        callDialog.setVisible(true);
                    } catch (IllegalStateException ex) {
                        outputArea.append("⚠ Antrian kosong: " + ex.getMessage() + "\n");
                        updateStatus("Antrian kosong");
                        JOptionPane.showMessageDialog(petugasDialog, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
                    }
                });
                
                lihatButton.addActionListener(e -> {
                    Integer berikutnya = antrian.lihatNomorBerikutnya();
                    String message = berikutnya != null ? 
                        "Nomor berikutnya adalah: " + berikutnya : 
                        "Tidak ada antrian yang menunggu";
                    
                    outputArea.append("ℹ Petugas " + nama + " melihat nomor berikutnya: " + 
                        (berikutnya != null ? berikutnya : "Kosong") + "\n");
                    
                    JOptionPane.showMessageDialog(petugasDialog, message, "Nomor Berikutnya", 
                        JOptionPane.INFORMATION_MESSAGE);
                });
                
                sisaButton.addActionListener(e -> {
                    int sisa = antrian.sisaAntrian();
                    outputArea.append("ℹ Petugas " + nama + " melihat sisa antrian: " + sisa + "\n");
                    
                    JOptionPane.showMessageDialog(petugasDialog, 
                        "Jumlah antrian yang tersisa: " + sisa, 
                        "Sisa Antrian", JOptionPane.INFORMATION_MESSAGE);
                });
                
                keluarButton.addActionListener(e -> {
                    petugasDialog.dispose();
                    updateStatus("Petugas " + nama + " telah keluar");
                });
                
                buttonPanel.add(panggilButton);
                buttonPanel.add(lihatButton);
                buttonPanel.add(sisaButton);
                buttonPanel.add(keluarButton);
                
                petugasPanel.add(welcomeLabel, BorderLayout.NORTH);
                petugasPanel.add(buttonPanel, BorderLayout.CENTER);
                
                petugasDialog.add(petugasPanel);
                petugasDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Nama petugas tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}