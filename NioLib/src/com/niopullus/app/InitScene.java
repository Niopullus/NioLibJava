package com.niopullus.app;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.guiscene.*;
import com.niopullus.NioLib.scene.guiscene.Button;
import com.niopullus.NioLib.scene.guiscene.Label;
import com.niopullus.NioLib.scene.mapeditorscene.WorldPickScene;

import java.awt.*;


/**Automatically presented when a NioLib project is run
 * Created by Owen on 4/3/2016.
 */
public class InitScene extends GUIScene {

    private Button button;
    private Button button2;
    private Button button3;
    private Button prevPage;
    private Button nextPage;
    private Series series1;

    /**
     * Scene setup
     */
    public InitScene() {
        final Theme theme = new Theme();
        theme.setBgColor(Color.ORANGE);
        theme.setBorderColor(Color.RED);
        theme.setTextColor(Color.BLUE);
        /**
        final Label label1 = new Label("This is a test for label 1", theme, 40);
        final Button label2 = new Button("This is a test for label 2", theme, 40, new GUISize(400, 200));
        final SelectionBox box1 = new SelectionBox("the first item", theme, 40);
        final SelectionBox box2 = new SelectionBox("the first item", theme, 40, new GUISize(400, 100));
        final TextBox textBox1 = new TextBox("typeeee", theme, 40, 500, 3);
        final TextBox textBox2 = new TextBox("typeeee", theme, 40, new GUISize(500, 200));
        final Label pageLabel = new Label("", theme, 40);
        series1 = new Series("#1 item mate", theme, 40, 6);
        prevPage = new Button("Prev Page", theme, 40, new GUISize(400, 0, true, false));
        nextPage = new Button("Next Page", theme, 40, new GUISize(400, 0, true, false));
        box1.addLine("rgrggr");
        box1.addLine("efefegh");
        box2.addLine("rijrggr");
        box2.addLine("erhtfdd");
        box2.addLine("efeujeeuieiuuifeiueiuefuief");
        label1.addLine("and this is line two!");
        label2.addLine("and this is line two!");
        label2.addLine("kekekekekekekekekekekekekekek");
        series1.addLine("this is a longer string of text");
        label1.setPosition(0, 200);
        label2.setPosition(0, -200);
        label2.setJustify(GUIElement.Justify.LEFT);
        box1.setPosition(-500, -100);
        box2.setPosition(500, -400);
        textBox1.setPosition(-200, 400);
        textBox2.setPosition(500, -100);
        prevPage.setPosition(-600, -200);
        nextPage.setPosition(-600, -400);
        pageLabel.setPosition(500, 0);
        series1.setPageDisplay(pageLabel);
        series1.setJustify(GUIElement.Justify.LEFT);
        series1.enableCheckBoxes();
        series1.setCheckLimit(3);
        series1.addLine("uhh im bored");
        series1.addLine("what the frick do I write?");
        series1.addLine("poop");
        series1.addLine("series.addLine(...)");
        series1.addLine("I'm adding a line");
        series1.addLine("Is this enough?");
        series1.addLine("Okay one more");
        //addElement(label1);
        //addElement(label2);
        addElement(box1);
        addElement(box2);
        addElement(prevPage);
        addElement(nextPage);
        addElement(pageLabel);
        //addElement(textBox1);
        //addElement(textBox2);
        addElement(series1);
        box2.setJustify(GUIElement.Justify.RIGHT);
        box1.setJustify(GUIElement.Justify.RIGHT);
         **/
        theme.setBgColor(Color.blue);
        theme.setTextColor(Color.ORANGE);
        theme.setBorderColor(Color.red);
        theme.setSelectedBgColor(Color.MAGENTA);
        theme.setSelectedBorderColor(Color.PINK);
        theme.setSelectedTextColor(Color.YELLOW);
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
        button3 = new Button("make the WORLD!", theme, 40);
        button3.setPosition(-350, 400);
        button3.setZ(40000);
        addElement(label);
        addElement(button);
        addElement(button2);
        addElement(textBox);
        addElement(selectionBox);
        addElement(button3);
    }

    /**
     * Runs when a button is activated
     * @param b that was selected by the mouse/keyboard
     */
    public void buttonActivate(final Button b) {
        if (b == button) {
            addSubScene(new TestSubScene());
        } else if (b == button2) {
            presentScene(new TestDScene());
        } else if (b == button3) {
            presentScene(new WorldPickScene());
        } else if (b == prevPage) {
            series1.prevPage();
        } else if (b == nextPage) {
            series1.nextPage();
        }
    }

}
