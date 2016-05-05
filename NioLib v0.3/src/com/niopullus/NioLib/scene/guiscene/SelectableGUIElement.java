package com.niopullus.NioLib.scene.guiscene;

import com.niopullus.NioLib.Animation;
import com.niopullus.NioLib.DrawElement;
import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.*;
import com.niopullus.NioLib.Draw;
import com.niopullus.NioLib.utilities.Utilities;

import java.awt.*;

/**
 * Created by Owen on 4/1/2016.
 */
public class SelectableGUIElement extends GUIElement {

    private Background selectedBG;
    private Background selectedBorderBG;
    private Color selectedTextColor;
    private boolean selected;
    private boolean overrideArrows;

    public SelectableGUIElement(String content, int x, int y, int width, int height) {
        super(content, x, y, width, height);
        this.selectedBG = new ColorBackground(Main.Width() / 2 + this.getX() - this.getWidth() / 2 + this.getBorderWidth(), Utilities.convertY(Main.Height() / 2 + this.getY() + this.getHeight() / 2 - this.getBorderWidth()), this.getWidth() - this.getBorderWidth() * 2, this.getHeight() - this.getBorderWidth() * 2, Color.WHITE);
        this.selectedBorderBG = new ColorBackground(Main.Width() / 2 + this.getX() - this.getWidth() / 2, Utilities.convertY(Main.Height() / 2 + this.getY() + this.getHeight() / 2), this.getWidth(), this.getHeight(), Color.CYAN);
        this.selectedTextColor = Color.black;
        this.selected = false;
        this.overrideArrows = false;
    }

    public void setSelectedColor(Color color) {
        ((ColorBackground) this.selectedBG).setColor(color);
    }

    public Color getSelectedColor() {
        return ((ColorBackground) this.selectedBG).getColor();
    }

    public void setSelectedBorderColor(Color color) {
        ((ColorBackground) this.selectedBorderBG).setColor(color);
    }

    public Color getSelectedBorderColor() {
        return ((ColorBackground) this.selectedBorderBG).getColor();
    }

    public void setSelectedTextColor(Color color) {
        this.selectedTextColor = color;
    }

    public Color getSelectedTextColor() {
        return this.selectedTextColor;
    }

    public void select() {
        this.selected = true;
    }

