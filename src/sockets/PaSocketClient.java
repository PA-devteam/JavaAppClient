/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author florian
 */
public class PaSocketClient extends Thread implements Runnable {

    private static Socket sock;
    private static PrintWriter writer;
    private static BufferedInputStream reader;

    private static ObjectOutputStream objectWriter;
    private static ObjectInputStream objectReader;
    private static String nameTest;

    public PaSocketClient() {
        init();
    }

    public PaSocketClient(String ip, int port) {
        try {
            nameTest = "client";

            // Initialise socket connection
            sock = new Socket(ip, port);
        } catch (IOException ex) {
            Logger.getLogger(PaSocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void init() {
        // Init writer and reader for String communication
        writer = null;
        reader = null;

        // Init writer and reader for Object communication
        objectWriter = null;
        objectReader = null;
    }

    @Override
    public void run() {
        try {
            // Initialise stream handlers
            initStreamHandlers(true);
        } catch (IOException ex) {
            Logger.getLogger(PaSocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void initStreamHandlers(Boolean pFlushWriter) throws IOException {
        // Get output stream
        OutputStream output = sock.getOutputStream();
        // Get input stream
        InputStream input = sock.getInputStream();

        // Init writer and reader for String communication
        writer = new PrintWriter(output, pFlushWriter);
        reader = new BufferedInputStream(input);

        // Init writer and reader for Object communication
        objectWriter = new ObjectOutputStream(output);
        objectReader = new ObjectInputStream(input);
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
