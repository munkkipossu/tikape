package app;

import app.pojo.Alue;
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
            return "";
        });

        for (Alue alue : alueet) {
            String alueenNimi = linkify(alue.getNimi());
            get("/" + alueenNimi, (req, res) -> {
                return "";
            });
        }
//        return "<form method=\"POST\" action=\"/alue1\">\n"
//                + "Lähettäjä:<br/>\n"
//                + "<input type=\"text\" name=\"nimi\"/><br/>\n"
//                + "<input type=\"submit\" value=\"Lisää ketju\"/>\n"
//                + "</form>";
//        post("/alue1", (req, res) -> {
//            String nimi = req.queryParams("nimi");
//            return "Uusi ketju lisätty: " + nimi;
//        });
        dao.connectionClose();
    }

    public static String linkify(String str) {
        String rv = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        rv = pattern.matcher(rv).replaceAll("");
        return rv.replace(' ', '-').replace('#', '-').replace('/', '-').replace('&', '-');
    }
}
