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
public class Viesti {

    private Integer viestiId;
    private Ketju ketju;
    private Kayttaja kayttaja;
    private Timestamp aika;
    private String teksti;

    public Integer getViestiId() {
        return viestiId;
    }

    public void setViestiId(Integer viestiId) {
        this.viestiId = viestiId;
    }

    public Ketju getKetju() {
        return ketju;
    }

    public void setKetju(Ketju ketju) {
        this.ketju = ketju;
    }

    public Kayttaja getKayttaja() {
        return kayttaja;
    }

    public void setKayttaja(Kayttaja kayttaja) {
        this.kayttaja = kayttaja;
    }

    public Timestamp getAika() {
        return aika;
    }

    public void setAika(Timestamp aika) {
        this.aika = aika;
    }

    public String getTeksti() {
        return teksti;
    }

    public void setTeksti(String teksti) {
        this.teksti = teksti;
    }
    
    
}
