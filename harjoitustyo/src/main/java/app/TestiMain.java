package app;

import app.pojo.Alue;
import app.pojo.Ketju;
import app.pojo.Viesti;
import app.pojo.ViestiDao;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;
import static spark.Spark.*;

public class TestiMain {

    public static void main(String[] args) throws Exception {
        ViestiDao dao = new ViestiDao("testi.db");
        List<Alue> alueet = dao.getAlueet();

//        Connection connection = DriverManager.getConnection("jdbc:sqlite:testi.db");
//
//        Statement statement = connection.createStatement();
//
//        ResultSet resultSet = statement.executeQuery("SELECT 1");
//
//        if(resultSet.next()) {
//            System.out.println("Hei tietokantamaailma!");
//        } else {
//            System.out.println("Yhteyden muodostaminen epäonnistui.");
//        }
//        
//      index(pääsivu) -> alue -> ketju
        get("/index", (req, res) -> {
            return "<h1>Index page</h1>"
                    + "<br/>"
                    + "<h3><a href=\"alueet\">Alueet</a></h3>";
        });

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
            List<Ketju> ketjut = dao.getKetjut(alue, 0);;
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
                String alueenNimi = viesti.getTeksti();
                v += alueenNimi + "<br/>";
            }
            return v
                    + "<form method=\"POST\" action=\"/alueet/" + alue + "/" + ketju + "\">\n"
                    + "Viesti:<br/>\n"
                    + "<input type=\"text\" name=\"viesti\"/><br/>\n"
                    + "</form>";
        });

        post("/alueet/:alue/:ketju", (req, res) -> {
            String viesti = req.queryParams("viesti");
            int alue = Integer.parseInt(req.params("alue"));
            int ketju = Integer.parseInt(req.params("ketju"));

            dao.setViesti(ketju, 1, viesti);
            return "Viestisi: '" + viesti + "' lisätty keskusteluun!"
                    + "<br/>"
                    + "<h3><a href=\"/alueet/" + alue + "/" + ketju + "\">Eteenpäin</a></h3>";
        });

        /*
         for (Alue alue : alueet) {
         String alueenNimi = linkify(alue.getNimi());
         get("/" + alueenNimi, (req, res) -> {
         return "";
         });
         }
         */
//        return "<form method=\"POST\" action=\"/alue1\">\n"
//                + "Lähettäjä:<br/>\n"
//                + "<input type=\"text\" name=\"nimi\"/><br/>\n"
//                + "<input type=\"submit\" value=\"Lisää ketju\"/>\n"
//                + "</form>";
//        post("/alue1", (req, res) -> {
//            String nimi = req.queryParams("nimi");
//            return "Uusi ketju lisätty: " + nimi;
//        });
        //dao.connectionClose();
    }

    public static String linkify(String str) {
        String rv = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        rv = pattern.matcher(rv).replaceAll("");
        return rv.replace(' ', '-').replace('#', '-').replace('/', '-').replace('&', '-');
    }
}
