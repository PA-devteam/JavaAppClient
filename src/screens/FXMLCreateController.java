/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import entities.Equation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sockets.PaSocketClient;
import sockets.PaSocketMessageEquation;

/**
 * FXML Controller class
 *
 * @author florian
 */
public class FXMLCreateController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML 
            private TextField name;
    @FXML 
            private TextArea description;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void handleClick(ActionEvent event) throws IOException {
        Button mItem = (Button) event.getSource();

        String side = mItem.getId();

        switch (side) {
            case "btnValidCreate":
                System.out.println("je passe");
              Equation eq=new Equation();
              eq.setLabel(name.getText());
              eq.setDescription(description.getText());
              PaSocketMessageEquation equation=new PaSocketMessageEquation();
              equation.setCreateEquation();
              equation.setEquation(eq);
              PaSocketClient.sendObject(equation);
                break;
            default:
                System.out.println(side);
                break;
        }
    }

   
  
    
}
