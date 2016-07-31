package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.draw.Parcel;
import com.niopullus.NioLib.draw.Canvas;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

/**Regulates various interactions pertaining to scenes
 * Created by Owen on 3/5/2016.
 */
public class SceneManager implements Parcel {

    private Scene currentScene;
    private List<Scene> scenes;

    public SceneManager() {
        scenes = new ArrayList<>();
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void tick() {
        if (currentScene != null) {
            currentScene.tock();
        }
    }

    public void parcelDraw(final Canvas canvas) {
        if (currentScene != null) {
            canvas.o.parcel(currentScene, 0, 0, 0, 0);
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
