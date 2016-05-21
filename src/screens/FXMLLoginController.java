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
import sockets.PaSocketClient;
import sockets.PaSocketMessageLogin;
import config.ConfigManager;

public class FXMLLoginController extends ScreensController {

    @FXML
    private GridPane paneLogin;

    @FXML
    private TextField userName;

    @FXML
    private PasswordField userPassword;
    
    @FXML
    private Button btnOk;
    
    @FXML
    private Button btnCancel;    

    /**
     * Initializes the controller class.
     */
    
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        // Get the last connected user
        userName.setText(ConfigManager.getStringProperty("login_last_logged"));

        paneLogin.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
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

        switch(side.toLowerCase()) {
            case "ok":
                System.out.println("case btn ok");
                submit();
                break;
            case "register":
                System.out.println("case btn register");
                register();
                break;
            default:
                System.out.println("default");
                System.out.println(side);
                break;
        }
    }

    private void register() {
        System.out.println("register");
        ScreensManager.setContent(Screens.REGISTER);
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
     * @return the userPassword
     */
    public TextField getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPassword(PasswordField userPassword) {
        this.userPassword = userPassword;
    }
    
    public void toggleFreeze(boolean frozen) {
        super.toggleFreeze(frozen);
        btnOk.setDisable(frozen);
        btnCancel.setDisable(frozen);       
    }
    
    @Override
    public void submit() {
        toggleFreeze(true);
        
        ScreensManager.toggleLoadingBar();
        
        PaSocketMessageLogin o = new PaSocketMessageLogin();

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

                        // Disable current iterated field
                        t.setDisable(true);

                        // Call setter method on target object with value obtained by getter method
                        setter.invoke(o, t.getText());
                    } catch (IllegalArgumentException|IllegalAccessException|InvocationTargetException|NoSuchMethodException ex) {
                        if(ex instanceof NoSuchMethodException) {
                            System.err.println("Method does not exist");
                        } else {
                            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            
            security.Authenticator.login(o);
        }
    }
}
