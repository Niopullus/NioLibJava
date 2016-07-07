package com.niopullus.NioLib.scene.guiscene;


import java.awt.*;

/**Clickable trigger for specified code
 * Created by Owen on 3/5/2016.
 */
public class Button extends SelectableGUIElement {

    public Button(final String content, final Font font, final int widthGap, final int heightGap) {
        super(content, font, widthGap, heightGap);
    }

    public Button(final String content, final Theme theme, final int fontSize) {
        this(content, theme.getFont(fontSize), theme.getWidthGap(), theme.getHeightGap());
    }

    public void activate() {
        final GUIScene scene = getGUIScene();
        scene.buttonActivate(this);
    }

}
