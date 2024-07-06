package LoginRegister;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
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
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class FXMLLogController implements Initializable {

    private int generatedOTP;

    @FXML
    private Label label;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField OTP;

    @FXML
    private Button ButtonOTP;

    @FXML
    private Button ButtonLogin;

    @FXML
    private Button linkRegister;

    private List<User> registeredUsers = new ArrayList<>();

    @FXML
    private void handleButtonOTPAction(ActionEvent event) {
        // Generate OTP acak
        Random random = new Random();
        generatedOTP = random.nextInt(900000) + 100000;

        // Menampilkan OTP
        OTP.setText(String.valueOf(generatedOTP));
    }

    @FXML
    private void handleButtonLoginAction(ActionEvent event) {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();
        int enteredOTP;

        try {
            enteredOTP = Integer.parseInt(OTP.getText());
        } catch (NumberFormatException e) {
            // Handle input OTP yang tidak valid
            showAlert("Error", "Invalid OTP input");
            return;
        }

        // Validasi
        for (User user : registeredUsers) {
            if (user.getUserName().equals(enteredUsername) && user.getPassword().equals(enteredPassword) && enteredOTP == generatedOTP) {
                // Login berhasil
                showAlert("Success", "Login successful");
                
                // Navigate to Dashboard
                navigateToDashboard();
                return;
            }
        }

        // Login gagal
        showAlert("Error", "Invalid username, password, or OTP");
    }

    private List<User> readUsersFromXML(String filename) {
        List<User> users = new ArrayList<>();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try (FileReader fileReader = new FileReader(filename)) {
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(fileReader);

            User user = null;
            while (xmlStreamReader.hasNext()) {
                int event = xmlStreamReader.next();
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        if ("user".equals(xmlStreamReader.getLocalName())) {
                            user = new User();
                        } else if ("firstName".equals(xmlStreamReader.getLocalName())) {
                            user.setFirstName(xmlStreamReader.getElementText());
                        } else if ("lastName".equals(xmlStreamReader.getLocalName())) {
                            user.setLastName(xmlStreamReader.getElementText());
                        } else if ("userName".equals(xmlStreamReader.getLocalName())) {
                            user.setUserName(xmlStreamReader.getElementText());
                        } else if ("password".equals(xmlStreamReader.getLocalName())) {
                            user.setPassword(xmlStreamReader.getElementText());
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        if ("user".equals(xmlStreamReader.getLocalName())) {
                            users.add(user);
                        }
                        break;
                }
            }
            xmlStreamReader.close();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Metode untuk menampilkan alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateToDashboard() {
        try {
            // Memuat halaman dashboard
            Parent root = FXMLLoader.load(getClass().getResource("/DashboardPengusaha/FXMLDashboard.fxml"));
            Scene scene = new Scene(root);
            // Mengambil stage saat ini
            Stage stage = (Stage) ButtonLogin.getScene().getWindow();
            // Mengatur scene baru untuk stage
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLinkRegister(ActionEvent event) {
        try {
            // Memuat halaman register
            Parent root = FXMLLoader.load(getClass().getResource("/FXMLReg.fxml"));
            Scene scene = new Scene(root);
            // Mengambil stage saat ini
            Stage stage = (Stage) linkRegister.getScene().getWindow();
            // Mengatur scene baru untuk stage
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registeredUsers = readUsersFromXML("users.xml");
    }
}
