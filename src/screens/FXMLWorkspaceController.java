package screens;

import entities.Equation;
import entities.EquationProperty;
import entities.UserProperty;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class FXMLWorkspaceController implements Initializable {
    
    //Utile pour la synchronisation
    private ObservableList<Equation> obsListEqt = FXCollections.observableArrayList();
       
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
    
    public static EquationProperty eqtppte = new EquationProperty();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initialisation des colonnes
        
        //Array d'équations
        ArrayList<Equation> lsteqt = new ArrayList<>();
        for(int i = 0; i<2; i++)
        {
            lsteqt.add(new Equation("test", "desc equation"));
        }
        
        ///Je remplis la liste observable
        obsListEqt.setAll(lsteqt);
        
        labelCol.setCellFactory(TextFieldTableCell.<Equation>forTableColumn());
        descriptionCol.setCellFactory(TextFieldTableCell.<Equation>forTableColumn());
        
        labelCol.setCellValueFactory(
            new PropertyValueFactory<>("label"));
        
        descriptionCol.setCellValueFactory(
            new PropertyValueFactory<>("description"));
        
        tvListEquation.setItems(obsListEqt);
        
        /*Iterator itr = obsListEqt.iterator();
        while(itr.hasNext()) {
        
        }*/
        
        
        
        
        
        
        
        
    }   
    
    
    
}