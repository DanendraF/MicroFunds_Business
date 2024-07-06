package LoginRegister;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLStreamException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class FXMLRegController implements Initializable {
    
    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private TextField username;

    @FXML
    private TextField password;
    
    @FXML
    private TextField cpassword;

    @FXML
    private Button ButtonRegister;

    @FXML
    private Button linkLogin;
    
    // List to store registered users
    private List<User> registeredUsers = new ArrayList<>();

    @FXML
    private void handleButtonRegisterAction(ActionEvent event) {
        String firstName = fname.getText();
        String lastName = lname.getText();
        String userName = username.getText();
        String passWord = password.getText();
        String cPassWord = cpassword.getText();
    
        // Basic validation
        if (firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || passWord.isEmpty() || cPassWord.isEmpty()) {
            showAlert("Error", "Input semua data yang diperintah!!");
            return;
        }
    
        if (!passWord.equals(cPassWord)) {
            showAlert("Error", "Passwords tidak sama!!");
            return;
        }
    
        // Check if username already exists
        for (User user : registeredUsers) {
            if (user.getUserName().equals(userName)) {
                showAlert("Error", "Username sudah digunakan!!");
                return;
            }
        }
    
        // Create a new User object and add it to the list
        User newUser = new User(firstName, lastName, userName, passWord);
        registeredUsers.add(newUser);
    
        // Write users to XML
        writeUsersToXML(registeredUsers, "users.xml");
    
        // Clear input fields
        fname.setText("");
        lname.setText("");
        username.setText("");
        password.setText("");
        cpassword.setText("");
    
        // Display a success message
        showAlert("Success", "Registrasi Berhasil");

        // Navigate to Dashboard
        navigateToDashboard();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void writeUsersToXML(List<User> users, String filename) {
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        try (FileWriter fileWriter = new FileWriter(filename)) {
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(fileWriter);
            xmlStreamWriter.writeStartDocument();
            xmlStreamWriter.writeStartElement("users");
            
            for (User user : users) {
                xmlStreamWriter.writeStartElement("user");

                xmlStreamWriter.writeStartElement("firstName");
                xmlStreamWriter.writeCharacters(user.getFirstName());
                xmlStreamWriter.writeEndElement();

                xmlStreamWriter.writeStartElement("lastName");
                xmlStreamWriter.writeCharacters(user.getLastName());
                xmlStreamWriter.writeEndElement();

                xmlStreamWriter.writeStartElement("userName");
                xmlStreamWriter.writeCharacters(user.getUserName());
                xmlStreamWriter.writeEndElement();

                xmlStreamWriter.writeStartElement("password");
                xmlStreamWriter.writeCharacters(user.getPassword());
                xmlStreamWriter.writeEndElement();

                xmlStreamWriter.writeEndElement();
            }
            
            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();
            xmlStreamWriter.close();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void navigateToDashboard() {
        try {
            // Memuat halaman dashboard
            Parent root = FXMLLoader.load(getClass().getResource("/DashboardPengusaha/FXMLDashboard.fxml"));
            Scene scene = new Scene(root);
            // Mengambil stage saat ini
            Stage stage = (Stage) ButtonRegister.getScene().getWindow();
            // Mengatur scene baru untuk stage
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLinkLogin(ActionEvent event) {
        try {
            // Memuat halaman login
            Parent root = FXMLLoader.load(getClass().getResource("/FXMLLog.fxml"));
            Scene scene = new Scene(root);
            // Mengambil stage saat ini
            Stage stage = (Stage) linkLogin.getScene().getWindow();
            // Mengatur scene baru untuk stage
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the controller class
    }
}
