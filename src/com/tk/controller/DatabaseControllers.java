package com.tk.controller;

import com.tk.config.DatabaseConfig;
import com.tk.model.BarangModel;
import com.tk.model.DetailPembelianModel;
import com.tk.model.DetailPenjualanModel;
import com.tk.model.KategoriModel;
import com.tk.model.PelangganModel;
import com.tk.model.PemasokModel;
import java.math.BigDecimal;
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

    // Metode Get
    public static List<BarangModel> getAllBarang() {
        List<BarangModel> listBarang = new ArrayList<>();
        String sql = "SELECT * FROM barang";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                BarangModel barang = new BarangModel(
                        rs.getInt("id_barang"),
                        rs.getString("id_kategori"),
                        rs.getString("nama_barang"),
                        rs.getString("deskripsi"),
                        rs.getDouble("harga"),
                        rs.getInt("stok")
                );
                listBarang.add(barang);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all barang: " + e.getMessage());
        }
        return listBarang;
    }

    public static List<PemasokModel> getAllPemasok() {
        List<PemasokModel> listPemasok = new ArrayList<>();
        String sql = "SELECT * FROM pemasok";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PemasokModel pemasok = new PemasokModel(
                        rs.getInt("id_pemasok"),
                        rs.getString("nama_pemasok"),
                        rs.getString("alamat"),
                        rs.getString("no_hp")
                );
                listPemasok.add(pemasok);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all pemasok: " + e.getMessage());
        }
        return listPemasok;
    }

    public static BarangModel getBarangById(String idBarang) {
        String sql = "SELECT * FROM barang WHERE id_barang = ?";
        BarangModel barang = null;

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idBarang);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    barang = new BarangModel(
                            rs.getInt("id_barang"),
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

    public static List<KategoriModel> getAllKategori() {
        List<KategoriModel> listKategori = new ArrayList<>();
        String sql = "SELECT * FROM kategori";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                KategoriModel kategori = new KategoriModel(
                        rs.getString("id_kategori"),
                        rs.getString("kategori")
                );
                listKategori.add(kategori);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all kategori: " + e.getMessage());
        }
        return listKategori;
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

    // Method Save
    public static boolean saveOrder(List<DetailPembelianModel> detailPembelianList, int idPemasok) {
        String sqlPembelian = "INSERT INTO pembelian (id_pemasok, tanggal, total_harga) VALUES (?, ?, ?)";
        String sqlDetailPembelian = "INSERT INTO detail_pembelian (id_pembelian, id_barang, harga, jumlah, subtotal) VALUES (?, ?, ?, ?, ?)";
        String sqlUpdateStok = "UPDATE barang SET stok = stok + ? WHERE id_barang = ?";

        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Mulai transaksi

            // Insert pembelian
            try (PreparedStatement pstmtPembelian = conn.prepareStatement(sqlPembelian, Statement.RETURN_GENERATED_KEYS)) {
                double totalHarga = detailPembelianList.stream()
                        .mapToDouble(detail -> detail.getHarga() * detail.getJumlah())
                        .sum();

                pstmtPembelian.setInt(1, idPemasok);
                pstmtPembelian.setDate(2, new java.sql.Date(System.currentTimeMillis()));
                pstmtPembelian.setDouble(3, totalHarga);
                pstmtPembelian.executeUpdate();

                int idPembelian;
                try (ResultSet generatedKeys = pstmtPembelian.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idPembelian = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating pembelian failed, no ID obtained.");
                    }
                }

                // Insert Detail Pembelian dan Update Stok
                try (PreparedStatement pstmtDetail = conn.prepareStatement(sqlDetailPembelian); PreparedStatement pstmtUpdateStok = conn.prepareStatement(sqlUpdateStok)) {

                    for (DetailPembelianModel detail : detailPembelianList) {
                        // Insert detail pembelian
                        pstmtDetail.setInt(1, idPembelian);
                        pstmtDetail.setInt(2, detail.getIdBarang());
                        pstmtDetail.setDouble(3, detail.getHarga());
                        pstmtDetail.setInt(4, detail.getJumlah());
                        pstmtDetail.setDouble(5, detail.getHarga() * detail.getJumlah());
                        pstmtDetail.addBatch();

                        // Update stok barang
                        pstmtUpdateStok.setInt(1, detail.getJumlah());
                        pstmtUpdateStok.setInt(2, detail.getIdBarang());
                        pstmtUpdateStok.addBatch();
                    }

                    pstmtDetail.executeBatch();
                    pstmtUpdateStok.executeBatch();
                }

                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error in saveOrder: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    public static boolean saveBarang(BarangModel barang) {
        String sql = "INSERT INTO barang (id_kategori, nama_barang, deskripsi, harga, stok) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, barang.getIdKategori());
            pstmt.setString(2, barang.getNamaBarang());
            pstmt.setString(3, barang.getDeskripsi());
            pstmt.setDouble(4, barang.getHarga());
            pstmt.setInt(5, barang.getStok());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error saving barang: " + e.getMessage());
            return false;
        }
    }

    // Metode Update
    public static boolean updateBarang(BarangModel barang) {
        String sql = "UPDATE barang SET id_kategori = ?, nama_barang = ?, deskripsi = ?, harga = ?, stok = ? WHERE id_barang = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, barang.getIdKategori());
            pstmt.setString(2, barang.getNamaBarang());
            pstmt.setString(3, barang.getDeskripsi());
            pstmt.setDouble(4, barang.getHarga());
            pstmt.setInt(5, barang.getStok());
            pstmt.setInt(6, barang.getIdBarang());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating barang: " + e.getMessage());
            return false;
        }
    }

    // Metode Delete
    public static boolean deleteBarang(int idBarang) {
        String sql = "DELETE FROM barang WHERE id_barang = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idBarang);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting barang: " + e.getMessage());
            return false;
        }
    }

    // ======== Pemasok ======
    public static boolean savePemasok(PemasokModel pemasok) {
        String sql = "INSERT INTO pemasok (nama_pemasok, alamat, no_hp) VALUES (?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pemasok.getNamaPemasok());
            pstmt.setString(2, pemasok.getAlamat());
            pstmt.setString(3, pemasok.getNoHp());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error saving pemasok: " + e.getMessage());
            return false;
        }
    }

    public static boolean updatePemasok(PemasokModel pemasok) {
        String sql = "UPDATE pemasok SET nama_pemasok = ?, alamat = ?, no_hp = ? WHERE id_pemasok = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pemasok.getNamaPemasok());
            pstmt.setString(2, pemasok.getAlamat());
            pstmt.setString(3, pemasok.getNoHp());
            pstmt.setInt(4, pemasok.getIdPemasok());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating pemasok: " + e.getMessage());
            return false;
        }
    }

    public static boolean deletePemasok(int idPemasok) {
        String sql = "DELETE FROM pemasok WHERE id_pemasok = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idPemasok);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting pemasok: " + e.getMessage());
            return false;
        }
    }

    public static PemasokModel getPemasokById(int idPemasok) {
        String sql = "SELECT * FROM pemasok WHERE id_pemasok = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idPemasok);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new PemasokModel(
                            rs.getInt("id_pemasok"),
                            rs.getString("nama_pemasok"),
                            rs.getString("alamat"),
                            rs.getString("no_hp")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting pemasok by ID: " + e.getMessage());
        }
        return null;
    }

    // ========== Pelanggan ==========
    public static List<PelangganModel> getAllPelanggan() {
        List<PelangganModel> listPelanggan = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PelangganModel pelanggan = new PelangganModel(
                        rs.getInt("id_pelanggan"),
                        rs.getString("nama_pelanggan"),
                        rs.getString("alamat"),
                        rs.getString("no_hp")
                );
                listPelanggan.add(pelanggan);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all pelanggan: " + e.getMessage());
        }
        return listPelanggan;
    }

    public static boolean savePelanggan(PelangganModel pelanggan) {
        String sql = "INSERT INTO pelanggan (nama_pelanggan, alamat, no_hp) VALUES (?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pelanggan.getNamaPelanggan());
            pstmt.setString(2, pelanggan.getAlamat());
            pstmt.setString(3, pelanggan.getNoHp());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error saving pelanggan: " + e.getMessage());
            return false;
        }
    }

    public static boolean updatePelanggan(PelangganModel pelanggan) {
        String sql = "UPDATE pelanggan SET nama_pelanggan = ?, alamat = ?, no_hp = ? WHERE id_pelanggan = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pelanggan.getNamaPelanggan());
            pstmt.setString(2, pelanggan.getAlamat());
            pstmt.setString(3, pelanggan.getNoHp());
            pstmt.setInt(4, pelanggan.getIdPelanggan());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating pelanggan: " + e.getMessage());
            return false;
        }
    }

    public static boolean deletePelanggan(int idPelanggan) {
        String sql = "DELETE FROM pelanggan WHERE id_pelanggan = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idPelanggan);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting pelanggan: " + e.getMessage());
            return false;
        }
    }

    // ======== Kategori ==========
