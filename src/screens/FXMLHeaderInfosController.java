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
        Button mItem = (Button) event.getSource();

        String btnId = mItem.getId();

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
}