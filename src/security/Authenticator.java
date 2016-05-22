package security;

import app.User;
import config.ConfigManager;
import java.io.IOException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import sockets.*;
import screens.ScreensManager;
import notifications.NotificationsManager;

public class Authenticator {
    // Current logged status of user
    public static BooleanProperty isAuth = new SimpleBooleanProperty(false);
    // Reference of current logged user
    public static User user;

    public static void authenticate(PaSocketMessage msg) {
        try {
            PaSocketClient.sendObject(msg);

            PaSocketMessage read = PaSocketClient.readObject();
            
            PaSocketResponse res = (PaSocketResponse) read;

            if(res.getErrors().size() > 0) {
                isAuth.set(false);
                
                String notifications = "";
                
                for(int i=0 ; i<res.getErrors().size() ; i++) {
                    PaSocketResponseError err = (PaSocketResponseError) res.getErrors().get(i);
                    notifications += err.getMessage() + "\n";
                    
                    System.err.println(err);
                }

                NotificationsManager.error("Erreur lors de l'enregistrement", notifications);
            } else {
                isAuth.set(true);
                user = (User) res.getContent();
                
                // Save the user as last logged user
                ConfigManager.setProperty("login_last_logged", user.getUsername());
                // Save the properties file
                ConfigManager.save("config.properties");                
            }

            if(ScreensManager.controller != null) {
                ScreensManager.controller.toggleFreeze(false);
            }
            
        } catch (IOException|ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }

    public static void logout() {
        isAuth.set(false);
    }
}