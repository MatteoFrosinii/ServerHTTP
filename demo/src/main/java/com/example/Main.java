package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server avviato");
            while (true) {
                Socket s = serverSocket.accept();
                System.out.println("Client " + s.getRemoteSocketAddress().toString() + " connesso al server");
                ClientThread clientThread = new ClientThread(s);
                System.out.println(s.getRemoteSocketAddress().toString() + " diventa il client " + clientThread.getName());
                clientThread.start();
            }
        } catch (IOException ignoredException){
        }
    }
}