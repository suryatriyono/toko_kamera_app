package com.tk.model;

import java.util.Objects;

/**
 *
 * @author STNVC
 */
public class KategoriModel {

    /**
     * @return the idKategori
     */
    public String getIdKategori() {
        return idKategori;
    }

    /**
     * @param idKategori the idKategori to set
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

    public KategoriModel() {
    }

    public KategoriModel(String idKategori, String kategori) {
        this.idKategori = idKategori;
        this.kategori = kategori;
    }

    @Override
    public String toString() {
        return "KategoriModel{"
                + "idKategori='" + idKategori + '\''
                + ", kategori='" + kategori + '\''
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
        KategoriModel that = (KategoriModel) o;
        return Objects.equals(idKategori, that.idKategori)
                && Objects.equals(kategori, that.kategori);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idKategori, kategori);
    }
}
