package org.sync.clipboard.listen;


import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

public interface ClipboardListen {

    default void start() {
        listen();
    }

    void change(Object object, Class<?> clazz);


    private void listen() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.addFlavorListener(e -> {
            try {
                if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                    String data = (String) clipboard.getData(DataFlavor.stringFlavor);
                    //发生改变

                    change(data, String.class);
                } else if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
                    Image data = (Image) clipboard.getData(DataFlavor.imageFlavor);
                    //发生改变
                    change(data, Image.class);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        // Keep the program running so it can listen to clipboard changes
        while (true) {
        }


    }


}
