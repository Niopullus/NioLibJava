package com.niopullus.NioLib.scene.mapeditorscene;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.guiscene.*;
import com.niopullus.NioLib.scene.guiscene.Button;
import com.niopullus.NioLib.scene.guiscene.Label;

import java.awt.*;

/**
 * Created by Owen on 4/13/2016.
 */
public class SaveMenu extends GUIScene {

    private MapEditorScene scene;
    private TextBox textBox;

    public SaveMenu(MapEditorScene superscene, String curFileName) {
        super(superscene);
        Background background = new ColorBackground(this.getDx(), this.getDy(), this.getWidth(), this.getHeight(), new Color(61, 179, 255, 180));
        setBackground(background);
        Label label = new Label("Save World", 0, 200, 300, 100);
        if (curFileName == null) {
            textBox = new TextBox("World Name", 0, 0, 500, 100);
        } else {
            textBox = new TextBox(curFileName.substring(0, curFileName.length() - 12), 0, 0, 500, 100);
        }
        Button submitButton = new Button("Submit", 200, -200, 200, 100);
        Button cancelButton = new Button("Cancel", -200, -200, 200, 100);
        addElement(label);
        addElement(textBox);
        addElement(submitButton);
        addElement(cancelButton);
        background.setZ(490);
        label.setZ(500);
        textBox.setZ(500);
        submitButton.setZ(500);
        cancelButton.setZ(500);
        submitButton.setColor(Color.orange);
        submitButton.setSelectedColor(Color.orange);
        this.scene = superscene;
    }

    public void buttonActivate(int index) {
        if (index == 1) {
            this.scene.saveMap(this.textBox.getContent());
        } else if (index == 2) {
            closeSubScene();
        }
    }

}
