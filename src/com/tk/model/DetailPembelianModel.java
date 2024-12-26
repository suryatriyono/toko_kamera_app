package com.tk.model;

/**
 *
 * @author STNVC
 */
public class DetailPembelianModel {

    /**
     * @return the idDetailPembelian
     */
    public String getIdDetailPembelian() {
        return idDetailPembelian;
    }

    /**
     * @param idDetailPembelian the idDetailPembelian to set
     */
    public void setIdDetailPembelian(String idDetailPembelian) {
        this.idDetailPembelian = idDetailPembelian;
    }

    /**
     * @return the idPembelian
     */
    public String getIdPembelian() {
        return idPembelian;
    }

    /**
     * @param idPembelian the idPembelian to set
     */
    public void setIdPembelian(String idPembelian) {
        this.idPembelian = idPembelian;
    }

    /**
     * @return the idBarang
     */
    public String getIdBarang() {
        return idBarang;
    }

    /**
     * @param idBarang the idBarang to set
     */
    public void setIdBarang(String idBarang) {
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

    private String idDetailPembelian;
    private String idPembelian;
    private String idBarang;
    private double harga;
    private int jumlah;
    private double subtotal;
    
    public DetailPembelianModel(String idBarang, double harga, int jumlah) {
        this.idBarang = idBarang;
        this.harga = harga;
        this.jumlah = jumlah;
        this.subtotal = harga * jumlah;
    }

    public DetailPembelianModel(String idDetailPembelian, String idPembelian, String idBarang, double harga, int jumlah, double subtotal) {
        this.idDetailPembelian = idDetailPembelian;
        this.idPembelian = idPembelian;
        this.idBarang = idBarang;
        this.harga = harga;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

}
