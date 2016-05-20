package com.niopullus.NioLib.utilities;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.dynscene.Node;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Owen on 3/5/2016.
 */
public abstract class Utilities {

    public static BufferedImage loadImage(String imageDir) {
        BufferedImage image = null;
        try {
            image = ImageIO.read((new Node("temp")).getClass().getClassLoader().getResourceAsStream(imageDir));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public static void drawCenteredString(String s, int tx, int ty, Graphics g) {
        int w = Main.Width();
        int h = Main.Height();
        FontMetrics fm = g.getFontMetrics();
        int x = ((w - fm.stringWidth(s)) / 2) + tx;
        int y = ((fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2)) + ty;
        g.drawString(s, x, Utilities.convertY(y) + (fm.getAscent() + fm.getDescent()) * 2 / 3);
    }

    public static int convertY(int y) {
        return Main.Height() - y;
    }

    public static <T extends Comparable> T lesser(T input1, T input2) {
        int comp = input1.compareTo(input2);
        if (comp < 0) {
            return input1;
        } else {
            return input2;
        }
    }

    public static <T extends Comparable> T greater(T input1, T input2) {
        int comp = input1.compareTo(input2);
        if (comp > 0) {
            return input1;
        } else {
            return input2;
        }
    }

    public static int absoluteSign(double input) {
        if (input > 0) {
            return 1;
        } else if (input < 0) {
            return -1;
        }
        return 0;
    }

    public static double invtrig(double y, double x) {
        double result = Math.atan(y / x);
        if (x < 0) {
            result += Math.PI;
        }
        return result;
    }

    public static boolean pointInRect(Rectangle rect, Point p) {
        return Utilities.pointInRect(rect.x, rect.y, rect.width, rect.height, p);
    }

    public static boolean pointInRect(int x, int y, int width, int height, Point p) {
        return p.x < x + width && p.y < y + height && p.x > x && p.y > y;
    }

    public static boolean rectIntersect(Rectangle r1, Rectangle r2) {
        return r1.x < r2.getMaxX() && r1.getMaxX() > r2.x && r1.y < r2.getMaxY() && r1.getMaxY() > r2.y;
    }

    public static ArrayList<BufferedImage> loadImages(ArrayList<String> dirs) {
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        for (String imgDir : dirs) {
            images.add(Utilities.loadImage(imgDir));
        }
        return images;
    }

    public static BufferedImage[] loadImages(String[] dirs) {
        BufferedImage[] images = new BufferedImage[dirs.length];
        for (int i = 0; i < dirs.length; i++) {
            images[i] = Utilities.loadImage(dirs[i]);
        }
        return images;
    }

    public ArrayList<BufferedImage> getImageSet(final String baseIMG, final int setCount) {
        final ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        for (int i = 0; i < setCount; i++) {
            images.add(Utilities.loadImage(baseIMG + "s" + i));
        }
        return images;
    }

    public ArrayList<ArrayList<BufferedImage>> getImageGroup(final String baseIMG, final int groupCount, final int setCount) {
        final ArrayList<ArrayList<BufferedImage>> images = new ArrayList<ArrayList<BufferedImage>>();
        for (int g = 0; g < groupCount; g++) {
            final ArrayList<BufferedImage> set = new ArrayList<>();
            for (int s = 0; s < groupCount; s++) {
                set.add(Utilities.loadImage(baseIMG + "g" + g + "s" + s));
            }
            images.add(set);
        }
        return images;
    }

}
