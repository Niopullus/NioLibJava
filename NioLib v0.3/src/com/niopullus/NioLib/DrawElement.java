package com.niopullus.NioLib;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Owen on 3/29/2016.
 */
public class DrawElement implements Comparable<DrawElement> {

    private BufferedImage image;
    private Color color;
    private int dx1;
    private int dy1;
    private int dx2;
    private int dy2;
    private int sx1;
    private int sy1;
    private int sx2;
    private int sy2;
    private int z;
    private int mode;
    private String string;
    private Font font;
    private double angle;
    public static final int MODE_RECT = 1;
    public static final int MODE_IMAGE = 2;
    public static final int MODE_TEXT = 3;
    public static final int MODE_TEXTCENTERED = 4;

    public DrawElement(int x, int y, int z, Color color, int width, int height) {
        this(x, y, z, 0, color, width, height);
    }

    public DrawElement(int x, int y, int z, double angle, Color color, int width, int height) {
        this.dx1 = x;
        this.dy1 = y;
        this.dx2 = x + width;
        this.dy2 = y + height;
        this.z = z;
        this.color = color;
        this.mode = MODE_RECT;
    }

    public DrawElement(int x, int y, BufferedImage image) {
        this(x, y, 0, 0, image);
    }

    public DrawElement(int x, int y, int z, BufferedImage image) {
        this(x, y, z, 0, image);
    }

    public DrawElement(int x, int y, int z, double angle, BufferedImage image) {
        this(x, y, z, angle, image.getWidth(), image.getHeight(), image);
    }

    public DrawElement(int x, int y, int z, int width, int height, BufferedImage image) {
        this(x, y, z, 0, width, height, image);
    }

    public DrawElement(int x, int y, int z, double angle, int width, int height, BufferedImage image) {
        this(x, y, x + width, y + height, 0, 0, image.getWidth(), image.getHeight(), z, angle, image);
    }

    public DrawElement(int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, int z, BufferedImage image) {
        this(dx1, dy1, dx2 , dy2, sx1, sy1, sx2, sy2, z, 0, image);
    }

    public DrawElement(int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, int z, double angle, BufferedImage image) {
        this.dx1 = dx1;
        this.dy1 = Main.Height() - dy1 - dy2;
        this.dx2 = dx2;
        this.dy2 = Main.Height() - dy1;
        this.sx1 = sx1;
        this.sy1 = sy1;
        this.sx2 = sx2;
        this.sy2 = sy2;
        this.z = z;
        this.angle = angle;
        this.image = image;
        this.mode = MODE_IMAGE;
    }

    public DrawElement(int x, int y, int z, String string, Font font, Color color, int mode) {
        this(x, y, z, 0, string, font, color, mode);
    }

    public DrawElement(int x, int y, int z, double angle, String string, Font font, Color color, int mode) {
        this.dx1 = x;
        this.dy1 = y;
        this.z = z;
        this.string = string;
        this.mode = mode;
        this.font = font;
        this.color = color;
        this.angle = angle;
    }

    public int compareTo(DrawElement element) {
        return (new Integer(this.z).compareTo(element.z));
    }

    public int getDx1() {
        return this.dx1;
    }

    public int getDy1() {
        return this.dy1;
    }

    public int getDx2() {
        return this.dx2;
    }

    public int getDy2() {
        return this.dy2;
    }

    public int getSx1() {
        return this.sx1;
    }

    public int getSy1() {
        return this.sy1;
    }

    public int getSx2() {
        return this.sx2;
    }

    public int getSy2() {
        return this.sy2;
    }

    public int getZ() {
        return this.z;
    }

    public int getMode() {
        return this.mode;
    }

    public Color getColor() {
        return this.color;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public int getWidth() {
        return this.dx2 - this.dx1;
    }

    public int getHeight() {
        return this.dy2 - dy1;
    }

    public String getString() {
        return this.string;
    }

    public Font getFont() {
        return this.font;
    }

    public double getAngle() {
        return this.angle;
    }

}
