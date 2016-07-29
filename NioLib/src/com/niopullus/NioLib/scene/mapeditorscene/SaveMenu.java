package com.niopullus.NioLib.scene.mapeditorscene;

import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.guiscene.*;
import com.niopullus.NioLib.scene.guiscene.Button;
import com.niopullus.NioLib.scene.guiscene.Label;

import java.awt.*;

/**Used to prompt the user as to how they would like to save a World from
 * the MapEditorScene.
 * Created by Owen on 4/13/2016.
 */
public class SaveMenu extends GUIScene {

    private TextBox textBox;
    private Button submitButton;
    private Button cancelButton;

    public SaveMenu(final String curFileName) {
        super();
        final Theme theme = new Theme();
        final Background background = new ColorBackground(new Color(61, 179, 255, 180));
        final Label label = new Label("Save World", theme, 40);
        label.setPosition(0, 300);
        textBox = new TextBox(curFileName, theme, 40, 500, 1);
        textBox.setPosition(0, 100);
        submitButton = new Button("Submit", theme, 40);
        submitButton.setPosition(0, -200);
        cancelButton = new Button("Cancel", theme, 40);
        cancelButton.setPosition(0, -300);
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

    public void buttonActivate(final Button b) {
        if (b == submitButton) {
            final MapEditorScene scene = (MapEditorScene) getSuperScene();
            scene.saveMap(textBox.getContent());
        } else if (b == cancelButton) {
            closeSubScene();
        }
    }

}
