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
    private SelectionBox selectionBox;

    public SaveMenu(String curFileName) {
        super();
        final Font font = new Font("Bold", Font.BOLD, 40);
        final Background background = new ColorBackground(new Color(61, 179, 255, 180));
        final Label label = new Label("Save World", font, 0, 200, 300, 100);
        selectionBox = new SelectionBox("File", font, 0, 400, 300, 100);
        textBox = new TextBox(curFileName.substring(0, curFileName.length() - 12), font, 0, 0, 500, 100);
        submitButton = new Button("Submit", font, 200, -200, 200, 100);
        cancelButton = new Button("Cancel", font, -200, -200, 200, 100);
        selectionBox.addLine("Jar");
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
            scene.saveMap(Data.DataRoot.FILE);
        } else if (element == cancelButton) {
            closeSubScene();
        }
    }

}
