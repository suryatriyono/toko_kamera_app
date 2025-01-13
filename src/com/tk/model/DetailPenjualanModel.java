package com.tk.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 *
 * @author STNVC
 */
public class DetailPenjualanModel {
    

    /**
     * @return the idDpenjualan
     */
    public int getIdDpenjualan() {
        return idDpenjualan;
    }

    /**
     * @param idDpenjualan the idDpenjualan to set
     */
    public void setIdDpenjualan(int idDpenjualan) {
        this.idDpenjualan = idDpenjualan;
    }

    /**
     * @return the idPenjualan
     */
    public int getIdPenjualan() {
        return idPenjualan;
    }

    /**
     * @param idPenjualan the idPenjualan to set
     */
    public void setIdPenjualan(int idPenjualan) {
        this.idPenjualan = idPenjualan;
    }

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
        this.subtotal = calculateSubtotal();
    }

    /**
     * @return the jumlah
     */
    public int getJumlah() {
        return jumlah;
    }

    /**
     * @param jumlah the jumlah to set
     */
    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
        this.subtotal = calculateSubtotal();
    }

    /**
     * @return the subtotal
     */
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    private int idDpenjualan;
    private int idPenjualan;
    private int idBarang;
    private BigDecimal harga;
    private int jumlah;
    private BigDecimal subtotal;

    // Konstruktor tanpa parameter
    public DetailPenjualanModel() {
    }

    // Konstruktor dengan parameter untuk pembuatan detail baru
    public DetailPenjualanModel(int idPenjualan, int idBarang, BigDecimal harga, int jumlah) {
        this.idPenjualan = idPenjualan;
        this.idBarang = idBarang;
        this.harga = harga;
        this.jumlah = jumlah;
        this.subtotal = calculateSubtotal();
    }

    // Konstruktor lengkap
    public DetailPenjualanModel(int idDpenjualan, int idPenjualan, int idBarang, BigDecimal harga, int jumlah) {
        this.idDpenjualan = idDpenjualan;
        this.idPenjualan = idPenjualan;
        this.idBarang = idBarang;
        this.harga = harga;
        this.jumlah = jumlah;
        this.subtotal = calculateSubtotal();
    }

    // Private method untuk menghitung subtotal
    private BigDecimal calculateSubtotal() {
        return this.harga.multiply(BigDecimal.valueOf(this.jumlah)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "DetailPenjualanModel{"
                + "idDpenjualan=" + idDpenjualan
                + ", idPenjualan=" + idPenjualan
                + ", idBarang=" + idBarang
                + ", harga=" + harga
                + ", jumlah=" + jumlah
                + ", subtotal=" + subtotal
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
        DetailPenjualanModel that = (DetailPenjualanModel) o;
        return idDpenjualan == that.idDpenjualan
                && idPenjualan == that.idPenjualan
                && idBarang == that.idBarang
                && jumlah == that.jumlah
                && Objects.equals(harga, that.harga)
                && Objects.equals(subtotal, that.subtotal);
    }
}
