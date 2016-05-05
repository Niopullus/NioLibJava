package com.niopullus.NioLib.scene.guiscene;


/**
 * Created by Owen on 3/5/2016.
 */
public class Button extends SelectableGUIElement {

    public Button(String content, int x, int y, int width, int height) {
        super(content, x, y, width, height);
    }

    public void selectionAction() {
        this.getGUIScene().buttonActivate(this.getIndex());
    }

}
