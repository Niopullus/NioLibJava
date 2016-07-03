package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.draw.Parcel;
import com.niopullus.NioLib.draw.StringSize;
import com.niopullus.NioLib.scene.*;
import com.niopullus.app.Config;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;

/**Display item for GUI scenes
 * Created by Owen on 3/6/2016.
 */
public class GUIElement implements Parcel {

    private List<String> lines;
    private Background borderBG;
    private Background bg;
    private Color textColor;
    private Canvas.DrawMode drawMode;
    private GUIScene scene;
    private Font font;
    private int x;
    private int y;
    private int z;
    private int width;
    private int height;
    private int borderSpacing;
    private int widthGap;
    private int heightGap;
    private int lineGap;
    private int fieldWidth;
    private int fieldHeight;
    private Justify justify;

    public GUIElement(final String content, final Font font, final int x, final int y, final int widthGap, final int heightGap) {
        this.lines = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.z = 0;
        this.borderSpacing = 10;
        this.textColor = Color.BLACK;
        this.font = font;
        this.bg = new ColorBackground(Color.WHITE);
        this.borderBG = new ColorBackground(Color.BLACK);
        this.justify = Justify.CENTER;
        this.widthGap = widthGap;
        this.heightGap = heightGap;
        lines.add(content);
        determineDimensions();
        updateBackgrounds();
    }

    public GUIElement(final String content, final String fontName, final int fontSize, final int x, final int y) {
        this(content, new Font(fontName, Font.BOLD, fontSize), x, y, Config.DEFAULTELEMENTGAPHEIGHT, Config.DEFAULTELEMENTGAPHEIGHT);
    }

    public String getContent() {
        return lines.get(0);
    }

    public Canvas.DrawMode getDrawMode() {
        return drawMode;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getBorderSpacing() {
        return borderSpacing;
    }

    public Background getBG() {
        return bg;
    }

    public int getWidthGap() {
        return widthGap;
    }

    public int getHeightGap() {
        return heightGap;
    }

    public Justify getJustify() {
        return justify;
    }

    public Background getBorderBG() {
        return borderBG;
    }

    public Font getFont() {
        return font;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public Rectangle getRectOrigin() {
        return new Rectangle(Main.Width() / 2 - width / 2 + x, Main.Height() / 2 - height / 2 + y, width, height);
    }

    public GUIScene getGUIScene() {
        return scene;
    }

    public int getLineGap() {
        return lineGap;
    }

    public String getLine(final int index) {
        return lines.get(index);
    }

    public String getLineDisplay(final int index) {
        return getLine(index);
    }

    public int getLineCount() {
        return lines.size();
    }

    public final Color getTextColor() {
        return textColor;
    }

    public void setGUIScene(final GUIScene scene) {
        this.scene = scene;
    }

    public void setJustify(final Justify justify) {
        this.justify = justify;
    }

    public void setDrawMode(final Canvas.DrawMode mode) {
        drawMode = mode;
    }

    public void setTextColor(final Color color) {
        this.textColor = color;
    }

    public void setColor(final Color color) {
        bg.setColor(color);
    }

    public void setBorderColor(final Color color) {
        borderBG.setColor(color);
    }

    public void setZ(final int z) {
        this.z = z;
    }

    public void setContent(final int index, final String text) {
        lines.set(index, text);
    }

    public void setWidthGap(final int gap) {
        widthGap = gap;
        determineDimensions();
        updateBackgrounds();
    }

    public void setHeightGap(final int gap) {
        heightGap = gap;
        determineDimensions();
        updateBackgrounds();
    }

    public void addLine(final String content) {
        lines.add(content);
        determineDimensions();
        updateBackgrounds();
    }

    public void removeLine(final int index) {
        lines.remove(index);
        determineDimensions();
        updateBackgrounds();
    }

    public void setFontName(final String fontName) {
        final int fontSize = font.getSize();
        font = new Font(fontName, Font.BOLD, fontSize);
    }

    public void setFontSize(final int fontSize) {
        final String fontName = font.getFontName();
        font = new Font(fontName, Font.BOLD, fontSize);
    }

    public void setBorderSpacing(final int borderSpacing) {
        this.borderSpacing = borderSpacing;
        determineDimensions();
        updateBackgrounds();
    }

    public void setBackground(final Background background) {
        background.setWidth(fieldWidth);
        background.setHeight(fieldHeight);
        bg = background;
    }

    public void setBorder(final Background background) {
        background.setWidth(width);
        background.setHeight(height);
        borderBG = background;
    }

    public void determineDimensions() {
        final FontMetrics metrics = StringSize.getFontMetrics(font);
        final int stringHeight = (metrics.getAscent() - metrics.getDescent()) * getLineCount() + (lines.size() - 1) * lineGap;
        int stringWidth = 0;
        for (String line : lines) {
            final int tempWidth = metrics.stringWidth(line);
            if (tempWidth > stringWidth) {
                stringWidth = tempWidth;
            }
        }
        this.fieldWidth = stringWidth + 2 * widthGap;
        this.fieldHeight = stringHeight + 2 * heightGap;
        this.width = fieldWidth + borderSpacing * 2;
        this.height = fieldHeight + borderSpacing * 2;
    }

    private void determineLineGap() {
        final FontMetrics metrics = StringSize.getFontMetrics(font);
        lineGap = metrics.getLeading();
    }

    public void updateBackgrounds() {
        updateBG();
        updateBorder();
    }

    private void updateBG() {
        bg.setWidth(fieldWidth);
        bg.setHeight(fieldHeight);
    }

    private void updateBorder() {
        borderBG.setWidth(width);
        borderBG.setHeight(height);
    }

    public void parcelDraw(final Canvas canvas) {
        final FontMetrics metrics = StringSize.getFontMetrics(font);
        final int height = metrics.getAscent() - metrics.getDescent();
        canvas.o.parcel(borderBG, 0, 0, 5, 0);
        canvas.o.parcel(bg, borderSpacing, borderSpacing, 10, 0);
        int yPos = borderSpacing + heightGap;
        for (int i = 0; i < lines.size(); i++) {
            final String line = getLineDisplay(lines.size() - i - 1);
            final int xPos = getXPos(getLine(lines.size() - i - 1));
            canvas.o.text(line, textColor, font, xPos, yPos, 20, 0);
            yPos += lineGap + height;
        }
    }

    public int getXPos(final String line) {
        final FontMetrics metrics = StringSize.getFontMetrics(font);
        final int diff = fieldWidth - metrics.stringWidth(line) - widthGap;
        int result = borderSpacing;
        if (justify == Justify.LEFT) {
            result += widthGap;
        } else if (justify == Justify.CENTER) {
            result += diff / 2 + widthGap / 2;
        } else if (justify == Justify.RIGHT) {
            result += diff;
        }
        return result;
    }

    public void setTheme(final Theme theme) {
        bg.setColor(theme.getBgColor());
        borderBG.setColor(theme.getBorderColor());
        textColor = theme.getTextColor();
        setBorderSpacing(theme.getBorderWidth());
        setFontName(theme.getFontName());
    }

    public void setLineGap(final int gap) {
        lineGap = gap;
        determineDimensions();
        updateBackgrounds();
    }

    public void setFieldWidth(final int width) {
        fieldWidth = width;
    }

    public void setFieldHeight(final int height) {
        fieldHeight = height;
    }

    public void setWidth(final int w) {
        width = w;
    }

    public void setHeight(final int h) {
        height = h;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public enum Justify {

        LEFT,
        CENTER,
        RIGHT

    }

}