    public void deselect() {
        this.selected = false;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public void setBorderWidth(int bwidth) {
        super.setBorderWidth(bwidth);
        Color c1 = ((ColorBackground) this.selectedBG).getColor();
        Color c2 = ((ColorBackground) this.selectedBorderBG).getColor();
        this.selectedBG = new ColorBackground(Main.Width() / 2 + this.getX() - this.getWidth() / 2 + this.getBorderWidth(), Utilities.convertY(Main.Height() / 2 + this.getY() + this.getHeight() / 2 - this.getBorderWidth()), this.getWidth() - this.getBorderWidth() * 2, this.getHeight() - this.getBorderWidth() * 2, Color.WHITE);
        this.selectedBorderBG = new ColorBackground(Main.Width() / 2 + this.getX() - this.getWidth() / 2, Utilities.convertY(Main.Height() / 2 + this.getY() + this.getHeight() / 2), this.getWidth(), this.getHeight(), Color.CYAN);
        ((ColorBackground) this.selectedBG).setColor(c1);
        ((ColorBackground) this.selectedBorderBG).setColor(c2);
    }

    public void setSelectedBackgroundToImage(String imgDir) {
        this.selectedBG = new ImageBackground(Main.Width() / 2 + this.getX() - this.getWidth() / 2 + this.getBorderWidth(), Utilities.convertY(Main.Height() / 2 + this.getY() + this.getHeight() / 2 - this.getBorderWidth()), this.getWidth() - this.getBorderWidth() * 2, this.getHeight() - this.getBorderWidth() * 2, imgDir);
    }

    public void setSelectedBackgroundToAnimation(Animation animation) {
        this.selectedBG = new AnimatedBackground(Main.Width() / 2 + this.getX() - getWidth() / 2 + this.getBorderWidth(), Utilities.convertY(Main.Height() / 2 + this.getY() + this.getHeight() / 2 - this.getBorderWidth()), this.getWidth() - this.getBorderWidth() * 2, this.getHeight() - this.getBorderWidth() * 2, animation);
    }

    public void setSelectedBackgroundToDynamicImage(String img, int xShiftSpeed, int yShiftSpeed, int wx, int wy, int wwidth, int wheight) {
        DynamicImageBackground dynamicImageBackground  = new DynamicImageBackground(Main.Width() / 2 + this.getX() - this.getWidth() / 2 + this.getBorderWidth(), Utilities.convertY(Main.Height() / 2 + this.getY() + this.getHeight() / 2 - this.getBorderWidth()), this.getWidth() - this.getBorderWidth() * 2, this.getHeight() - this.getBorderWidth() * 2, img);
        dynamicImageBackground.setxShiftSpeed(xShiftSpeed);
        dynamicImageBackground.setyShiftSpeed(yShiftSpeed);
        dynamicImageBackground.setWindow(new Rectangle(wx, wy, wwidth, wheight));
        this.selectedBG = dynamicImageBackground;
    }

    public void setSelectedBackgroundToDynamicAnimation(Animation animation, int xShiftSpeed, int yShiftSpeed, int wx, int wy, int wwidth, int wheight) {
        DynamicAnimatedBackground dynamicAnimatedBackground  = new DynamicAnimatedBackground(Main.Width() / 2 + this.getX() - this.getWidth() / 2 + this.getBorderWidth(), Utilities.convertY(Main.Height() / 2 + this.getY() + this.getHeight() / 2 - this.getBorderWidth()), this.getWidth() - this.getBorderWidth() * 2, this.getHeight() - this.getBorderWidth() * 2, animation);
        dynamicAnimatedBackground.setxShiftSpeed(xShiftSpeed);
        dynamicAnimatedBackground.setyShiftSpeed(yShiftSpeed);
        dynamicAnimatedBackground.setWindow(new Rectangle(wx, wy, wwidth, wheight));
        this.selectedBG = dynamicAnimatedBackground;
    }

    public void setSelectedBorderToImage(String imgDir) {
        this.selectedBorderBG = new ImageBackground(Main.Width() / 2 + this.getX() - this.getWidth() / 2, Utilities.convertY(Main.Height() / 2 + this.getY() + this.getHeight() / 2), this.getWidth(), this.getHeight(), imgDir);
    }

    public void setSelectedBorderToAnimation(Animation animation) {
        this.selectedBorderBG = new AnimatedBackground(Main.Width() / 2 + this.getX() - this.getWidth() / 2, Utilities.convertY(Main.Height() / 2 + this.getY() + this.getHeight() / 2), this.getWidth(), this.getHeight(), animation);
    }

    public void setSelectedBorderToDynamicImage(String img, int xShiftSpeed, int yShiftSpeed, int wx, int wy, int wwidth, int wheight) {
        DynamicImageBackground dynamicImageBackground = new DynamicImageBackground(Main.Width() / 2 + this.getX() - this.getWidth() / 2, Utilities.convertY(Main.Height() / 2 + this.getY() + this.getHeight() / 2), this.getWidth(), this.getHeight(), img);
        dynamicImageBackground.setxShiftSpeed(xShiftSpeed);
        dynamicImageBackground.setyShiftSpeed(yShiftSpeed);
        dynamicImageBackground.setWindow(new Rectangle(wx, wy, wwidth, wheight));
        this.selectedBorderBG = dynamicImageBackground;
    }

    public void setSelectedBorderToDynamicAnimation(Animation animation, int xShiftSpeed, int yShiftSpeed, int wx, int wy, int wwidth, int wheight) {
        DynamicAnimatedBackground dynamicAnimatedBackground = new DynamicAnimatedBackground(Main.Width() / 2 + this.getX() - this.getWidth() / 2, Utilities.convertY(Main.Height() / 2 + this.getY() + this.getHeight() / 2), this.getWidth(), this.getHeight(), animation);
        dynamicAnimatedBackground.setxShiftSpeed(xShiftSpeed);
        dynamicAnimatedBackground.setyShiftSpeed(yShiftSpeed);
        dynamicAnimatedBackground.setWindow(new Rectangle(wx, wy, wwidth, wheight));
        this.selectedBorderBG = dynamicAnimatedBackground;
    }

    public void selectionAction() {

    }

    public void draw() {
        if (this.selected) {
            this.selectedBorderBG.draw();
            if (this.doDrawBG()) {
                this.selectedBG.draw();
            } else {
                Draw.rect(this.getBG().getX(), this.getBG().getY(), this.getBG().getWidth(), this.getBG().getHeight(), this.getZ(), this.getGUIScene().getBackgroundColor());
            }
            Color color = this.getSelectedTextColor();
            Draw.text(this.getX(), this.getY(), this.getZ(), this.getContent(), new Font(this.getFontName(), Font.BOLD, this.getFontSize()), color, DrawElement.MODE_TEXTCENTERED);

        } else {
            super.draw();
        }
    }

    public Background getSelectedBG() {
        return this.selectedBG;
    }

    public Background getSelectedBorderBG() {
        return this.selectedBorderBG;
    }

    public void upArrow() {

    }

    public void downArrow() {

    }

    public boolean doOverrideArrows() {
        return this.overrideArrows;
    }

    public void overrideArrows() {
        this.overrideArrows = true;
    }

    public void disableOverrideArrows() {
        this.overrideArrows = false;
    }

    public void setTheme(Theme t) {
        super.setTheme(t);
        if (this.selectedBG instanceof ColorBackground) {
            ((ColorBackground) this.selectedBG).setColor(t.getSelectedBgColor());
        }
        if (this.selectedBorderBG instanceof ColorBackground) {
            ((ColorBackground) this.selectedBorderBG).setColor(t.getSelectedBorderColor());
        }
        this.selectedTextColor = t.getSelectedTextColor();
    }

    public void setZ(int z) {
        super.setZ(z);
        this.selectedBG.setZ(z);
        this.selectedBorderBG.setZ(z);
    }

}
