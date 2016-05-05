package screens;

import resources.ResourceLoader;
import java.util.Vector;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class ScreensManager {

    public static Vector<Enum<Screens>> history = new Vector<Enum<Screens>>();
    public static StackPane container;

    public static void cancel() {
        if(history.size() > 0) {
            // History size minus 2, because size() start at index 1 
            // and we want the element BEFORE the last element
            setContent(history.elementAt(history.size() - 2));
        } else {
            setContent(Screens.NOT_FOUND);
        }
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
}