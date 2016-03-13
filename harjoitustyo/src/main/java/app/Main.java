package app;

import app.pojo.Alue;
import app.pojo.Ketju;
import app.pojo.Viesti;
import app.pojo.ViestiDao;
import java.util.List;
import static spark.Spark.*;

public class Main {

    public static void main(String[] args) throws Exception {

        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        // käytetään oletuksena paikallista sqlite-tietokantaa
        String jdbcOsoite = "jdbc:sqlite:testi.db";
        // jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        }

        ViestiDao dao = new ViestiDao(jdbcOsoite);

        get("/", (req, res) -> {
            res.redirect("/alueet");
            return "Redirect ei toiminut. <a href=\"alueet\">Sinun pitäisi olla täällä</a>";
        });

        get("/alueet", (req, res) -> {
            String a = "<h2>Otsikko</h2>"
                    + "<br>";
            List<Alue> alueet = dao.getAlueet();
            for (Alue alue : alueet) {
                String alueenNimi = alue.getNimi();
                int alueenId = alue.getAlueId();
                int alueLkm = alue.getLkm();
                String aika = alue.getAika().toString();
                a += "<a href=\"/alueet/" + alueenId + "/show/0\">" + alueenNimi + ": " + alueLkm + " viestiä, viimeksi päivitetty " + aika + "</a>" + "<br/>";
            }
            return a
                    + giveHTMLRows(3)
                    + "<form method=\"POST\" action=\"/alueet\">\n"
                    + "Uusi alue:<br/>\n"
                    + "<input type=\"text\" name=\"subforum\"/><br/>\n"
                    + "<input type=\"submit\" value=\"Lisää alue\"/>"
                    + "</form>";
        });

        post("/alueet", (req, res) -> {
            String alue = req.queryParams("subforum");

            dao.addAlue(alue);
            return "Alue: '" + alue + "' lisätty foorumiin!"
                    + "<br/>"
                    + "<h3><a href=\"alueet\">Eteenpäin</a></h3>";
        });

        get("/alueet/:alue/show/:offset", (req, res) -> {
            int alue = Integer.parseInt(req.params("alue"));
            int offset = Integer.parseInt(req.params("offset"));
            List<Ketju> ketjut = dao.getKetjut(alue, offset);
            String k = "<h2>Otsikko</h2>"
                    + "<br>"
                    + "<h3><a href=\"/alueet\">Takaisin</a></h3>"
                    + "<br>";
            for (Ketju ketju : ketjut) {
                String ketjunAvaus = ketju.getAvaus();
                int ketjuId = ketju.getKetjuId();
                int ketjuLkm = ketju.getLkm();
                String aika = ketju.getAika().toString();
                k += "<a href=\"/alueet/" + alue + "/" + ketjuId + "/show/0\">" + ketjunAvaus + ": " + ketjuLkm + " viestiä, viimeksi päivitetty " + aika + "</a>" + "<br/>";
            }
            k += giveHTMLRows(2);
            if (offset >= 10) {
                k += "&emsp;&emsp;<a href=\"/alueet/" + alue + "/show/" + (offset - 10) + "\">&larr; Edelliset 10</a>";
            }
            if (ketjut.size() == 10) {
                k += "&emsp;&emsp;"
                        + "<a href=\"/alueet/" + alue + "/show/" + (offset + 10) + "\">Seuraavat 10 &rarr;</a>";
            }
            k += giveHTMLRows(3);
            k += "<form method=\"POST\" action=\"/alueet/" + alue + "/show/0\">\n"
                    + "Uusi keskustelunavaus:<br/>\n"
                    + "<input type=\"text\" name=\"thread\"/><br/>\n"
                    + "<input type=\"submit\" value=\"Lisää ketju\"/>"
                    + "</form>"
                    + "<br/><h3>";
            return k;
        });

        post("/alueet/:alue/show/:offset", (req, res) -> {
            String ketju = req.queryParams("thread");
            int alue = Integer.parseInt(req.params("alue"));

            dao.addKetju(alue, ketju);
            return "Ketju: '" + ketju + "' lisätty alueeseen!"
                    + "<br/>"
                    + "<h3><a href=\"/alueet/" + alue + "/show/0\">Eteenpäin</a></h3>";
        });

        get("/alueet/:alue/:ketju/show/:offset", (req, res) -> {
            int alue = Integer.parseInt(req.params("alue"));
            int ketju = Integer.parseInt(req.params("ketju"));
            int offset = Integer.parseInt(req.params("offset"));
            List<Viesti> viestit = dao.getViestit(ketju, offset);

            String v = "<h2>Otsikko</h2>"
                    + "<br>"
                    + "<h3><a href=\"/alueet/" + alue + "/show/0\">Takaisin</a></h3>"
                    + "<br>";
            for (Viesti viesti : viestit) {
                String viestinTeksti = viesti.getTeksti();
                String kayttajaNimi = viesti.getKayttajaNimi();
                v += kayttajaNimi + ": " + viestinTeksti + "<br/>";
            }
            v += giveHTMLRows(2);
            if (offset >= 10) {
                v += "&emsp;&emsp;<a href=\"/alueet/" + alue + "/" + ketju + "/show/" + (offset - 10) + "\">&larr; Edelliset 10</a>";
            }
            if (viestit.size() == 10) {
                v += "&emsp;&emsp;"
                        + "<a href=\"/alueet/" + alue + "/" + ketju + "/show/" + (offset + 10) + "\">Seuraavat 10 &rarr;</a>";
            }
            v += giveHTMLRows(3);
            v += "<form method=\"POST\" action=\"/alueet/" + alue + "/" + ketju + "/show/0\">\n"
                    + "Nimi:<br/>\n"
                    + "<input type=\"text\" name=\"kayttaja\"/><br/>\n"
                    + "Viesti:<br/>\n"
                    + "<input type=\"text\" name=\"viesti\"/><br/>\n"
                    + "<input type=\"submit\" value=\"Lisää viesti\"/>"
                    + "</form>";
            return v + "";
        });

        post("/alueet/:alue/:ketju/show/:offset", (req, res) -> {
            String viesti = req.queryParams("viesti");
            String kayttaja = req.queryParams("kayttaja");
            int alue = Integer.parseInt(req.params("alue"));
            int ketju = Integer.parseInt(req.params("ketju"));
            int kayttajaId = dao.getKayttajaId(kayttaja);
            if (kayttajaId == -1) {
                kayttajaId = dao.addKayttaja(kayttaja);
            }
            dao.addViesti(ketju, kayttajaId, viesti);
            return "Viestisi: '" + viesti + "' lisätty keskusteluun!"
                    + "<br/>"
                    + "<h3><a href=\"/alueet/" + alue + "/" + ketju + "/show/0\">Eteenpäin</a></h3>";
        });
        //dao.connectionClose();
    }

    private static String giveHTMLRows(int i) {
        String s = "";
        while (i > 0) {
            s += "<br/>";
            i--;
        }
        return s;
    }
}
