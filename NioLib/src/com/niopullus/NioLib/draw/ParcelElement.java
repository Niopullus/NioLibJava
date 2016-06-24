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

    public List<DrawElement> getElements() {
        return elements;
    }

    public void display(final Graphics2D g) {
        System.out.println("ehbf");
        for (DrawElement element : elements) {
            System.out.println("pefgre" + (element instanceof ShapeElement));
            element.setDx1(getDx1() + element.getDx1());
            element.setDy1(getDy1() + element.getDy1());
            element.setDx2(getDx2() + element.getDx2());
            element.setDy2(getDy2() + element.getDy2());
            element.setZ(getZ() + element.getZ());
            element.setAngle(getAngle() + element.getAngle());
            element.draw(g);
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
