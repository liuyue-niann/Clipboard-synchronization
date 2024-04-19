package org.sync.clipboard.listen;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 剪贴板监听轮询`
 */
public abstract class ClipboardListen {

    private static final Logger log = LoggerFactory.getLogger(ClipboardListen.class);
    Transferable lastClipboardContent = null;

    private static Transferable getClipboardContent() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        return clipboard.getContents(null);
    }

    private static boolean isTransferableEqual(Transferable a, Transferable b) {
        if (a == null || b == null) {
            return a == b;
        }
        if (a.isDataFlavorSupported(DataFlavor.stringFlavor) && b.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                String dataA = (String) a.getTransferData(DataFlavor.stringFlavor);
                String dataB = (String) b.getTransferData(DataFlavor.stringFlavor);
                return dataA.equals(dataB);
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
                return false;
            }
        } else if (a.isDataFlavorSupported(DataFlavor.imageFlavor) && b.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            try {
                BufferedImage imageA = (BufferedImage) a.getTransferData(DataFlavor.imageFlavor);
                BufferedImage imageB = (BufferedImage) b.getTransferData(DataFlavor.imageFlavor);
                return compareImages(imageA, imageB);
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private static boolean compareImages(BufferedImage imageA, BufferedImage imageB) {
        if (imageA.getWidth() != imageB.getWidth() || imageA.getHeight() != imageB.getHeight()) {
            return false;
        }
        for (int y = 0; y < imageA.getHeight(); y++) {
            for (int x = 0; x < imageA.getWidth(); x++) {
                if (imageA.getRGB(x, y) != imageB.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void start() {
        listen();
    }

    public abstract void change(Object object, Class<?> clazz);

    private void listen() {
        // 创建一个线程来执行轮询任务
        Thread pollingThread = new Thread(() -> {
            while (true) {
                // 检查剪贴板内容是否发生变化
                Transferable clipboardContent = getClipboardContent();
                if (!isTransferableEqual(clipboardContent, lastClipboardContent)) {
                    if (clipboardContent != null && clipboardContent.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                        try {
                            //剪贴板新增了文字
                            Object data = clipboardContent.getTransferData(DataFlavor.stringFlavor);
                            log.info("剪贴板新增了文字:{}", data);
                            change(data, String.class);
                        } catch (UnsupportedFlavorException | IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (clipboardContent != null && clipboardContent.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                        try {
                            // 剪贴板新增了图片
                            Object data = clipboardContent.getTransferData(DataFlavor.imageFlavor);
                            log.info("剪贴板新增了图像:{}", data);
                            change(data, Image.class);
                        } catch (UnsupportedFlavorException | IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    lastClipboardContent = clipboardContent;
                }

                // 等待一段时间后再次轮询
                try {
                    Thread.sleep(1000); // 等待1秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        // 启动线程
        pollingThread.start();
    }


}
