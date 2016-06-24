package com.niopullus.NioLib.scene.mapeditorscene;

import com.niopullus.NioLib.Data;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.guiscene.*;
import com.niopullus.NioLib.scene.guiscene.Button;
import com.niopullus.NioLib.scene.guiscene.Label;
import com.niopullus.app.InitScene;

import java.awt.*;
import java.util.List;


/**GUIScene used for the user to select a world to be sent to the map
 * editor scene
 * Created by Owen on 4/13/2016.
 */
public class WorldPickScene extends GUIScene {

    private Label chooseWorldLabel;
    private SelectionBox worlds;
    private Button submit;
    private Button back;

    public WorldPickScene() {
        super();
        final Font font = new Font("Bold", Font.BOLD, 40);
        final List<String> worldNames;
        chooseWorldLabel = new Label("Select World", font, 0, 300, 600, 150);
        worlds = new SelectionBox("Create new world", font, 0, 0, 600, 100);
        submit = new Button("Submit", font, 0, -200, 300, 100);
        back = new Button("Back", font, -400, 300, 100, 50);
        setBackground(new ColorBackground(new Color(61, 179, 255)));
        chooseWorldLabel.setFontSize(75);
        submit.setColor(Color.ORANGE);
        submit.setSelectedColor(Color.orange);
        worlds.setZ(1000);
        worldNames = Data.getFileNamesFromFile("/worlds");
        for (String name : worldNames) {
            worlds.addLine(name);
        }
        addElement(chooseWorldLabel);
        addElement(worlds);
        addElement(submit);
        addElement(back);
    }

    public void buttonActivate(final SelectableGUIElement element) {
        if (element == submit) {
            final String selectedWorldName = worlds.getContent();
            if (selectedWorldName.equals("Create new world")) {
                presentScene(new MapEditorScene());
            } else {
                presentScene(new MapEditorScene(selectedWorldName));
            }
        } else if (element == back) {
            presentScene(new InitScene());
        }
    }

}
