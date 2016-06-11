package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.Animation;
import com.niopullus.NioLib.draw.Draw;

/**Rectangular display of an animation
 * Created by Owen on 3/24/2016.
 */
public class AnimatedBackground extends Background {

    private Animation animation;

    public AnimatedBackground(final Animation animation, final int width, final int height) {
        super(width, height);
        this.animation = animation;
    }

    public AnimatedBackground(final Animation animation, final double scaleFactor) {
        this(animation, (int) (animation.getWidth() * scaleFactor), (int) (animation.getHeight() * scaleFactor));
    }

    public AnimatedBackground(final Animation animation) {
        this(animation, 1);
    }

    public void draw(final int x, final int y, final int z, final Draw.DrawMode mode) {
        Draw.mode(mode).animation(animation, x, y, getWidth(), getHeight(), z);
    }

}
