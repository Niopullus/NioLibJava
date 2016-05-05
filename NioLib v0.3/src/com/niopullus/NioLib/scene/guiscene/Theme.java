package com.niopullus.NioLib.scene.guiscene;


import java.awt.*;

/**
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
    private int borderWidth;

    public Theme() {
        this.bgColor = Color.WHITE;
        this.selectedBgColor = Color.WHITE;
        this.borderColor = Color.BLACK;
        this.selectedBorderColor = Color.CYAN;
        this.textColor = Color.BLACK;
        this.selectedTextColor = Color.black;
        this.fontName = "Bold";
        this.borderWidth = 15;
    }

    public void setBgColor(Color c) {
        this.bgColor = c;
    }

    public void setSelectedBgColor(Color c) {
        this.selectedBgColor = c;
    }

    public void setBorderColor(Color c) {
        this.borderColor = c;
    }

    public void setSelectedBorderColor(Color c) {
        this.selectedBorderColor = c;
    }

    public void setTextColor(Color c) {
        this.textColor = c;
    }

    public void setSelectedTextColor(Color c) {
        this.selectedTextColor = c;
    }

    public void setFontName(String n) {
        this.fontName = n;
    }

    public void setBorderWidth(int width) {
        this.borderWidth = width;
    }

    public Color getBgColor() {
        return this.bgColor;
    }

    public Color getSelectedBgColor() {
        return this.selectedBgColor;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public Color getSelectedBorderColor() {
        return this.selectedBorderColor;
    }

    public Color getTextColor() {
        return this.textColor;
    }

    public Color getSelectedTextColor() {
        return this.selectedTextColor;
    }

    public int getBorderWidth() {
        return this.borderWidth;
    }

    public String getFontName() {
        return this.fontName;
    }

}
