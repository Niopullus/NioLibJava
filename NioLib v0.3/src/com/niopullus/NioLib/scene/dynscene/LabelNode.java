package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Draw;
import com.niopullus.NioLib.DrawElement;
import com.niopullus.NioLib.utilities.EString;

import java.awt.*;

/**Node that displays text
 * Created by Owen on 4/7/2016.
 */
public class LabelNode extends Node {

    private EString content;
    private Color color;
    private String fontName;
    private int fontSize;

    public LabelNode(final EString content, final Color color, final String fontName, final int fontSize) {
        super(content.get(), 0, 0);
        this.content = content;
        this.color = color;
        this.fontName = fontName;
        this.fontSize = fontSize;
    }

    public LabelNode(final String content, final Color color, final String fontName, final int fontsize) {
        this(new EString(content), color, fontName, fontsize);
    }

    public String getContentAsString() {
        return content.get();
    }

    public EString getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content.set(content);
    }

    public void setContent(final EString content) {
        this.content = content;
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    public void setFontName(final String fontName) {
        this.fontName = fontName;
    }

    public void setFontSize(final int fontSize) {
        this.fontSize = fontSize;
    }

    public void draw() {
        final Font font = new Font(this.fontName, Font.BOLD, this.fontSize);
        Draw.text(getX(), getY(), getZ(), content.get(), font, color, DrawElement.MODE_TEXT);
    }

}
