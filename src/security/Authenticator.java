package security;

import app.User;
import java.io.IOException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import sockets.*;
import screens.ScreensManager;
import notifications.NotificationsManager;

public class Authenticator {

    public static BooleanProperty isAuth = new SimpleBooleanProperty(false);
    public static User user;

    public static boolean login(PaSocketMessageLogin msg) {
        boolean isLogged = false;
        
        try {
            PaSocketClient.sendObject(msg);

            PaSocketMessage read = PaSocketClient.readObject();
            
            PaSocketResponse res = (PaSocketResponse) read;

            if(res.getErrors().size() > 0) {
                ScreensManager.toggleLoadingBar();
                
                String notifications = "";
                
                for(int i=0 ; i<res.getErrors().size() ; i++) {
                    PaSocketResponseError err = (PaSocketResponseError) res.getErrors().get(i);
                    notifications += err.getMessage() + "\n";
                    
                    System.err.println(err);
                }

                NotificationsManager.error("Erreur lors de la connexion", notifications);
            } else {
                isLogged = true;
                user = (User) res.getContent();
            }

            if(ScreensManager.controller != null) {
                ScreensManager.controller.toggleFreeze(false);
            }
            
        } catch (IOException|ClassNotFoundException ex) {
            System.err.println(ex);
        }
        
        return isLogged;
    }

    public static boolean register() {
        isAuth.set(true);

        return isAuth.get();
    }

    public static void logout() {
        isAuth.set(false);
    }
}