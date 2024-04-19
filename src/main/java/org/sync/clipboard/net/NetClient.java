package org.sync.clipboard.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sync.clipboard.utils.ConfUtils;
import org.sync.clipboard.utils.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;


public class NetClient {

    private static final Logger log = LoggerFactory.getLogger(NetClient.class);

    /**
     * 将数据发送给所有设备
     */
    public static void send(String text) {
        Set<String> ipList = ConfUtils.getConfIpList();
        for (String ip : ipList) {
            String str = "ws://%s:7788/websocket".formatted(ip);
            log.info("data——to:{}", ip);
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

    public static void send(Image img) {
        BufferedImage image = ImgUtils.toBufferedImage(img);
        String imageString = ImgUtils.imageToString(image);
        send(imageString);

    }

}
