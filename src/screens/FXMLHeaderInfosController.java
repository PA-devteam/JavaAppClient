package screens;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import security.Authenticator;

public class FXMLHeaderInfosController implements Initializable {

    @FXML
    private ImageView avatarHolder;

    @FXML
    private GridPane userInfosMenu;

    @FXML
    private Label userName;

    @FXML
    private Label userRoles;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Visible IF user is authenticated
        userInfosMenu.visibleProperty().bind(Authenticator.isAuth);
        
        // Binding user name value
        userName.textProperty().bind(guibinding.GuiBinder.user.getUserName());
        
        // Binding user roles value
        userRoles.textProperty().bind(guibinding.GuiBinder.user.getUserRoles());
        
        // Binding user avatar
        avatarHolder.imageProperty().bind(guibinding.GuiBinder.user.getUserAvatarImage());
    }
}