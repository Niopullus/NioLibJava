package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.draw.StringSize;
import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.dynscene.Dir;

import java.awt.*;

/**A GUIElement that allows the user to select between preset choices
 * Created by Owen on 3/31/2016.
 */
public class SelectionBox extends SelectableGUIElement {

    private int selection;
    private boolean expand;

    public SelectionBox(final String content, final Font font, final int x, final int y, final int widthGap, final int heightGap) {
        super(content, font, x, y, widthGap, heightGap);
        selection = 0;
        expand = false;
        determineDimensions();
        updateBackgrounds();
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
        final Dir dir = pack.direction;
        if (dir == Dir.N) {
            downArrow();
        } else if (dir == Dir.S) {
            upArrow();
        }
    }

    public void parcelDraw(final Canvas canvas) {
        if (expand) {
            Background bg;
            Background border;
            Color textColor;
            int yPos = (int) (-getHeight() * ((double) selection - 0.5 * getLineCount() + 0.5));
            for (int i = 0; i < getLineCount(); i++) {
                final String line = getLine(i);
                final int xPos = getXPos(line);
                bg = i != selection ? getBG() : getSelectedBG();
                border = i != selection ? getBorderBG() : getSelectedBorderBG();
                textColor = i != selection ? getTextColor() : getSelectedTextColor();
                canvas.o.parcel(border, 0, yPos, 20, 0);
                canvas.o.parcel(bg, getBorderSpacing(), yPos + getBorderSpacing(), 30, 0);
                canvas.o.text(line, textColor, getFont(), xPos, yPos + getBorderSpacing() + getHeightGap(), 40, 0);
                yPos += getHeight();
            }
        } else {
            final Background bg = !getSelected() ? getBG() : getSelectedBG();
            final Background border = !getSelected() ? getBorderBG() : getSelectedBorderBG();
            final Color textColor = !getSelected() ? getTextColor() : getSelectedTextColor();
            final FontMetrics metrics = StringSize.getFontMetrics(getFont());
            final int height = metrics.getAscent() - metrics.getDescent();
            final int yPos = getBorderSpacing() + getHeightGap();
            final String line = getLineDisplay(selection);
            final int xPos = getXPos(getLine(selection));
            canvas.o.parcel(border, 0, 0, 20, 0);
            canvas.o.parcel(bg, getBorderSpacing(), getBorderSpacing(), 0, 0);
            canvas.o.text(line, textColor, getFont(), xPos, yPos, 20, 0);
        }
    }

    public void determineDimensions() {
        final FontMetrics metrics = StringSize.getFontMetrics(getFont());
        final int stringHeight = metrics.getAscent() - metrics.getDescent();
        int stringWidth = 0;
        for (int i = 0; i < getLineCount(); i++) {
            final String line = getLine(i);
            final int tempWidth = metrics.stringWidth(line);
            if (tempWidth > stringWidth) {
                stringWidth = tempWidth;
            }
        }
        setFieldWidth(stringWidth + 2 * getWidthGap());
        setFieldHeight(stringHeight + 2 * getHeightGap());
        setWidth(getFieldWidth() + getBorderSpacing() * 2);
        setHeight(getFieldHeight() + getBorderSpacing() * 2);
    }

}
