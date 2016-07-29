package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.scene.dynscene.Node;

/**Allows Tiles/Nodes/Other to interact with their scene Polymorphically
 * Created by Owen on 5/16/2016.
 */
public interface NodeHandler {

    void addChild(final Node node);
    void removeChild(final Node node);
    int getNodeCount();

}
