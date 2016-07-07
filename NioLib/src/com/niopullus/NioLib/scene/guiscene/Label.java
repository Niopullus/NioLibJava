package com.niopullus.NioLib.scene.guiscene;

import java.awt.*;

/**Non-interactive GUIElement for displaying text
 * Created by Owen on 3/6/2016.
 */
public class Label extends GUIElement {

    public Label(final String content, final Font font, final int widthGap, final int heightGap) {
        super(content, font, widthGap, heightGap);
    }

    public Label(final String content, final Theme theme, final int fontSize) {
        this(content, theme.getFont(fontSize), theme.getWidthGap(), theme.getHeightGap());
    }

}
