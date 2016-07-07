package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.Sketch;
import com.niopullus.NioLib.draw.Canvas;

/**Background that can display any Object which conforms to Sketch
 * Created by Owen on 7/4/16.
 */
public class SketchBackground extends Background {

    private Sketch sketch;

    public SketchBackground(final Sketch _sketch) {
        this(_sketch, 0, 0);
    }

    public SketchBackground(final Sketch _sketch, final int width, final int height) {
        super(width, height);
        sketch = _sketch;
    }

    public Sketch getSketch() {
        return sketch;
    }

    public void parcelDraw(final Canvas canvas) {
        canvas.o.sketch(sketch, 0, 0, getWidth(), getHeight(), 0);
    }

}
