package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

/**Regulates various interactions pertaining to scenes
 * Created by Owen on 3/5/2016.
 */
public class SceneManager {

    private Main main;
    private Scene currentScene;
    private List<Scene> scenes;

    public SceneManager(final Main main) {
        this.main = main;
        this.scenes = new ArrayList<>();
    }

    public Point getMousePos() {
        return main.getMousePosition();
    }

    public boolean getMouseHeld() {
        return main.getMouseHeld();
    }

    public boolean getRightMouseHeld() {
        return main.getRightMouseHeld();
    }

    public boolean getMiddleMouseHeld() {
        return main.getMiddleMouseHeld();
    }

    public void tick() {
        if (currentScene != null) {
            currentScene.tock();
        }
    }

    public void draw() {
        if (currentScene != null) {
            currentScene.draw();
        }
    }

    public void keyPress(final KeyEvent key) {
        currentScene.fkeyPress(key);
    }

    public void keyReleased(final KeyEvent key) {
        currentScene.fkeyReleased(key);
    }

    public int presentScene(final Scene scene, final boolean save) {
        int index = -1;
        if (save) {
            index = scenes.size();
            scenes.add(scene);
        }
        currentScene = scene;
        scene.setSceneManager(this);
        scene.setDx(0);
        scene.setDy(0);
        scene.setWidth(Main.Width());
        scene.setHeight(Main.Height());
        return index;
    }

    public void presentScene(final Scene scene) {
        presentScene(scene, false);
    }

    public void mouseMoved(final MouseEvent e) {
        if (currentScene != null) {
            currentScene.mouseMoved(e);
        }
    }

    public void mousePressed(final MouseEvent e) {
        if (currentScene != null) {
            currentScene.mousePressed(e);
        }
    }

    public void mouseReleased(final MouseEvent e) {
        if (currentScene != null) {
            currentScene.mouseReleased(e);
        }
    }

    public void mouseWheelMoved(final MouseWheelEvent e) {
        if (currentScene != null) {
            currentScene.mouseWheelMoved(e);
        }
    }

}
