package dev.baaart;

import dev.baaart.difftool.model.ClipboardGrabber;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OutputTextGrabberTest {

    private static final String TEST_STRING = "clipboard test";
    private static final String EMPTY_STRING = "";
    private final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private StringSelection selection;

    @SneakyThrows
    @Test
    void get() {
        selection = new StringSelection(TEST_STRING);
        clipboard.setContents(selection, null);
        try {
            assertEquals(TEST_STRING, ClipboardGrabber.INSTANCE.getContents());
        } catch (Exception ex) {

        }
    }

    @SneakyThrows
    @Test
    void emptyTest() {
        selection = new StringSelection(EMPTY_STRING);
        clipboard.setContents(selection, null);
        try {
            assertEquals(EMPTY_STRING, ClipboardGrabber.INSTANCE.getContents());
        } catch (Exception ex) {

        }
    }
}