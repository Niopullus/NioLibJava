package com.niopullus.NioLib.scene.guiscene;

import java.awt.event.KeyEvent;

/**
 * Created by Owen on 3/5/2016.
 */
public class TextBox extends SelectableGUIElement {

    private boolean expand;
    private boolean tick;
    private int tickTimer;

    public TextBox(String defaultContent, int x, int y, int width, int height) {
        super(defaultContent, x, y, width, height);
    }

    public void selectionAction() {
        if (!this.expand) {
            this.expand = true;
            this.overrideArrows();
        } else {
            this.expand = false;
            this.disableOverrideArrows();
        }
    }

    public void keyPress(KeyEvent k) {
        if (this.expand) {
            if (k.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (this.getContentO().length() - 1 >= 0) {
                    this.setContent(this.getContentO().substring(0, this.getContentO().length() - 1));
                }
            } else if (k.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
                this.setContent(this.getContentO() + k.getKeyChar());
            }
        }
    }

    public String getContent() {
        this.tickTimer++;
        if (this.tickTimer >= 30) {
            this.tick = !this.tick;
            this.tickTimer = 0;
        }
        if (this.tick && this.expand) {
            return super.getContent() + "_";
        } else {
            return super.getContent();
        }
    }

}
