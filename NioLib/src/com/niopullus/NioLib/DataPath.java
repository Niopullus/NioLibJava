package com.niopullus.NioLib;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Owen on 4/13/2016.
 */
public class DataPath {

    private ArrayList<Integer> path;

    public DataPath(int... path) {
        this.path = new ArrayList<Integer>();
        for (int i : path) {
            this.path.add(i);
        }
    }

    public DataPath() {
        this.path = null;
    }

    public int get() {
        Integer firstPath = this.path.get(0);
        this.path.remove(0);
        return firstPath;
    }

    public int oget(final int index) {
        return path.get(index);
    }

    public int count() {
        if (this.path == null) {
            return 0;
        } else {
            return this.path.size();
        }
    }

}
