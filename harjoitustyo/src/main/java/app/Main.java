package app;

import app.pojo.Alue;
import app.pojo.Ketju;
import app.pojo.Viesti;
import app.pojo.ViestiDao;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

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
        List<Alue> alueet = dao.getAlueet();

        get("/index", (req, res) -> {
            return "<h1>Index page</h1>"
                    + "<br/>"
                    + "<h3><a href=\"alueet\">Alueet</a></h3>";
        });
//        get("/index", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("alueet", alueet);
//
//            return new ModelAndView(map, "index");
//        }, new ThymeleafTemplateEngine());

        get("/alueet", (req, res) -> {
            String a = "<h2>Otsikko</h2>"
                    + "<br>"
                    + "<h3><a href=\"/index\">Takaisin</a></h3>"
                    + "<br>";
            for (Alue alue : alueet) {
                String alueenNimi = alue.getNimi();
                int alueenId = alue.getAlueId();
                a += "<a href=\"/alueet/" + alueenId + "\">" + alueenNimi + "</a>" + "<br/>";
            }
            return a
                    + "<form method=\"POST\" action=\"/alueet\">\n"
                    + "Uusi alue:<br/>\n"
                    + "<input type=\"text\" name=\"subforum\"/><br/>\n"
                    + "<input type=\"submit\" value=\"Lisää alue\"/>"
                    + "</form>";
        });

        post("/alueet", (req, res) -> {
            String alue = req.queryParams("subforum");

            //tähän daon metodi joka lisää alueen;
            return "Alue: '" + alue + "' lisätty foorumiin!"
                    + "<br/>"
                    + "<h3><a href=\"alueet\">Eteenpäin</a></h3>";
        });

        // Alueen ketjut
        get("/alueet/:alue", (req, res) -> {
            int alue = Integer.parseInt(req.params("alue"));
            List<Ketju> ketjut = dao.getKetjut(alue, 0);
            String k = "<h2>Otsikko</h2>"
                    + "<br>"
                    + "<h3><a href=\"/alueet\">Takaisin</a></h3>"
                    + "<br>";
            for (Ketju ketju : ketjut) {
                String ketjunAvaus = ketju.getAvaus();
                int ketjuId = ketju.getKetjuId();
                k += "<a href=\"/alueet/" + alue + "/" + ketjuId + "\">" + ketjunAvaus + "</a>" + "<br/>";
            }
            return k
                    + "<form method=\"POST\" action=\"/alueet/" + alue + "\">\n"
                    + "Uusi ketju:<br/>\n"
                    + "<input type=\"text\" name=\"thread\"/><br/>\n"
                    + "<input type=\"submit\" value=\"Lisää ketju\"/>"
                    + "</form>";
        });

        post("/alueet/:alue", (req, res) -> {
            String ketju = req.queryParams("thread");
            int alue = Integer.parseInt(req.params("alue"));

            //tähän daon metodi joka lisää ketjun;
            return "Ketju: '" + ketju + "' lisätty alueeseen!"
                    + "<br/>"
                    + "<h3><a href=\"/alueet/" + alue + "\">Eteenpäin</a></h3>";
        });

        // Kejtun viestit
        get("/alueet/:alue/:ketju", (req, res) -> {
            int alue = Integer.parseInt(req.params("alue"));
            int ketju = Integer.parseInt(req.params("ketju"));
            List<Viesti> viestit = dao.getViestit(ketju, 0);

            String v = "<h2>Otsikko</h2>"
                    + "<br>"
                    + "<h3><a href=\"/alueet/" + alue + "\">Takaisin</a></h3>"
                    + "<br>";
            for (Viesti viesti : viestit) {
                String viestinTeksti = viesti.getTeksti();
                String kayttajaNimi = viesti.getKayttajaNimi();
                v += kayttajaNimi + ": " + viestinTeksti + "<br/>";
            }
            return v
                    + "<form method=\"POST\" action=\"/alueet/" + alue + "/" + ketju + "\">\n"
                    + "Nimi:<br/>\n"
                    + "<input type=\"text\" name=\"kayttaja\"/><br/>\n"
                    + "Viesti:<br/>\n"
                    + "<input type=\"text\" name=\"viesti\"/><br/>\n"
                    + "<input type=\"submit\" value=\"Lisää viesti\"/>"
                    + "</form>";
        });

        post("/alueet/:alue/:ketju", (req, res) -> {
            String viesti = req.queryParams("viesti");
            String kayttaja = req.queryParams("kayttaja");
            int alue = Integer.parseInt(req.params("alue"));
            int ketju = Integer.parseInt(req.params("ketju"));
            int kayttajaId = dao.getKayttajaId(kayttaja);
            if (kayttajaId == -1) {
                //mitä tässä?
            }
            dao.setViesti(ketju, kayttajaId, viesti);
            return "Viestisi: '" + viesti + "' lisätty keskusteluun!"
                    + "<br/>"
                    + "<h3><a href=\"/alueet/" + alue + "/" + ketju + "\">Eteenpäin</a></h3>";
        });
        //dao.connectionClose();
    }
}
