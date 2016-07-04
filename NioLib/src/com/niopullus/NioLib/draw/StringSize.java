package com.niopullus.NioLib.draw;

import java.awt.*;
import java.awt.Canvas;

/**Used as a relay for getting FontMetrics
 * Created by Owen on 6/26/2016.
 */
public final class StringSize {

    private final static Canvas canvas = new Canvas();

    private StringSize() {
        //Blank implementation
    }

    public static FontMetrics getFontMetrics(final Font font) {
        return canvas.getFontMetrics(font);
    }

}
