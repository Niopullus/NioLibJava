package com.niopullus.NioLib.draw;

import com.niopullus.NioLib.scene.guiscene.Size;

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

    public static Size getStringSize(final String line, final Font font) {
        final FontMetrics metrics = canvas.getFontMetrics(font);
        final int width = metrics.stringWidth(line);
        final int height = metrics.getAscent() - metrics.getDescent();
        return new Size(width, height);
    }

}
