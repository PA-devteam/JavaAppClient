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

public class FXMLLoginController implements Initializable, ScreensSubmitable {

    @FXML
    private GridPane paneLogin;
    
    @FXML
    private TextField userName;
    
    @FXML
    private PasswordField userPassword;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

    @Override
    public void submit() {
        System.out.println("Submit");

        PaSocketMessageLogin o = new PaSocketMessageLogin();
        
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
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            PaSocketClient.sendObject(o); 
        } 
    }
}
