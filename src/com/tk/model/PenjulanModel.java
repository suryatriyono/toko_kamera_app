package com.tk.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author STNVC
 */
public class PenjulanModel {

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
     * @return the idPelanggan
     */
    public int getIdPelanggan() {
        return idPelanggan;
    }

    /**
     * @param idPelanggan the idPelanggan to set
     */
    public void setIdPelanggan(int idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    /**
     * @return the tanggal
     */
    public Date getTanggal() {
        return tanggal;
    }

    /**
     * @param tanggal the tanggal to set
     */
    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    /**
     * @return the totalHarga
     */
    public BigDecimal getTotalHarga() {
        return totalHarga;
    }

    /**
     * @param totalHarga the totalHarga to set
     */
    public void setTotalHarga(BigDecimal totalHarga) {
        this.totalHarga = totalHarga;
    }
    private int idPenjualan;
    private int idPelanggan;
    private Date tanggal;
    private BigDecimal totalHarga;

    public PenjulanModel() {
    }

    public PenjulanModel(int idPenjualan, int idPelanggan, Date tanggal, BigDecimal totalHarga) {
        this.idPenjualan = idPenjualan;
        this.idPelanggan = idPelanggan;
        this.tanggal = tanggal;
        this.totalHarga = totalHarga;
    }
    
    
}
