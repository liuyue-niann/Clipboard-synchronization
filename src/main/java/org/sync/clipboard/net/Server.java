package org.sync.clipboard.net;


import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sync.clipboard.sync.ClipboardApp;

import java.net.InetSocketAddress;

public class Server extends WebSocketServer {


    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public Server(int i) {
        super(new InetSocketAddress(i));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        log.info("接收数据:{}", s);
        //写入剪贴板
        ClipboardApp.read(s);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        throw new RuntimeException(e);
    }

    @Override
    public void onStart() {
        log.info("started...");
    }


}
