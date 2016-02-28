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
public class Kayttaja {
    private Integer kayttajaNumero;
    private String nimi;
    private List<Viesti> viestit;

    public Integer getKayttajaNumero() {
        return kayttajaNumero;
    }

    public void setKayttajaNumero(Integer kayttajaNumero) {
        this.kayttajaNumero = kayttajaNumero;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public List<Viesti> getViestit() {
        return viestit;
    }

    public void setViestit(List<Viesti> viestit) {
        this.viestit = viestit;
    }

}
