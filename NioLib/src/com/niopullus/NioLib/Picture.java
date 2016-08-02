package com.niopullus.NioLib;

import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.draw.Parcel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

/**Class wrapper for BufferedImage to implement several interfaces and
 * add additional NioLib support
 * Created by Owen on 7/4/2016.
 */
public class Picture implements Sketch, Parcel {

    private BufferedImage image;
    private String name;
    private UUID id;
    private final static List<Picture> pictures = new ArrayList<>();

    public Picture(final BufferedImage _image, final String _name) {
        image = _image;
        name = _name;
        id = new UUID("pic");
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int getPictureWidth() {
        return getWidth();
    }

    public int getPictureHeight() {
        return getHeight();
    }

    public void parcelDraw(final Canvas canvas) {
        canvas.o.image(image, 0, 0, image.getWidth(), image.getHeight(), 0);
    }

    private static Comparator<Picture> getComparator() {
        return (final Picture p1, final Picture p2) -> {
            final UUID id1 = p1.id;
            final UUID id2 = p2.id;
            return id1.compareTo(id2);
        };
    }

    private static void sortPictures() {
        final Comparator<Picture> comparator = getComparator();
        Collections.sort(pictures, comparator);
    }

    public static void loadPictureFromJar(final String fileName, final String picName) {
        final BufferedImage image = Utilities.loadImage(fileName);
        final Picture picture = new Picture(image, picName);
        pictures.add(picture);
        sortPictures();
    }

    public static void loadPictureFromFile(final String fileDir, final String picName) {
        try {
            final File file = Root.getFile(fileDir);
            final BufferedImage image = ImageIO.read(file);
            final Picture picture = new Picture(image, picName);
            pictures.add(picture);
            sortPictures();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static Picture getPicture(final String picName) {
        final Comparator<Picture> comparator = getComparator();
        final Picture sample = new Picture(null, picName);
        final int index = Collections.binarySearch(pictures, sample, comparator);
        if (index >= 0) {
            return pictures.get(index);
        }
        return null;
    }

}
