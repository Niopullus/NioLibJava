package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.Draw;
import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.utilities.Utilities;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**Manages elements of a scene designed to serve as a GUI
 * Buttons should be instance variables so they can be referenced from buttonActivate()
 * Created by Owen on 3/5/2016.
 */
public class GUIScene extends Scene {

    private ArrayList<GUIElement> elements;
    private ArrayList<SelectableGUIElement> selectableElements;
    private Background background;
    private int selected;
    private boolean drawBG;
    private boolean invertDirection;

    public GUIScene() {
        super();
        this.elements = new ArrayList<GUIElement>();
        this.selectableElements = new ArrayList<SelectableGUIElement>();
        this.selected = -1;
        this.drawBG = true;
        this.invertDirection = false;
        this.background = new ColorBackground(Main.Width(), Main.Height(), Color.GRAY);
    }

    public GUIScene(Scene superscene) {
        super(superscene);
        this.elements = new ArrayList<GUIElement>();
        this.selectableElements = new ArrayList<SelectableGUIElement>();
        this.background = new ColorBackground(Main.Width(), Main.Height(), Color.GRAY);
        this.selected = -1;
        this.drawBG = true;
        this.invertDirection = false;
    }

    @Override
    public final void draw() {
        if (this.drawBG) {
            this.background.draw();
        }
        for (GUIElement guiElement : this.elements) {
            guiElement.draw();
        }
        if (this.getSubscene() != null) {
            this.getSubscene().draw();
        }
    }

    public void tick() {

    }

    @Override
    public void keyPress(KeyEvent k) {
        int key = k.getKeyCode();
        if (this.getSubscene() != null && this.getSubscene() instanceof GUIScene) {
            this.getSubscene().keyPress(k);
        } else {
            switch (key) {
                case KeyEvent.VK_UP: pressUp(false); break;
                case KeyEvent.VK_DOWN: pressDown(false); break;
                case KeyEvent.VK_ENTER: pressEnter(); break;
                default: this.selectableElements.get(this.selected).keyPress(k);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {

    }

    public void addElement(GUIElement guiElement) {
        this.elements.add(guiElement);
        guiElement.setGUIScene(this);
        if (guiElement instanceof SelectableGUIElement) {
            this.selectableElements.add((SelectableGUIElement) guiElement);
            guiElement.setIndex(this.selectableElements.size() - 1);
            if (this.selected == -1) {
                this.selected = 0;
                this.selectableElements.get(0).select();
            }
        }
    }

    public void pressDown(boolean override) {
        if (this.invertDirection && !override) {
            this.pressUp(true);
            return;
        }
        if (this.getSubscene() != null && this.getSubscene() instanceof GUIScene) {
            ((GUIScene) (this.getSubscene())).pressDown(true);
        } else {
            if (this.selectableElements.get(this.selected).doOverrideArrows()) {
                this.selectableElements.get(this.selected).downArrow();
            } else if (this.selected - 1 >= 0) {
                this.selectableElements.get(this.selected).deselect();
                this.selected--;
                this.selectableElements.get(this.selected).select();
            }
        }
    }

    public void pressUp(boolean override) {
        if (this.invertDirection && !override) {
            this.pressDown(true);
        }
        if (this.getSubscene() != null && this.getSubscene() instanceof GUIScene) {
            ((GUIScene) (this.getSubscene())).pressUp(true);
        } else {
            if (this.selectableElements.get(this.selected).doOverrideArrows()) {
                this.selectableElements.get(this.selected).upArrow();
            } else if (this.selected + 1 < this.selectableElements.size()) {
                this.selectableElements.get(this.selected).deselect();
                this.selected++;
                this.selectableElements.get(this.selected).select();
            }
        }
    }

    public void pressEnter() {
        if (this.selected >= 0) {
            final SelectableGUIElement selectedElement = selectableElements.get(selected);
            buttonActivate(selectedElement);
        }
    }

    private void debug() {

    }

    public void buttonActivate(final SelectableGUIElement element) {
        //To be overridden
    }

    public void setBackgroundColor(Color color) {
        ((ColorBackground) this.background).setColor(color);
    }

    public Color getBackgroundColor() {
        return ((ColorBackground) this.background).getColor();
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public int getSelected() {
        return this.selected;
    }

    public void setSelected(int index) {
        this.selected = index;
    }

    public void enableInvertDirection() {
        this.invertDirection = true;
    }

    public void disableInvertDirection() {
        this.invertDirection = false;
    }

    public void toggleInvertDirection() {
        this.invertDirection = !this.invertDirection;
    }

    public void mouseMove() {
        if (this.selectableElements.size() > 0) {
            if (!(this.selectableElements.get(this.selected).doOverrideArrows())) {
                for (SelectableGUIElement element: this.selectableElements) {
                    if (Utilities.pointInRect(element.getX() + Main.Width() / 2 - element.getWidth() / 2, Utilities.convertY(this.background.getHeight() / 2 + element.getY() + element.getHeight() / 2), element.getWidth(), element.getHeight(), new Point(this.getMousePos().x - this.getDx(), this.getMousePos().y))) {
                        element.select();
                        this.selected = element.getIndex();
                    } else {
                        if (this.selected != element.getIndex()) {
                            element.deselect();
                        }
                    }
                }
            }
        }
    }

    public void mousePress() {
        if (this.selectableElements.size() > 0) {
            if (Utilities.pointInRect(this.selectableElements.get(this.selected).getX() + Main.Width() / 2 - this.selectableElements.get(this.selected).getWidth() / 2, Utilities.convertY(this.background.getHeight() / 2 + this.selectableElements.get(this.selected).getY() + this.selectableElements.get(this.selected).getHeight() / 2), this.selectableElements.get(this.selected).getWidth(), this.selectableElements.get(this.selected).getHeight(), new Point(this.getMousePos().x - this.getDx(), this.getMousePos().y))) {
                this.pressEnter();
            }
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (this.selectableElements.get(selected) instanceof SelectionBox) {
            int notches = e.getWheelRotation();
            if (notches < 0) {
                this.selectableElements.get(this.selected).upArrow();
            } else if (notches > 0) {
                this.selectableElements.get(this.selected).downArrow();
            }
        }
    }

}
