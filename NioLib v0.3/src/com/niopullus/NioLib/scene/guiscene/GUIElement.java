package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.Animation;
import com.niopullus.NioLib.Draw;
import com.niopullus.NioLib.DrawElement;
import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.*;
import com.niopullus.NioLib.utilities.Utilities;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Owen on 3/6/2016.
 */
public class GUIElement {

    private String content;
    private String fontName;
    private Background borderBG;
    private Background bg;
    private Color textColor;
    private GUIScene guiScene;
    private int x;
    private int y;
    private int z;
    private int width;
    private int height;
    private int borderWidth;
    private int fontSize;
    private boolean drawBG;

    public GUIElement() {
        this("");
    }

    public GUIElement(String content) {
        this(content, 0 , 0, 0, 0);
    }

    public GUIElement(String content, int x, int y, int width, int height) {
        this(content, x, y, 0, width, height);
    }

    public GUIElement(String content, int x, int y, int z, int width, int height) {
        this.content = content;
        this.x = x;
        this.y = y;
        this.z = z;
        this.borderWidth = 10;
        this.borderBG = new ColorBackground(Main.Width() / 2 + this.x - width / 2, Utilities.convertY(Main.Height() / 2 + this.y + height / 2), width, height, Color.BLACK);
        this.bg = new ColorBackground(Main.Width() / 2 + this.x - width / 2 + this.borderWidth, Utilities.convertY(Main.Height() / 2 + this.y + height / 2 - this.borderWidth), width - this.borderWidth * 2, height - this.borderWidth * 2, Color.WHITE);
        this.width = width;
        this.height = height;
        this.textColor = Color.BLACK;
        this.fontSize = 30;
        this.drawBG = true;
        this.fontName = "Bold";
    }

    public void draw() {
        this.borderBG.draw();
        if (this.drawBG) {
            this.bg.draw();
        } else {
            Draw.rect(this.bg.getX(), this.bg.getY(), this.bg.getWidth(), this.bg.getHeight(), this.z, this.guiScene.getBackgroundColor());
        }
        Color color = this.textColor;
        Draw.text(this.x, this.y, this.z, this.content, new Font(this.fontName, Font.BOLD, this.fontSize), color, DrawElement.MODE_TEXTCENTERED);
    }

