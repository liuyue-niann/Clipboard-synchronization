package org.sync.clipboard.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public static String write() {
        try {
            Transferable contents = clipboard.getContents(null);
            return (String) contents.getTransferData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 写入剪贴板
     */
    public static void read(String text) {
        String oldText = write();
        if (oldText.equals(text)) return;
        try {
            StringSelection selection = new StringSelection(text);
            clipboard.setContents(selection, null);
            log.info("read Clipboard:{}",text);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
