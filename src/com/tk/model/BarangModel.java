package com.tk.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author STNVC
 */
public class BarangModel {

    /**
     * @return the idBarang
     */
    public int getIdBarang() {
        return idBarang;
    }

    /**
     * @param idBarang the idBarang to set
     */
    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    /**
     * @return the idKategori
     */
    public String getIdKategori() {
        return idKategori;
    }

    /**
     * @param idKategori the idKategori to set
     */
    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    /**
     * @return the namaBarang
     */
    public String getNamaBarang() {
        return namaBarang;
    }

    /**
     * @param namaBarang the namaBarang to set
     */
    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    /**
     * @return the deskripsi
     */
    public String getDeskripsi() {
        return deskripsi;
    }

    /**
     * @param deskripsi the deskripsi to set
     */
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    /**
     * @return the harga
     */
    public BigDecimal getHarga() {
        return harga;
    }

    /**
     * @param harga the harga to set
     */
    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    /**
     * @return the stok
     */
    public int getStok() {
        return stok;
    }

    /**
     * @param stok the stok to set
     */
    public void setStok(int stok) {
        this.stok = stok;
    }
    private int idBarang;
    private String idKategori;  // Tetap menggunakan String untuk fleksibilitas
    private String namaBarang;
    private String deskripsi;
    private BigDecimal harga;
    private int stok;

    public BarangModel() {
    }

    public BarangModel(int idBarang, String idKategori, String namaBarang, String deskripsi, BigDecimal harga, int stok) {
        this.idBarang = idBarang;
        this.idKategori = idKategori;
        this.namaBarang = namaBarang;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.stok = stok;
    }

    public BarangModel(int idBarang, String namaBarang, String kategori, int stok) {
        this.idBarang = idBarang;
        this.namaBarang = namaBarang;
        this.idKategori = kategori; // Menggunakan idKategori untuk menyimpan nama kategori
        this.stok = stok;
        // Inisialisasi field lain dengan nilai default jika diperlukan
        this.deskripsi = "";
        this.harga = BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return "BarangModel{"
                + "idBarang=" + idBarang
                + ", idKategori='" + idKategori + '\''
                + ", namaBarang='" + namaBarang + '\''
                + ", deskripsi='" + deskripsi + '\''
                + ", harga=" + harga
                + ", stok=" + stok
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BarangModel that = (BarangModel) o;
        return idBarang == that.idBarang
                && stok == that.stok
                && Objects.equals(idKategori, that.idKategori)
                && Objects.equals(namaBarang, that.namaBarang)
                && Objects.equals(deskripsi, that.deskripsi)
                && Objects.equals(harga, that.harga);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBarang, idKategori, namaBarang, deskripsi, harga, stok);
    }

}
