package com.niopullus.NioLib;

import java.io.Serializable;

/**Used to Universally identify an object
 * Created by Owen on 3/5/2016.
 */
public class UUID implements Comparable<UUID> {

    private static int currentID = 0;
    private String name;
    private int idNum;

    public UUID() {
        this("Unnamed");
    }

    public UUID(final String _name) {
        name = _name;
        idNum = currentID + 1;
        currentID++;
    }

    public String toString() {
        return name + idNum;
    }

    public boolean equals(Object object) {
        if (object instanceof UUID) {
            final UUID uuid = (UUID) object;
            return name.equals(uuid.getName()) && idNum == uuid.idNum;
        } else {
            return false;
        }
    }

    public int compareTo(UUID uuid) {
        final int compareString = name.compareTo(uuid.name);
        if (compareString == 0) {
            final Integer num = idNum;
            return num.compareTo(uuid.idNum);
        }
        return compareString;
    }

    public String getName() {
        return name;
    }

    public int getIDNum() {
        return idNum;
    }

}
