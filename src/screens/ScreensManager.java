package screens;

import java.util.HashMap;
import resources.ResourceLoader;
import java.util.Vector;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

public class ScreensManager {

    public static Vector<Enum<Screens>> history = new Vector<Enum<Screens>>();
    public static StackPane container;
    public static ProgressBar loadingBar;
    public static ScreensController controller;
    public static HashMap viewParams = new HashMap<String, Object>();
    
    public static int foo = 0;

    public static void cancel() {
        if(history.size() > 0) {
            // History size minus 2, because size() start at index 1 
            // and we want the element BEFORE the last element
            setContent(history.elementAt(history.size() - 2));
        } else {
            setContent(Screens.NOT_FOUND);
        }
    }
    
    public static Enum<Screens> toReload = null;
    public static void reload(Enum<Screens> view) {
        toReload = view;
        setContent(Screens.RELOADER);
    }

    public static void setContent(Enum<Screens> view) {
        Node resource = ResourceLoader.loadView(view);

        if(resource != null) {
            container.getChildren().clear();
            container.getChildren().add(resource);
            container.setLayoutX(0);
            container.setLayoutY(0);
            
            history.add(view);
        }
    }
    
    public static void toggleLoadingBar() {
        boolean status = loadingBar.isDisabled();
        loadingBar.setDisable(!status);
        loadingBar.setProgress((status) ? -1 : 0);
    }
}