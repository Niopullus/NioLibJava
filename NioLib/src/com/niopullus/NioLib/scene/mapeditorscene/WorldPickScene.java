package com.niopullus.NioLib.scene.mapeditorscene;

import com.niopullus.NioLib.Data;
import com.niopullus.NioLib.Root;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.dynscene.World;
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
        final List<String> worldNames;
        final Theme theme = new Theme();
        chooseWorldLabel = new Label("Select World", theme, 40);
        chooseWorldLabel.setPosition(0, 250);
        worlds = new SelectionBox("Create new world", theme, 40);
        worlds.setPosition(0, -150);
        submit = new Button("Submit", theme, 40);
        submit.setPosition(0, -300);
        back = new Button("Back", theme, 40);
        back.setPosition(-800, 450);
        setBackground(new ColorBackground(new Color(61, 179, 255)));
        chooseWorldLabel.setFontSize(75);
        submit.setColor(Color.ORANGE);
        submit.setSelectedColor(Color.orange);
        worlds.setZ(1000);
        worldNames = Root.getFileNamesFromFile("/worlds");
        for (String name : worldNames) {
            worlds.addLine(name);
        }
        addElement(chooseWorldLabel);
        addElement(worlds);
        addElement(submit);
        addElement(back);
    }

    public void buttonActivate(final Button b) {
        if (b == submit) {
            final String selectedWorldName = worlds.getContent();
            if (selectedWorldName.equals("Create new world")) {
                presentScene(new MapEditorScene());
            } else {
                presentScene(new MapEditorScene(World.loadWorld(worlds.getContent())));
            }
        } else if (b == back) {
            presentScene(new InitScene());
        }
    }

}
