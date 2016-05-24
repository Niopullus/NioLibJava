package com.niopullus.NioLib.scene.guiscene;


/**
 * Created by Owen on 3/5/2016.
 */
public class Button extends SelectableGUIElement {

    public Button(final String content, final int x, final int y, final int width, final int height) {
        super(content, x, y, width, height);
    }

    public void selectionAction() {
        this.getGUIScene().buttonActivate(this.getIndex());
    }

}
