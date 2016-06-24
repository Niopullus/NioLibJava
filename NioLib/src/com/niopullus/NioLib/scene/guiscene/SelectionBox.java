package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.draw.Canvas;
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
        this.selection = 0;
        this.expand = false;
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

    public void parcelDraw(final Canvas canvas) {
        final Background bg = getBG();
        final Background borderBG = getBorderBG();
        final Background selectedBG = getSelectedBG();
        final Background selectedBorderBG = getSelectedBorderBG();
        int yPos = -(getLineCount() * getHeight() / 2);
        for (int i = 0; i < getLineCount(); i++) {
            final String line = getLine(i);
            final int xPos = getXPos(line);
            final Background activateBG = i != selection ? bg : selectedBG;
            final Background activateBorder = i != selection ? borderBG : selectedBorderBG;
            canvas.o.parcel(activateBG, xPos, yPos, 0, 0);
            canvas.o.parcel(activateBorder, xPos + getBorderSpacing(), yPos + getBorderSpacing(), 0, 0);
            yPos += getHeight();
        }
    }

}
