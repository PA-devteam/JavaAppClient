package notifications;

import java.util.Optional;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import resources.ResourceLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;
import javafx.stage.Window;
import screens.Screens;
import screens.ScreensManager;

public class NotificationsManager {
        
    public static void pop() {
        if(ScreensManager.container != null) {
            System.out.println("showNotif");
            
            Node popupContent = ResourceLoader.loadView(Screens.NOTIFICATION);
            
            Bounds p = popupContent.localToScene(popupContent.getBoundsInLocal());
            
            Popup pop = PopupBuilder.create().content(popupContent).autoFix(true).autoHide(false).build();
            System.out.println("popup created");

            Window window = ScreensManager.container.getScene().getWindow();
            Point2D windowCoord = new Point2D(window.getX(), window.getY());
            
            pop.setAnchorX(windowCoord.getX());
            pop.setAnchorY(windowCoord.getY());
            
            pop.setOpacity(0.95);
            pop.show(window);
            
            System.out.println("popup shown");
        } else {
            System.err.println("Cannot show notification, missing scene");
        }
    }
    
    public static void alert(AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();        
    }
    
    public static boolean prompt(String title, String header, String content) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);        
       
        Optional<ButtonType> result = alert.showAndWait();
        
        return (result.get() == ButtonType.OK);
    }
    
    /** ALERT METHOD SHORTHAND HELPERS **/
    public static void info(String header, String content) {
        alert(AlertType.INFORMATION, "Information", header, content);
    }
    
    /** ALERT METHOD SHORTHAND HELPERS **/
    public static void success(String header, String content) {
        alert(AlertType.CONFIRMATION, "Succès", header, content);
    }
    
    /** ALERT METHOD SHORTHAND HELPERS **/
    public static void warn(String header, String content) {
        alert(AlertType.WARNING, "Avertissement", header, content);
    }
    
    /** ALERT METHOD SHORTHAND HELPERS **/
    public static void error(String header, String content) {
        alert(AlertType.ERROR, "Erreur", header, content);
    }
    
    /** ALERT METHOD SHORTHAND HELPERS **/
    public static void debug(String header, String content) {
        alert(AlertType.NONE, "Debug", header, content);
    }
}
