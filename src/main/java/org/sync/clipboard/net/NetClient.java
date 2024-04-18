package org.sync.clipboard.net;

import org.sync.clipboard.utils.ConfUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;


public class NetClient {

    /**
     * 将数据发送给所有设备
     */
    public static void send(String text) {
        Set<String> ipList = ConfUtils.getConfIpList();
        for (String ip : ipList) {
            String str = "ws://%s:7788/websocket".formatted(ip);
            try {
                URI uri = new URI(str);
                Client client = new Client(uri);
                client.connect();
                client.send(text);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
