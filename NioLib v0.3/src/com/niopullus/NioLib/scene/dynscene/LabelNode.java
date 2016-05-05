package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Draw;
import com.niopullus.NioLib.DrawElement;
import com.niopullus.NioLib.utilities.EString;

import java.awt.*;

/**
 * Created by Owen on 4/7/2016.
 */
public class LabelNode extends Node {

    private EString content;
    private Color color;
    private String fontName;
    private int fontSize;

    public LabelNode(String content, Color color, String fontName, int fontSize) {
        super(content, 0, 0);
        this.content = new EString(content);
        this.color = color;
        this.fontName = fontName;
        this.fontSize = fontSize;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public void setContent(EString content) {
        this.content = content;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void draw() {
        Draw.text(this.getX(), this.getY(), this.getZ(), this.content.get(), new Font(this.fontName, Font.BOLD, this.fontSize), this.color, DrawElement.MODE_TEXT);
    }

}
