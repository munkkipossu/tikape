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
public class Alue {

    private Integer alueId;
    private String nimi;
    private int lkm;
    private Timestamp aika;

    //alueId, nimi, ketjut, aika
    public Alue(Integer alueId, String nimi, int lkm, Timestamp aika) {
        this.alueId = alueId;
        this.nimi = nimi;
        this.lkm = lkm;
        this.aika = aika;
    }

    public Integer getAlueId() {
        return alueId;
    }

    public void setAlueId(Integer alueId) {
        this.alueId = alueId;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public int getLkm() {
        return lkm;
    }

    public Timestamp getAika() {
        return aika;
    }
}
