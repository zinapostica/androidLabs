package com.example.lab7;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author
 */
class ClientSocket extends Thread {
    public static int clientNum;
    private Socket clientSocket;
    private BufferedReader keyboardReader;
    private DataInputStream input;
    private DataOutputStream output;
    private int id;

    public ClientSocket(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        ClientSocket.clientNum++;
        this.id = ClientSocket.clientNum;
        this.keyboardReader = new BufferedReader(new InputStreamReader(System.in));
        this.input = new DataInputStream(clientSocket.getInputStream());
        this.output = new DataOutputStream(clientSocket.getOutputStream());
    }

    public void run() {
        try {
            this.output.writeUTF("Weclome to our booking system! Type in a seat number to check if it is have been reserved or not ");
            boolean booked = false;
            while (!booked && !this.clientSocket.isClosed()) {
                String input = this.input.readUTF();
                if (input.matches("[0-9]+")) {
                    boolean busy = false;
                    for (String position : Server.getPozitiiOcupate()) {
                        if (position.equals(input)) {
                            this.output.writeUTF(input + " has already been booked, please try another seat");
                                    busy = true;
                            break;
                        }
                    }
                    if (Server.getPozitiiOcupate().size() == 0 || !busy) {
                        this.output.writeUTF(input + " is available, would you like to book it? (y/n)");
                        busy = false;
                        do {
                            String input2 = this.input.readUTF();
                            switch (input2) {
                                case "y":
                                    Server.getPozitiiOcupate().add(input);
                                    this.output.writeUTF("You successfully booked position number " +
                                            input + ", thank you!");
                                    booked = true;
                                    busy = true;
                                    break;
                                case "n":
                                    this.output.writeUTF("You chose not to book position number " + input
                                            + ", please choose another position");
                                    busy = true;
                                    break;
                                default:
                                    this.output.writeUTF("Invalid response, please choose y or n");
                            }
                        } while (!busy);
                    }
                } else {
                    this.output.writeUTF("Please enter a number, no letters");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class Server extends Thread {
    private final int port;
    private static ArrayList<String> pozitiiOcupate;
    private ServerSocket serverSocket;
    private ArrayList<ClientSocket> clientSockets;

    public Server(int port) {
        this.port = port;
        this.pozitiiOcupate = new ArrayList<String>();
        this.pornesteServer();
        this.clientSockets = new ArrayList<ClientSocket>();
    }

    public static ArrayList<String> getPozitiiOcupate() {
        return pozitiiOcupate;
    }

    public boolean pornesteServer() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("Server: Server running at port " + this.port);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server: Error at starting the server");
            return false;
        }
    }

    public void run() {
        while (!this.serverSocket.isClosed()) {
            try {
                this.clientSockets.add(new ClientSocket(serverSocket.accept()));
                System.out.println("server: New Client Connection");
                this.clientSockets.get(this.clientSockets.size() - 1).start();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Server: Error connecting with client");
            }
        }
    }
}


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Server server = new Server(9876);
        server.start();
    }
}