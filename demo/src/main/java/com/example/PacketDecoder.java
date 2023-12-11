package com.example;

public class PacketDecoder {
    private static PacketDecoder pd;

    private PacketDecoder() {
    }

    public static PacketDecoder getInstance() {
        if (pd == null) {
            pd = new PacketDecoder();
        }
        return pd;
    }

    public void definePacket(String packet, ClientThread clientThread) {
        switch (clientThread.getConnectionState()) {
            case 1:
                String[] dataBySpace = packet.split(" ");
                if (dataBySpace.length == 3) {
                    if (dataBySpace[2].contains("HTTP")) {
                        clientThread.setConnectionState(2);
                        clientThread.setRequestedPath(dataBySpace[1]); //.replaceFirst("/", "")
                        System.out.println("Iniziato il data stream da parte di " + clientThread.getName() + " :");
                        System.out.println("Data from " + clientThread.getName() + " : " + packet);
                        break;
                    }
                }
                System.out.println("Errore : il client " + clientThread.getName() + " non ha inviato il primo pacchetto in modo corretto");
                break;

            case 2:
                if(packet.trim().equals("")){
                    System.out.println("Data stream finito da " + clientThread.getName());
                    clientThread.setConnectionState(3);
                    System.out.println("Processo la richiesta ricevuta da client " + clientThread.getName());
                    dataManager.returnFile(clientThread);
                    break;
                }
                System.out.println("Data from " + clientThread.getName() + " : " + packet);
                break;
                
            default:
                System.out.println(clientThread.getConnectionState() + "???");
                break;
        }
    }
}