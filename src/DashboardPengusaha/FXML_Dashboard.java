package DashboardPengusaha;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FXML_Dashboard extends Application {
    
    /*
     * The created stage object is passed as an argument to the start() method of the Application class
     */
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/DashboardPengusaha/FXMLDashboard.fxml"));

        /*
         * You can create a scene by instantiating the Scene Class
         * You can optimize for the size of the scene by passing its dimensions (height and width) 
         * along with the root node to its constructor
         * In this example, "root" is the root node, width = 640, height = 480
         * Scene scene = new Scene(root,640, 480);
         */
        Scene scene = new Scene(root);
        stage.setTitle("MicroFunds");

        /*
         * At an instance, the scene object is added to only one stage
         */
        stage.setScene(scene);

        /*
         * You have to call the show() method to display the contents of a stage
         */
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
