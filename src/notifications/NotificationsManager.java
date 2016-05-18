/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifications;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import resources.ResourceLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;
import javafx.stage.Window;
import screens.Screens;
import screens.ScreensManager;

/**
 *
 * @author sebastien
 */
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
}
