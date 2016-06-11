package com.niopullus.NioLib.utilities;

import java.io.Serializable;
import java.lang.reflect.Array;

/**
 * Created by Owen on 4/2/2016.
 */
public class SignedContainer<T> implements Serializable {

    private Object[][] content;
    private int width;
    private int height;

    public SignedContainer(int size) {
        this.content = new Object[size * 2][size * 2];
        this.width = size;
        this.height = size;
    }

    public SignedContainer(int width, int height) {
        this.content = new Object[width * 2][height * 2];
        this.width = width;
        this.height = height;
    }

    public void set(int x, int y, T part) {
        int i = this.width + x;
        int j = this.height + y;
        if (i > 0 && j > 0 && i < this.content.length && j < this.content[0].length) {
            this.content[i][j] = part;
        }
    }

    public T get(int x, int y) {
        int i = this.width + x;
        int j = this.height + y;
        if (i > 0 && j > 0 && i < this.content.length && j < this.content[0].length) {
            return (T) this.content[i][j];
        } else {
            return null;
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isValidLoc(int x, int y) {
        return this.width + x > 0 && this.width + x < this.content.length && this.height + y > 0 && this.height + y < this.content[0].length;
    }

}
