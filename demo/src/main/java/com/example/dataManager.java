package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class dataManager {
    private static dataManager dm;

    private dataManager() {}

    public static synchronized dataManager getInstance(){
        if (dm == null){
            dm = new dataManager();
        }return dm;
    }

    public static void returnFile(ClientThread clientThread) {
        File fileRequested = new File("demo" + clientThread.getRequestedPath());
        System.out.println("Cerco file richiesto alla path" + fileRequested.getAbsolutePath());
        if(fileRequested.exists() && !fileRequested.isDirectory()){
            System.out.println("il file richiesto esiste");
            try {
                int index = 0;
                Scanner scanner = new Scanner(fileRequested);
                ServerResponse.SendGoodResponse(clientThread);
                System.out.println("Inizio a leggere dal file richiesto da " + clientThread.getName());
                while (scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    index++;
                    clientThread.getClientConnection().send(line);
                    System.out.println("Leggo linea " + index);
                }
                scanner.close();
                System.out.println("Chiudo connessione con client " + clientThread.getName());
                clientThread.getSocket().close();
                clientThread.setRunning(false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Il file non esiste");
            ServerResponse.SendBadResponse(clientThread);
            System.out.println("Chiudo connessione con client " + clientThread.getName());
            try {
                clientThread.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clientThread.setRunning(false);
        }
    }

}
