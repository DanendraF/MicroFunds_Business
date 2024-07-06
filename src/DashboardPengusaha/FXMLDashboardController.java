package DashboardPengusaha;

import DashboardPengusaha.FinancialStatsData.FinancialBulanan;
import DashboardPengusaha.FXMLFinancialStatsController;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class FXMLDashboardController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private Button SBBusProfile;

    @FXML
    private Button SBFinancialStats;

    @FXML
    private Button SBPayment;

    @FXML
    private TableView<FinancialBulanan> income;

    @FXML
    private TableColumn<FinancialBulanan, String> bulanColumn;

    @FXML
    private TableColumn<FinancialBulanan, Integer> pendapatanColumn;

    private ObservableList<FinancialBulanan> incomeData = FXCollections.observableArrayList();

    @FXML
    private TextField Ubusiness;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Ubusiness.setEditable(false); // Set TextField to be non-editable
        loadBusinessNameFromXML();
    }

    @FXML
    private void handleSBBusProfileAction(ActionEvent event) {
        loadPage("/DashboardPengusaha/FXMLBusinessProfile.fxml");
    }

    @FXML
    private void handleSBFinancialStatsAction(ActionEvent event) {
        loadFinancialStatsData();
    }

    @FXML
    private void handleSBPaymentAction(ActionEvent event) {
        loadPage("/DashboardPengusaha/FXMLPayment.fxml");
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

    private void loadBusinessNameFromXML() {
        try {
            File xmlFile = new File("business_data.xml");
            if (xmlFile.exists()) {
                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(xmlFile);
                document.getDocumentElement().normalize();

                Node businessNode = document.getElementsByTagName("business").item(0);
                if (businessNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element businessElement = (Element) businessNode;
                    String businessName = businessElement.getElementsByTagName("businessName").item(0).getTextContent();
                    Ubusiness.setText(businessName);
                }
            } else {
                System.out.println("File XML tidak ditemukan.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadFinancialStatsData() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardPengusaha/FXMLFinancialStats.fxml"));
            Parent root = loader.load();
            FXMLFinancialStatsController statsController = loader.getController();
    
            // Ambil data dari FXMLFinancialStatsController
            List<FinancialBulanan> statsData = statsController.getPendapatanList();
    
            // Konversi List menjadi ObservableList
            ObservableList<FinancialBulanan> observableStatsData = FXCollections.observableArrayList(statsData);
    
            // Set data ke TableView income
            bulanColumn.setCellValueFactory(cellData -> cellData.getValue().bulanProperty());
            pendapatanColumn.setCellValueFactory(cellData -> cellData.getValue().pendapatanProperty().asObject());
    
            income.setItems(observableStatsData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

}
