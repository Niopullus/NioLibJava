package com.niopullus.NioLib.scene.guiscene;


import java.awt.*;

/**Clickable trigger for specified code
 * Created by Owen on 3/5/2016.
 */
public class Button extends SelectableGUIElement {

    public Button(final String content, final Font font, final int widthGap, final int heightGap, final Theme theme) {
        super(content, font, widthGap, heightGap, theme);
    }

    public Button(final String content, final Font font, final int widthGap, final int heightGap) {
        this(content, font, widthGap, heightGap, null);
    }

    public Button(final String content, final Theme theme, final int fontSize) {
        this(content, theme.getFont(fontSize), theme.getWidthGap(), theme.getHeightGap(), theme);
    }

    public void activate() {
        final GUIScene scene = getGUIScene();
        scene.buttonActivate(this);
    }

}
