package com.tk.model;

import java.util.Objects;

/**
 *
 * @author STNVC
 */
public class PemasokModel {

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
     * @return the namaPemasok
     */
    public String getNamaPemasok() {
        return namaPemasok;
    }

    /**
     * @param namaPemasok the namaPemasok to set
     */
    public void setNamaPemasok(String namaPemasok) {
        this.namaPemasok = namaPemasok;
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
    private int idPemasok;
    private String namaPemasok;
    private String alamat;
    private String noHp;

    public PemasokModel() {
    }

    public PemasokModel(int idPemasok, String namaPemasok, String alamat, String noHp) {
        this.idPemasok = idPemasok;
        this.namaPemasok = namaPemasok;
        this.alamat = alamat;
        this.noHp = noHp;
    }
    
    @Override
    public String toString() {
        return "PemasokModel{" +
                "idPemasok=" + idPemasok +
                ", namaPemasok='" + namaPemasok + '\'' +
                ", alamat='" + alamat + '\'' +
                ", noHp='" + noHp + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PemasokModel that = (PemasokModel) o;
        return idPemasok == that.idPemasok &&
                Objects.equals(namaPemasok, that.namaPemasok) &&
                Objects.equals(alamat, that.alamat) &&
                Objects.equals(noHp, that.noHp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPemasok, namaPemasok, alamat, noHp);
    }
}
