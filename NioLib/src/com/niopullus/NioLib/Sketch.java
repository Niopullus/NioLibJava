package com.niopullus.NioLib;

import java.awt.image.BufferedImage;

/**Denotes an Object as drawable without need for special concerns
 * Created by Owen on 7/4/2016.
 */
public interface Sketch {

    BufferedImage getImage();
    int getWidth();
    int getHeight();
    int getPictureWidth();
    int getPictureHeight();

}
