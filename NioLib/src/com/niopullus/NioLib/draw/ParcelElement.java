package com.niopullus.NioLib.draw;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owen on 6/11/2016.
 */
public class ParcelElement extends DrawElement {

    private List<DrawElement> elements;
    private double xSF;
    private double ySF;

    public ParcelElement() {
        super(0, 0, 0, 0, 0, 0);
    }

    public List<DrawElement> getElements() {
        return elements;
    }

    public double getxSF() {
        return xSF;
    }

    public double getySF() {
        return ySF;
    }

    public void display(final Graphics2D g, final DrawPosition drawPosition) {
        for (DrawElement element : elements) {
            element.draw(g);
        }
    }

    public void define(final ParcelElementPack pack) {
        setDx1(pack.x);
        setDy1(pack.y);
        setDx2(pack.x + pack.width);
        setDy2(pack.y + pack.height);
        setZ(pack.z);
        setAngle(pack.angle);
        elements = pack.elements;
        xSF = pack.xSF;
        ySF = pack.ySF;
    }

    public List<DrawElement> retrieveElements() {
        final List<DrawElement> result = new ArrayList<>();
        for (DrawElement element : elements) {
            result.add(element);
            if (element instanceof ParcelElement) {
                final ParcelElement parcelElement = (ParcelElement) element;
                result.addAll(parcelElement.retrieveElements());
            }
        }
        return result;
    }

    public int getWidth() {
        return (int) (super.getWidth() * xSF);
    }

    public int getHeight() {
        return (int) (super.getHeight() * ySF);
    }

    public static class ParcelElementPack {

        public int x;
        public int y;
        public int width;
        public int height;
        public double xSF;
        public double ySF;
        public int z;
        public double angle;
        public List<DrawElement> elements;

    }

}
