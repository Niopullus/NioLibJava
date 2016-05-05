package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.Animation;

/**
 * Created by Owen on 3/24/2016.
 */
public class AnimatedBackground extends Background {

    private Animation animation;

    public AnimatedBackground() {
        this(0, 0, 0, 0, null);
    }

    public AnimatedBackground(int x, int y, int width, int height, Animation animation) {
        super(x, y, width, height);
        this.animation = animation;
    }

    public void draw() {
        this.draw(this.getX(), this.getY());
    }

    public void draw(int x, int y) {
        this.animation.draw(x, y, x + this.getWidth(), y + this.getHeight(), this.getZ(), 0);
    }

}
