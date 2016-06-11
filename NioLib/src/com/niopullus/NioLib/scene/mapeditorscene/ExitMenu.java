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

    public ExitMenu(Scene scene) {
        super(scene);
        Background background = new ColorBackground(this.getDx(), this.getDy(), this.getWidth(), this.getHeight(), new Color(61, 179, 255, 180));
        setBackground(background);
        Label label = new Label("Exit?", 0, 200, 300, 100);
        Button submitButton = new Button("Yes", 200, -200, 200, 100);
        Button cancelButton = new Button("No", -200, -200, 200, 100);
        addElement(label);
        addElement(submitButton);
        addElement(cancelButton);
        background.setZ(490);
        label.setZ(500);
        submitButton.setZ(500);
        cancelButton.setZ(500);
        submitButton.setColor(Color.orange);
        submitButton.setSelectedColor(Color.orange);
    }

    public void buttonActivate(int index) {
        if (index == 0) {
            presentScene(new InitScene());
        } else if (index == 1) {
            closeSubScene();
        }
    }

}
