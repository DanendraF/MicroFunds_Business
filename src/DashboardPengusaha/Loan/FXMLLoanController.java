package DashboardPengusaha.Loan;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FXMLLoanController implements Initializable {
    
    @FXML
    private Label label;

    @FXML
    private TextField Lnama;

    @FXML
    private TextField Lnik;

    @FXML
    private TextField Lalamat;

    @FXML
    private TextField Ltelp;

    @FXML
    private TextField Lpinjaman;

    @FXML
    private TextField Lpendapatanbln;

    @FXML
    private Button submit;

    @FXML
    private void handleButtonSubmitAction(ActionEvent event) {
        String nama = Lnama.getText();
        String nik = Lnik.getText();
        String alamat = Lalamat.getText();
        String telp = Ltelp.getText();
        String pinjaman = Lpinjaman.getText();
        String pendapatanbln = Lpendapatanbln.getText();

        LoanData loanData = new LoanData(nama, nik, alamat, telp, pinjaman, pendapatanbln);

        try {
            saveLoanDataToXML(loanData);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Data has been saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save data!");
        }
    }

    private void saveLoanDataToXML(LoanData loanData) 
            throws ParserConfigurationException, TransformerException, IOException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // Root element
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("LoanData");
        doc.appendChild(rootElement);

        // Nama element
        Element name = doc.createElement("nama");
        name.appendChild(doc.createTextNode(loanData.getNama()));
        rootElement.appendChild(name);

        // NIK element
        Element NIK = doc.createElement("nik");
        NIK.appendChild(doc.createTextNode(loanData.getNik()));
        rootElement.appendChild(NIK);

        // Alamat element
        Element address = doc.createElement("alamat");
        address.appendChild(doc.createTextNode(loanData.getAlamat()));
        rootElement.appendChild(address);

        // Telp element
        Element phone = doc.createElement("telp");
        phone.appendChild(doc.createTextNode(loanData.getTelp()));
        rootElement.appendChild(phone);

        // Pinjaman element
        Element loan = doc.createElement("pinjaman");
        loan.appendChild(doc.createTextNode(loanData.getPinjaman()));
        rootElement.appendChild(loan);

        // Pendapatan Bulanan element
        Element income = doc.createElement("pendapatanbln");
        income.appendChild(doc.createTextNode(loanData.getPendapatanbln()));
        rootElement.appendChild(income);

        // Write the content into XML file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("loan_data.xml"));

        transformer.transform(source, result);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK && alertType == AlertType.INFORMATION) {
                // Navigate to dashboard
                navigateToDashboard();
            }
        });
    }

    private void navigateToDashboard() {
        try {
            // Sesuaikan path ini dengan lokasi FXMLDashboard.fxml dalam proyekmu
            URL fxmlLocation = getClass().getResource("/DashboardPengusaha/FXMLDashboard.fxml");
            if (fxmlLocation == null) {
                throw new IOException("Dashboard FXML file not found.");
            }
            Parent root = FXMLLoader.load(fxmlLocation);
            Stage stage = (Stage) submit.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
