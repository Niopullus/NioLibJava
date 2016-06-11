package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.draw.Draw;
import com.niopullus.NioLib.draw.DrawElement;
import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.dynscene.Dir;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

/**
 * Created by Owen on 3/31/2016.
 */
public class SelectionBox extends SelectableGUIElement {

    private int selection;
    private boolean expand;

    public SelectionBox(final String content, final Font font, final int x, final int y, final int widthGap, final int heightGap) {
        super(content, font, x, y, widthGap, heightGap);
        this.selection = 0;
        this.expand = false;
    }

    public void addSelection(final String item) {
        addLine(item);
        selection = getLineCount() / 2;
    }

    public void activate() {
        if (!expand) {
            expand = true;
            enableOverrideArrows();
        } else {
            expand = false;
            disableOverrideArrows();
        }
    }

    public void upArrow() {
        if (selection - 1 >= 0) {
            selection--;
        }
    }

    public void downArrow() {
        if (selection + 1 < getLineCount()) {
            selection++;
        }
    }

    public void moveMouseWheel(final Scene.MouseWheelPack pack) {
        final Dir dir = pack.direction;
        if (dir == Dir.N) {
            upArrow();
        } else if (dir == Dir.S) {
            downArrow();
        }
    }

    public void draw() {
        final Background bg = getBG();
        final Background borderBG = getBorderBG();
        final Background selectedBG = getSelectedBG();
        final Background selectedBorderBG = getSelectedBorderBG();
        int yPos = getY();
        for (int i = 0; i < getLineCount(); i++) {
            final String line = getLine(i);
            final int xPos = getXPos(line);
            Draw.mode(getDrawMode()).text(line, getTextColor(), getFont(), xPos, yPos + getHeight() / 2, getZ(), 0);
            if (i != selection) {
                selectedBG.draw(getX() + getBorderWidth(), yPos + getBorderWidth(), getZ(), getDrawMode());
                selectedBorderBG.draw(getX(), yPos, getZ(), getDrawMode());
            } else {
                bg.draw(getX() + getBorderWidth(), yPos + getBorderWidth(), getZ(), getDrawMode());
                borderBG.draw(getX(), yPos, getZ(), getDrawMode());
            }
            yPos += getHeight();
        }
    }

}
