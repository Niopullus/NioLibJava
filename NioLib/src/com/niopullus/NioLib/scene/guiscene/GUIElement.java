package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.UUID;
import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.draw.Parcel;
import com.niopullus.NioLib.draw.StringSize;
import com.niopullus.NioLib.scene.*;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;

/**Display item for GUI scenes
 * Created by Owen on 3/6/2016.
 */
public class GUIElement implements Parcel, Comparable<GUIElement> {

    private List<String> lines;
    private List<Integer> displayChars;
    private int displayLines;
    private Background borderBG;
    private Background bg;
    private Color textColor;
    private GUIScene scene;
    private int x;
    private int y;
    private int z;
    private int borderSpacing;
    private int lineGap;
    private Justify justify;
    private Object tag;
    private UUID id;
    private GUISize size;
    private String fontName;
    private int fontSize;

    public GUIElement(final String content, final Theme theme, final int _fontSize, final GUISize _size) {
        lines = new ArrayList<>();
        displayChars = new ArrayList<>();
        displayLines = 0;
        borderSpacing = 0;
        fontSize = _fontSize;
        justify = Justify.CENTER;
        if (content != null) {
            lines.add(content);
        }
        x = 0;
        y = 0;
        z = 0;
        size = _size;
        id = new UUID("guiElement");
        setupBackgrounds();
        applyTheme(theme);
        updateDimensions();
        updateBackgrounds();
    }

    public String getContent() {
        return lines.get(0);
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
        return size.getWidth();
    }

    public int getHeight() {
        return size.getHeight();
    }

    public Object getTag() {
        return tag;
    }

    public int getBorderSpacing() {
        return borderSpacing;
    }

    public Background getBG() {
        return bg;
    }

    public int getWidthGap() {
        return size.getWidthGap();
    }

    public int getHeightGap() {
        return size.getHeightGap();
    }

    public Justify getJustify() {
        return justify;
    }

    public Background getBorderBG() {
        return borderBG;
    }

    public boolean isKeepWidth() {
        return size.isKeepWidth();
    }

    public boolean isKeepHeight() {
        return size.isKeepHeight();
    }

    public Rectangle getRect() {
        final int width = getWidth();
        final int height = getHeight();
        return new Rectangle(x, y, width, height);
    }

