package org.sync.clipboard.net;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class Client extends WebSocketClient {


    private static final Logger log = LoggerFactory.getLogger(Client.class);

    public Client(URI uri) throws URISyntaxException {
        super(uri);
    }

    public void send(String message) {
        while (true) {
            try {
                if (isOpen()) {
                    super.send(message);
                    log.info("send:{}", message);
                    break;
                }
                Thread.sleep(50);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }


    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("onOpenStatus:{}", serverHandshake.getHttpStatus());
    }

    @Override
    public void onMessage(String s) {
        log.info("client:msg:{}", s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("onCloseStatus:{}", s);
    }

    @Override
    public void onError(Exception e) {

    }


}
