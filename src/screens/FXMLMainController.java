package screens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import security.Authenticator;
import sockets.PaSocketClient;
import sockets.PaSocketMessageLogin;

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
    private GridPane userMenu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Store the reference of the screens container
        ScreensManager.container = StackPaneMain;

        // Set the current view content
        ScreensManager.setContent(Screens.LOGIN);

        // Visible IF not auth
        btnLogin.visibleProperty().bind(Authenticator.isAuth.not());
        btnRegister.visibleProperty().bind(Authenticator.isAuth.not());

        // Visible is auth
        btnLogout.visibleProperty().bind(Authenticator.isAuth);
//        userMenu.visibleProperty().bind(Authenticator.isAuth);

        //instructions à exécuter lors de cet événement
//        PaSocketMessageLogin pasl = new PaSocketMessageLogin("test", "test");
//        PaSocketClient.sendObject(pasl);

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
