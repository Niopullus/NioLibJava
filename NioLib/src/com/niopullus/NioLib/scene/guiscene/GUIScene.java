package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.draw.Draw;
import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.dynscene.Dir;
import com.niopullus.NioLib.utilities.Utilities;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**Manages elements of a scene designed to serve as a GUI
 * Buttons should be instance variables so they can be referenced from buttonActivate()
 * Created by Owen on 3/5/2016.
 */
public class GUIScene extends Scene {

    private List<GUIElement> elements;
    private List<SelectableGUIElement> selectableElements;
    private Background background;
    private SelectableGUIElement selected;
    private int selectedIndex;
    private boolean invertDirection;

    public GUIScene() {
        super();
        this.elements = new ArrayList<>();
        this.selectableElements = new ArrayList<>();
        this.selected = null;
        this.invertDirection = false;
        this.background = new ColorBackground(Color.GRAY, Main.Width(), Main.Height());
        this.selectedIndex = 0;
    }

    public final void draw() {
        background.draw(0, 0, 0, Draw.DrawMode.ORIGIN);
        for (GUIElement guiElement : elements) {
            guiElement.draw();
        }
        if (getSubscene() != null) {
            final Scene subScene = getSubscene();
            subScene.draw();
        }
    }

    public void keyPress(final KeyPack pack) {
        switch (pack.code) {
            case KeyEvent.VK_UP: arrow(Dir.N); break;
            case KeyEvent.VK_DOWN: arrow(Dir.S); break;
            case KeyEvent.VK_ENTER: activate(); break;
            default: selected.keyPress(pack);
        }
    }

    private void arrow(final Dir dir) {
        if (dir == Dir.N) {
            if (!invertDirection) {
                pressUp();
            } else {
                pressDown();
            }
        } else if (dir == Dir.S) {
            if (!invertDirection) {
                pressDown();
            } else {
                pressUp();
            }
        }
    }

    public void addElement(final GUIElement guiElement) {
        elements.add(guiElement);
        guiElement.setGUIScene(this);
        if (guiElement instanceof SelectableGUIElement) {
            selectableElements.add((SelectableGUIElement) guiElement);
        }
    }

    public void pressDown() {
        if (selected.isOverrideArrows()) {
            selected.downArrow();
        } else if (selected != null && selectedIndex - 1 >= 0) {
            selected.deselect();
            selectedIndex--;
            selected = selectableElements.get(selectedIndex);
            selected.select();
        }
    }

    public void pressUp() {
        if (selected.isOverrideArrows()) {
            selected.upArrow();
        } else if (selected != null && selectedIndex + 1 < selectableElements.size()) {
            selected.deselect();
            selectedIndex++;
            selected = selectableElements.get(selectedIndex);
            selected.select();
        }
    }

    public void activate() {
        if (selected != null) {
            final Rectangle rect = selected.getRectOrigin();
            if (Utilities.pointInRect(rect, getMousePos())) {
                selected.activate();
            }
        }
    }

    public void buttonActivate(final SelectableGUIElement element) {
        //To be overridden
    }

    public void setBackgroundColor(Color color) {
        background.setColor(color);
    }

    public Color getBackgroundColor() {
        return background.getColor();
    }

    public void setBackground(final Background background) {
        this.background = background;
    }

    public SelectableGUIElement getSelected() {
        return selected;
    }

    public void setSelected(final int index) {
        selected = selectableElements.get(index);
    }

    public void setSelected(final SelectableGUIElement element) {
        selected = element;
    }

    public void enableInvertDirection() {
        invertDirection = true;
    }

    public void disableInvertDirection() {
        invertDirection = false;
    }

    public void toggleInvertDirection() {
        invertDirection = !invertDirection;
    }

    public void mouseMove(final MousePack pack) {
        if (selectableElements.size() > 0) {
            if (!selected.isOverrideMouse()) {
                for (SelectableGUIElement element: selectableElements) {
                    final Rectangle rect = element.getRectOrigin();
                    if (Utilities.pointInRect(rect, getMousePos())) {
                        element.select();
                        selected = element;
                    } else {
                        if (selected != element) {
                            element.deselect();
                        }
                    }
                }
            } else {
                selected.moveMouse(pack);
            }
        }
    }

    public void mousePress(final MousePack pack) {
        if (!selected.isOverrideMouse()) {
            activate();
        } else {
            selected.mousePress(pack);
        }

    }

    public void mouseWheelMoved(final MouseWheelPack pack) {
        if (selected.isOverrideMouseWheel()) {
            selected.moveMouseWheel(pack);
        }
    }

}
