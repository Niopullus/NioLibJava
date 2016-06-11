package com.niopullus.NioLib.scene.dynscene.reference;

import com.niopullus.NioLib.scene.dynscene.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**Stores references, keeps track of IDs
 * Created by Owen on 5/30/2016.
 */
public class ReferenceManager {

    private List<TileReference> tilerefs;
    private List<NodeReference> noderefs;
    private List<TileReference> sortedTileRefs;
    private List<NodeReference> sortedNodeRefs;
    private int curIDTile = 1;
    private int curIDNode = 0;

    public ReferenceManager() {
        this.tilerefs = new ArrayList<>();
        this.noderefs = new ArrayList<>();
        this.sortedNodeRefs = new ArrayList<>();
        this.sortedTileRefs = new ArrayList<>();
    }

    private int getCurIDTile() {
        final int id = curIDTile;
        curIDTile++;
        return id;
    }

    private int getCurIDNode() {
        final int id = curIDNode;
        curIDNode++;
        return id;
    }

    public TileReference getTileRef(final int id) {
        if (id >= 1 && id <= getTileCount()) {
            return tilerefs.get(id - 1);
        } else {
            return null;
        }
    }

    public NodeReference getNodeRef(final int id) {
        if (id < getNodeCount() && id >= 0) {
            return noderefs.get(id);
        } else {
            return null;
        }
    }

    public TileReference getTileRef(final String name) {
        final int index;
        final Comparator<TileReference> comparator =
                (final TileReference ref1, final TileReference ref2) -> ref1.getName().compareTo(ref2.getName());
        final TileReference reference = new TileReference() {
            public String getName() {
                return name;
            }
        };
        index = Collections.binarySearch(tilerefs, reference, comparator);
        if (index >= 0) {
            return sortedTileRefs.get(index);
        }
        return null;
    }

    public NodeReference getNodeRef(final String name) {
        final int index;
        final Comparator<NodeReference> comparator =
                (final NodeReference ref1, final NodeReference ref2) -> ref1.getName().compareTo(ref2.getName());
        final NodeReference reference = new NodeReference() {
            public String getName() {
                return name;
            }
        };
        index = Collections.binarySearch(noderefs, reference, comparator);
        if (index >= 0) {
            return sortedNodeRefs.get(index);
        }
        return null;
    }

    public void registerTile(final TileReference.TileReferencePack pack) {
        final TileReference ref;
        ref = new TileReference(pack);
        tilerefs.add(ref);
        sortedTileRefs.add(ref);
        Collections.sort(sortedTileRefs);
        pack.sample.setReference(getTileRef(pack.name));
    }

    public void registerMultiTile(final MultiTileReference.MultiTileReferencePack pack) {
        final MultiTileReference ref;
        ref = new MultiTileReference(pack);
        tilerefs.add(ref);
        sortedTileRefs.add(ref);
        Collections.sort(sortedTileRefs);
        pack.sample.setReference(getTileRef(pack.name));
    }

    public void registerNode(final String name, final double defaultXScale, final double defaultYScale, final Node sample) {
        final NodeReference ref = new NodeReference(name, defaultXScale, defaultYScale, getCurIDNode(), sample);
        noderefs.add(ref);
        sortedNodeRefs.add(ref);
        Collections.sort(sortedNodeRefs);
        sample.setReference(getNodeRef(name));
    }

    public void registerTile(final String name, final TileReference reference) {
        tilerefs.add(reference);
        sortedTileRefs.add(reference);
        Collections.sort(sortedNodeRefs);
    }

    public void registerNode(final String name, final NodeReference reference) {
        noderefs.add(reference);
        sortedNodeRefs.add(reference);
        Collections.sort(sortedNodeRefs);
    }

    public int getTileCount() {
        return tilerefs.size();
    }

    public int getNodeCount() {
        return noderefs.size();
    }

}
