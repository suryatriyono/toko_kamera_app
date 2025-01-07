package com.tk.model;

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
    public double getHarga() {
        return harga;
    }

    /**
     * @param harga the harga to set
     */
    public void setHarga(double harga) {
        this.harga = harga;
        this.subtotal = harga * jumlah;
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
        this.subtotal = harga * jumlah;
    }

    /**
     * @return the subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    private int idDetailPembelian;
    private int idPembelian;
    private int  idBarang;
    private double harga;
    private int jumlah;
    private double subtotal;
    
    public DetailPembelianModel(int idBarang, double harga, int jumlah) {
        this.idBarang = idBarang;
        this.harga = harga;
        this.jumlah = jumlah;
        this.subtotal = harga * jumlah;
    }

    public DetailPembelianModel(int idDetailPembelian, int idPembelian, int idBarang, double harga, int jumlah, double subtotal) {
        this.idDetailPembelian = idDetailPembelian;
        this.idPembelian = idPembelian;
        this.idBarang = idBarang;
        this.harga = harga;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

}
