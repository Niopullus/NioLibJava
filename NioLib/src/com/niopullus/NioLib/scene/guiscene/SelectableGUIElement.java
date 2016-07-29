package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.draw.StringSize;
import com.niopullus.NioLib.scene.*;

import java.awt.*;
import java.util.List;

/**GUIElement that can be informed that they have been selected by the GUIScene
 * Created by Owen on 4/1/2016.
 */
public class SelectableGUIElement extends GUIElement {

    private Background selectedBG;
    private Background selectedBorderBG;
    private Color selectedTextColor;
    private boolean selected;
    private boolean overrideMouse;
    private boolean overrideArrows;
    private boolean overrideKeys;
    private boolean overrideMouseWheel;

    public SelectableGUIElement(final String content, final Theme theme, final int fontSize, final GUISize size) {
        super(content, theme, fontSize, size);
        selectedTextColor = Color.black;
        selected = false;
        overrideMouse = false;
        overrideArrows = false;
        overrideKeys = false;
        overrideMouseWheel = false;
        setTheme(theme);
    }

    public Color getSelectedColor() {
        return selectedBG.getColor();
    }

    public Color getSelectedBorderColor() {
        return selectedBorderBG.getColor();
    }

    public Color getSelectedTextColor() {
        return selectedTextColor;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public Background getSelectedBG() {
        return selectedBG;
    }

    public Background getSelectedBorderBG() {
        return selectedBorderBG;
    }

    public void setSelectedColor(final Color color) {
        selectedBG.setColor(color);
    }

    public void setSelectedBorderColor(final Color color) {
        selectedBorderBG.setColor(color);
    }

    public void setSelectedTextColor(final Color color) {
        selectedTextColor = color;
    }

    public void setTheme(final Theme t) {
        super.setTheme(t);
        if (selectedBG != null && selectedBorderBG != null) {
            selectedBG.setColor(t.getSelectedBgColor());
            selectedBorderBG.setColor(t.getSelectedBorderColor());
            selectedTextColor = t.getSelectedTextColor();
        }
    }

    public void setupBackgrounds() {
        super.setupBackgrounds();
        selectedBG = new ColorBackground(Color.WHITE);
        selectedBorderBG = new ColorBackground(Color.CYAN);
    }

    public void updateBackgrounds() {
        super.updateBackgrounds();
        updateSelectedBG();
        updateSelectedBorder();
    }

    private void updateSelectedBG() {
        if (selectedBG != null) {
            selectedBG.setWidth(getFieldWidth());
            selectedBG.setHeight(getFieldHeight());
        }
    }

    private void updateSelectedBorder() {
        if (selectedBorderBG != null) {
            selectedBorderBG.setWidth(getWidth());
            selectedBorderBG.setHeight(getHeight());
        }
    }

    public void select() {
        selected = true;
    }

    public void deselect(final boolean force) {
        selected = false;
    }

    public void parcelDraw(final Canvas canvas) {
        if (selected) {
            final Font font = getFont();
            final int heightGap = getHeightGap();
            final int fieldHeight = getFieldHeight();
            final FontMetrics metrics = StringSize.getFontMetrics(font);
            final int height = metrics.getAscent() - metrics.getDescent();
            final int displayLines = getDisplayLines();
            final int borderSpacing = getBorderSpacing();
            final int lineGap = getLineGap();
            final List<Integer> displayChars = getDisplayChars();
            final int linesHeight = displayLines * (height + lineGap) - lineGap;
            final int hrGap = (fieldHeight - heightGap * 2 - linesHeight) / 2;
            int yPos;
            canvas.o.parcel(selectedBorderBG, 0, 0, 5, 0);
            canvas.o.parcel(selectedBG, borderSpacing, borderSpacing, 10, 0);
            yPos = fieldHeight + borderSpacing - heightGap - hrGap - height;
            for (int i = 0; i < displayLines; i++) {
                final String line = getLineDisplay(i);
                final String initLine = getLine(i);
                final Integer chars = displayChars.get(i);
                final String displayLine;
                final String initDisplayLine;
                final int xPos;
                if (chars != null) {
                    displayLine = line.substring(0, chars + 1) + "...";
                    initDisplayLine = initLine.substring(0, chars + 1) + "...";
                } else {
                    displayLine = line;
                    initDisplayLine = initLine;
                }
                xPos = getXPos(initDisplayLine);
                canvas.o.text(displayLine, selectedTextColor, font, xPos, yPos, 20, 0, 1);
                yPos -= lineGap + height;
            }
        } else {
            super.parcelDraw(canvas);
        }
    }

    public void activate() {
        //To be overridden
    }

    public void upArrow() {
        //To be overridden
    }

    public void downArrow() {
        //To be overridden
    }

    public void keyPress(final Scene.KeyPack pack) {
        //To be overridden
    }

    public void keyRelease(final Scene.KeyPack pack) {
        //To be overridden
    }

    public void moveMouse(final Scene.MousePack pack) {
        //To be overridden
    }

    public void mousePress(final Scene.MousePack pack) {
        //To be overridden
    }

    public void moveMouseWheel(final Scene.MouseWheelPack pack) {
        //To be overridden
    }

    public boolean isOverrideMouse() {
        return overrideMouse;
    }

    public boolean isOverrideArrows() {
        return overrideArrows;
    }

    public boolean isOverrideKeys() {
        return overrideKeys;
    }

    public boolean isOverrideMouseWheel() {
        return overrideMouseWheel;
    }

    public void enableOverrideMouse() {
        overrideMouse = true;
    }

    public void enableOverrideArrows() {
        overrideArrows = true;
    }

    public void enableOverrideKeys() {
        overrideKeys = true;
    }

    public void enableOverrideMouseWheel() {
        overrideMouseWheel = true;
    }

    public void disableOverrideMouse() {
        overrideMouse = false;
    }

    public void disableOverrideArrows() {
        overrideArrows = false;
    }

    public void disableOverrideKeys() {
        overrideKeys = false;
    }

    public void disableOverrideMouseWheel() {
        overrideMouseWheel = false;
    }

}
