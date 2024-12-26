package com.tk.model;

/**
 *
 * @author STNVC
 */
public class PemasokModel {

    /**
     * @return the idPemasok
     */
    public String getIdPemasok() {
        return idPemasok;
    }

    /**
     * @param idPemasok the idPemasok to set
     */
    public void setIdPemasok(String idPemasok) {
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
    private String idPemasok;
    private String namaPemasok;
    private String alamat;
    private String noHp;

    public PemasokModel(String idPemasok, String namaPemasok, String alamat, String noHp) {
        this.idPemasok = idPemasok;
        this.namaPemasok = namaPemasok;
        this.alamat = alamat;
        this.noHp = noHp;
    }
  
}
