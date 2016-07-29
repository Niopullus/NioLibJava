package com.niopullus.NioLib.scene.guiscene;

import java.awt.*;

/**Non-interactive GUIElement for displaying text
 * Created by Owen on 3/6/2016.
 */
public class Label extends GUIElement {

    public Label(final String content, final Theme theme, final int fontSize, final GUISize size) {
        super(content, theme, fontSize, size);
    }

    public Label(final String content, final Theme theme, final int fontSize) {
        this(content, theme, fontSize, new GUISize());
    }

}
