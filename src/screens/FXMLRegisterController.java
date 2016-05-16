/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class FXMLRegisterController implements Initializable {
    
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
                    register();
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
                register();
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
    
    private void register() {
        System.out.println("register");
        
        PaSocketMessageRegister pasr = new PaSocketMessageRegister();
        pasr.setAction(PaSocketAction.REGISTER);
        pasr.setUserFirstName("seb");
        pasr.setUserLastName("fricker");
        pasr.setUserName("sfricker");
        pasr.setUserEmail("test@test.com");
        pasr.setUserPassword("1234");
        pasr.setUserConfirmPassword("1234");
        
        PaSocketClient.sendObject(pasr);

//        if(Authenticator.register()) {
//            ScreensManager.setContent(Screens.WORKSPACE);
//        } else {
//            System.err.println("Registration failed");
//        }
    }
}
