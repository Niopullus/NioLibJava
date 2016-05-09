package com.niopullus.NioLib.scene.dynscene;

/**
 * Created by Owen on 4/27/2016.
 */
public class NodeReference extends Reference {

    private double defaultXScale;
    private double defaultYScale;

    public NodeReference(String name, double defaultXScale, double defaultYScale, int id, Node sample) {
        super(name, id, sample);
        this.defaultXScale = defaultXScale;
        this.defaultYScale = defaultYScale;
    }

    public Node getSample() {
        return (Node) super.getSample();
    }

    public double getDefaultXScale() {
        return this.defaultXScale;
    }

    public double getDefaultYScale() {
        return this.defaultYScale;
    }

}
