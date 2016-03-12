package app;

import app.pojo.Alue;
import app.pojo.Ketju;
import app.pojo.Viesti;
import app.pojo.ViestiDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.Normalizer;
import java.util.ArrayList;
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
            return "Index page";
        });

        get("/alueet", (req, res) -> {
            String a = "";
            for (Alue alue : alueet) {
                String alueenNimi = alue.getNimi();
                a += alueenNimi + "<br/>";
            }
            return a;
        });

        // Alueen ketjut
        get("/alueet/:alue", (req, res) -> {
            List<Ketju> ketjut = alueet.get(Integer.parseInt(req.params("alue")))
                    .getKetjut();
            String k = "";
            for (Ketju ketju : ketjut) {
                String alueenNimi = ketju.getAvaus();
                k += alueenNimi + "<br/>";
            }
            return k;
        });
        
        // Kejtun viestit
        get("/alueet/:alue/:ketju", (req, res) -> {
            int alue = Integer.parseInt(req.params("alue"));
            int ketju = Integer.parseInt(req.params("ketju"));
            List<Viesti> viestit = alueet.get(alue)
                                         .getKetjut().get(ketju)
                                         .getViestit();
            
            String k = "";
            for (Viesti viesti : viestit) {
                String alueenNimi = viesti.getTeksti();
                k += alueenNimi + "<br/>";
            }
            return k
                + "<form method=\"POST\" action=\"/alueet/"+alue+"/"+ketju+"\">\n"
                + "Viesti:<br/>\n"
                + "<input type=\"text\" name=\"viesti\"/><br/>\n"
                + "</form>";
        });
        
        post("/alueet/:alue/:ketju", (req, res) -> {
            String viesti = req.queryParams("viesti");
            int alue = Integer.parseInt(req.params("alue"));
            int ketju = Integer.parseInt(req.params("ketju"));
            
            dao.setViesti(ketju, 1, viesti);
            return "";
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
