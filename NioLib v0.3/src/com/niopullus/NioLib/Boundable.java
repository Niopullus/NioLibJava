package com.niopullus.NioLib;

/**Defines a rectangle that marks the position of an object
 * Created by Owen on 5/16/2016.
 */
public interface Boundable {

    int getX();
    int getY();
    int getWidth();
    int getHeight();

    default int getMinX() {
        return getX();
    }

    default int getMinY() {
        return getY();
    }

    default int getMidX() {
        return getX() + getWidth() / 2;
    }

    default int getMidY() {
        return getY() + getHeight() / 2;
    }

    default int getMaxX() {
        return getX() + getWidth();
    }

    default int getMaxY() {
        return getY() + getHeight();
    }

}
