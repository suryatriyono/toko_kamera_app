/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tk.model;

import java.util.Date;

/**
 *
 * @author STNVC
 */
public class PembelianModel {

    /**
     * @return the idDpembelian
     */
    public String getIdDpembelian() {
        return idDpembelian;
    }

    /**
     * @param idDpembelian the idDpembelian to set
     */
    public void setIdDpembelian(String idDpembelian) {
        this.idDpembelian = idDpembelian;
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

    private String idDpembelian;
    private String idPembelian;
    private String idBarang;
    private double harga;
    private int jumlah;
    private double subtotal;

    public PembelianModel(String idDpembelian, String idPembelian, String idBarang, double harga, int jumlah, double subtotal) {
        this.idDpembelian = idDpembelian;
        this.idPembelian = idPembelian;
        this.idBarang = idBarang;
        this.harga = harga;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

}
