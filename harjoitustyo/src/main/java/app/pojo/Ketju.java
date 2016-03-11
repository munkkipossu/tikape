/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.pojo;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author mp
 */
public class Ketju {

    private int ketjuId;
    private int alueId;
    private List<Viesti> viestit;
    private String avaus;
    private Timestamp aika;

    public Ketju(int ketjuId, int alue, List<Viesti> viestit, String avaus, Timestamp aika) {
        this.ketjuId = ketjuId;
        this.alueId = alue;
        this.viestit = viestit;
        this.avaus = avaus;
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

    public List<Viesti> getViestit() {
        return viestit;
    }

    public void setViestit(List<Viesti> viestit) {
        this.viestit = viestit;
    }

    public String getAvaus() {
        return avaus;
    }

    public void setAvaus(String avaus) {
        this.avaus = avaus;
    }

    public int getLkm() {
        return viestit.size();
    }
}
