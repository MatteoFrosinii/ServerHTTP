package com.example;
import java.io.File;
import java.time.LocalDateTime;

public class ServerResponse {
    private static ServerResponse r;
    private ServerResponse(){}
    public static synchronized ServerResponse getInstance(){
        if (r == null) {
            r = new ServerResponse();
        }
        return r;
    }

    public static void SendGoodResponse(ClientThread clientThread){
        clientThread.getClientConnection().send("HTTP/1.1 200 Ok");
        clientThread.getClientConnection().send("Date : " + LocalDateTime.now().toString());
        clientThread.getClientConnection().send("Server: server275");
        clientThread.getClientConnection().send("Content-Type: text/plain; charset=UTF-8");
        clientThread.getClientConnection().send("Content-Length: " + new File("demo" + clientThread.getRequestedPath()).length());
        clientThread.getClientConnection().send("");
    }

    public static void SendBadResponse(ClientThread clientThread){
        clientThread.getClientConnection().send("HTTP/1.1 404 Not Found");
        clientThread.getClientConnection().send("Date : " + LocalDateTime.now().toString());
        clientThread.getClientConnection().send("Server: server275");
        clientThread.getClientConnection().send("Content-Type: text/plain; charset=UTF-8");
        clientThread.getClientConnection().send("Content-Length: 26");
        clientThread.getClientConnection().send("");
        clientThread.getClientConnection().send("The resource was not found");
    }
}
