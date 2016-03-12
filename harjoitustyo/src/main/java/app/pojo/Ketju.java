/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.pojo;

import java.sql.Timestamp;

/**
 *
 * @author mp
 */
public class Ketju {

    private int ketjuId;
    private int alueId;
    private String avaus;
    private int lkm;
    private Timestamp aika;

    public Ketju(int ketjuId, int alueId, String avaus, int lkm, Timestamp aika) {
        this.ketjuId = ketjuId;
        this.alueId = alueId;
        this.avaus = avaus;
        this.lkm = lkm;
        this.aika = aika;
    }

    public Integer getKetjuId() {
        return ketjuId;
    }

    public void setKetjuId(Integer ketjuId) {
        this.ketjuId = ketjuId;
    }

    public int getAlueId() {
        return alueId;
    }

    public void setAlueId(int alueId) {
        this.alueId = alueId;
    }

    public String getAvaus() {
        return avaus;
    }

    public void setAvaus(String avaus) {
        this.avaus = avaus;
    }

    public int getLkm() {
        return lkm;
    }
}
