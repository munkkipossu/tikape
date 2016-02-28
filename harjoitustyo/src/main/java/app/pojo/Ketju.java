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
public class Ketju {
    
    private Integer ketjuId;
    private Alue alue;
    private List<Viesti> viestit;
    private String avaus;
    
    
    public Integer getKetjuId() {
        return ketjuId;
    }

    public void setKetjuId(Integer ketjuId) {
        this.ketjuId = ketjuId;
    }

    public Alue getAlue() {
        return alue;
    }

    public void setAlue(Alue alue) {
        this.alue = alue;
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
   
}
