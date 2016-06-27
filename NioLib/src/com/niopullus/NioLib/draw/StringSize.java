package com.niopullus.NioLib.draw;

import java.awt.*;
import java.awt.Canvas;

/**
 * Created by Owen on 6/26/2016.
 */
public class StringSize {

    private static final Canvas canvas = new Canvas();

    public static FontMetrics getFontMetrics(final Font font) {
        return canvas.getFontMetrics(font);
    }

}
