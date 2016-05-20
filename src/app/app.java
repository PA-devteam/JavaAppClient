package app;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resources.ResourceLoader;
import screens.Screens;
import sockets.PaSocketClient;
import config.ConfigManager;

public class app extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfigManager.load("config.properties");

        /*****************************************************************/
        /**************************** EXAMPLE ****************************/
        // Set new property
        ConfigManager.setProperty("login_last_logged", "user666");
        // Save the file
        ConfigManager.save("config.properties");
        /*****************************************************************/

        if (!ConfigManager.isEmpty()) {
            // Declare new root parent for application scene
            Parent root;

            // Initialise root by loading screen content
            root = (Parent) ResourceLoader.loadView(Screens.MAIN);

            // Check if root has been loaded
            if (root != null) {
                // Socket serveur ip to reach
                String ip = ConfigManager.getStringProperty("ss_ip");
                // Socket serveur port to reach
                int port = ConfigManager.getIntProperty("ss_port");
                // Application title
                String frameTitle = ConfigManager.getStringProperty("app_name_fr");
                // Application width
                int frameWidth = ConfigManager.getIntProperty("app_width");
                // Application height
                int frameHeight = ConfigManager.getIntProperty("app_height");

                // Intialise a new scene from root
                Scene scene = new Scene(root);

                // Load FXML CSS theme file
                ResourceLoader.loadStyle("FXMLMainStyle.css");
                /**
                 * ************************* JavaFx Css reference **************************
                 */
                /* https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html  */
                /**
                 * *************************************************************************
                 */

                // Load font 'fontawesome' (only containing icons)
                ResourceLoader.loadFont("fontawesome-webfont.ttf", 12);
                /**
                 * ********** Font Awesome icons website ***********
                 */
                /* https://fortawesome.github.io/Font-Awesome/icons */
                /**
                 * *************************************************
                 */

                // Set application main frame width
                primaryStage.setMinWidth(frameWidth);
                // Set application main frame height
                primaryStage.setMinHeight(frameHeight);
                // Set application main frame title
                primaryStage.setTitle(frameTitle);

                // Apply scene into application primary scene
                primaryStage.setScene(scene);
                // Show the primary stage in application
                primaryStage.show();

                // Initialise a new socket client
                PaSocketClient client = new PaSocketClient(ip, port);

                // Check if the client has established connection to the server
                //            if(client.isAlive()) {
                // Call Start method from Thread Class, init a new thread and call run method
                client.start();
                //            } else {
                //                // Otherwise, notify the user of the socket failed error
                //                //ScreensManager.setContent(Screens.SOCKET_FAILED);
                //                NotificationsManager.alert(Alert.AlertType.ERROR, "Erreur", "Erreur de connexion", "La connexion au serveur a été interrompue");
                //            }
            } else {
                System.err.println("Cannot start application, no root found");
            }
        } else {
            System.err.println("Cannot start application, no config file found");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
