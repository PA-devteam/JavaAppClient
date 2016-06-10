package guibinding;

import entities.*;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GuiBinder {
    public static UserProperty user = new UserProperty();
    public static ArrayList<String> roles = new ArrayList<>();
    //public static ArrayList<Equation> listEquation=new ArrayList<>();
    public static ObservableList<Equation> listEquation = FXCollections.observableArrayList();
    public static SimpleStringProperty listEquationSize=new SimpleStringProperty ();
    public static EquationProperty equation=new EquationProperty();
}
