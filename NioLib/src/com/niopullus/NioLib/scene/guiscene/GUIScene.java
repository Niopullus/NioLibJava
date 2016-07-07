package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.dynscene.Direction;
import com.niopullus.NioLib.Utilities;

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
        elements = new ArrayList<>();
        selectableElements = new ArrayList<>();
        selected = null;
        invertDirection = false;
        background = new ColorBackground(Color.GRAY, Main.Width(), Main.Height());
        selectedIndex = 0;
    }

    public final void drawScene(final Canvas canvas) {
        canvas.o.parcel(background, 0, 0, 0, 0);
        for (GUIElement guiElement : elements) {
            canvas.c(Main.Width(), Main.Height()).parcel(guiElement, guiElement.getX(), guiElement.getY(), guiElement.getZ(), 0);
        }
    }

    public final void tick() {
        update();
    }

    public void update() {
        //To be overridden
    }

    public void keyPress(final KeyPack pack) {
        boolean override1;
        if (selected == null) {
            override1 = true;
        } else {
            override1 = !selected.isOverrideKeys();
        }
        if (pack.code == KeyEvent.VK_ENTER) {
            if (selected != null) {
                selected.activate();
            }
        } else if (override1) {
            boolean override2;
            if (selected == null) {
                override2 = true;
            } else {
                override2 = !selected.isOverrideArrows();
            }
            if (pack.code == KeyEvent.VK_UP) {
                if (override2) {
                    arrow(Direction.N);
                } else {
                    selected.upArrow();
                }
            } else if (pack.code == KeyEvent.VK_DOWN) {
                if (override2) {
                    arrow(Direction.N);
                } else {
                    selected.downArrow();
                }
            }
        } else {
            selected.keyPress(pack);
        }
    }

    private void arrow(final Direction dir) {
        if (selectableElements.size() != 1) {
            if (dir == Direction.N) {
                if (!invertDirection) {
                    pressUp();
                } else {
                    pressDown();
                }
            } else if (dir == Direction.S) {
                if (!invertDirection) {
                    pressDown();
                } else {
                    pressUp();
                }
            }
        } else {
            selected = selectableElements.get(0);
            selected.select();
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
        if (selected != null && selected.isOverrideArrows()) {
            selected.downArrow();
        } else if (selectedIndex - 1 >= 0) {
            if (selected != null) {
                selected.deselect(false);
            }
            selectedIndex--;
            selected = selectableElements.get(selectedIndex);
            selected.select();
        }
    }

    public void pressUp() {
        if (selected != null && selected.isOverrideArrows()) {
            selected.upArrow();
        } else if (selectedIndex + 1 < selectableElements.size()) {
            if (selected != null) {
                selected.deselect(false);
            }
            selectedIndex++;
            selected = selectableElements.get(selectedIndex);
            selected.select();
        }
    }

    public void activate() {
        updateSelection(getMousePos(), true);
        if (selected != null) {
            final Rectangle rect = selected.getRectOrigin();
            if (Utilities.pointInRect(rect, getMousePos())) {
                selected.activate();
            }
        }
    }

    public void buttonActivate(final Button button) {
        //To be overridden
    }

    public Color getBackgroundColor() {
        return background.getColor();
    }

    public void setBackground(final Background background) {
        this.background = background;
        background.setWidth(getWidth());
        background.setHeight(getHeight());
    }

    public Background getBackground() {
        return background;
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

    private void updateSelection(final Point point, final boolean force) {
        boolean chosen = false;
        for (SelectableGUIElement element: selectableElements) {
            final Rectangle rect = element.getRectOrigin();
            if (Utilities.pointInRect(rect, point) && !chosen) {
                element.select();
                selected = element;
                chosen = true;
            } else {
                element.deselect(force);
            }
        }
    }

    public void updateScene() {
        background.setWidth(getWidth());
        background.setHeight(getHeight());
    }

    public void mouseMove(final MousePack pack) {
        if (selected == null || !selected.isOverrideMouse()) {
            updateSelection(pack.getPos(), false);
        } else {
            selected.moveMouse(pack);
        }
    }

    public void mousePress(final MousePack pack) {
        if (selected != null) {
            if (!selected.isOverrideMouse()) {
                activate();
            } else {
                selected.mousePress(pack);
            }
        }
    }

    public void mouseWheelMove(final MouseWheelPack pack) {
        if (selected != null) {
            if (selected.isOverrideMouseWheel()) {
                selected.moveMouseWheel(pack);
            }
        }
    }

}
