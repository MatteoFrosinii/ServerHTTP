package com.example;

import java.net.Socket;
import com.example.ClientConnection;

public class ClientThread extends Thread{
    private Socket s;
    private Boolean running = true;
    private ClientConnection clientConnection;
    // 1 : start
    // 2 : data
    // 3 : done
    private int connectionState;

    private String requestedPath;
    
    public String getRequestedPath() {
        return requestedPath;
    }

    public void setRequestedPath(String requestedPath) {
        this.requestedPath = requestedPath;
    }

    public ClientThread (Socket s){
        this.s = s;
    }    

    public Socket getSocket() {
        return s;
    }

    public void setSocket(Socket s) {
        this.s = s;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    
    public int getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(int connectionState) {
        this.connectionState = connectionState;
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    @Override
    public void run(){
        ClientConnection connessione = new ClientConnection(s);
        setClientConnection(connessione);
        setConnectionState(1);
        do {
            PacketManager.getInstance().packetRead(connessione.getLine(), this);
        } while (isRunning());
    }
}