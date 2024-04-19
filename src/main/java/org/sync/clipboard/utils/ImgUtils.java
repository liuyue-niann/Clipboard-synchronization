package org.sync.clipboard.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;

public class ImgUtils {

    private static final Logger log = LoggerFactory.getLogger(ImgUtils.class);

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        BufferedImage bufferedImage = new BufferedImage(
                image.getWidth(null),
                image.getHeight(null),
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bufferedImage;
    }

     public static String imageToString(BufferedImage image) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    private static boolean isValidBase64(String str) {
        try {
            Base64.getDecoder().decode(str);
            return true; // 如果解码成功，说明是有效的 Base64 编码
        } catch (IllegalArgumentException e) {
            return false; // 解码失败，说明不是有效的 Base64 编码
        }
    }


     public static Image stringToImage(String imageString) {
        try {
            if (!isValidBase64(imageString)) {
                return null;
            }
            byte[] imageBytes = Base64.getDecoder().decode(imageString);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            BufferedImage read = ImageIO.read(inputStream);
            return toImage(read)==null ? null:read;
        } catch (IOException e) {
            return null;
        }
    }

    private static Image toImage(BufferedImage bufferedImage) {
        return bufferedImage==null? null : bufferedImage.getScaledInstance(
                bufferedImage.getWidth(),
                bufferedImage.getHeight(),
                Image.SCALE_DEFAULT
        );
    }

}
