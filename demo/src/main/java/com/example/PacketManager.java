package com.example;

public class PacketManager {
    private static PacketManager pm;

    private PacketManager() {}

    public static PacketManager getInstance() {
        if (pm == null) {
            pm = new PacketManager();
        }
        return pm;
    }

    public void packetRead(String packet, ClientThread clientThread){
        PacketDecoder.getInstance().definePacket(packet, clientThread);
    }
}