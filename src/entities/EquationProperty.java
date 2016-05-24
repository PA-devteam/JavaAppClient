/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author florian
 */
public class EquationProperty {
    private SimpleStringProperty equationLabel;
    private SimpleStringProperty equationDescription;

    
    public SimpleStringProperty getEquationLabel() {
        return equationLabel;
    }

    public void setEquationLabel(SimpleStringProperty equationLabel) {
        this.equationLabel = equationLabel;
    }

    public SimpleStringProperty getEquationDescription() {
        return equationDescription;
    }

    public void setEquationDescription(SimpleStringProperty equationDescription) {
        this.equationDescription = equationDescription;
    }
    
    public EquationProperty()
    {
        equationLabel = new SimpleStringProperty();
        equationDescription = new SimpleStringProperty();
    }
    
    public EquationProperty(Equation eqt)
    {
        equationLabel = new SimpleStringProperty(eqt.getLabel());
        equationDescription = new SimpleStringProperty(eqt.getDescription());
    }
}

