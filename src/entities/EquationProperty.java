package entities;

import java.util.Date;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EquationProperty {

    private SimpleIntegerProperty id;
    private SimpleStringProperty label;
    private SimpleStringProperty description;
    private SimpleBooleanProperty isValid;
    private SimpleStringProperty validatedBy;
    public  SimpleStringProperty createdBy;
    private SimpleStringProperty validationDate;
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

//   /** @pdRoleInfo migr=no name=Rule assc=equationRules mult=0..* */
//   public Rule[] equationRules;
//   /** @pdRoleInfo migr=no name=Element assc=equationElement mult=1..* type=Composition */
//   public ArrayList<Element> equationElement;


    public EquationProperty() {
        equationLabel = new SimpleStringProperty();
        equationDescription = new SimpleStringProperty();
        this.id = new SimpleIntegerProperty();
        this.label = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.isValid = new SimpleBooleanProperty();
        this.validatedBy = new SimpleStringProperty();
        this.validationDate = new SimpleStringProperty();
        this.createdBy = new SimpleStringProperty();
    }

    public EquationProperty(Equation a) {
        equationLabel = new SimpleStringProperty(a.getLabel());
        equationDescription = new SimpleStringProperty(a.getDescription());

        this.id = new SimpleIntegerProperty(a.getId());
        this.label = new SimpleStringProperty(a.getLabel());
        this.description = new SimpleStringProperty(a.getDescription());
        this.isValid = new SimpleBooleanProperty(a.getIsValid());
        this.validatedBy = new SimpleStringProperty(a.getValidatedBy().toString());
        this.validationDate = new SimpleStringProperty(a.getValidationDate().toString());
        this.createdBy = new SimpleStringProperty(a.getCreatedBy().toString());
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id.setValue(id);
    }

    public SimpleStringProperty getLabel() {
        return label;
    }

    public void setLabel(SimpleStringProperty label) {
        this.label = label;
    }

    public void setLabel(String label) {
        this.label.setValue(label);
    }

    public SimpleStringProperty getDescription() {
        return description;
    }

    public void setDescription(SimpleStringProperty description) {
        this.description = description;
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }

    public SimpleBooleanProperty getIsValid() {
        return isValid;
    }

    public void setIsValid(SimpleBooleanProperty isValid) {
        this.isValid = isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid.setValue(isValid);
    }

    public SimpleStringProperty getValidatedBy() {
        return validatedBy;
    }

    public void setValidatedBy(SimpleStringProperty validatedBy) {
        this.validatedBy = validatedBy;
    }

    public void setValidatedBy(User validatedBy) {
        this.validatedBy.setValue(validatedBy.toString());
    }

    public SimpleStringProperty getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(SimpleStringProperty validationDate) {
        this.validationDate = validationDate;
    }

    public void setValidationDate(Date validationDate) {
        this.validationDate.setValue(validationDate.toString());
    }

    public SimpleStringProperty getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SimpleStringProperty createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy.setValue(createdBy.toString());
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy.setValue(createdBy);
    }
}
