package com.niopullus.NioLib.draw;

import java.awt.*;
import java.util.List;

/**Associates several components that need to be drawn in a particular structure
 * Created by Owen on 6/11/2016.
 */
public interface Parcel {

    void integrate(final ParcelDelegate delegate);

}
