package org.sync.clipboard.net;


import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

/**
 * 扫描局域网设备
 * TODO
 */
public class Scan {


    public static List<String> scanNet(String address, int port) {
        return null;
    }

    boolean isOpen(String address, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(address, port), 10);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
