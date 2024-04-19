package org.sync.clipboard.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sync.clipboard.utils.ImgUtils;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class ClipboardApp {


    final static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static final Logger log = LoggerFactory.getLogger(ClipboardApp.class);

    /**
     * 读取本机剪贴板数据
     *
     * @return string
     */
    public static Clipboard write() {
        return clipboard;
    }


    /**
     * 写入剪贴板
     */
    public static void read(String text) {
        Image image = ImgUtils.stringToImage(text);
        if (image != null) {
            read(image);
        } else {
            try {
                if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                    Transferable contents = clipboard.getContents(null);
                    String oldText = (String) contents.getTransferData(DataFlavor.stringFlavor);
                    if (!oldText.equals(text)) {
                        StringSelection selection = new StringSelection(text);
                        clipboard.setContents(selection, null);
                        log.info("写入剪贴板文本:{}", text);
                    }
                } else if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
                    Transferable contents = clipboard.getContents(null);
                    Image oldImg = (Image) contents.getTransferData(DataFlavor.imageFlavor);
                    Image newImg = ImgUtils.stringToImage(text);
                    //比较新对象是否跟剪贴板对象相同
                    if (!ImgUtils.compareImages(oldImg, newImg)) {
                        ImageSelection selection = new ImageSelection(newImg);
                        clipboard.setContents(selection, null);
                        log.info("写入剪贴板图片:{}", text);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void read(Image image) {
        try {
            ImageSelection selection = new ImageSelection(image);
            clipboard.setContents(selection, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}

class ImageSelection implements Transferable {

    Image i;

    public ImageSelection(Image i) {
        this.i = i;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] flavors = new DataFlavor[1];
        flavors[0] = DataFlavor.imageFlavor;
        return flavors;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        DataFlavor[] flavors = getTransferDataFlavors();
        for (DataFlavor dataFlavor : flavors) {
            if (flavor.equals(dataFlavor)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.equals(DataFlavor.imageFlavor) && i != null) {
            return i;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}
