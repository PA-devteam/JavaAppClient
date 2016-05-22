package security;

import app.User;
import config.ConfigManager;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import sockets.*;
import screens.ScreensManager;
import notifications.NotificationsManager;
import screens.Screens;

public class Authenticator {

    // Current logged status of user
    public static BooleanProperty isAuth = new SimpleBooleanProperty(false);
    // Reference of current logged user
    public static User user;

    public static void authenticate(PaSocketMessage msg) {

        /*****************************************************************/
        /* Note : Need to call runLater because Thread synchronisation ! */
        /*****************************************************************/        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                PaSocketResponse res = (PaSocketResponse) msg;

                if (res.getErrors().size() > 0) {
                    isAuth.set(false);

                    String notifications = "";
                    int errorsNb = res.getErrors().size();
                    PaSocketResponseError err;

                    for (int i = 0; i < errorsNb; i++) {
                        err = (PaSocketResponseError) res.getErrors().get(i);
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
                    
                    ScreensManager.setContent(Screens.WORKSPACE);
                }

                if (ScreensManager.controller != null) {
                    ScreensManager.controller.toggleFreeze(false);
                }
            }
        });
    }

    public static void logout() {
        isAuth.set(false);
    }
}
