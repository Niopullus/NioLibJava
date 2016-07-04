package com.niopullus.NioLib.scene.dynscene.reference;

import com.niopullus.NioLib.scene.dynscene.Node;


/**Static way of interacting with a ReferenceManager
 * Created by Owen on 6/2/2016.
 */
public class Ref {

    private static ReferenceManager referenceManager = new ReferenceManager();

    private Ref() {
        //Blank implementation
    }

    public void setReferenceManager(final ReferenceManager referenceManager) {
        Ref.referenceManager = referenceManager;
    }

    public static void registerTile(final TileReference.TileReferencePack pack) {
        referenceManager.registerTile(pack);
    }

    public static void registerMultiTile(final MultiTileReference.MultiTileReferencePack pack) {
        referenceManager.registerMultiTile(pack);
    }

    public static void registerNode(final String name, final double defaultXScale, final double defaultYScale, final Node sample) {
        referenceManager.registerNode(name, defaultXScale, defaultYScale, sample);
    }

    public static TileReference getTileRef(final int id) {
        return referenceManager.getTileRef(id);
    }

    public static TileReference getTileRef(final String name) {
        return referenceManager.getTileRef(name);
    }

    public static MultiTileReference getMultiTileRef(final int id) {
        return (MultiTileReference) referenceManager.getTileRef(id);
    }

    public static MultiTileReference getMultiTileRef(final String name) {
        return (MultiTileReference) referenceManager.getTileRef(name);
    }

    public static NodeReference getNodeRef(final int id) {
        return referenceManager.getNodeRef(id);
    }

    public static NodeReference getNodeRef(final String name) {
        return referenceManager.getNodeRef(name);
    }

    public static int getTileCount() {
        return referenceManager.getTileCount();
    }

    public static int getNodeCount() {
        return referenceManager.getNodeCount();
    }

}
