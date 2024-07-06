package DashboardPengusaha;

import DashboardPengusaha.FinancialStatsData.FinancialBulanan;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FXMLFinancialStatsController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private LineChart<String, Number> stats;

    @FXML
    private TextField income;

    @FXML
    private Button Dashboard;

    @FXML
    private Button add;

    @FXML
    private Button SBBusProfile;

    @FXML
    private Button SBFinancialStats;

    @FXML
    private Button SBPayment;

    private List<FinancialBulanan> pendapatanList = new ArrayList<>();
    private int currentMonthIndex = 1;

    public List<FinancialBulanan> getPendapatanList() {
        return pendapatanList;
    }
    

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadPage(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();

            Stage stage = (Stage) SBBusProfile.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions, e.g., show an error message
        }
    }

    @FXML
    private void handleDashboardAction(ActionEvent event) {
        loadPage("/DashboardPengusaha/FXMLDashboard.fxml");
    }

    @FXML
    private void handleSBBusProfileAction(ActionEvent event) {
        loadPage("/DashboardPengusaha/FXMLBusinessProfile.fxml");
    }

    @FXML
    private void handleSBFinancialStatsAction(ActionEvent event) {
        loadPage("/DashboardPengusaha/FXMLFinancialStats.fxml");
    }

    @FXML
    private void handleSBPaymentAction(ActionEvent event) {
        loadPage("/DashboardPengusaha/FXMLPayment.fxml");
    }

    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        String inputText = income.getText().trim();

        if (inputText.isEmpty()) {
            showAlert("Error", "Input pendapatan tidak boleh kosong.");
            return;
        }

        int pendapatan = Integer.parseInt(inputText);

        if (pendapatan <= 0) {
            showAlert("Error", "Pendapatan harus lebih dari 0.");
            return;
        }

        String bulan = getMonthName(currentMonthIndex);
        XYChart.Series<String, Number> series = findOrCreateSeries("Pendapatan Bulanan");
        series.getData().add(new XYChart.Data<>(bulan, pendapatan));

        pendapatanList.add(new FinancialBulanan(bulan, pendapatan));

        income.clear();
        currentMonthIndex++;

        saveDataToXML();
    }

    private XYChart.Series<String, Number> findOrCreateSeries(String seriesName) {
        for (XYChart.Series<String, Number> series : stats.getData()) {
            if (series.getName().equals(seriesName)) {
                return series;
            }
        }
        XYChart.Series<String, Number> newSeries = new XYChart.Series<>();
        newSeries.setName(seriesName);
        stats.getData().add(newSeries);
        return newSeries;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDataFromXML();
        displayDataOnChart();
    }

    private void loadDataFromXML() {
        try {
            File xmlFile = new File("pendapatan.xml");
            if (xmlFile.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("data");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String bulan = element.getElementsByTagName("bulan").item(0).getTextContent();
                        int pendapatan = Integer.parseInt(element.getElementsByTagName("pendapatan").item(0).getTextContent());
                        pendapatanList.add(new FinancialBulanan(bulan, pendapatan));
                        currentMonthIndex = i + 2; // Update currentMonthIndex untuk bulan berikutnya
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void displayDataOnChart() {
        XYChart.Series<String, Number> series = findOrCreateSeries("Pendapatan Bulanan");

        for (FinancialBulanan data : pendapatanList) {
            series.getData().add(new XYChart.Data<>(data.getBulan(), data.getPendapatan()));
        }
    }

    private void saveDataToXML() {
        try {
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileWriter("pendapatan.xml"));

            writer.writeStartDocument();
            writer.writeStartElement("pendapatanList");

            for (FinancialBulanan data : pendapatanList) {
                writer.writeStartElement("data");

                writer.writeStartElement("bulan");
                writer.writeCharacters(data.getBulan());
                writer.writeEndElement();

                writer.writeStartElement("pendapatan");
                writer.writeCharacters(String.valueOf(data.getPendapatan()));
                writer.writeEndElement();

                writer.writeEndElement();
            }

            writer.writeEndElement();
            writer.writeEndDocument();

            writer.flush();
            writer.close();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private String getMonthName(int monthIndex) {
        String[] monthNames = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        return monthNames[monthIndex - 1];
    }
}
