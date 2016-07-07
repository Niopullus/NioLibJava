package com.niopullus.NioLib.scene.mapeditorscene;

import com.niopullus.NioLib.Data;
import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.guiscene.*;
import com.niopullus.NioLib.scene.guiscene.Button;
import com.niopullus.NioLib.scene.guiscene.Label;

import java.awt.*;

/**
 * Created by Owen on 4/13/2016.
 */
public class SaveMenu extends GUIScene {

    private TextBox textBox;
    private Button submitButton;
    private Button cancelButton;

    public SaveMenu(String curFileName) {
        super();
        final Theme theme = new Theme();
        final Background background = new ColorBackground(new Color(61, 179, 255, 180));
        final Label label = new Label("Save World", theme, 40);
        label.setPosition(300, 100);
        textBox = new TextBox(curFileName.substring(0, curFileName.length() - 12), theme, 40, 500, 1);
        textBox.setPosition(500, 100);
        submitButton = new Button("Submit", theme, 40);
        submitButton.setPosition(200, 100);
        cancelButton = new Button("Cancel", theme, 40);
        cancelButton.setPosition(200, 100);
        setBackground(background);
        addElement(label);
        addElement(textBox);
        addElement(submitButton);
        addElement(cancelButton);
        label.setZ(500);
        textBox.setZ(500);
        submitButton.setZ(500);
        cancelButton.setZ(500);
        submitButton.setColor(Color.orange);
        submitButton.setSelectedColor(Color.orange);
    }

    public void buttonActivate(final SelectableGUIElement element) {
        if (element == submitButton) {
            final MapEditorScene scene = (MapEditorScene) getSuperScene();
            scene.saveMap();
        } else if (element == cancelButton) {
            closeSubScene();
        }
    }

}
