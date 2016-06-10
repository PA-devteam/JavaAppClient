/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import entities.Equation;
import guibinding.GuiBinder;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import notifications.NotificationsManager;
import sockets.PaSocketAction;
import sockets.PaSocketClient;
import sockets.PaSocketMessageEquation;

public class FXMLEquationViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField createdBy;

    @FXML
    private TextField dateCreated;

    @FXML
    private TextField description;

    @FXML
    private TextField label;

    @FXML
    private TextField validationDate;

    @FXML
    private CheckBox isValidated;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createdBy.textProperty().bind(GuiBinder.equation.getCreatedBy());
        label.textProperty().bind(GuiBinder.equation.getLabel());
        description.textProperty().bind(GuiBinder.equation.getDescription());
        validationDate.textProperty().bind(GuiBinder.equation.getValidationDate());
        isValidated.selectedProperty().bind(GuiBinder.equation.getIsValid());

        PaSocketMessageEquation equation = new PaSocketMessageEquation();

        equation.setAction(PaSocketAction.EQUATIONBYID);
        Equation equations = new Equation();
        equations.setId((int) ScreensManager.viewParams.get("id"));
        equation.setEquation(equations);
        PaSocketClient.sendObject(equation);
    }  
}
