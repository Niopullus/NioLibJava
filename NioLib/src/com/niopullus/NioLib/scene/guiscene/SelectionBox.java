package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.draw.StringSize;
import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.dynscene.Direction;

import java.awt.*;
import java.util.List;

/**A GUIElement that allows the user to select between preset choices
 * Created by Owen on 3/31/2016.
 */
public class SelectionBox extends SelectableGUIElement {

    private int selection;
    private boolean expand;

    public SelectionBox(final String content, final Theme theme, final int fontSize, final GUISize size) {
        super(content, theme, fontSize, size);
        selection = 0;
        expand = false;
    }

    public SelectionBox(final String content, final Theme theme, final int fontSize) {
        this(content, theme, fontSize, new GUISize());
    }

    public String getContent() {
        return getLine(selection);
    }

    public void addLine(final String item) {
        super.addLine(item);
        selection = getLineCount() / 2;
    }

    public void activate() {
        if (!expand) {
            expand = true;
            enableOverrideArrows();
            enableOverrideMouseWheel();

        } else {
            expand = false;
            disableOverrideArrows();
            disableOverrideMouseWheel();
        }
    }

    public void deselect(final boolean force) {
        if (expand) {
            if (force) {
                super.deselect(true);
                expand = false;
                disableOverrideArrows();
                disableOverrideMouseWheel();
            }
        } else {
            super.deselect(force);
        }
    }

    public void upArrow() {
        if (selection + 1 < getLineCount()) {
            selection++;
        }
    }

    public void downArrow() {
        if (selection - 1 >= 0) {
            selection--;
        }
    }

    public void moveMouseWheel(final Scene.MouseWheelPack pack) {
        final Direction dir = pack.getDirection();
        if (dir == Direction.N) {
            downArrow();
        } else if (dir == Direction.S) {
            upArrow();
        }
    }

    public void parcelDraw(final Canvas canvas) {
        final Font font = getFont();
        final int textHeight = StringSize.getStringHeight(font);
        final int fieldHeight = getFieldHeight();
        final int heightGap = getHeightGap();
        final int borderSpacing = getBorderSpacing();
        if (expand) {
            Background bg;
            Background border;
            Color textColor;
            int yPos = (int) (-getHeight() * ((double) selection - 0.5 * getLineCount() + 0.5));
            for (int i = 0; i < getLineCount(); i++) {
                final String line = getLineDisplay(i);
                final List<Integer> displayChars = getDisplayChars();
                final int xPos;
                final int rhGap = (fieldHeight - heightGap * 2 - textHeight) / 2;
                final Integer lineChars = displayChars.get(i);
                final String displayLine;
                if (lineChars != null) {
                    displayLine = line.substring(0, lineChars + 1) + "...";
                } else {
                    displayLine = line;
                }
                xPos = getXPos(displayLine);
                bg = i != selection ? getBG() : getSelectedBG();
                border = i != selection ? getBorderBG() : getSelectedBorderBG();
                textColor = i != selection ? getTextColor() : getSelectedTextColor();
                canvas.o.parcel(border, 0, yPos, 20, 0);
                canvas.o.parcel(bg, getBorderSpacing(), yPos + getBorderSpacing(), 30, 0);
                canvas.o.text(displayLine, textColor, getFont(), xPos, yPos + borderSpacing + heightGap + rhGap, 40, 0, 1);
                yPos += getHeight();
            }
        } else {
            super.parcelDraw(canvas);
        }
    }

    public void determineHeight() {
        final int stringHeight = StringSize.getStringHeight(getFont());
        setFieldHeight(stringHeight + 2 * getHeightGap());
        setHeight(getFieldHeight() + getBorderSpacing() * 2);
    }

}