//    public static List<KategoriModel> getAllKategori() {
//        List<KategoriModel> listKategori = new ArrayList<>();
//        String sql = "SELECT * FROM kategori";
//
//        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                KategoriModel kategori = new KategoriModel(
//                        rs.getString("id_kategori"),
//                        rs.getString("kategori")
//                );
//                listKategori.add(kategori);
//            }
//        } catch (SQLException e) {
//            System.err.println("Error getting all kategori: " + e.getMessage());
//        }
//        return listKategori;
//    }
    public static boolean saveKategori(KategoriModel kategori) {
        String sql = "INSERT INTO kategori (id_kategori, kategori) VALUES (?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, kategori.getIdKategori());
            pstmt.setString(2, kategori.getKategori());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error saving kategori: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateKategori(KategoriModel kategori) {
        String sql = "UPDATE kategori SET kategori = ? WHERE id_kategori = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, kategori.getKategori());
            pstmt.setString(2, kategori.getIdKategori());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating kategori: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteKategori(String idKategori) {
        String sql = "DELETE FROM kategori WHERE id_kategori = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idKategori);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting kategori: " + e.getMessage());
            return false;
        }
    }

    public static KategoriModel getKategoriById(String idKategori) {
        String sql = "SELECT * FROM kategori WHERE id_kategori = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idKategori);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new KategoriModel(
                            rs.getString("id_kategori"),
                            rs.getString("kategori")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting kategori by ID: " + e.getMessage());
        }
        return null;
    }

    // ===== Penjualan =====
    public static boolean savePenjualan(int idPelanggan, double totalHarga, List<DetailPenjualanModel> detailList) {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            // Simpan penjualan
            String sqlPenjualan = "INSERT INTO penjualan (id_pelanggan, tanggal, total_harga) VALUES (?, ?, ?)";
            int idPenjualan;
            try (PreparedStatement pstmt = conn.prepareStatement(sqlPenjualan, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, idPelanggan);
                pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
                pstmt.setBigDecimal(3, BigDecimal.valueOf(totalHarga));
                pstmt.executeUpdate();

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idPenjualan = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating penjualan failed, no ID obtained.");
                    }
                }
            }

            // Simpan detail penjualan
            saveDetailPenjualan(conn, idPenjualan, detailList);

            // Update stok barang
            updateStokBarang(conn, detailList);

            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void saveDetailPenjualan(Connection conn, int idPenjualan, List<DetailPenjualanModel> detailList) throws SQLException {
        String sql = "INSERT INTO detail_penjualan (id_penjualan, id_barang, harga, jumlah, subtotal) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (DetailPenjualanModel detail : detailList) {
                pstmt.setInt(1, idPenjualan);
                pstmt.setInt(2, detail.getIdBarang());
                pstmt.setBigDecimal(3, BigDecimal.valueOf(detail.getHarga()));
                pstmt.setInt(4, detail.getJumlah());
                pstmt.setBigDecimal(5, BigDecimal.valueOf(detail.getSubtotal()));
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    private static void updateStokBarang(Connection conn, List<DetailPenjualanModel> detailList) throws SQLException {
        String sql = "UPDATE barang SET stok = stok - ? WHERE id_barang = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (DetailPenjualanModel detail : detailList) {
                pstmt.setInt(1, detail.getJumlah());
                pstmt.setInt(2, detail.getIdBarang());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    public static BarangModel getBarangByNama(String namaBarang) {
        String sql = "SELECT * FROM barang WHERE nama_barang = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, namaBarang);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new BarangModel(
                            rs.getInt("id_barang"),
                            rs.getString("id_kategori"),
                            rs.getString("nama_barang"),
                            rs.getString("deskripsi"),
                            rs.getBigDecimal("harga").doubleValue(),
                            rs.getInt("stok")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BarangModel getBarangById(int idBarang) {
        String sql = "SELECT * FROM barang WHERE id_barang = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idBarang);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new BarangModel(
                            rs.getInt("id_barang"),
                            rs.getString("id_kategori"),
                            rs.getString("nama_barang"),
                            rs.getString("deskripsi"),
                            rs.getBigDecimal("harga").doubleValue(),
                            rs.getInt("stok")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<BarangModel> searchBarang(String keyword) {
        List<BarangModel> result = new ArrayList<>();
        String sql = "SELECT * FROM barang WHERE nama_barang LIKE ? OR id_barang LIKE ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BarangModel barang = new BarangModel(
                            rs.getInt("id_barang"),
                            rs.getString("id_kategori"),
                            rs.getString("nama_barang"),
                            rs.getString("deskripsi"),
                            rs.getDouble("harga"),
                            rs.getInt("stok")
                    );
                    result.add(barang);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching barang: " + e.getMessage());
        }
        return result;
    }
}
