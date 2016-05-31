package entities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import sun.misc.BASE64Decoder;

public class UserProperty {
    private SimpleStringProperty userFirstName;
    private SimpleStringProperty userLastName;
    private SimpleStringProperty userName;
    private SimpleStringProperty userRoles;
    private SimpleStringProperty userEmail;
    private ObjectProperty<Image> userAvatarImage;
    
    public SimpleStringProperty getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(SimpleStringProperty userFirstName) {
        this.userFirstName = userFirstName;
    }
    
    public void setUserFirstName(String userFirstName) {
        this.userFirstName.setValue(userFirstName);
    }    

    public SimpleStringProperty getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(SimpleStringProperty userLastName) {
        this.userLastName = userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName.setValue(userLastName);
    }    
    
    public SimpleStringProperty getUserName() {
        return userName;
    }

    public void setUserName(SimpleStringProperty userName) {
        this.userName = userName;
    }
    
    public void setUserName(String userName) {
        this.userName.setValue(userName);
    }    

    public SimpleStringProperty getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(SimpleStringProperty userRoles) {
        this.userRoles = userRoles;
    }
     public void setUserRoles(ArrayList<Role> userRoles) {
     if(userRoles!=null)  {
         String usrRole="";
     
         int rolesNb = userRoles.size();
         for(int i=0;i<rolesNb;i++){
//            usrRole=" ,"+userRoles.get(i).getLabel();
            usrRole = userRoles.get(i).getLabel();
            
            if(i < rolesNb-1) {
                usrRole += ", ";
            }
         }
        this.userRoles.setValue(usrRole);
     }
    }
    public void setUserRoles(String userRoles) {
        this.userRoles.setValue(userRoles);
    }    

    public ObjectProperty<Image> getUserAvatarImage() {
        return userAvatarImage;
    }

    public void setUserAvatarImage(Image userAvatarImage) {
        this.userAvatarImage.setValue(userAvatarImage);
    }
    
    public void setUserAvatarImage(String userAvatarImage) {
        if(userAvatarImage.length() > 0) {
            System.out.println("LENGTH > 0");
            try {
                ByteArrayInputStream imageStream;
                BASE64Decoder decoder = new BASE64Decoder();
                imageStream = new ByteArrayInputStream(decoder.decodeBuffer(userAvatarImage));
                this.userAvatarImage.setValue(new Image(imageStream));
                imageStream.close();
            } catch(IOException ex) {
                System.err.println(ex);
            }
        } else {
            System.out.println("NO AVATAR, LOADING DEFAULT");
            this.userAvatarImage.setValue(new Image("/resources/icon_avatar_grey.png"));
        }   
    }

    public SimpleStringProperty getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(SimpleStringProperty userEmail) {
        this.userEmail = userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail.setValue(userEmail);
    }    
    
    public UserProperty() {
        userFirstName   = new SimpleStringProperty();
        userLastName    = new SimpleStringProperty();
        userName        = new SimpleStringProperty();
        userRoles       = new SimpleStringProperty();
        userAvatarImage = new SimpleObjectProperty<>();
        userEmail       = new SimpleStringProperty();
    }
    
    public UserProperty(User usr) {
        userFirstName   = new SimpleStringProperty(usr.getFirstname());
        userLastName    = new SimpleStringProperty(usr.getLastname());
        userName        = new SimpleStringProperty(usr.getUsername());
        userRoles       = new SimpleStringProperty();        
        userAvatarImage = new SimpleObjectProperty<>();
        userEmail       = new SimpleStringProperty(usr.getEmail());
    }
}
