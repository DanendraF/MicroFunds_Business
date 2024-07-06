package DashboardPengusaha.FormBusiness;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private TextField OwnerName;

    @FXML
    private TextField BusinessName;

    @FXML
    private TextField Location;

    @FXML
    private DatePicker Est;

    @FXML
    private ChoiceBox<String> category;

    @FXML
    private TextField Description;

    @FXML
    private Button submitButton;

    @FXML
    private Button backButton;

    private void loadPage(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions, e.g., show an error message
        }
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        loadPage("/DashboardPengusaha/FXMLBusinessProfile.fxml");
    }

    private void saveDataToXML(String owner, String business, String location, String estDate, String cat, String description) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            // Root element
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("business");
            document.appendChild(root);

            // Owner element
            Element ownerElement = document.createElement("owner");
            ownerElement.appendChild(document.createTextNode(owner));
            root.appendChild(ownerElement);

            // Business element
            Element businessElement = document.createElement("businessName");
            businessElement.appendChild(document.createTextNode(business));
            root.appendChild(businessElement);

            // Location element
            Element locationElement = document.createElement("location");
            locationElement.appendChild(document.createTextNode(location));
            root.appendChild(locationElement);

            // EstDate element
            Element estDateElement = document.createElement("estDate");
            estDateElement.appendChild(document.createTextNode(estDate));
            root.appendChild(estDateElement);

            // Category element
            Element categoryElement = document.createElement("category");
            categoryElement.appendChild(document.createTextNode(cat));
            root.appendChild(categoryElement);

            // Description element
            Element descriptionElement = document.createElement("description");
            descriptionElement.appendChild(document.createTextNode(description));
            root.appendChild(descriptionElement);

            // Create the XML file
            // Transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("business_data.xml"));

            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSubmitAction(ActionEvent event) {
        String owner = OwnerName.getText();
        String business = BusinessName.getText();
        String location = Location.getText();
        String estDate = Est.getValue() != null ? Est.getValue().toString() : "";
        String cat = category.getValue();
        String description = Description.getText();

        // Memeriksa apakah ada data yang kosong
        if (owner.isEmpty() || business.isEmpty() || location.isEmpty() || estDate.isEmpty() || cat == null || description.isEmpty()) {
            // Tampilkan alert data tidak lengkap
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Data Tidak Lengkap");
            alert.setHeaderText(null);
            alert.setContentText("Mohon lengkapi semua data sebelum submit!");
            alert.showAndWait();
        } else {
            // Simpan data ke XML
            saveDataToXML(owner, business, location, estDate, cat, description);

            // Success
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Pendaftaran Berhasil");
            alert.setHeaderText(null);
            alert.setContentText("Data telah berhasil disubmit!");
            alert.showAndWait();
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inisialisasi ChoiceBox dengan beberapa kategori
        category.getItems().addAll("Technology", "Finance", "Retail", "Healthcare", "Education");
    }
}
