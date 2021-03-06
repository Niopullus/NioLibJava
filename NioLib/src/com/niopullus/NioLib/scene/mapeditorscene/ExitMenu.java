package com.niopullus.NioLib.scene.mapeditorscene;

import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.guiscene.*;
import com.niopullus.NioLib.scene.guiscene.Button;
import com.niopullus.NioLib.scene.guiscene.Label;
import com.niopullus.app.InitScene;

import java.awt.*;

/**Used to prompt the user as to whether they would like to exit a scene
 * and return to InitScene
 * Created by Owen on 4/13/2016.
 */
public class ExitMenu extends GUIScene {

    private Button submitButton;
    private Button cancelButton;

    public ExitMenu() {
        super();
        final Theme theme = new Theme();
        final Background background = new ColorBackground(new Color(61, 179, 255, 180));
        final Label label = new Label("Exit?", theme, 40);
        submitButton = new Button("Yes", theme, 40);
        cancelButton = new Button("No", theme, 40);
        submitButton.setPosition(0, 100);
        cancelButton.setPosition(0, -100);
        label.setPosition(0, 300);
        setBackground(background);
        addElement(label);
        addElement(submitButton);
        addElement(cancelButton);
        label.setZ(500);
        submitButton.setZ(500);
        cancelButton.setZ(500);
        submitButton.setColor(Color.orange);
        submitButton.setSelectedColor(Color.orange);
    }

    public void buttonActivate(final Button b) {
        if (b == submitButton) {
            presentScene(new InitScene());
        } else if (b == cancelButton) {
            closeSubScene();
        }
    }

}
