package dev.baaart;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public enum OutputTextGrabber {
    INSTANCE;

    private final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public String get() throws IOException, UnsupportedFlavorException {
        return clipboard.getData(DataFlavor.stringFlavor).toString();
    }

}
