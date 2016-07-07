package com.niopullus.NioLib;

import com.niopullus.NioLib.scene.guiscene.*;
import com.niopullus.NioLib.scene.guiscene.Button;
import com.niopullus.NioLib.scene.guiscene.Label;
import com.niopullus.app.Config;
import com.niopullus.app.InitScene;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**If configured in such a way, the first time the program is run,
 * this scene will appear so the user can choose the directory
 * they wish to use
 * Created by Owen on 6/6/2016.
 */
public class SelectDirScene extends GUIScene {

    private FileManager fileManager;
    private Label label;
    private Label complain;
    private Button done;
    private TextBox textBox;

    public SelectDirScene(final FileManager fm) {
        final Theme theme = new Theme();
        final String defaultFolder = Data.getJarFolder();
        fileManager = fm;
        label = new Label("Choose the directory you would like to use for this program", theme, 40);
        complain = new Label("That directory is invalid", theme, 40);
        done = new Button("Done", theme, 40);
        textBox = new TextBox(defaultFolder, theme, 40, 500, 1);
        addElement(label);
        addElement(done);
        addElement(textBox);
    }

    public void buttonActivate(final SelectableGUIElement element) {
        if (element == done) {
            final String folder = textBox.getContent();
            if (Data.fileExists(folder)) {
                Data.createFileFromFile(folder, Config.DIRNAME);
                Root.init(fileManager, folder);
                presentScene(new InitScene());
                System.out.println("Initialized Root");
            } else {
                addElement(complain);
            }
        }
    }

}
