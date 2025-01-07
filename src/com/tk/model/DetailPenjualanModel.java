/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tk.model;

import java.math.BigDecimal;

/**
 *
 * @author STNVC
 */
public class DetailPenjualanModel {

    private int idDpenjualan;
    private int idPenjualan;
    private int idBarang;
    private double harga;
    private int jumlah;
    private double subtotal;

    // Constructor untuk membuat detail penjualan baru (tanpa id)
    public DetailPenjualanModel(int idPenjualan, int idBarang, double harga, int jumlah) {
        this.idPenjualan = idPenjualan;
        this.idBarang = idBarang;
        this.harga = harga;
        this.jumlah = jumlah;
        this.calculateSubtotal();
    }

    // Constructor lengkap (untuk data yang diambil dari database)
    public DetailPenjualanModel(int idDpenjualan, int idPenjualan, int idBarang, double harga, int jumlah, double subtotal) {
        this.idDpenjualan = idDpenjualan;
        this.idPenjualan = idPenjualan;
        this.idBarang = idBarang;
        this.harga = harga;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

    // Getters and Setters
    public int getIdDpenjualan() {
        return idDpenjualan;
    }

    public void setIdDpenjualan(int idDpenjualan) {
        this.idDpenjualan = idDpenjualan;
    }

    public int getIdPenjualan() {
        return idPenjualan;
    }

    public void setIdPenjualan(int idPenjualan) {
        this.idPenjualan = idPenjualan;
    }

    public int getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
        calculateSubtotal();
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
        calculateSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    // Method untuk menghitung subtotal
    public void calculateSubtotal() {
        this.subtotal = this.harga * this.jumlah;
    }

}
