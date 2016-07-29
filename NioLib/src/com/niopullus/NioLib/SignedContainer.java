package com.niopullus.NioLib;

import java.io.Serializable;
import java.lang.reflect.Array;

/**Functions as an ArrayList that includes negative indexes
 * Created by Owen on 4/2/2016.
 */
public class SignedContainer<T> implements Serializable {

    private Object[][] content;
    private int width;
    private int height;

    public SignedContainer(final int size) {
        this(size, size);
    }

    public SignedContainer(int width, int height) {
        this.content = new Object[width * 2][height * 2];
        this.width = width;
        this.height = height;
    }

    public void set(int x, int y, T part) {
        final int i = width + x;
        final int j = height + y;
        if (i > 0 && j > 0 && i < content.length && j < content[0].length) {
            content[i][j] = part;
        }
    }

    public T get(final int x, final int y) {
        final int i = width + x;
        final int j = height + y;
        if (i > 0 && j > 0 && i < content.length && j < content[0].length) {
            return (T) content[i][j];
        } else {
            return null;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isValidLoc(int x, int y) {
        return width + x > 0 && width + x < content.length && height + y > 0 && height + y < content[0].length;
    }

}
