/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.pojo;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author mp
 */
public class Alue {

    private Integer alueId;
    private String nimi;
    private List<Ketju> ketjut;
    private Timestamp aika;

    //alueId, nimi, ketjut, aika
    public Alue(Integer alueId, String nimi, List<Ketju> ketjut, Timestamp aika) {
        this.alueId = alueId;
        this.nimi = nimi;
        this.ketjut = ketjut;
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

    public List<Ketju> getKetjut() {
        return ketjut;
    }

    public void addKetjut(Collection<Ketju> ketjut) {
        this.ketjut.addAll(ketjut);
    }

    public void setKetjut(List<Ketju> ketjut) {
        this.ketjut = ketjut;
    }

    public int getLkm() {
        return ketjut.size();
    }
}
