package com.niopullus.NioLib;

import com.niopullus.NioLib.utilities.UncomparableException;

import java.io.Serializable;

/**
 * Created by Owen on 3/5/2016.
 */
public class UUID implements Comparable, Serializable {

    private static int currentID = 0;
    private String name;
    private int id;

    public UUID() {
        this("Unnamed");
    }

    public UUID(String name) {
        this.name = name;
        this.id = currentID + 1;
        this.currentID++;
    }

    public String toString() {
        return this.name + this.id;
    }

    public boolean equals(Object object) {
        if (!(object instanceof UUID)) {
            try {
                throw new UncomparableException();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        UUID id = (UUID) object;
        return this.name.equals(id.getName()) && this.id == id.getID();
    }

    public int compareTo(Object object) {
        if (!(object instanceof UUID)) {
            try {
                throw new UncomparableException();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        UUID id = (UUID) object;
        int compare = 0;
        int compareNames = this.name.compareTo(id.getName());
        if (compareNames == 0) {
            if (this.id > id.getID()) {
                compare = 1;
            } else if (this.id < id.getID()) {
                compare = -1;
            }
        } else {
            compare = compareNames;
        }
        return compare;
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.id;
    }

}
