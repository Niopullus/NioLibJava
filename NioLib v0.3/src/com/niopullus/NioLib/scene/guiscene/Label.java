package com.niopullus.NioLib.scene.guiscene;

/**
 * Created by Owen on 3/6/2016.
 */
public class Label extends GUIElement {

    public Label() {
        this(new String(), 0, 0, 0, 0, 0);
    }

    public Label(String content, int x, int y, int width, int height) {
        this(content, x, y, 0, width, height);
    }

    public Label(String content, int x, int y, int z, int width, int height) {
        super(content, x, y, z, width, height);
    }

}
