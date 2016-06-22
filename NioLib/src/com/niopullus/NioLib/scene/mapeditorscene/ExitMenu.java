package com.niopullus.NioLib.scene.mapeditorscene;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.guiscene.*;
import com.niopullus.NioLib.scene.guiscene.Button;
import com.niopullus.NioLib.scene.guiscene.Label;
import com.niopullus.app.InitScene;

import java.awt.*;

/**
 * Created by Owen on 4/13/2016.
 */
public class ExitMenu extends GUIScene {

    private Button submitButton;
    private Button cancelButton;

    public ExitMenu() {
        super();
        final Font font = new Font("Bold", Font.BOLD, 40);
        final Background background = new ColorBackground(new Color(61, 179, 255, 180));
        final Label label = new Label("Exit?", font, 0, 200, 300, 100);
        submitButton = new Button("Yes", font,  200, -200, 200, 100);
        cancelButton = new Button("No", font, -200, -200, 200, 100);
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

    public void buttonActivate(final SelectableGUIElement element) {
        if (element == submitButton) {
            presentScene(new InitScene());
        } else if (element == cancelButton) {
            closeSubScene();
        }
    }

}
