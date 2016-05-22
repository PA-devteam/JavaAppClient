package app;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resources.ResourceLoader;
import screens.Screens;
import config.ConfigManager;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import sockets.PaSocket;
import notifications.NotificationsManager;

public class app extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        // Set action to handle exit behavior
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // Prompt the user to choose an action to perform
                boolean exit = NotificationsManager.prompt(
                        "Quitter l'application",
                        "Souhaitez-vous vraiment quitter l'application ?",
                        "Attention toutes les données non sauvegardées seront perdues"
                );
                
                // IF user has clicked OK button
                if(exit) {
                    // Exit application
                    Platform.exit();
                } else {
                    // Otherwise, just consume the event and do nothing
                    event.consume();
                }
            }
        });
        
        // Load application configuration file
        ConfigManager.load("config.properties");       

        // If configuration loading has succeed
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
                /************************** JavaFx Css reference ***************************/
                /* https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html */
                /***************************************************************************/

                // Load font 'fontawesome' (only containing icons)
                ResourceLoader.loadFont("fontawesome-webfont.ttf", 6);
                /************ Font Awesome icons website ************/
                /* https://fortawesome.github.io/Font-Awesome/icons */
                /****************************************************/

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

                // Initialise a new socket connection
                PaSocket.initialise(ip, port);
            } else {
                System.err.println("Cannot start application, no root found");
                notifications.NotificationsManager.error("Impossible de démarrer l'application", "La vue parente n'a pas été trouvée");
            }
        } else {
            System.err.println("Cannot start application, no config file found");
            notifications.NotificationsManager.error("Impossible de démarrer l'application", "Aucun fichier de configuration trouvé");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
