package com.niopullus.NioLib;

import com.niopullus.NioLib.scene.guiscene.Button;
import com.niopullus.NioLib.scene.guiscene.GUIScene;
import com.niopullus.NioLib.scene.guiscene.Label;
import com.niopullus.NioLib.scene.guiscene.TextBox;

import java.util.ArrayList;

/**If configured in such a way, the first time the program is run,
 * this scene will appear so the user can choose the directory
 * they wish to use
 * Created by Owen on 6/6/2016.
 */
public class SelectDirScene extends GUIScene {

    private Label label;
    private Button done;
    private Button checkValid;
    private TextBox textBox;

    public SelectDirScene() {
        label = new Label();
    }

}