    public void setTextColor(Color color) {
        this.textColor = color;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public String getContent() {
        return this.content;
    }

    public void showBG() {
        this.drawBG = true;
    }

    public void hideBG() {
        this.drawBG = false;
    }

    public void setGUIScene(GUIScene guiScene) {
        this.guiScene = guiScene;
    }

    public void setColor(Color color) {
        ((ColorBackground) this.bg).setColor(color);
    }

    public void setBorderColor(Color color) {
        ((ColorBackground) this.borderBG).setColor(color);
    }

    public void setBorderWidth(int bwidth) {
        this.borderWidth = bwidth;
        Color c1 = ((ColorBackground) this.bg).getColor();
        Color c2 = ((ColorBackground) this.borderBG).getColor();
        this.borderBG = new ColorBackground(Main.Width() / 2 + this.x - width / 2, Utilities.convertY(Main.Height() / 2 + this.y + height / 2), width, height, ((ColorBackground) this.borderBG).getColor());
        this.bg = new ColorBackground(Main.Width() / 2 + this.x - width / 2 + this.borderWidth, Utilities.convertY(Main.Height() / 2 + this.y + height / 2 - this.borderWidth), width - this.borderWidth * 2, height - this.borderWidth * 2, ((ColorBackground) this.bg).getColor());
        ((ColorBackground) this.bg).setColor(c1);
        ((ColorBackground) this.borderBG).setColor(c2);
    }

    public void setBackgroundToImage(String imgDir) {
        this.bg = new ImageBackground(Main.Width() / 2 + this.x - width / 2 + this.borderWidth, Utilities.convertY(Main.Height() / 2 + this.y + height / 2 - this.borderWidth), width - this.borderWidth * 2, height - this.borderWidth * 2, imgDir);
    }

    public void setBackgroundToAnimation(Animation animation) {
        this.bg = new AnimatedBackground(Main.Width() / 2 + this.x - width / 2 + this.borderWidth, Utilities.convertY(Main.Height() / 2 + this.y + height / 2 - this.borderWidth), width - this.borderWidth * 2, height - this.borderWidth * 2, animation);
    }

    public void setBackgroundToDynamicImage(String img, int xShiftSpeed, int yShiftSpeed, int wx, int wy, int wwidth, int wheight) {
        DynamicImageBackground dynamicImageBackground  = new DynamicImageBackground(Main.Width() / 2 + this.x - width / 2 + this.borderWidth, Utilities.convertY(Main.Height() / 2 + this.y + height / 2 - this.borderWidth), width - this.borderWidth * 2, height - this.borderWidth * 2, img);
        dynamicImageBackground.setxShiftSpeed(xShiftSpeed);
        dynamicImageBackground.setyShiftSpeed(yShiftSpeed);
        dynamicImageBackground.setWindow(new Rectangle(wx, wy, wwidth, wheight));
        this.bg = dynamicImageBackground;
    }

    public void setBackgroundToDynamicAnimation(Animation animation, int xShiftSpeed, int yShiftSpeed, int wx, int wy, int wwidth, int wheight) {
        DynamicAnimatedBackground dynamicAnimatedBackground  = new DynamicAnimatedBackground(Main.Width() / 2 + this.x - width / 2 + this.borderWidth, Utilities.convertY((int) (Main.Height() / 2 + this.y + height / 2 - this.borderWidth)), width - this.borderWidth * 2, height - this.borderWidth * 2, animation);
        dynamicAnimatedBackground.setxShiftSpeed(xShiftSpeed);
        dynamicAnimatedBackground.setyShiftSpeed(yShiftSpeed);
        dynamicAnimatedBackground.setWindow(new Rectangle(wx, wy, wwidth, wheight));
        this.bg = dynamicAnimatedBackground;
    }

    public void setBorderToImage(String imgDir) {
        this.borderBG = new ImageBackground((int) (Main.Width() / 2 + this.x - width / 2), Utilities.convertY(Main.Height() / 2 + this.y + height / 2), width, height, imgDir);
    }

    public void setBorderToAnimation(Animation animation) {
        this.borderBG = new AnimatedBackground((int) (Main.Width() / 2 + this.x - width / 2), Utilities.convertY(Main.Height() / 2 + this.y + height / 2), width, height, animation);
    }

    public void setBorderToDynamicImage(String img, int xShiftSpeed, int yShiftSpeed, int wx, int wy, int wwidth, int wheight) {
        DynamicImageBackground dynamicImageBackground = new DynamicImageBackground(Main.Width() / 2 + this.x - width / 2, Utilities.convertY((int) (Main.Height() / 2 + this.y + height / 2)), width, height, img);
        dynamicImageBackground.setxShiftSpeed(xShiftSpeed);
        dynamicImageBackground.setyShiftSpeed(yShiftSpeed);
        dynamicImageBackground.setWindow(new Rectangle(wx, wy, wwidth, wheight));
        this.borderBG = dynamicImageBackground;
    }

    public void setBorderToDynamicAnimation(Animation animation, int xShiftSpeed, int yShiftSpeed, int wx, int wy, int wwidth, int wheight) {
        DynamicAnimatedBackground dynamicAnimatedBackground = new DynamicAnimatedBackground((int) (Main.Width() / 2 + this.x - width / 2), Utilities.convertY((int) (Main.Height() / 2 + this.y + height / 2)), width, height, animation);
        dynamicAnimatedBackground.setxShiftSpeed(xShiftSpeed);
        dynamicAnimatedBackground.setyShiftSpeed(yShiftSpeed);
        dynamicAnimatedBackground.setWindow(new Rectangle(wx, wy, wwidth, wheight));
        this.borderBG = dynamicAnimatedBackground;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getBorderWidth() {
        return this.borderWidth;
    }

    public GUIScene getGUIScene() {
        return this.guiScene;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public boolean doDrawBG() {
        return this.drawBG;
    }

    public Background getBG() {
        return this.bg;
    }

    public Background getBorderBG() {
        return this.borderBG;
    }

    public int getZ() {
        return this.z;
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Background getBg() {
        return this.bg;
    }

    public void keyPress(KeyEvent k) {

    }

    public final String getContentO() {
        return this.content;
    }

    public final Color getTextColor() {
        return this.textColor;
    }

    public final void setFontName(String name) {
        this.fontName = name;
    }

    public void setTheme(Theme t) {
        if (this.bg instanceof ColorBackground) {
            ((ColorBackground) this.bg).setColor(t.getBgColor());
        }
        if (this.borderBG instanceof ColorBackground) {
            ((ColorBackground) this.borderBG).setColor(t.getBorderColor());
        }
        this.textColor = t.getTextColor();
        this.setBorderWidth(t.getBorderWidth());
        this.fontName = t.getFontName();
    }

    public Rectangle getRect() {
        return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
    //HAHA

    public void setZ(int z) {
        this.z = z;
        this.bg.setZ(z);
        this.borderBG.setZ(z);
    }

}
