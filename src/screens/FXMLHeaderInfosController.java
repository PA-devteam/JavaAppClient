package screens;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import security.Authenticator;
import guibinding.GuiBinder;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import notifications.NotificationsManager;

public class FXMLHeaderInfosController implements Initializable {

    @FXML
    private ImageView avatarHolder;

    @FXML
    private GridPane userInfosMenu;

    @FXML
    private Label userName;

    @FXML
    private Label userRoles;
    
    @FXML
    private Button btnMessages;
    
    @FXML
    private Button btnNotifications;
    
    @FXML
    private Button btnChat;
    
    @FXML
    private MenuItem btnProfil;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Visible IF user is authenticated
        userInfosMenu.visibleProperty().bind(Authenticator.isAuth);
        
        // Binding user name value
        userName.textProperty().bind(GuiBinder.user.getUserName());
        
        // Binding user roles value
        userRoles.textProperty().bind(GuiBinder.user.getUserRoles());
        
        // Binding user avatar
        avatarHolder.imageProperty().bind(GuiBinder.user.getUserAvatarImage());
    }
    
    @FXML
    private void handleClick(ActionEvent event) throws IOException {
        Button item = (Button) event.getSource();
        
        String btnId = item.getId();

        switch (btnId) {
            case "btnNotifications":
                System.out.println("Showing notifications");
                NotificationsManager.info("Notifications", "Fonctionnalité en cours de développement");
                break;
            case "btnChat":
                System.out.println("Showing chat");
                NotificationsManager.info("Tchat", "Fonctionnalité en cours de développement");
                break;
            case "btnMessages":
                System.out.println("Showing messages");
                NotificationsManager.info("Messagerie", "Fonctionnalité en cours de développement");
                break;
            default:
                System.out.println("Action '" + btnId + "' is not a valid");
                break;
        }
    }
    
    @FXML
    private void handleMenuItemClick(ActionEvent event) throws IOException {
        MenuItem item = (MenuItem) event.getSource();
        
        String btnId = item.getId();

        switch (btnId) {
            case "btnProfil":
                System.out.println("Showing profil");
                ScreensManager.setContent(Screens.PROFIL);
                break;
            case "btnLogout":
                System.out.println("Logout");
                Authenticator.logout();
//                ScreensManager.setContent(Screens.LOGIN);
                break;                
            default:
                System.out.println("Action '" + btnId + "' is not a valid");
                break;
        }        
    }
}