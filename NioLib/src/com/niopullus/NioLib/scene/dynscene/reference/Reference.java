package com.niopullus.NioLib.scene.dynscene.reference;

/**Serves to limit repetition of data between certain Objects
 * This class may be sub-classed, however, this may cause issues if done incorrectly.
 * Various objects will usually be able to summon their Reference.
 * In the case of sub-classed references, also known as Custom References,
 * The reference object will likely need to be cast down to the sub-class.
 * Created by Owen on 4/26/2016.
 */
public class Reference implements Comparable<Reference> {

    private String name;
    private Object sample;
    private int id;

    public Reference(final String _name, final int _id, final Object _sample) {
        name = _name;
        sample = _sample;
        id = _id;
    }

    public Reference() {
        this(null, 0, null);
    }

    public Object getSample() {
        return sample;
    }

    public int getNode() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int compareTo(final Reference ref) {
        return name.compareTo(ref.name);
    }

}