    public Rectangle getRectOrigin() {
        final int width = getWidth();
        final int height = getHeight();
        final int rectX = Main.Width() / 2 - width / 2 + x;
        final int rectY = Main.Height() / 2 - height / 2 + y;
        return new Rectangle(rectX, rectY, width, height);
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

    public Font getFont() {
        return new Font(fontName, Font.BOLD, fontSize);
    }

    public int getDisplayLines() {
        return displayLines;
    }

    public List<Integer> getDisplayChars() {
        return displayChars;
    }

    public GUISize getSize() {
        return size;
    }

    public void setSize(final GUISize _size) {
        size.setWidth(_size.getWidth());
        size.setHeight(_size.getHeight());
        size.setWidthGap(_size.getWidthGap());
        size.setHeightGap(_size.getHeightGap());
        size.setKeepWidth(_size.isKeepWidth());
        size.setKeepHeight(_size.isKeepHeight());
    }

    public void setX(final int _x) {
        x = _x;
    }

    public void setY(final int _y) {
        y = _y;
    }

    public void setPosition(final int x, final int y) {
        setX(x);
        setY(y);
    }

    public void setTag(final Object _tag) {
        tag = _tag;
    }

    public void setGUIScene(final GUIScene _scene) {
        scene = _scene;
    }

    public void setJustify(final Justify _justify) {
        justify = _justify;
    }

    public void setTextColor(final Color _color) {
        textColor = _color;
    }

    public void setZ(final int _z) {
        z = _z;
    }

    public void setContent(final int index, final String text) {
        lines.set(index, text);
        updateDimensions();
        updateBackgrounds();
    }

    public void setWidthGap(final int gap) {
        size.setWidthGap(gap);
        updateDimensions();
        updateBackgrounds();
    }

    public void setHeightGap(final int gap) {
        size.setHeightGap(gap);
        updateDimensions();
        updateBackgrounds();
    }

    public void addLine(final String content) {
        lines.add(content);
        updateDimensions();
        updateBackgrounds();
    }

    public void removeLine(final int index) {
        lines.remove(index);
        updateDimensions();
        updateBackgrounds();
    }

    public void setFontName(final String _fontName) {
        fontName = _fontName;
        updateDimensions();
        updateBackgrounds();
    }

    public void setFontSize(final int _fontSize) {
        fontSize = _fontSize;
        updateDimensions();
        updateBackgrounds();
    }

    public void setBorderSpacing(final int _borderSpacing) {
        borderSpacing = _borderSpacing;
        updateDimensions();
        updateBackgrounds();
    }

    public void setBackground(final Background background) {
        final int fieldWidth = getFieldWidth();
        final int fieldHeight = getFieldHeight();
        background.setWidth(fieldWidth);
        background.setHeight(fieldHeight);
        bg = background;
    }

    public void setBorder(final Background background) {
        final int width = getWidth();
        final int height = getHeight();
        background.setWidth(width);
        background.setHeight(height);
        borderBG = background;
    }

    public void setupBackgrounds() {
        bg = new ColorBackground(Color.WHITE);
        borderBG = new ColorBackground(Color.BLACK);
    }

    private void setupUUID() {
        id = new UUID("element");
    }

    public void setDisplayLines(final int lines) {
        displayLines = lines;
    }

    private void applyTheme(final Theme theme) {
        if (theme != null) {
            final int width = getWidth();
            final int height = getHeight();
            setTheme(theme);
            setFieldWidth(width - theme.getBorderGap() * 2);
            setFieldHeight(height - theme.getBorderGap() * 2);
        }
    }

    public void updateDimensions() {
        initWidth();
        initHeight();
    }

    public void initWidth() {
        final boolean keepWidth = isKeepWidth();
        if (keepWidth) {
            shortenContent();
        } else {
            determineWidth();
        }
    }

    public void initHeight() {
        final boolean keepHeight = isKeepHeight();
        if (keepHeight) {
            linePotential();
        } else {
            determineHeight();
        }
    }

    public void shortenContent() {
        fillDisplayChars();
        for (int i = 0; i < lines.size(); i++) {
            shortenLine(i);
        }
    }

    public void shortenLine(final int index) {
        final String line = lines.get(index);
        final int widthFree = getWidthFree();
        final Integer chars = shortenTo(line, widthFree);
        displayChars.set(index, chars);
    }

    public int getWidthFree() {
        final int fieldWidth = getFieldWidth();
        final int widthGap = getWidthGap();
        return fieldWidth - widthGap * 2;
    }

    public void fillDisplayChars() {
        displayChars = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            displayChars.add(null);
        }
    }

    public Integer shortenTo(final String line, final int width) {
        if (line != null) {
            final Font font = getFont();
            if (StringSize.getStringWidth(line, font) <= width) {
                return null;
            }
            for (int i = 0; i < line.length(); i++) {
                final String modLine = line.substring(0, i + 1) + "...";
                final int modWidth = StringSize.getStringWidth(modLine, font);
                if (modWidth > width) {
                    return i - 1;
                }
            }
            return null;
        }
        return null;
    }

    public void linePotential() {
        final Font font = getFont();
        final int heightGap = getHeightGap();
        final int fieldHeight = getFieldHeight();
        final int heightSpace = fieldHeight - heightGap * 2 + lineGap;
        displayLines = getLinePotential(font, heightSpace, lineGap);
        if (displayLines > lines.size()) {
            displayLines = lines.size();
        }
    }

    public int getLinePotential(final Font font, final int heightSpace, final int lineGap) {
        final int lineHeight = StringSize.getStringHeight(font) + lineGap;
        return heightSpace / lineHeight;
    }

    public void determineWidth() {
        final Font font = getFont();
        final int widthGap = getWidthGap();
        final int fieldWidth;
        int stringWidth = 0;
        for (String line : lines) {
            final int tempWidth = StringSize.getStringWidth(line, font);
            if (tempWidth > stringWidth) {
                stringWidth = tempWidth;
            }
        }
        fieldWidth = stringWidth + 2 * widthGap;
        setFieldWidth(fieldWidth);
        setWidth(fieldWidth + borderSpacing * 2);
        fillDisplayChars();
    }

