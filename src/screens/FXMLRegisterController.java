package screens;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import security.Authenticator;
import sockets.PaSocketAction;
import sockets.PaSocketClient;
import sockets.PaSocketMessageRegister;

public class FXMLRegisterController implements Initializable, ScreensSubmitable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paneRegister.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    System.out.println("ENTER");
                    submit();
                }
            }
        });
    }

    @FXML
    private void handleClick(ActionEvent event) throws IOException {
        Button mItem = (Button) event.getSource();

        String side = mItem.getText();

        switch (side.toLowerCase()) {
            case "ok":
                System.out.println("ok");
                submit();
                break;
            case "cancel":
                System.out.println("cancel");
                ScreensManager.cancel();
                break;
            default:
                System.out.println("default");
                System.out.println(side);
                break;
        }
    }

    @Override
    public void submit() {
        System.out.println("Submit");

        PaSocketMessageRegister o = new PaSocketMessageRegister();

        // Using class reflection to store values from the current controller to new object
        Field[] fields = this.getClass().getDeclaredFields();

        if (fields.length > 0) {
            for (Field field : fields) {
                if (field.getType().isAssignableFrom(TextField.class) || field.getType().isAssignableFrom(PasswordField.class)) {
                    try {
                        String cap = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

                        Method setter = o.getClass().getMethod("set" + cap, field.getClass().getTypeName().getClass());
                        Method getter = this.getClass().getMethod("get" + cap);

                        setter.invoke(o, getter.invoke(this));
                    } catch (NoSuchMethodException ex) {
                        System.err.println("Method does not exist");
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(FXMLRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(FXMLRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            PaSocketClient.sendObject(o);
        }
    }

    /**
     * @return the userFirstName
     */
    public String getUserFirstName() {
        return userFirstName.getText();
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
    public String getUserLastName() {
        return userLastName.getText();
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
    public String getUserName() {
        return userName.getText();
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
    public String getUserEmail() {
        return userEmail.getText();
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
    public String getUserPassword() {
        return userPassword.getText();
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
    public String getUserConfirmPassword() {
        return userConfirmPassword.getText();
    }

    /**
     * @param userConfirmPassword the userConfirmPassword to set
     */
    public void setUserConfirmPassword(PasswordField userConfirmPassword) {
        this.userConfirmPassword = userConfirmPassword;
    }
}
