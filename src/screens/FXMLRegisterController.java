/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import sockets.PaSocketMessageLogin;
import sockets.PaSocketMessageRegister;

/**
 * FXML Controller class
 *
 * @author sebastien
 */
public class FXMLRegisterController implements Initializable, ScreensSubmitable {
    
    @FXML
    private GridPane paneRegister;

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
//                    register();
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
//                register();
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
    
//    private void register() {
//        System.out.println("register");
//        
//        PaSocketMessageRegister pasr = new PaSocketMessageRegister();
//        pasr.setAction(PaSocketAction.REGISTER);
//        pasr.setUserFirstName("seb");
//        pasr.setUserLastName("fricker");
//        pasr.setUserName("sfricker");
//        pasr.setUserEmail("test@test.com");
//        pasr.setUserPassword("1234");
//        pasr.setUserConfirmPassword("1234");
//        
//        PaSocketClient.sendObject(pasr);
//
////        if(Authenticator.register()) {
////            ScreensManager.setContent(Screens.WORKSPACE);
////        } else {
////            System.err.println("Registration failed");
////        }
//    }

    @Override
    public void submit() {
        System.out.println("Submit");

        PaSocketMessageRegister o = new PaSocketMessageRegister();
        
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
}
