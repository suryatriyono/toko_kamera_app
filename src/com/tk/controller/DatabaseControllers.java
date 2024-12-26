package com.tk.controller;

import com.tk.config.DatabaseConfig;
import com.tk.model.BarangModel;
import com.tk.model.DetailPembelianModel;
import com.tk.model.PemasokModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author STNVC
 */
public class DatabaseControllers {

    private static Connection getConnection() throws SQLException {
        return DatabaseConfig.getConnection();
    }

    // Metode untuk menambah barang
    public static boolean addBarang(BarangModel barang) {
        String sql = "INSERT INTO barang (id_barang, nama_barang, deskripsi, id_kategori, harga, stok) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, barang.getIdBarang());
            pstmt.setString(2, barang.getNamaBarang());
            pstmt.setString(3, barang.getDeskripsi());
            pstmt.setString(4, barang.getIdKategori());
            pstmt.setDouble(5, barang.getHarga());
            pstmt.setInt(6, barang.getStok());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<PemasokModel> getAllPemasok() {
        List<PemasokModel> listPemasok = new ArrayList<>();
        String sql = "SELECT * FROM pemasok";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PemasokModel pemasok = new PemasokModel(
                        rs.getString("id_pemasok"),
                        rs.getString("nama_pemasok"),
                        rs.getString("alamat"),
                        rs.getString("no_hp")
                );
                listPemasok.add(pemasok);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        
        return listPemasok;
    }

    public static List<BarangModel> getAllBarang() {
        List<BarangModel> listBarang = new ArrayList<>();
        String sql = "SELECT * FROM barang";
        try (Connection conn = DatabaseConfig.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BarangModel barang = new BarangModel(
                        rs.getString("id_barang"),
                        rs.getString("id_kategori"),
                        rs.getString("nama_barang"),
                        rs.getString("deskripsi"),
                        rs.getDouble("harga"),
                        rs.getInt("stok")
                );
                listBarang.add(barang);
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        return listBarang;
    }

    // Metode untuk mengambil barang berdasarkan ID
    public static BarangModel getBarangById(String idBarang) {
        String sql = "SELECT * FROM barang WHERE id_barang = ?";
        BarangModel barang = null;

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idBarang);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    barang = new BarangModel(
                            rs.getString("id_barang"),
                            rs.getString("nama_barang"),
                            rs.getString("deskripsi"),
                            rs.getString("id_kategori"),
                            rs.getDouble("harga"),
                            rs.getInt("stok")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return barang;
    }

    // Metode untuk memperbarui barang
    public static boolean updateBarang(BarangModel barang) {
        String sql = "UPDATE barang SET nama_barang = ?, deskripsi = ?, id_kategori = ?, harga = ?, stok = ? WHERE id_barang = ?";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, barang.getNamaBarang());
            pstmt.setString(2, barang.getDeskripsi());
            pstmt.setString(3, barang.getIdKategori());
            pstmt.setDouble(4, barang.getHarga());
            pstmt.setInt(5, barang.getStok());
            pstmt.setString(6, barang.getIdBarang());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metode untuk menghapus barang
    public static boolean deleteBarang(String idBarang) {
        String sql = "DELETE FROM barang WHERE id_barang = ?";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idBarang);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metode untuk mengambil nama barang berdasarkan ID
    public static String getNamaBarangById(String idBarang) {
        String sql = "SELECT nama_barang FROM barang WHERE id_barang = ?";
        String namaBarang = null;

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idBarang);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    namaBarang = rs.getString("nama_barang");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return namaBarang;
    }

    public static boolean saveOrder(List<DetailPembelianModel> detailPembelianList, String idPemasok) {
        String sqlPembelian = "INSERT INTO pembelian (id_pemasok, tanggal, total_harga) VALUES (?, ?, 0)";
        String sqlDetailPembelian = "INSERT INTO detail_pembelian (id_pembelian, id_barang, harga, jumlah, subtotal) VALUES (?, ?, ?, ?, ?)";
        String sqlUpdateTotalHarga = "UPDATE pembelian SET total_harga = ? WHERE id_pembelian = ?";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false); // Mulai transaksi

            String idPembelian = null; // Variable untuk menyimpan ID yang di hasilkan oleh auto-increment

            // Insert pembelian
            try (PreparedStatement pstmtPembelian = conn.prepareStatement(sqlPembelian, Statement.RETURN_GENERATED_KEYS)) {
                pstmtPembelian.setString(1, idPemasok);
                pstmtPembelian.setDate(2, new java.sql.Date(System.currentTimeMillis()));
                pstmtPembelian.executeUpdate();

                // Ambil ID Pembeian yang di hasilkan
                try (ResultSet generatedKeys = pstmtPembelian.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idPembelian = generatedKeys.getString(1); // ID yang di hasilkan
                    }
                }
            }

            double totalHarga = 0; // Untuk menghitung totalHarga

            // Insert Detail Pembelian
            try (PreparedStatement pstmtDetail = conn.prepareStatement(sqlDetailPembelian)) {
                for (DetailPembelianModel detail : detailPembelianList) {
                    double subtotal = detail.getHarga() * detail.getJumlah();

                    pstmtDetail.setString(1, idPemasok);
                    pstmtDetail.setString(2, detail.getIdBarang());
                    pstmtDetail.setDouble(3, detail.getHarga());
                    pstmtDetail.setInt(4, detail.getJumlah());
                    pstmtDetail.setDouble(5, subtotal);

                    pstmtDetail.executeUpdate();

                    totalHarga += subtotal;
                }
            }

            // Update total harga di table pembelian
            try (PreparedStatement pstmtUpdateTotal = conn.prepareStatement(sqlUpdateTotalHarga)) {
                System.out.println(totalHarga);
                pstmtUpdateTotal.setDouble(1, totalHarga);
                pstmtUpdateTotal.setString(2, idPembelian);
                
                pstmtUpdateTotal.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

}
