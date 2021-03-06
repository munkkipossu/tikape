package app.pojo;

/**
 *
 * @author tkarkine
 */
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.jsoup.Jsoup;

public class ViestiDao {

    private Connection yhteys;

    public ViestiDao(String db) throws SQLException {
        try {
            System.out.println(db);
            yhteys = DriverManager.getConnection(db);
            System.out.println("Yhteys tietokantaan toimii!");
        } catch (Exception e) {
            System.out.println("Virhe " + e);
        }

    }

    public List<Alue> getAlueet() throws SQLException {
        List<Alue> alueet = new ArrayList<>();
        String kysely = "SELECT Alue.alueId, Alue.nimi, count(Viesti.viestiId), max(Viesti.aika) From Viesti "
                + "join Ketju on Viesti.ketjuId=Ketju.ketjuId "
                + "join Alue on Ketju.alueId=Alue.alueId "
                + "group by Alue.alueId order by Alue.nimi ASC";

        PreparedStatement stmt = this.yhteys.prepareStatement(kysely);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int alueId = rs.getInt(1);
            String nimi = rs.getString(2);
            int lkm = rs.getInt(3);
            String aikaTeksti = rs.getString(4);
            Timestamp aika = Timestamp.valueOf(aikaTeksti);
            Alue alue = new Alue(alueId, nimi, lkm, aika);
            alueet.add(alue);

        }
        stmt.close();
        rs.close();
        return alueet;
    }

    public List<Ketju> getKetjut(int alueId, int offSet) throws SQLException {
        List<Ketju> rivit = new ArrayList<>();

        String kysely = "SELECT Ketju.ketjuId, Ketju.avaus, count(Viesti.viestiId), max(Viesti.aika) as uusin From Viesti "
                + "join Ketju on Viesti.ketjuId=Ketju.ketjuId "
                + "join Alue on Ketju.alueId=Alue.alueId "
                + "where Alue.alueid= ? "
                + "group by Ketju.ketjuID order by uusin DESC"
                + " LIMIT 10 OFFSET ?";

        PreparedStatement stmt = yhteys.prepareStatement(kysely);
        stmt.setInt(1, alueId);

        stmt.setInt(2, offSet);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int ketjuId = rs.getInt(1);
            String avaus = rs.getString(2);
            int lkm = rs.getInt(3);
            String aikaTeksti = rs.getString(4);
            Timestamp aika = Timestamp.valueOf(aikaTeksti);
            Ketju ketju = new Ketju(ketjuId, alueId, avaus, lkm, aika);
            rivit.add(ketju);

        }
        stmt.close();
        rs.close();
        return rivit;
    }

    public List<Viesti> getViestit(int ketjuId, int offSet) throws SQLException {
        List<Viesti> rivit = new ArrayList<>();
        String kysely = "SELECT viestiId,ketjuId, aika, teksti, kayttaja.nimi  From Viesti "
                + "join kayttaja on viesti.kayttajaID=kayttaja.kayttajaid "
                + "where Viesti.ketjuId= ? "
                + "order by Viesti.aika DESC"
                + " LIMIT 10 OFFSET ?";

        PreparedStatement stmt = yhteys.prepareStatement(kysely);
        stmt.setInt(1, ketjuId);
        stmt.setInt(2, offSet);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt(1);
            //int ketju = rs.getInt(2);
            String aikaTeksti = rs.getString(3);
            String teksti = rs.getString(4);
            String kayttajaNimi = rs.getString(5);
            Timestamp aika = Timestamp.valueOf(aikaTeksti);

            rivit.add(new Viesti(id, ketjuId, kayttajaNimi, aika, teksti));
        }
        stmt.close();
        rs.close();
        return rivit;
    }

    public void addViesti(int ketjuId, int kayttaja, String teksti) throws SQLException {
        String kysely = "INSERT INTO Viesti "
                + "(ketjuId, kayttajaId, aika, teksti) "
                + "VALUES (?, ?, ?, ?)";

        PreparedStatement stmt = yhteys.prepareStatement(kysely);
        stmt.setInt(1, ketjuId);
        stmt.setInt(2, kayttaja);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdfDate.format(System.currentTimeMillis());
        stmt.setString(3, strDate);
        stmt.setString(4, Jsoup.parse(teksti).text());

        System.out.println("setting viesti: " + teksti);
        stmt.execute();
        stmt.close();
    }

    public void addAlue(String nimi) throws SQLException {
        String kysely = "INSERT INTO Alue (nimi) VALUES(?)";

        PreparedStatement stmt = yhteys.prepareStatement(kysely);
        stmt.setString(1, nimi);
        stmt.execute();
        stmt.close();
    }

    public void addKetju(int alueId, String avaus) throws SQLException {
        String kysely = "INSERT INTO Ketju (alueId, avaus) VALUES (?, ?)";

        PreparedStatement stmt = yhteys.prepareStatement(kysely);
        stmt.setInt(1, alueId);
        stmt.setString(2, avaus);
        stmt.execute();
        stmt.close();
    }

    public int addKayttaja(String nimi) throws SQLException {
        String kysely = "INSERT INTO Kayttaja (nimi) VALUES(?)";

        PreparedStatement stmt = yhteys.prepareStatement(kysely);
        stmt.setString(1, nimi);
        stmt.execute();
        stmt.close();

        return getKayttajaId(nimi);
    }

    public int getKayttajaId(String nimi) throws SQLException {
        int viite = -1;
        String kysely = "Select kayttajaId from kayttaja where nimi= ?";
        PreparedStatement stmt = yhteys.prepareStatement(kysely);
        stmt.setString(1, nimi);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            viite = rs.getInt(1);
        }
        return viite;
        // palauttaa -1 jos kayttajaa ei ole, muutoin kayttajan id;n
    }

    public void connectionClose() throws SQLException {
        yhteys.close();
    }
}
