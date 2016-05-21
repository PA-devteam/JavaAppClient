package screens;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sockets.PaSocket;

public class FXMLErrorSocketFailedController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    @FXML
    private void reconnect() {
        PaSocket.initialise();
    }
}
