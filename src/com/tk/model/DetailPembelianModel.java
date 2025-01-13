package com.tk.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 *
 * @author STNVC
 */
public class DetailPembelianModel {

    /**
     * @return the idDetailPembelian
     */
    public int getIdDetailPembelian() {
        return idDetailPembelian;
    }

    /**
     * @param idDetailPembelian the idDetailPembelian to set
     */
    public void setIdDetailPembelian(int idDetailPembelian) {
        this.idDetailPembelian = idDetailPembelian;
    }

    /**
     * @return the idPembelian
     */
    public int getIdPembelian() {
        return idPembelian;
    }

    /**
     * @param idPembelian the idPembelian to set
     */
    public void setIdPembelian(int idPembelian) {
        this.idPembelian = idPembelian;
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

    private int idDetailPembelian;
    private int idPembelian;
    private int idBarang;
    private BigDecimal harga;
    private int jumlah;
    private BigDecimal subtotal;

    // Konstruktor tanpa parameter
    public DetailPembelianModel() {
    }

    // Konstruktor dengan parameter untuk pembuatan detail baru
    public DetailPembelianModel(int idBarang, BigDecimal harga, int jumlah) {
        this.idBarang = idBarang;
        this.harga = harga;
        this.jumlah = jumlah;
        this.subtotal = calculateSubtotal();
    }

    // Konstruktor lengkap
    public DetailPembelianModel(int idDetailPembelian, int idPembelian, int idBarang, BigDecimal harga, int jumlah) {
        this.idDetailPembelian = idDetailPembelian;
        this.idPembelian = idPembelian;
        this.idBarang = idBarang;
        this.harga = harga;
        this.jumlah = jumlah;
        this.subtotal = calculateSubtotal();
    }

    // Private method untuk menghitung subtotal
    private BigDecimal calculateSubtotal() {
        return this.harga.multiply(BigDecimal.valueOf(this.jumlah)).setScale(2, RoundingMode.HALF_UP);
    }

    // Override toString method untuk representasi string yang lebih baik
    @Override
    public String toString() {
        return "DetailPembelianModel{"
                + "idDetailPembelian=" + idDetailPembelian
                + ", idPembelian=" + idPembelian
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
        DetailPembelianModel that = (DetailPembelianModel) o;
        return idDetailPembelian == that.idDetailPembelian
                && idPembelian == that.idPembelian
                && idBarang == that.idBarang
                && jumlah == that.jumlah
                && Objects.equals(harga, that.harga)
                && Objects.equals(subtotal, that.subtotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDetailPembelian, idPembelian, idBarang, harga, jumlah, subtotal);
    }

}