    public void determineHeight() {
        final Font font = getFont();
        final int heightGap = getHeightGap();
        final int fieldHeight;
        final int stringHeight = StringSize.getStringHeight(font) * getLineCount() + (lines.size() - 1) * lineGap;
        fieldHeight = stringHeight + 2 * heightGap;
        setFieldHeight(fieldHeight);
        setHeight(fieldHeight + borderSpacing * 2);
        displayLines = lines.size();
    }

    public void updateBackgrounds() {
        updateBG();
        updateBorder();
    }

    private void updateBG() {
        final int fieldWidth = getFieldWidth();
        final int fieldHeight = getFieldHeight();
        bg.setWidth(fieldWidth);
        bg.setHeight(fieldHeight);
    }

    private void updateBorder() {
        final int width = getWidth();
        final int height = getHeight();
        borderBG.setWidth(width);
        borderBG.setHeight(height);
    }

    public void parcelDraw(final Canvas canvas) {
        final Font font = getFont();
        final int heightGap = getHeightGap();
        final int fieldHeight = getFieldHeight();
        final int height = StringSize.getStringHeight(font);
        final int linesHeight = displayLines * (height + lineGap) - lineGap;
        final int hrGap = (fieldHeight - heightGap * 2 - linesHeight) / 2;
        final Background bg = getCurrentBG();
        final Background border = getCurrentBorder();
        final Color textColor = getCurrentTextColor();
        int yPos;
        canvas.o.parcel(border, 0, 0, 5, 0);
        canvas.o.parcel(bg, borderSpacing, borderSpacing, 10, 0);
        yPos = getHeight() - borderSpacing - heightGap - hrGap - height;
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
            canvas.o.text(displayLine, textColor, font, xPos, yPos, 20, 0, 1);
            yPos -= lineGap + height;
        }
    }

    public Background getCurrentBG() {
        return bg;
    }

    public Background getCurrentBorder() {
        return borderBG;
    }

    public Color getCurrentTextColor() {
        return textColor;
    }

    public void removeFromScene() {
        final GUIScene scene = getGUIScene();
        scene.removeElement(this);
    }

    public int getXPos(final String line) {
        final int widthGap = getWidthGap();
        final int fieldWidth = getFieldWidth();
        return calcXPos(line, fieldWidth - widthGap * 2);
    }

    public int calcXPos(final String line, final int space) {
        if (line != null) {
            final Font font = getFont();
            final int diff = space - StringSize.getStringWidth(line, font);
            final int widthGap = getWidthGap();
            int result = borderSpacing + widthGap;
            if (justify == Justify.CENTER) {
                result += diff / 2;
            } else if (justify == Justify.RIGHT) {
                result += diff;
            }
            return result;
        }
        return 0;
    }

    public void setTheme(final Theme theme) {
        if (bg instanceof ColorBackground) {
            final ColorBackground colorBG = (ColorBackground) bg;
            colorBG.setColor(theme.getBgColor());
        }
        if (borderBG instanceof ColorBackground) {
            final ColorBackground colorBorder = (ColorBackground) borderBG;
            colorBorder.setColor(theme.getBorderColor());
        }
        textColor = theme.getTextColor();
        borderSpacing = theme.getBorderGap();
        fontName = theme.getFontName();
        lineGap = theme.getLineGap();
    }

    public void setLineGap(final int gap) {
        lineGap = gap;
        updateDimensions();
        updateBackgrounds();
    }

    public void setFieldWidth(final int width) {
        size.setFieldWidth(width);
    }

    public void setFieldHeight(final int height) {
        size.setFieldHeight(height);
    }

    public void setWidth(final int width) {
        size.setWidth(width);
    }

    public void setHeight(final int height) {
        size.setHeight(height);
    }

    public int getFieldWidth() {
        return size.getFieldWidth();
    }

    public int getFieldHeight() {
        return size.getFieldHeight();
    }

    public int compareTo(final GUIElement element) {
        return id.compareTo(element.id);
    }

    public enum Justify {

        LEFT,
        CENTER,
        RIGHT

    }

}
