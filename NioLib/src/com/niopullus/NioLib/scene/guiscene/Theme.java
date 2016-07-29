package com.niopullus.NioLib.scene.guiscene;


import java.awt.*;

/**Used as a convenience for common attributes of a GUIElement
 * Created by Owen on 4/6/2016.
 */
public class Theme {

    private Color bgColor;
    private Color selectedBgColor;
    private Color borderColor;
    private Color selectedBorderColor;
    private Color textColor;
    private Color selectedTextColor;
    private String fontName;
    private int borderGap;
    private int widthGap;
    private int heightGap;
    private int lineGap;

    public Theme() {
        bgColor = Color.WHITE;
        selectedBgColor = Color.WHITE;
        borderColor = Color.BLACK;
        selectedBorderColor = Color.CYAN;
        textColor = Color.BLACK;
        selectedTextColor = Color.black;
        fontName = "Bold";
        borderGap = 15;
        widthGap = 10;
        heightGap = 10;
        lineGap = 10;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public Color getSelectedBgColor() {
        return selectedBgColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public Color getSelectedBorderColor() {
        return selectedBorderColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Color getSelectedTextColor() {
        return selectedTextColor;
    }

    public int getBorderGap() {
        return borderGap;
    }

    public String getFontName() {
        return fontName;
    }

    public int getWidthGap() {
        return widthGap;
    }

    public int getHeightGap() {
        return heightGap;
    }

    public Font getFont(final int fontSize) {
        return new Font(fontName, Font.BOLD, fontSize);
    }

    public int getLineGap() {
        return lineGap;
    }

    public void setBgColor(final Color c) {
        bgColor = c;
    }

    public void setSelectedBgColor(final Color c) {
        selectedBgColor = c;
    }

    public void setBorderColor(final Color c) {
        borderColor = c;
    }

    public void setSelectedBorderColor(final Color c) {
        selectedBorderColor = c;
    }

    public void setTextColor(final Color c) {
        textColor = c;
    }

    public void setSelectedTextColor(final Color c) {
        selectedTextColor = c;
    }

    public void setFontName(final String n) {
        fontName = n;
    }

    public void setBorderWidth(final int gap) {
        borderGap = gap;
    }

    public void setWidthGap(final int gap) {
        widthGap = gap;
    }

    public void setHeightGap(final int gap) {
        heightGap = gap;
    }

}
