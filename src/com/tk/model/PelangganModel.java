package com.tk.model;

import java.util.Objects;

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

    public PelangganModel() {
    }

    public PelangganModel(int idPelanggan, String namaPelanggan, String alamat, String noHp) {
        this.idPelanggan = idPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.alamat = alamat;
        this.noHp = noHp;
    }

    @Override
    public String toString() {
        return "PelangganModel{"
                + "idPelanggan=" + idPelanggan
                + ", namaPelanggan='" + namaPelanggan + '\''
                + ", alamat='" + alamat + '\''
                + ", noHp='" + noHp + '\''
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
        PelangganModel that = (PelangganModel) o;
        return idPelanggan == that.idPelanggan
                && Objects.equals(namaPelanggan, that.namaPelanggan)
                && Objects.equals(alamat, that.alamat)
                && Objects.equals(noHp, that.noHp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPelanggan, namaPelanggan, alamat, noHp);
    }

}
