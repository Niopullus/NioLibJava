package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.Draw;
import com.niopullus.NioLib.DrawElement;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Owen on 4/6/2016.
 */
public class MLLabel extends GUIElement {

    private ArrayList<String> lines;
    private int lineSpacing;

    public MLLabel(int x, int y, int width, int height) {
        super("", x, y, width, height);
        this.lines = new ArrayList<String>();
        this.lineSpacing = 20;
    }

    public void draw() {
        super.draw();
        for (int i = 0; i < this.lines.size(); i++) {
            Draw.text(this.getX() - this.getWidth() / 2 + this.getBorderWidth(), this.getY() - this.getHeight() / 2 - this.lineSpacing * i, this.getZ(), this.lines.get(i), new Font(this.getFontName(), Font.BOLD, this.getFontSize()), this.getTextColor(), DrawElement.MODE_TEXT);
        }
    }

    public void setLineSpacing(int ls) {
        this.lineSpacing = ls;
    }

}
