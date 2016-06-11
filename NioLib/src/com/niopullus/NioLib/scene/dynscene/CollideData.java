package com.niopullus.NioLib.scene.dynscene;

/**Requires that classes that wish to participate in Collision referencing have necessary methods
 * Created by Owen on 3/23/2016.
 */
public interface CollideData {

    double getElasticity();
    double getFriction();
    String getName();
    void victimCollision(final Collision collision);
    void causerCollision(final Collision collision);

}
