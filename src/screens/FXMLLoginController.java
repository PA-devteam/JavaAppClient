/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import screens.Screens;
import screens.ScreensManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import security.Authenticator;
import sockets.PaSocketClient;
import sockets.PaSocketMessageLogin;

/**
 * FXML Controller class
 *
 * @author sebastien
 */
public class FXMLLoginController implements Initializable {

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
        paneLogin.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    System.out.println("ENTER");
                    login();
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
                login();
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
    
    private void login() {
        System.out.println("login");
        
        PaSocketMessageLogin pasl = new PaSocketMessageLogin("test", "test");
        PaSocketClient.sendObject(pasl);           
        

//        if(Authenticator.login(userName.getText(), userPassword.getText())) {
//            ScreensManager.setContent(Screens.WORKSPACE);
//        } else {
//            System.err.println("Authentication failed");
//        }        
    }
    
    private void register() {
        System.out.println("register");
        ScreensManager.setContent(Screens.REGISTER);
    }
}
