package screens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import notifications.NotificationsManager;
import security.Authenticator;
import sockets.PaSocket;
import sockets.PaSocketClient;

public class FXMLMainController implements Initializable {

    @FXML
    private MenuItem btnLogin;

    @FXML
    private MenuItem btnRegister;

    @FXML
    private StackPane StackPaneMain;
    
    @FXML
    private Menu headerMenu;
    
    @FXML
    private ProgressBar loadingBar;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Store the reference of the screens container
        ScreensManager.container = StackPaneMain;
        
        // Store the reference of the loading bar
        ScreensManager.loadingBar = loadingBar;

        // Set the current view content
        ScreensManager.setContent(Screens.LOGIN);

        // Visible IF not auth
        btnLogin.visibleProperty().bind(Authenticator.isAuth.not());
        btnRegister.visibleProperty().bind(Authenticator.isAuth.not());

        // Visible IF socket is unlocked
        headerMenu.visibleProperty().bind(PaSocket.isUnLocked);
    }

    @FXML
    private void handleClick(ActionEvent event) throws IOException {
        MenuItem mItem = (MenuItem) event.getSource();

        String bId = mItem.getId();

        switch (bId) {
            case "btnRegister":
                ScreensManager.setContent(Screens.REGISTER);
                break;
            case "btnLogin":
                ScreensManager.setContent(Screens.LOGIN);
                break;
            case "btnParams":
                ScreensManager.setContent(Screens.APP_PARAMETERS);
                break;
            case "btnExit":
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
                    
                    PaSocketClient.currentThread().interrupt();
                    
                    System.exit(0);
                }
                break;                
            default:
                System.out.println("Button action not supported");
                break;
        }
    }
}
