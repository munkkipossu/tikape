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

    private int viestiId;
    private int ketjuId;
    private String kayttajaNimi;
    private Timestamp aika;
    private String teksti;

    //id, ketju, kayttaja, aika, teksti
    public Viesti(int viestiId, int ketjuId, String kayttajaNimi, Timestamp aika, String teksti) {
        this.viestiId = viestiId;
        this.ketjuId = ketjuId;
        this.kayttajaNimi = kayttajaNimi;
        this.aika = aika;
        this.teksti = teksti;
    }

    public int getKetjuId() {
        return ketjuId;
    }

    public void setKetjuId(int ketjuId) {
        this.ketjuId = ketjuId;
    }

    public Integer getViestiId() {
        return viestiId;
    }

    public void setViestiId(int viestiId) {
        this.viestiId = viestiId;
    }

    public String getKayttajaNimi() {
        return kayttajaNimi;
    }

    public void setKayttajaNimi(String kayttajaNimi) {
        this.kayttajaNimi = kayttajaNimi;
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
