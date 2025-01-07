/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tk.model;

/**
 *
 * @author STNVC
 */
public class KategoriModel {

    /**
     * @return the idKetegori
     */
    public String getIdKategori() {
        return idKategori;
    }

    /**
     * @param idKetegori the idKetegori to set
     */
    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    /**
     * @return the kategori
     */
    public String getKategori() {
        return kategori;
    }

    /**
     * @param kategori the kategori to set
     */
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    private String idKategori;
    private String kategori;

    public KategoriModel(String idKategori, String kategori) {
        this.idKategori = idKategori;
        this.kategori = kategori;
    }
}
