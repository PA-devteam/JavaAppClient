package sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import screens.Screens;
import screens.ScreensManager;

public class PaSocketClient extends Thread implements Runnable {

    private static Socket sock;
    private static ObjectOutputStream objectWriter;
    private static ObjectInputStream objectReader;
    private static String nameTest;
    private static int retries;

    public PaSocketClient() {
        init();
    }

    public PaSocketClient(String ip, int port) {
        try {
            init();
            nameTest = "client";

            // Initialise socket connection
            sock = new Socket(ip, port);
        } catch (IOException ex) {
            if(ex instanceof ConnectException) {
                System.err.println("Socket connection failure detected");
            } else {
                Logger.getLogger(PaSocketClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void init() {
        // Init writer and reader for Object communication
        objectWriter = null;
        objectReader = null;
    }

    @Override
    public void run() {
        try {
            handleGuiSocketError(Screens.LOGIN, true);            

            // Initialise stream handlers
            initStreamHandlers();
            
            // Reset number of connection retries
            retries = 0;
        } catch (IOException ex) {
            if(ex instanceof SocketException) {
                // Increment connection retries
                retries++;

                // Try to reconnect
                retryToConnect();
            } else {
                Logger.getLogger(PaSocketClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void retryToConnect() {
        // Set SOCKET_RETRY screen
        handleGuiSocketError(Screens.SOCKET_RETRY, false);

        if(retries>0 && retries <= 3) {
            System.err.println("Trying to reconnect, attempt " + retries + "/3");
            try {
                System.err.println("Waiting " + 1000*retries + "ms");
                // Wait for some time in ms
                sleep(1000 * retries);
                // Re run initialisation
                run();
            } catch (InterruptedException ex1) {
                Logger.getLogger(PaSocketClient.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } else {
            handleGuiSocketError(Screens.SOCKET_FAILED, false);
            retries = 0;
        }
    }
    
    private void handleGuiSocketError(Screens screen, boolean locked) {
        System.out.println(screen);
        /*****************************************************************/
        /* Note : Need to call runLater because Thread synchronisation ! */
        /*****************************************************************/
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                PaSocket.isUnLocked.setValue(locked);
                ScreensManager.setContent(screen);
            }
        });        
    }

    private static void initStreamHandlers() throws IOException {
        if(sock != null) {
            // Get output stream
            OutputStream output = sock.getOutputStream();
            
            // Get input stream
            InputStream input = sock.getInputStream();

            // Init writer and reader for Object communication
            objectWriter = new ObjectOutputStream(output);
            objectReader = new ObjectInputStream(input);
        } else {
            throw new SocketException("No living connection");
        }
    }

    public static PaSocketMessage readObject() throws IOException, ClassNotFoundException {
        PaSocketMessage response;
        Object msg;

        msg = objectReader.readObject();
        response = (PaSocketMessage) msg;

        System.out.println("\t * " + nameTest + " Received message " + response.getAction());

        return response;
    }

    public static void sendObject(PaSocketMessage pMsg) {
        try {
            objectWriter.writeObject(pMsg);
            objectWriter.flush();

            System.out.println(nameTest + " : " + pMsg);
        } catch (IOException ex) {
            Logger.getLogger(PaSocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}