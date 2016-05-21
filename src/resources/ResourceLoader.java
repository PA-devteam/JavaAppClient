package resources;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.text.Font;
import screens.*;

public class ResourceLoader {
    public static Node loadView(Enum<Screens> view) {
        return loadView(view.toString());
    }

    public static Node loadView(String resource) {
        Node result;

        try {
            result = FXMLLoader.load(loadPackageResource(resource));
        } catch (IOException ex) {
            System.err.println(ex);
            result = null;
        }

        return result;
    }
    
    public static void loadFont(String fontName, int fontSize) {
         Font.loadFont(loadLocalResource(fontName), fontSize);      
    }

    public static void loadStyle(String style) {
        ScreensManager.container.getScene().getStylesheets().add(loadLocalResource(style));    
    }
    
    private static String loadLocalResource(String resource) {
        return ResourceLoader.class.getResource(resource).toExternalForm();
    }
    
    private static URL loadPackageResource(String resource) {
        return ScreensManager.class.getResource(resource);
    }
}