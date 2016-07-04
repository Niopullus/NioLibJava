package com.niopullus.NioLib.scene.dynscene.reference;

import com.niopullus.NioLib.scene.dynscene.Node;

/**Reference pertaining to nodes
 * Not necessary for functionality of nodes, only serves to eliminate repetition of data
 * Created by Owen on 4/27/2016.
 */
public class NodeReference extends Reference {

    private double defaultXScale;
    private double defaultYScale;

    public NodeReference(final String name, final double defaultXScale, final double defaultYScale, final int id, final Node sample) {
        super(name, id, sample);
        this.defaultXScale = defaultXScale;
        this.defaultYScale = defaultYScale;
    }

    public NodeReference() {
        this(null, 0, 0, 0, null);
    }

    /*
    Not to be used in a mutable context. If one wishes mutate the sample,
    One should use getSampleCopy()
     */
    public Node getSample() {
        return (Node) super.getSample();
    }

    public Node getSampleCopy() {
        final Node sample = getSample();
        return sample.copy();
    }

    public double getDefaultXScale() {
        return defaultXScale;
    }

    public double getDefaultYScale() {
        return defaultYScale;
    }

}
