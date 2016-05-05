package com.niopullus.NioLib;

import com.niopullus.NioLib.utilities.Utilities;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Owen on 3/30/2016.
 */
public class Draw {

    private static ArrayList<DrawElement> elements;

    private static void add(DrawElement drawElement) {
        if (drawElement.getMode() != DrawElement.MODE_TEXT && drawElement.getMode() != DrawElement.MODE_TEXTCENTERED && drawElement.getDx1() < Main.Width() && drawElement.getDx2() > 0 && drawElement.getDy1() < Main.Height() && drawElement.getDy2() > 0) {
            elements.add(drawElement);
        } else if (drawElement.getMode() == DrawElement.MODE_TEXT || drawElement.getMode() == DrawElement.MODE_TEXTCENTERED) {
            elements.add(drawElement);
        }
    }

    public static void init() {
        Draw.elements = new ArrayList<DrawElement>();
    }

    public static void rect(int x, int y, int width, int height, int z, Color color) {
        add(new DrawElement(x, y, z, color, width, height));
    }

    public static void rect(int x, int y, int width, int height, int z, double angle, Color color) {
        add(new DrawElement(x, y, z, angle, color, width, height));
    }

    public static void image(int x, int y, BufferedImage image) {
        add(new DrawElement(x, y, image));
    }

    public static void image(int x, int y, int z, BufferedImage image) {
        add(new DrawElement(x, y, z, image));
    }

    public static void image(int x, int y, int z, double angle, BufferedImage image) {
        add(new DrawElement(x, y, z, angle, image));
    }

    public static void image(int x, int y, int z, int width, int height, BufferedImage image) {
        add(new DrawElement(x, y, z, width, height, image));
    }

    public static void image(int x, int y, int z, double angle, int width, int height, BufferedImage image) {
        add(new DrawElement(x, y, z, angle, width, height, image));
    }

    public static void image(int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, int z, BufferedImage image) {
        add(new DrawElement(dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, z, image));
    }

    public static void image(int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, int z, double angle, BufferedImage image) {
        add(new DrawElement(dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, z, angle, image));
    }

    public static void text(int x, int y, int z, String string, Font font, Color color, int mode) {
        add(new DrawElement(x, y, z, string, font, color, mode));
    }

    public static void text(int x, int y, int z, double angle, String string, Font font, Color color, int mode) {
        add(new DrawElement(x, y, z, angle, string, font, color, mode));
    }

    public static void display(Graphics2D g) {
        Collections.sort(Draw.elements);
        g.setColor(Color.WHITE);
        //g.fillRect(0, 0, (int) (Main.WIDTH * Main.SCALE), (int) (Main.HEIGHT * Main.SCALE));
        for (DrawElement element : Draw.elements) {
            AffineTransform old = g.getTransform();
            g.translate(element.getDx1() + element.getWidth() / 2, element.getDy1() + element.getHeight() / 2);
            g.rotate(element.getAngle());
            g.translate(-(element.getDx1() + element.getWidth() / 2), -(element.getDy1() + element.getHeight() / 2));
            if (element.getMode() == DrawElement.MODE_RECT) {
                g.setColor(element.getColor());
                g.fillRect(element.getDx1(), element.getDy1(), element.getWidth(), element.getHeight());
            } else if (element.getMode() == DrawElement.MODE_IMAGE) {
                g.drawImage(element.getImage(), element.getDx1(), element.getDy1(), element.getDx2(), element.getDy2(), element.getSx1(), element.getSy1(), element.getSx2(), element.getSy2(), null);
            } else if (element.getMode() == DrawElement.MODE_TEXT) {
                g.setColor(element.getColor());
                g.setFont(element.getFont());
                g.drawString(element.getString(), element.getDx1(), element.getDy1());
            } else if (element.getMode() == DrawElement.MODE_TEXTCENTERED) {
                g.setColor(element.getColor());
                g.setFont(element.getFont());
                Utilities.drawCenteredString(element.getString(), element.getDx1(), element.getDy1(), g);
            }
            g.setTransform(old);
        }
        Draw.elements = new ArrayList<DrawElement>();
    }

}
