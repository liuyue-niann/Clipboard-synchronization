package org.sync.clipboard;

import org.sync.clipboard.listen.ClipboardListen;
import org.sync.clipboard.net.NetClient;
import org.sync.clipboard.net.Server;

import java.awt.*;

public class SyncClipboardApp implements ClipboardListen {


    public static void main(String[] args) {
        SyncClipboardApp listen = new SyncClipboardApp();
        Server server = new Server(7788);
        server.start();
        listen.start();
    }


    @Override
    public void change(Object object, Class<?> clazz) {
        if (clazz == String.class) {
            NetClient.send((String) object);
        } else if (clazz == Image.class) {
            //TODO 未完成
        }
    }

}
