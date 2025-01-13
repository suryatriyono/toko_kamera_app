package com.tk.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author STNVC
 */
public class PembelianModel {

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
     * @return the idPemasok
     */
    public int getIdPemasok() {
        return idPemasok;
    }

    /**
     * @param idPemasok the idPemasok to set
     */
    public void setIdPemasok(int idPemasok) {
        this.idPemasok = idPemasok;
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
    private int idPembelian;
    private int idPemasok;
    private Date tanggal;
    private BigDecimal totalHarga;

    public PembelianModel() {
    }

    public PembelianModel(int idPembelian, int idPemasok, Date tanggal, BigDecimal totalHarga) {
        this.idPembelian = idPembelian;
        this.idPemasok = idPemasok;
        this.tanggal = tanggal;
        this.totalHarga = totalHarga;
    }

    @Override
    public String toString() {
        return "PembelianModel{"
                + "idPembelian=" + idPembelian
                + ", idPemasok=" + idPemasok
                + ", tanggal=" + tanggal
                + ", totalHarga=" + totalHarga
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
        PembelianModel that = (PembelianModel) o;
        return idPembelian == that.idPembelian
                && idPemasok == that.idPemasok
                && Objects.equals(tanggal, that.tanggal)
                && Objects.equals(totalHarga, that.totalHarga);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPembelian, idPemasok, tanggal, totalHarga);
    }
}
