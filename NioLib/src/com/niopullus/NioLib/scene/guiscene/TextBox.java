package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.utilities.EString;

import java.awt.*;
import java.awt.event.KeyEvent;

/**Editable text field for GUIScenes
 * Created by Owen on 3/5/2016.
 */
public class TextBox extends SelectableGUIElement {

    private boolean expand;
    private int tick;
    private int maxLines;
    private int currentLine;

    public TextBox(final String content, final Font font, final int x, final int y, final int widthGap, final int heightGap) {
        super(content, font, x, y, widthGap, heightGap);
        expand = false;
        tick = 0;
        maxLines = 5;
        currentLine = 0;
    }

    public void selectionAction() {
        if (!expand) {
            expand = true;
            enableOverrideArrows();
        } else {
            expand = false;
            disableOverrideArrows();
        }
    }

    public void keyPress(final Scene.KeyPack pack) {
        if (expand) {
            final String line = getLine(currentLine);
            if (pack.code == KeyEvent.VK_BACK_SPACE) {
                if (line.length() - 1 >= 0) {
                    setContent(currentLine, line.substring(0, line.length() - 1));
                }
            } else if (pack.code != KeyEvent.CHAR_UNDEFINED) {
                setContent(currentLine, line + pack.letter);
            }
        }
    }



}
