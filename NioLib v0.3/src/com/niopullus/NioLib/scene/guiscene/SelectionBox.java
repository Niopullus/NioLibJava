package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.DrawElement;
import com.niopullus.NioLib.Draw;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Owen on 3/31/2016.
 */
public class SelectionBox extends SelectableGUIElement {

    private ArrayList<String> selections;
    private int selection;
    private boolean expand;

    public SelectionBox(int x, int y, int width, int height) {
        super(null, x, y, width, height);
        this.selections = new ArrayList<String>();
        this.selection = 0;
        this.expand = false;
    }

    public void addSelection(String selection) {
        this.selections.add(selection);
        this.selection = this.selections.size() / 2;
        this.setContent(this.selections.get(this.selection));
    }

    public void selectionAction() {
        if (!this.expand) {
            this.expand = true;
            this.overrideArrows();
        } else {
            this.setContent(this.selections.get(this.selection));
            this.expand = false;
            this.disableOverrideArrows();
        }
    }

    public void deselect() {
        super.deselect();
    }

    public void draw() {
        if (this.expand) {
            Font font = new Font(this.getFontName(), Font.BOLD, this.getFontSize());
            int itCount = 0;
            for (int i = this.selection - 1; i >= 0; i--) {
                itCount++;
                int xb = this.getSelectedBorderBG().getX();
                int yb = this.getSelectedBorderBG().getY();
                int xs = this.getSelectedBG().getX();
                int ys = this.getSelectedBG().getY();
                int height = this.getSelectedBorderBG().getHeight();
                this.getBorderBG().draw(xb, yb - itCount * height);
                this.getBG().draw(xs, ys - itCount * height);
                Draw.text(this.getX(), this.getY() + itCount * height, this.getZ(), this.selections.get(i), font, this.getSelectedTextColor(), DrawElement.MODE_TEXTCENTERED);
            }
            this.getSelectedBorderBG().draw();
            this.getSelectedBG().draw();
            Draw.text(this.getX(), this.getY(), this.getZ(), this.selections.get(this.selection), font, this.getSelectedTextColor(), DrawElement.MODE_TEXTCENTERED);
            itCount = 0;
            for (int i = this.selection + 1; i < this.selections.size(); i++) {
                itCount++;
                int xb = this.getSelectedBorderBG().getX();
                int yb = this.getSelectedBorderBG().getY();
                int xs = this.getSelectedBG().getX();
                int ys = this.getSelectedBG().getY();
                int height = this.getSelectedBorderBG().getHeight();
                this.getBorderBG().draw(xb, yb + itCount * height);
                this.getBG().draw(xs, ys + itCount * height);
                Draw.text(this.getX(), this.getY() - itCount * height, this.getZ(), this.selections.get(i), font, this.getSelectedTextColor(), DrawElement.MODE_TEXTCENTERED);
            }
        } else {
            super.draw();
        }
    }

    public void upArrow() {
        if (this.selection - 1 >= 0) {
            this.selection--;
        }
    }

    public void downArrow() {
        if (this.selection + 1 < this.selections.size()) {
            this.selection++;
        }
    }

}
