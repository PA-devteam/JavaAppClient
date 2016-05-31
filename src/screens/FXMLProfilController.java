package screens;

import guibinding.GuiBinder;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class FXMLProfilController implements Initializable {

    @FXML
    private GridPane paneRegister;

    @FXML
    private TextField userFirstName;

    @FXML
    private TextField userLastName;

    @FXML
    private TextField userName;

    @FXML
    private TextField userEmail;

    @FXML
    private PasswordField userPassword;

    @FXML
    private PasswordField userConfirmPassword;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancel;

    @FXML
    private ComboBox listRole;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userFirstName.textProperty().bind(GuiBinder.user.getUserFirstName());
        userLastName.textProperty().bind(GuiBinder.user.getUserLastName());
        userEmail.textProperty().bind(GuiBinder.user.getUserEmail());
        userName.textProperty().bind(GuiBinder.user.getUserName());

        listRole.setItems(FXCollections.observableArrayList(GuiBinder.roles));
        listRole.getSelectionModel().select(GuiBinder.roles.indexOf(GuiBinder.user.getUserRoles().getValue()));
    }

    @FXML
    private void handleClick(ActionEvent event) throws IOException {
        Button mItem = (Button) event.getSource();

        String side = mItem.getId();

        switch (side.toLowerCase()) {
            case "btnOk":
                System.out.println("ok");
                break;
            case "btnCancel":
                System.out.println("cancel");
                ScreensManager.cancel();
                break;
            default:
                System.out.println("default");
                System.out.println(side);
                break;
        }
    }
}