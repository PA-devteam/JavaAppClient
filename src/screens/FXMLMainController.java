package screens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import security.Authenticator;
import sockets.PaSocket;

public class FXMLMainController implements Initializable {

    @FXML
    private MenuItem btnLogin;

    @FXML
    private MenuItem btnRegister;

    @FXML
    private MenuItem btnLogout;

    @FXML
    private StackPane StackPaneMain;
    
    @FXML
    private Menu headerMenu;
    
    @FXML
    private ProgressBar loadingBar;

    @FXML
    private ImageView avatarHolder;    
    
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

        // Visible IF auth
        btnLogout.visibleProperty().bind(Authenticator.isAuth);
        
        // Visible IF socket is unlocked
        headerMenu.visibleProperty().bind(PaSocket.isUnLocked);
    }

    @FXML
    private void handleClick(ActionEvent event) throws IOException {
        MenuItem mItem = (MenuItem) event.getSource();

        String side = mItem.getText();

        // Substring at index 2 beacause there is an icon and a whitespace
        side = side.substring(2);

        switch (side.toLowerCase()) {
            case "inscription":
                System.out.println("inscription");
                ScreensManager.setContent(Screens.REGISTER);
                break;
            case "connexion":
                System.out.println("connexion");
                ScreensManager.setContent(Screens.LOGIN);
                break;
            case "déconnexion":
                System.out.println("déconnexion");
                Authenticator.logout();
                ScreensManager.setContent(Screens.LOGIN);
                break;
            default:
                System.out.println("default");
                break;
        }
    }
}
