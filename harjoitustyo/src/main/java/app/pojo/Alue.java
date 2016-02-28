/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.pojo;

import java.util.List;

/**
 *
 * @author mp
 */
public class Alue {
    private Integer alueId;
    private String nimi;
    private List<Ketju> ketjut;

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

    public void setKetjut(List<Ketju> ketjut) {
        this.ketjut = ketjut;
    }
}
