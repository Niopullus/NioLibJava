package com.niopullus.NioLib.scene.guiscene;


import java.awt.*;

/**Clickable trigger for specified code
 * Created by Owen on 3/5/2016.
 */
public class Button extends SelectableGUIElement {

    public Button(final String content, final Font font, final int x, final int y, final int widthGap, final int heightGap) {
        super(content, font, x, y, widthGap, heightGap);
    }

    public void activate() {
        final GUIScene scene = getGUIScene();
        scene.buttonActivate(this);
    }

}
