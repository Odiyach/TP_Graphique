package sio.tp4;

import javafx.scene.chart.XYChart;
import sio.tp4.Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class GraphiqueController
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public GraphiqueController()
    {
        cnx = ConnexionBDD.getCnx();
    }




    public HashMap<String, Integer> getDataGraphique1() throws SQLException {
        HashMap<String,Integer> datas = new HashMap<>();

        ps = cnx.prepareStatement("SELECT\n" +
                "    magazine.nomMag,\n" +
                "    COUNT(article.idArticle) AS NombreArticles\n" +
                "FROM\n" +
                "    magazine\n" +
                "LEFT JOIN\n" +
                "    article ON magazine.idMag = article.numMag\n" +
                "GROUP BY\n" +
                "    magazine.nomMag;");

        rs = ps.executeQuery();
        while (rs.next())
        {
            datas.put(rs.getString(1), rs.getInt(2));
        }
        rs.close();
        ps.close();

        return datas;
    }

    public HashMap<String,Integer> getDataGraphique2() throws SQLException
    {
        HashMap<String, Integer> datas = new HashMap();

        try {
            cnx = ConnexionBDD.getCnx();
            ps = cnx.prepareStatement("SELECT specialite.nomSpe, COUNT(*) as nbPigiste from pigiste inner JOIN posseder on pigiste.idPigiste=posseder.numPig INNER JOIN specialite on posseder.numSpe=specialite.idSpe GROUP by specialite.idSpe ORDER BY `specialite`.`nomSpe` DESC");
            rs = ps.executeQuery();
            while (rs.next())
            {
                datas.put(rs.getString(1), rs.getInt(2));
            }
            rs.close();
        }catch (SQLException ex)
        {
            throw new RuntimeException();

        }

        return datas;

    }

    public HashMap<String, HashMap<String, Integer>> getDataGraphique3() throws SQLException {
        HashMap<String, HashMap<String, Integer>> datas = new HashMap<>();

        ps = cnx.prepareStatement("SELECT\n" +
                " p.nomPigiste AS NomPigiste,\n" +
                " m.nomMag AS NomMagazine,\n" +
                " COUNT(a.idArticle) AS NombreArticles\n" +
                "FROM\n" +
                " article a\n" +
                " INNER JOIN\n" +
                " magazine m ON a.numMag = m.idMag\n" +
                " INNER JOIN\n" +
                " pigiste p ON a.numPig = p.idPigiste\n" +
                "GROUP BY\n" +
                " m.nomMag, p.nomPigiste\n" +
                "ORDER BY\n" +
                " m.nomMag, p.nomPigiste;\n");
        rs = ps.executeQuery();

        while (rs.next())
        {
            String nomMagazine = rs.getString("NomMagazine");
            String nomPigiste = rs.getString("NomPigiste");
            int nombreArticles = rs.getInt("NombreArticles");

            if (!datas.containsKey(nomMagazine))
            {
                HashMap<String, Integer> datas2 = new HashMap<>();
                datas2.put(nomPigiste, nombreArticles);
                datas.put(nomMagazine, datas2);
            }
            else
            {
                datas.get(nomMagazine).put(nomPigiste, nombreArticles);
            }
        }

        rs.close();
        ps.close();

        return datas;
    }

    public HashMap<String, Double> getDataGraphique4() throws SQLException {
        HashMap<String,Double> datas = new HashMap<>();

        ps = cnx.prepareStatement("SELECT\n" +
                "    pigiste.nomPigiste AS NomPigiste,\n" +
                "    SUM(pigiste.prixFeuillet * article.nbFeuillets) AS ChiffreAffaires\n" +
                "FROM\n" +
                "    article \n" +
                "INNER JOIN\n" +
                "    pigiste  ON article.numPig = pigiste.idPigiste\n" +
                "GROUP BY\n" +
                "    pigiste.nomPigiste;\n");

        rs = ps.executeQuery();
        while (rs.next())
        {
            datas.put(rs.getString(1), rs.getDouble(2));
        }
        rs.close();
        ps.close();

        return datas;
    }

}
