package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Scanner;

public class DataManager {
    private static DataManager dm;

    private DataManager() {}

    public static synchronized DataManager getInstance(){
        if (dm == null){
            dm = new DataManager();
        }return dm;
    }

    public static void returnFile(ClientThread clientThread) {
        File fileRequested = new File("test" + clientThread.getRequestedPath());
        System.out.println("Cerco file richiesto alla path" + fileRequested.getAbsolutePath());
        if(fileRequested.exists() && !fileRequested.isDirectory()){
            System.out.println("il file richiesto esiste");
            try {
                int index = 0;
                Scanner scanner = new Scanner(fileRequested);
                ServerResponse.SendGoodResponse(clientThread);
                System.out.println("Inizio a leggere dal file richiesto da " + clientThread.getName());
                
                InputStream input = new FileInputStream(fileRequested);
                byte[] buf = new byte[8192];
                int n;
                while((n = input.read(buf)) != -1){
                    clientThread.getClientConnection().getOut().write(buf, 0, n);
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
