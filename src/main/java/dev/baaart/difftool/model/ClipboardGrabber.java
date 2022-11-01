package dev.baaart.difftool.model;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public enum ClipboardGrabber {
    INSTANCE;

    private final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public String getContents() throws IOException, UnsupportedFlavorException {
        return clipboard.getData(DataFlavor.stringFlavor).toString();
    }

}
