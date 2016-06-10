package screens;

import entities.Equation;
import entities.EquationProperty;
import entities.UserProperty;
import guibinding.GuiBinder;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import notifications.NotificationsManager;
import sockets.PaSocketAction;
import sockets.PaSocketClient;
import sockets.PaSocketMessageEquation;

public class FXMLWorkspaceController implements Initializable {

    //Utile pour la synchronisation
    //private ObservableList<Equation> obsListEqt = FXCollections.observableArrayList();
    @FXML
    private TableView tvListEquation;

    @FXML
    private TableColumn<Equation, String> idCol;

    @FXML
    private TableColumn<Equation, String> labelCol;

    @FXML
    private TableColumn<Equation, String> descriptionCol;

    @FXML
    private TableColumn<Equation, String> isValidatedCol;

    @FXML
    private TableColumn<Equation, String> creatorCol;

    @FXML
    private TableColumn<Equation, String> lastUpdCol;

    @FXML
    private TableColumn<Equation, String> comCol;
    
    @FXML
    private Button btnViewEquation;
    
    @FXML
    private Label labNbTotalItems;

    public static EquationProperty eqtppte = new EquationProperty();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        PaSocketMessageEquation equation = new PaSocketMessageEquation();
        equation.setAction(PaSocketAction.LISTEQUATION);
        PaSocketClient.sendObject(equation);
        

        
        labNbTotalItems.textProperty().bind(GuiBinder.listEquationSize);

        labelCol.setCellFactory(TextFieldTableCell.<Equation>forTableColumn());
        descriptionCol.setCellFactory(TextFieldTableCell.<Equation>forTableColumn());

        labelCol.setCellValueFactory(new PropertyValueFactory<>("label"));

        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        isValidatedCol.setCellValueFactory(new PropertyValueFactory<>("isValid"));

        creatorCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));

        lastUpdCol.setCellValueFactory(new PropertyValueFactory<>("validationDate"));

        tvListEquation.setItems(GuiBinder.listEquation);
    }
    

    @FXML
    private void handleClick(ActionEvent event) throws IOException {
        Button item = (Button) event.getSource();
        String btnId = item.getId();
        
        Equation selected;

        switch (btnId) {
            case "btnViewEquation":
                System.out.println("Showing Equation View");
                selected = (Equation)tvListEquation.getSelectionModel().getSelectedItem();
                ScreensManager.viewParams.put("id", selected.getId());
                ScreensManager.setContent(Screens.VIEW);
                break;
            case "btnDeleteEquation" :
                System.out.println("Showing Equation DELETE");
                
                tvListEquation.setDisable(true);

                selected = (Equation)tvListEquation.getSelectionModel().getSelectedItem();
                
                boolean deleteOk = NotificationsManager.prompt(
                        "Suppression d'equation",
                        "Souhaitez-vous vraiment supprimer l'équation '" + selected.getLabel() + "'?",
                        "Attention toutes les données non sauvegardées seront perdues"
                );
                
                // IF user has clicked OK button
                if(deleteOk) {
                    PaSocketMessageEquation equation = new PaSocketMessageEquation();
                    equation.setAction(PaSocketAction.DELETEEQUATION);
                    equation.setEquation(selected);
                    PaSocketClient.sendObject(equation);
                } else {
                    System.out.println("Deleting cancelled for Equation #" + selected.getId());
                }           
                tvListEquation.setDisable(false);
                break;
            case "btnCreateEquation":
                ScreensManager.setContent(Screens.CREATE);
                break;
            default:
                System.out.println("Action '" + btnId + "' is not a valid");
                break;
        }
    }
}
