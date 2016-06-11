package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Boundable;

/**A node that when nodes approach it, will trigger the trigger(CollideData) method
 * Created by Owen on 4/6/2016.
 */
public interface Trigger extends Boundable {

    void trigger(final CollideData data);
    int getXRad();
    int getYRad();

}
