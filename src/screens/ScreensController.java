package screens;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ScreensController implements Initializable, ScreensSubmitable{
    
    @Override
    public void toggleFreeze(boolean frozen) {
        ScreensManager.toggleLoadingBar();
        
        // Using class reflection to store values from the current controller to new object
        Field[] fields = this.getClass().getDeclaredFields();

        // Check if there are some fields to iterate
        if (fields.length > 0) {
            // Iterate overs fields
            for (Field field : fields) {
                // Check field type
                if (field.getType().isAssignableFrom(TextField.class) || field.getType().isAssignableFrom(PasswordField.class)) {
                    try {
                        // Capitalize first letter of current iterated field
                        String cap = capitalizeFieldName(field);

                        // Retrieve getter method from origin object
                        Method getter = this.getClass().getMethod("get" + cap);

                        // Retrieve Textfield from current controller
                        TextField t = (TextField) getter.invoke(this);

                        // Disable current iterated field
                        t.setDisable(frozen);
                    } catch (IllegalArgumentException|IllegalAccessException|InvocationTargetException|NoSuchMethodException ex) {
                        if(ex instanceof NoSuchMethodException) {
                            System.err.println("Method does not exist");
                        } else {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }   
        }
    }

    final protected String capitalizeFieldName(Field field) {
        String fieldName = field.getName();
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScreensManager.controller = this;
    }

    @Override
    public void submit() {
        
    }
}