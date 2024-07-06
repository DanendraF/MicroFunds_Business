package DashboardPengusaha;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLPaymentController implements Initializable {

    @FXML
    private Button Dashboard;

    @FXML
    private Button SBBusProfile;

    @FXML
    private Button SBFinancialStats;

    @FXML
    private Button SBPayment;

    @FXML
    private TextField NamaPenerima;

    @FXML
    private TextField NamaPengirim;

    @FXML
    private TextField Nominal;

    @FXML
    private Button Accept;

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
    private void handleAcceptAction(ActionEvent event) {
        String sender = NamaPengirim.getText();
        String recipient = NamaPenerima.getText();
        String amount = Nominal.getText();

        if (validateInput(sender, recipient, amount)) {
            savePaymentToXML(sender, recipient, amount);
            showAlert(AlertType.INFORMATION, "Berhasil", "Pembayaran berhasil disimpan.");
            clearFields();
        } else {
            showAlert(AlertType.WARNING, "Peringatan", "Semua data harus diisi.");
        }
    }

    private boolean validateInput(String sender, String recipient, String amount) {
        return !sender.isEmpty() && !recipient.isEmpty() && !amount.isEmpty();
    }

    private void savePaymentToXML(String sender, String recipient, String amount) {
        try {
            File xmlFile = new File("payments.xml");
            Document document;
            Element root;

            if (xmlFile.exists()) {
                // Load existing XML file
                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                document = documentBuilder.parse(xmlFile);
                root = document.getDocumentElement();
            } else {
                // Create new XML document
                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                document = documentBuilder.newDocument();

                // Root element
                root = document.createElement("payments");
                document.appendChild(root);
            }

            // Payment element
            Element payment = document.createElement("payment");
            root.appendChild(payment);

            // Sender element
            Element senderElement = document.createElement("sender");
            senderElement.appendChild(document.createTextNode(sender));
            payment.appendChild(senderElement);

            // Recipient element
            Element recipientElement = document.createElement("recipient");
            recipientElement.appendChild(document.createTextNode(recipient));
            payment.appendChild(recipientElement);

            // Amount element
            Element amountElement = document.createElement("amount");
            amountElement.appendChild(document.createTextNode(amount));
            payment.appendChild(amountElement);

            // Write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(xmlFile);

            transformer.transform(domSource, streamResult);

            System.out.println("Payment saved to XML file.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        NamaPengirim.clear();
        NamaPenerima.clear();
        Nominal.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Accept.setOnAction(this::handleAcceptAction);
    }
}
