/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appclient;

import sockets.PaSocketClient;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sockets.PaSocketMessageLogin;
import sockets.PaSocketMessageRegister;

/**
 *
 * @author florian
 */
public class AppClient extends Application {

    private String ip = "127.0.0.1";
    private int port = 3003;
    private String frameTitle = "P.A.G.E. - Client";
    private int frameWidth = 640;
    private int frameHeight = 480;

    @Override
    public void start(Stage primaryStage) {

        PaSocketClient client = new PaSocketClient(ip, port);
        // start method from Thread Class init a new thread and call run method
        client.start();

        StackPane root = new StackPane();

        //Création d'un bouton Register
        Button register = new Button("Register");

        register.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                //instructions à exécuter lors de cet événement
                PaSocketMessageRegister pasr = new PaSocketMessageRegister("florian", "condoumi", "fcondoumi", "12345", "12345", "f@g.com");

                PaSocketClient.sendObject(pasr);
            }
        });
        
           Button login = new Button("Login");

        login.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                //instructions à exécuter lors de cet événement
                PaSocketMessageLogin pasl = new PaSocketMessageLogin("test", "test");

                PaSocketClient.sendObject(pasl);
            }
        });

       root.getChildren().add(login);
       // root.getChildren().add(register);

        Scene scene = new Scene(root, frameWidth, frameHeight);

        primaryStage.setTitle(frameTitle);
        primaryStage.setScene(scene);

        primaryStage.show();
        // Initialise a new socket client
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
