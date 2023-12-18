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
        Response goodResponse = new Response(200, clientThread);
        clientThread.getClientConnection().send(goodResponse.getReplyLine());
        System.out.println("Request line inviata a " + clientThread.getName() + " : "+ goodResponse.getReplyLine());
        clientThread.getClientConnection().send(goodResponse.getDate());
        System.out.println("Data inviata a " + clientThread.getName() + " : "+ goodResponse.getDate());
        clientThread.getClientConnection().send(goodResponse.getServer());
        System.out.println("Server inviato a " + clientThread.getName() + " : "+ goodResponse.getServer());

        goodResponse.setContenType("Content-Type: " + clientThread.getFileContentType());
        clientThread.getClientConnection().send(goodResponse.getContenType());
        System.out.println("Content type inviato a " + clientThread.getName() + " : " + goodResponse.getContenType());

        goodResponse.setContentLength("Content-Length: " + new File("test" + clientThread.getRequestedPath()).length());
        clientThread.getClientConnection().send(goodResponse.getContentLength());
        System.out.println("Content lenght inviato a " + clientThread.getName() + " : " + goodResponse.getContentLength());

        clientThread.getClientConnection().send("");
        System.out.println("Inviato end a "+ clientThread.getName());
    }

    public static void SendBadResponse(ClientThread clientThread){
        Response badResponse = new Response(404, clientThread);
        clientThread.getClientConnection().send(badResponse.getReplyLine());
        System.out.println("Request line inviata a " + clientThread.getName() + " : " + badResponse.getReplyLine());
        clientThread.getClientConnection().send(badResponse.getDate());
        System.out.println("Data inviata a " + clientThread.getName() + " : " + badResponse.getDate());
        clientThread.getClientConnection().send(badResponse.getServer());
        System.out.println("Server inviato a " + clientThread.getName() + " : " + badResponse.getServer());

        badResponse.setContenType("Content-Type: text/plain; charset=UTF-8");
        clientThread.getClientConnection().send(badResponse.getContenType());
        System.out.println("Content type inviato a " + clientThread.getName() + " : " + badResponse.getContenType());

        badResponse.setContentLength("Content-Length: 26");
        clientThread.getClientConnection().send(badResponse.getContentLength());
        System.out.println("Content lenght inviato a " + clientThread.getName() + " : " + badResponse.getContentLength());

        clientThread.getClientConnection().send("");
        System.out.println("Inviato end a "+ clientThread.getName());        

        badResponse.setAddInfo("The resource was not found");
        clientThread.getClientConnection().send(badResponse.getAddInfo());
        System.out.println("addInfo aggiunte a"  + clientThread.getName() + " : " + badResponse.getAddInfo());
    }

    private static void printResponse(){
        
    }
}