/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mp
 */
public class Kayttaja {

    private int kayttajaNumero;
    private String nimi;
    private List<Viesti> viestit;

    public Kayttaja(int kayttajaNumero, String nimi) {
        this.kayttajaNumero = kayttajaNumero;
        this.nimi = nimi;
        this.viestit = new ArrayList();
    }

    public Kayttaja(int kayttajaNumero, String nimi, List<Viesti> viestit) {
        this.kayttajaNumero = kayttajaNumero;
        this.nimi = nimi;
        this.viestit = viestit;
    }

    public int getKayttajaNumero() {
        return kayttajaNumero;
    }

    public void setKayttajaNumero(int kayttajaNumero) {
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
