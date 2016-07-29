package com.niopullus.NioLib.scene.guiscene;


import java.awt.*;

/**Clickable trigger for specified code
 * Created by Owen on 3/5/2016.
 */
public class Button extends SelectableGUIElement {

    public Button(final String content, final Theme theme, final int fontSize, final GUISize size) {
        super(content, theme, fontSize, size);
    }

    public Button(final String content, final Theme theme, final int fontSize) {
        this(content, theme, fontSize, new GUISize());
    }

    public void activate() {
        final GUIScene scene = getGUIScene();
        scene.buttonActivate(this);
    }

}
