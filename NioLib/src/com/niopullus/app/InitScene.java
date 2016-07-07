package com.niopullus.app;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.guiscene.*;
import com.niopullus.NioLib.scene.guiscene.Button;
import com.niopullus.NioLib.scene.guiscene.Label;

import java.awt.*;


/**Automatically presented when a NioLib project is run
 * Created by Owen on 4/3/2016.
 */
public class InitScene extends GUIScene {

    private Button button;
    private Button button2;

    /**
     * Scene setup
     */
    public InitScene() {
        final Theme theme = new Theme();
        final Label label = new Label("Test Label", theme, 40);
        final TextBox textBox = new TextBox("(Sample Text)", theme, 40, 500, 3);
        final SelectionBox selectionBox = new SelectionBox("First thingy", theme, 40);
        label.setPosition(-200, -200);
        textBox.setPosition(200, 200);
        selectionBox.setPosition(200, -200);
        setBackground(new ColorBackground(Color.orange));
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
        button = new Button("Test Button", theme, 40);
        button.setPosition(0, 0);
        button2 = new Button("The Second Button", theme, 40);
        button2.setPosition(-600, 200);
        textBox.setLineGap(20);
        textBox.setWidthGap(30);
        textBox.setJustify(GUIElement.Justify.RIGHT);
        selectionBox.addLine("fricku");
        selectionBox.addLine("fefeeef");
        selectionBox.addLine("pick me!");
        selectionBox.addLine("the great balancer");
        selectionBox.addLine("feeeeef");
        //addElement(label);
        addElement(button);
        //addElement(button2);
        //addElement(textBox);
        //addElement(selectionBox);
    }

    /**
     * Runs when a button is activated
     * @param b that was selected by the mouse/keyboard
     */
    public void buttonActivate(final Button b) {
        if (b == button) {
            //addSubScene(new TestSubScene());
        } else if (b == button2) {
            //presentScene(new TestDScene());
        }
    }

    public void update() {
        //System.out.println(getMousePos() + " - " + button.getRectOrigin());
    }

}
