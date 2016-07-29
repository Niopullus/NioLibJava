package com.niopullus.NioLib.scene.mapeditorscene;

import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.guiscene.Button;
import com.niopullus.NioLib.scene.guiscene.GUIScene;
import com.niopullus.NioLib.scene.guiscene.Label;
import com.niopullus.NioLib.scene.guiscene.Theme;

import java.awt.*;

/**Used to display to the user of a ListScene that what they have done
 * has resulted in an unintended action.
 * Created by Owen on 7/24/2016.
 */
public class ErrorScene extends GUIScene {

    private Button okay;

    public ErrorScene(final String msg) {
        super();
        final Theme theme = new Theme();
        theme.setBgColor(new Color(255, 67, 40));
        theme.setBorderColor(new Color(191, 2, 0));
        theme.setSelectedBgColor(new Color(255, 67, 40));
        theme.setSelectedBorderColor(new Color(191, 2, 0));
        theme.setSelectedTextColor(new Color(5, 255, 236));
        final Label title = new Label("Error", theme, 70);
        final Label message = new Label(msg, theme, 40);
        okay = new Button("Continue", theme, 40);
        title.setPosition(0, 200);
        message.setPosition(0, 0);
        okay.setPosition(0, -200);
        setBackground(new ColorBackground(new Color(255, 188, 43)));
        addElement(title);
        addElement(message);
        addElement(okay);
    }

    public void buttonActivate(final Button b) {
        closeSubScene();
    }

}
