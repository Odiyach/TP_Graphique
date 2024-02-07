package sio.tp4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

import sio.tp4.Tools.ConnexionBDD;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TP4Controller implements Initializable {

    ConnexionBDD maCnx;
    GraphiqueController graphiqueController;
    HashMap<String, Integer> datasGraphique1;
    XYChart.Series<String, Number> serieGraph1;
    XYChart.Series<String, Number> serieGraph3;
    XYChart.Series<String, Number> serieGraph4;
    @FXML
    private Button cmdGraph1;
    @FXML
    private Button cmdGraph2;
    @FXML
    private Button cmdGraph3;
    @FXML
    private Button cmdGraph4;
    @FXML
    private AnchorPane apGraph1;
    @FXML
    private AnchorPane apGraph2;
    @FXML
    private AnchorPane apGraph3;
    @FXML
    private AnchorPane apGraph4;
    @FXML
    private Label lblTitre;
    @FXML
    private LineChart graph1;
    @FXML
    private PieChart graph2;
    @FXML
    private BarChart graph3;
    @FXML
    private BarChart graph4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblTitre.setText("TP4 : Graphique n°1");
        apGraph1.toFront();

        try {
            maCnx = new ConnexionBDD(); // Pour se connecter a la bdd

            graphiqueController = new GraphiqueController();


            graphique1();


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    public void menuClicked(Event event) throws SQLException {
        if (event.getSource() == cmdGraph1) {
            lblTitre.setText("TP4 : Graphique n°1");
            apGraph1.toFront();

        }
        if (event.getSource() == cmdGraph2) {
            lblTitre.setText("TP4 : Graphique n°2");
            apGraph2.toFront();
            graphique2();

        }
        if (event.getSource() == cmdGraph3) {
            lblTitre.setText("TP4 : Graphique n°3");
            apGraph3.toFront();
            graphique3();

        }
        if (event.getSource() == cmdGraph4) {
            lblTitre.setText("TP4 : Graphique n°4");
            apGraph4.toFront();
            graphique4();

        }
    }

    @FXML
    public void graphique1() throws SQLException {

        graph1.getData().clear();

        serieGraph1 = new XYChart.Series<>();
        serieGraph1.setName("Nom des magazines");

        HashMap<String, Integer> donnees = graphiqueController.getDataGraphique1();

        for (String nomMag : donnees.keySet()) {
            serieGraph1.getData().add(new XYChart.Data<>(nomMag, donnees.get(nomMag)));
        }
        graph1.getXAxis().setTickLabelRotation(360);
        graph1.getData().add(serieGraph1);
    }


    public void graphique2() throws SQLException {
        graph2.getData().clear();
        serieGraph1 = new XYChart.Series<>();


        ObservableList datas = FXCollections.observableArrayList();
        HashMap<String, Integer> datasGraphique2 = graphiqueController.getDataGraphique2();

        for (String valeur : datasGraphique2.keySet()) {
            datas.add(new PieChart.Data(valeur, datasGraphique2.get(valeur)));
        }
        graph2.setData(datas);

        /*for (PieChart.Data entry : graph2.getData()) {
            Tooltip t = new Tooltip(entry.getPieValue() + " : " + entry.getName());
            t.setStyle("-fx-background-color:#3D9AA");
            Tooltip.install(entry.getNode(), t);
        }*/
    }


    // Methode avec HashMap
    public void graphique3() throws SQLException
    {
        HashMap<String, HashMap<String, Integer>> donnees = graphiqueController.getDataGraphique3();

        graph3.getData().clear();

        for (String nomAction : donnees.keySet())
        {
            serieGraph3 = new XYChart.Series<>();
            serieGraph3.setName(nomAction);

            HashMap<String, Integer> graph = donnees.get(nomAction);
            for (String nomPigiste : graph.keySet()) {
                int nombreArticles = graph.get(nomPigiste);
                serieGraph3.getData().add(new XYChart.Data<>(nomPigiste, nombreArticles));
}
            graph3.getData().add(serieGraph3);
        }
    }

    public void graphique4() throws SQLException {
        graph4.getData().clear();
        HashMap<String,Double> dataGraphique4 = graphiqueController.getDataGraphique4();
        serieGraph4 = new XYChart.Series<>();
        serieGraph4.setName("Nom des pigistes");
        for (String nomPig : dataGraphique4.keySet())
        {
            serieGraph4.getData().add(new XYChart.Data<>(nomPig, dataGraphique4.get(nomPig)));
        }
        graph4.getXAxis().setTickLabelRotation(360);


        graph4.getData().add(serieGraph4);
        /*for (PieChart.Data entry : graph2.getData())
        {
            Tooltip t = new Tooltip(entry.getPieValue() + " : " + entry.getName());
            t.setStyle("-fx-background-color:#3D9ADA");
            Tooltip.install(entry.getNode(), t);
        }*/
    }


}
