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
    private int height;
    private int lineLimit;

    public TextBox(final String content, final Theme theme, final int fontSize, final GUISize size, final int lineLimit) {
        super(content, theme, fontSize, size);
        expand = false;
        tick = 0;
        currentLine = 0;
        determineLineLimit(lineLimit, size);
        fillLines();
        fillDisplayChars();
        initHeight();
        updateDimensions();
        updateBackgrounds();
    }

    public TextBox(final String content, final Theme theme, final int fontSize, final int width, final int lines) {
        this(content, theme, fontSize, new GUISize(width, 0, true, false), lines);
    }

    public TextBox(final String content, final Theme theme, final int fontSize, final GUISize size) {
        this(content, theme, fontSize, size, 0);
    }

    private void fillLines() {
        for (int i = 1; i < lineLimit; i++) {
            addLine("");
        }
    }

    private void determineLineLimit(final int limit, final GUISize size) {
        final int lineGap = getLineGap();
        if (size.isKeepHeight()) {
            final Font font = getFont();
            lineLimit = getLinePotential(font, size.getFieldHeight() - size.getHeightGap() * 2, lineGap);
            setDisplayLines(lineLimit);
        } else {
            lineLimit = limit;
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
        final int packCode = pack.getCode();
        final char packLetter = pack.getLetter();
        if (expand) {
            final String line = getLine(currentLine);
            if (packCode == KeyEvent.VK_BACK_SPACE) {
                if (line.length() - 1 >= 0) {
                    setContent(currentLine, line.substring(0, line.length() - 1));
                }
            } else if (packCode == KeyEvent.VK_UP) {
                if (currentLine - 1 >= 0) {
                    currentLine--;
                }
            } else if (packCode == KeyEvent.VK_DOWN) {
                if (currentLine + 1 < lineLimit) {
                    currentLine++;
                }
            } else if (packLetter != KeyEvent.CHAR_UNDEFINED) {
                final String potLine = line + packLetter;
                if (StringSize.getStringWidth(potLine, getFont()) + getWidthGap() * 2 <= getFieldWidth()) {
                    setContent(currentLine, potLine);
                }
            }
        }
    }

    public void determineDimensions() {
        final int height = StringSize.getStringHeight(getFont());
        final int width = getWidth();
        setFieldWidth(width - getBorderSpacing() * 2);
        setFieldHeight(lineLimit * height + getHeightGap() * 2 + (lineLimit - 1) * getLineGap());
        setWidth(width);
        setHeight(getFieldHeight() + 2 * getBorderSpacing());
    }

    public void initWidth() {
        //Blank implementation
    }

    public void initHeight() {
        final boolean keepHeight = isKeepHeight();
        if (!keepHeight) {
            final Font font = getFont();
            final int heightGap = getHeightGap();
            final int fieldHeight;
            final int borderSpacing = getBorderSpacing();
            final int lineGap = getLineGap();
            final int stringHeight = StringSize.getStringHeight(font) * lineLimit + (lineLimit - 1) * lineGap;
            fieldHeight = stringHeight + 2 * heightGap;
            setFieldHeight(fieldHeight);
            setHeight(fieldHeight + borderSpacing * 2);
            setDisplayLines(lineLimit);
        }
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
