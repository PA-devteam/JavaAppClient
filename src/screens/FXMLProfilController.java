package screens;

import guibinding.GuiBinder;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import sockets.PaSocketClient;
import sockets.PaSocketMessageRegister;

public class FXMLProfilController extends ScreensController implements Initializable  {

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
        userFirstName.textProperty().unbind();
        userLastName.textProperty().bind(GuiBinder.user.getUserLastName());
         userLastName.textProperty().unbind();
       userEmail.textProperty().bind(GuiBinder.user.getUserEmail());
        userName.textProperty().bind(GuiBinder.user.getUserName());
        userName.setDisable(true);
        userEmail.textProperty().unbind();
     
        listRole.setItems(FXCollections.observableArrayList(GuiBinder.roles));
        listRole.getSelectionModel().select(GuiBinder.roles.indexOf(GuiBinder.user.getUserRoles().getValue()));
    }

    @FXML
    private void handleClick(ActionEvent event) throws IOException {
        Button mItem = (Button) event.getSource();

        String side = mItem.getId();

        switch (side.toLowerCase()) {
            case "btnok":
                System.out.println("Mettre à jour");
                submit();
                break;
            case "btncancel":
                System.out.println("cancel");
                ScreensManager.cancel();
                break;
            default:
                System.out.println("defaultp");
                System.out.println(side);
                break;
        }
    }
    
    @Override
    public void toggleFreeze(boolean frozen) {
        super.toggleFreeze(frozen);
        btnOk.setDisable(frozen);
        btnCancel.setDisable(frozen);
    }

    @Override
    public void submit() {
        toggleFreeze(true);

        PaSocketMessageRegister o = new PaSocketMessageRegister();
        o.setActionInfo();

        // Using class reflection to store values from the current controller to new object
        Field[] fields = this.getClass().getDeclaredFields();

        // Check if there are some fields to iterate
        if (fields.length > 0) {
            // Iterate overs fields
            for (Field field : fields) {
                // Check field type
                if (field.getType().isAssignableFrom(TextField.class) || field.getType().isAssignableFrom(PasswordField.class)) {
                    try {
                        // Capitalize first letter of current iterated field
                        String cap = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

                        // Retrieve setter method from target object
                        Method setter = o.getClass().getMethod("set" + cap, field.getClass().getTypeName().getClass());

                        // Retrieve getter method from origin object
                        Method getter = this.getClass().getMethod("get" + cap);

                        // Retrieve Textfield from current controller
                        TextField t = (TextField) getter.invoke(this);

                        // Call setter method on target object with value obtained by getter method
                        setter.invoke(o, t.getText());
                    } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                        if (ex instanceof NoSuchMethodException) {
                            System.err.println("Method does not exist");
                        } else {
                            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            
            o.setRoles((String)listRole.getValue());
            PaSocketClient.sendObject(o);
        }
    }
    
    public TextField getUserFirstName() {
        return userFirstName;
    }

    /**
     * @param userFirstName the userFirstName to set
     */
    public void setUserFirstName(TextField userFirstName) {
        this.userFirstName = userFirstName;
    }

    /**
     * @return the userLastName
     */
    public TextField getUserLastName() {
        return userLastName;
    }

    /**
     * @param userLastName the userLastName to set
     */
    public void setUserLastName(TextField userLastName) {
        this.userLastName = userLastName;
    }

    /**
     * @return the userName
     */
    public TextField getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(TextField userName) {
        this.userName = userName;
    }

    /**
     * @return the userEmail
     */
    public TextField getUserEmail() {
        return userEmail;
    }

    /**
     * @param userEmail the userEmail to set
     */
    public void setUserEmail(TextField userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * @return the userPassword
     */
    public PasswordField getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPassword(PasswordField userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * @return the userConfirmPassword
     */
    public PasswordField getUserConfirmPassword() {
        return userConfirmPassword;
    }

    /**
     * @param userConfirmPassword the userConfirmPassword to set
     */
    public void setUserConfirmPassword(PasswordField userConfirmPassword) {
        this.userConfirmPassword = userConfirmPassword;
    }
}