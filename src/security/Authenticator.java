package security;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Authenticator {
    
    public static BooleanProperty isAuth = new SimpleBooleanProperty(false);
    
    public static boolean login(String userName, String userPassword) {             
        isAuth.set(("demo".equals(userName) && "demo".equals(userPassword)));
        return isAuth.get();
    }
    
    public static boolean register() {
        isAuth.set(true);
        
        return isAuth.get();
    }
    
    public static void logout() {
        isAuth.set(false);
    }
}