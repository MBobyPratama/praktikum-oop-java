# Sistem Manajemen Antrian

![TEORI-ANTRIAN-2](https://github.com/user-attachments/assets/a7743b54-2498-4576-aef7-b792bcaade78)

## 🤝 Kontributor
- Quyun Isnawan - 2310512049
- Muhammad Sulthan Yasin Zain - 2310512055
- Muhammad Boby Pratama - 2310512056
- Hanna Meilova Hababan - 2310512081
- Taufiq Maulana Prakoso - 2310512085

## Deskripsi Proyek
Sistem Manajemen Antrian adalah aplikasi desktop berbasis Java yang menerapkan konsep Pemrograman Berorientasi Objek (OOP). Aplikasi ini memungkinkan pengunjung untuk mengambil nomor antrian dan petugas untuk memanggil nomor antrian, dengan antarmuka grafis yang intuitif dan modern.

## ✨ Fitur Utama
- **Sistem multi-pengguna** - Mode Pengunjung dan Petugas
- **Antarmuka grafis modern** dengan tema warna konsisten
- **Pengambilan nomor antrian** oleh pengunjung
- **Pemanggilan antrian** oleh petugas
- **Informasi real-time** tentang status antrian
- **Pembatasan jumlah antrian** untuk menghindari overload
- **Notifikasi visual** untuk setiap operasi

## 🏗️ Struktur Proyek
praktikum-oop-java/ <br>
      ├── User.java             # Kelas abstrak dasar untuk semua pengguna <br>
      ├── Pengunjung.java       # Kelas untuk pengunjung yang mengambil antrian <br>
      ├── Petugas.java          # Kelas untuk petugas yang memanggil antrian <br>
      ├── Antrian.java          # Interface yang mendefinisikan operasi antrian <br>
      ├── AntrianBiasa.java     # Implementasi dari interface Antrian <br>
      └── MainAppGUI.java       # Kelas utama dengan implementasi GUI

## 🧩 Konsep OOP yang Diimplementasikan
#### 1. Inheritance (Pewarisan)
```java
public class Pengunjung extends User {
    // ...
}

public class Petugas extends User {
    // ...
}
```

#### 2. Encapsulation (Enkapsulasi)
```java
private int nomorAntrian;

public int getNomorAntrian() {
    return nomorAntrian;
}
```

#### 3. Polymorphism (Polimorfisme)
```java
@Override
public void tampilkanMenu() {
    System.out.println("Menu Pengunjung:");
    // ...
}
```

#### 4. Abstraction (Abstraksi)
```java
public abstract class User {
    // ...
    public abstract void tampilkanMenu();
}
```

#### 5. Interface
```java
public interface Antrian {
    int ambilNomor();
    int panggilNomor();
    Integer lihatNomorBerikutnya();
    int sisaAntrian();
}
```

#### 6. Exception Handling
```java
try {
    int dipanggil = antrian.panggilNomor();
    return dipanggil;
} catch (IllegalStateException e) {
    System.out.println(e.getMessage());
    return 0;
}
```

#### 7. Static & Final Constants
```java
private static final String PESAN_ANTRIAN_KOSONG = "Antrian kosong. Tidak ada nomor untuk dipanggil.";
private static final int MAKSIMAL_ANTRIAN = 5;
```

#### 8. Java Collections
```java
private final ArrayList<Integer> daftarAntrian;
```

## 🚀 Cara Menjalankan Aplikasi
Prasyarat:
- Java Development Kit (JDK) 8 atau lebih tinggi
- Sistem operasi: Windows, macOS, atau Linux

Langkah-langkah:
1. Clone repositori
```bash
git clone https://github.com/MBobyPratama/praktikum-oop-java.git
cd praktikum-oop-java
```

2. Kompilasi source code
```bash
javac *.java
```

3. Jalankan aplikasi
```bash
java MainAppGUI
```

## 📝 Petunjuk Penggunaan
### Mode Pengunjung
1. Klik tombol "Masuk sebagai Pengunjung"
2. Masukkan nama Anda
3. Pilih "Ya" untuk mendapatkan nomor antrian
4. Sistem akan menampilkan nomor antrian Anda

### Mode Petugas
1. Klik tombol "Masuk sebagai Petugas"
2. Masukkan nama Anda sebagai petugas
3. Gunakan panel petugas untuk:
- Memanggil nomor antrian berikutnya
- Melihat nomor yang akan dipanggil selanjutnya
- Memeriksa jumlah antrian yang tersisa
- Keluar dari sistem

## 📸 Screenshots
### Tampilan Utama
![image](https://github.com/user-attachments/assets/51799bc0-5437-45fa-81d6-5ca552a52caf)

### Mode Pengunjung
![image](https://github.com/user-attachments/assets/316de08c-224e-4931-aef3-154c522c3ee2)

### Mode Petugas
![image](https://github.com/user-attachments/assets/cc980944-781a-4efa-8492-edf10f0a9fe4)

## 🛠️ Batasan Sistem
- Jumlah maksimum antrian adalah 5
- Sistem akan menampilkan pesan error jika antrian penuh atau kosong
