package com.niopullus.app;

import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.guiscene.*;
import com.niopullus.NioLib.scene.guiscene.Button;
import com.niopullus.NioLib.scene.guiscene.Label;

import java.awt.*;


/**Automatically presented when a NioLib project is run
 * Created by Owen on 4/3/2016.
 */
public class InitScene extends GUIScene {

    /**
     * Scene setup
     */
    public InitScene() {
        final Font font = new Font("Bold", Font.BOLD, 40);
        final Label label = new Label("Test Label", font, 0, 0, 10, 10);
        label.addLine("Tacos are good");
        label.addLine("frick u");
        label.addLine("jjjj");
        label.addLine("Did the j's ruin it?");
        label.setJustify(GUIElement.Justify.LEFT);
        label.setLineGap(10);
        label.setBackground(new ColorBackground(Color.BLUE));
        label.setBorder(new ColorBackground(Color.GREEN));
        label.setTextColor(Color.MAGENTA);
        label.setBorderSpacing(20);
        final Button button = new Button("Test Button", font, 0, 200, 10, 10);
        addElement(label);
        addElement(button);
    }

    /**
     * Runs when a button is activated
     * @param element that was selected by the mouse/key board
     */
    public void buttonActivate(final SelectableGUIElement element) {

    }

}
