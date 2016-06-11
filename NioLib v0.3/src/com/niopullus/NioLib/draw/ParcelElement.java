package com.niopullus.NioLib.draw;

import java.awt.*;
import java.util.List;

/**
 * Created by Owen on 6/11/2016.
 */
public class ParcelElement extends DrawElement {

    private List<DrawElement> elements;

    public ParcelElement(final ParcelElementPack pack) {
        super(pack.x, pack.y, pack.x + pack.width, pack.y + pack.height, pack.z, pack.angle);
        elements = pack.elements;
    }

    public void display(final Graphics g) {
        for (DrawElement element : elements) {
            element.
        }
    }

    public static class ParcelElementPack {

        public List<DrawElement> elements;
        public int x;
        public int y;
        public int width;
        public int height;
        public int z;
        public double angle;

    }

}
