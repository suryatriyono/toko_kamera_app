package com.tk.model;

/**
 *
 * @author STNVC
 */
public class OrderModel {

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
    }

    /**
     * @return the status
     */
    public StatusTypeModel getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(StatusTypeModel status) {
        this.status = status;
    }
    private String idBarang;  
    private String namaBarang;  
    private double harga;  
    private int jumlah; 
    private StatusTypeModel status;

//    public OrderModel() {
//    }

    public OrderModel(String idBarang, String namaBarang, double harga, int jumlah, StatusTypeModel status) {
        this.idBarang = idBarang;
        this.namaBarang = namaBarang;
        this.harga = harga;
        this.jumlah = jumlah;
        this.status = status;
    }
    
    
}
