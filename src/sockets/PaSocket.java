package sockets;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class PaSocket {
    // Ref to socket client
    private static PaSocketClient client;
    // Ref to socket server ip
    private static String ip;
    // Ref to socket server port
    private static int port;
    // Define if the socket client is available or not
    public static BooleanProperty isUnLocked = new SimpleBooleanProperty(false);
    
    public static void initialise(String pIp, int pPort) {
        // Store ip
        ip = pIp;
        // Store port
        port = pPort;

        // Initialise a new socket client
        client = new PaSocketClient(pIp, pPort);

        // Call Start method from Thread Class, init a new thread and call run method
        client.start();
    }

    public static void initialise() {
        System.out.println(ip + ":" + port);
        
        // Initialise a new socket client
        client = new PaSocketClient(ip, port);

        // Call Start method from Thread Class, init a new thread and call run method
        client.start();
    }
}
