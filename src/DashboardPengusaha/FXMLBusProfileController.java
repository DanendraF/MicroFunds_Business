    package DashboardPengusaha;


    import java.io.File;
    import java.io.IOException;
    import java.net.URL;
    import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.fxml.Initializable;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.stage.Stage;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.TextField;

    public class FXMLBusProfileController implements Initializable {

        @FXML
        private Label label;

        @FXML
        private Button Dashboard;

        @FXML
        private Button SBBusProfile;

        @FXML
        private Button SBFinancialStats;

        @FXML
        private Button SBPayment;

        @FXML
        private Button SBFundingStatus;

        @FXML
        private TextField getOwner;

        @FXML
        private TextField getBusinessName;

        @FXML
        private TextField getLocation;

        @FXML
        private TextField getEst;

        @FXML
        private TextField getCategory;

        @FXML
        private TextField getDesc;

        @FXML
        private Button loan;

        @FXML
        private Button RegBusiness;

        @FXML
        private void handleLoanButtonAction(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardPengusaha/Loan/FXMLpinjaman.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

            private void loadBusinessDataFromXML() {
        try {
            File xmlFile = new File("business_data.xml");
            if (xmlFile.exists()) {
                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(xmlFile);
                document.getDocumentElement().normalize();

                Element root = document.getDocumentElement();
                getOwner.setText(getTagValue("owner", root));
                getBusinessName.setText(getTagValue("businessName", root));
                getLocation.setText(getTagValue("location", root));
                getEst.setText(getTagValue("estDate", root));
                getCategory.setText(getTagValue("category", root));
                getDesc.setText(getTagValue("description", root));

                // Set TextField to be non-editable
                getOwner.setEditable(false);
                getBusinessName.setEditable(false);
                getLocation.setEditable(false);
                getEst.setEditable(false);
                getCategory.setEditable(false);
                getDesc.setEditable(false);
            } else {
                System.out.println("File XML tidak ditemukan.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTagValue(String tagName, Element element) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

        @FXML
        private void handleDashboardAction(ActionEvent event) {
            loadPage("/DashboardPengusaha/FXMLDashboard.fxml");
        }


        @FXML
        private void handleRegBusinessAction(ActionEvent event) {
            loadPage("/DashboardPengusaha/FormBusiness/FXMLDocument.fxml");
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
        private void handleSBFundingStatusAction(ActionEvent event) {
            loadPage("/DashboardPengusaha/FXMLFundingStatus.fxml");
        }


        
        @Override
        public void initialize(URL url, ResourceBundle rb) {
            loadBusinessDataFromXML();
        }

    }
