/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tk.model;

/**
 *
 * @author STNVC
 */
public class PelangganModel {

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
     * @return the namaPelanggan
     */
    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    /**
     * @param namaPelanggan the namaPelanggan to set
     */
    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    /**
     * @return the alamat
     */
    public String getAlamat() {
        return alamat;
    }

    /**
     * @param alamat the alamat to set
     */
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    /**
     * @return the noHp
     */
    public String getNoHp() {
        return noHp;
    }

    /**
     * @param noHp the noHp to set
     */
    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
    private int idPelanggan;
    private String namaPelanggan;
    private String alamat;
    private String noHp;

    public PelangganModel(int idPelanggan, String namaPelanggan, String alamat, String noHp) {
        this.idPelanggan = idPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.alamat = alamat;
        this.noHp = noHp;
    }
    
    
}
