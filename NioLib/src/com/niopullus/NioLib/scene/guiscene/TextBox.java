package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.draw.StringSize;
import com.niopullus.NioLib.scene.Scene;

import java.awt.*;
import java.awt.event.KeyEvent;

/**Editable text field for GUIScenes
 * Created by Owen on 3/5/2016.
 */
public class TextBox extends SelectableGUIElement {

    private boolean expand;
    private int tick;
    private int currentLine;
    private int width;
    private int height;
    private int lineLimit;

    public TextBox(final String content, final Font font, final int widthGap, final int heightGap, final int _width, final int lines) {
        super(content, font, widthGap, heightGap);
        expand = false;
        tick = 0;
        currentLine = 0;
        width = _width;
        lineLimit = lines;
        determineDimensions();
        updateBackgrounds();
        fillLines();
    }

    public TextBox(final String content, final Theme theme, final int fontSize, final int width, final int lines) {
        this(content, theme.getFont(fontSize), theme.getWidthGap(), theme.getHeightGap(), width, lines);
    }

    private void fillLines() {
        for (int i = 1; i < lineLimit; i++) {
            addLine("");
        }
    }

    public void deselect(final boolean force) {
        if (expand) {
            if (force) {
                super.deselect(true);
                expand = false;
            }
        } else {
            super.deselect(force);
        }
    }

    public void activate() {
        if (!expand) {
            expand = true;
            enableOverrideKeys();
        } else {
            expand = false;
            disableOverrideKeys();
        }
    }

    public void keyPress(final Scene.KeyPack pack) {
        if (expand) {
            final String line = getLine(currentLine);
            if (pack.code == KeyEvent.VK_BACK_SPACE) {
                if (line.length() - 1 >= 0) {
                    setContent(currentLine, line.substring(0, line.length() - 1));
                }
            } else if (pack.code == KeyEvent.VK_UP) {
                if (currentLine - 1 >= 0) {
                    currentLine--;
                }
            } else if (pack.code == KeyEvent.VK_DOWN) {
                if (currentLine + 1 < lineLimit) {
                    currentLine++;
                }
            } else if (pack.letter != KeyEvent.CHAR_UNDEFINED) {
                final String potLine = line + pack.letter;
                final FontMetrics metrics = StringSize.getFontMetrics(getFont());
                if (metrics.stringWidth(potLine) + getWidthGap() * 2 <= getFieldWidth()) {
                    setContent(currentLine, potLine);
                }
            }
        }
    }

    public void determineDimensions() {
        final FontMetrics fontMetrics = StringSize.getFontMetrics(getFont());
        final int height = fontMetrics.getAscent() - fontMetrics.getDescent();
        setFieldWidth(width - getBorderSpacing() * 2);
        setFieldHeight(lineLimit * height + getHeightGap() * 2 + (lineLimit - 1) * getLineGap());
        setWidth(width);
        setHeight(getFieldHeight() + 2 * getBorderSpacing());
    }

    public String getLineDisplay(final int index) {
        if (expand && index == currentLine) {
            if (tick < 20) {
                tick++;
            } else {
                tick = 0;
            }
            return getLine(index) + (tick < 10 ? "|" : "");
        }
        return super.getLineDisplay(index);
    }

}
