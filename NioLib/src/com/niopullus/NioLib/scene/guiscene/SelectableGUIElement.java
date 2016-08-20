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

    public Color getSelectedTextColor() {
        return selectedTextColor;
    }

    public boolean getSelected() {
        return selected;
    }

    public Background getSelectedBG() {
        return selectedBG;
    }

    public Background getSelectedBorderBG() {
        return selectedBorderBG;
    }

    public void setSelectedTextColor(final Color color) {
        selectedTextColor = color;
    }

    public void setTheme(final Theme theme) {
        super.setTheme(theme);
        if (selectedBG instanceof ColorBackground) {
            final ColorBackground colorBG = (ColorBackground) selectedBG;
            colorBG.setColor(theme.getSelectedBgColor());
        }
        if (selectedBorderBG instanceof ColorBackground) {
            final ColorBackground colorBorder = (ColorBackground) selectedBorderBG;
            colorBorder.setColor(theme.getSelectedBorderColor());
        }
        selectedTextColor = theme.getSelectedTextColor();
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

    public Background getCurrentBG() {
        return selected ? selectedBG : super.getCurrentBG();
    }

    public Background getCurrentBorder() {
        return selected ? selectedBorderBG : super.getCurrentBorder();
    }

    public Color getCurrentTextColor() {
        return selected ? selectedTextColor : super.getCurrentTextColor();
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

    public void mouseRelease(final Scene.MousePack pack) {
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
