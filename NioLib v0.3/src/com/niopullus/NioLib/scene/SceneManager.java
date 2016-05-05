package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

/**
 * Created by Owen on 3/5/2016.
 */
public class SceneManager {

    private Main main;
    private Scene currentScene;
    private ArrayList<Scene> scenes;

    public SceneManager(Main main) {
        this.main = main;
        this.scenes = new ArrayList<Scene>();
    }

    public void tick() {
        if (this.currentScene != null) {
            this.currentScene.tick();
        }
    }

    public void draw() {
        if (this.currentScene != null) {
            this.currentScene.draw();
        }
    }

    public void keyPress(KeyEvent key) {
        this.currentScene.fkeyPress(key);
    }

    public void keyReleased(KeyEvent key) {
        this.currentScene.fkeyReleased(key);
    }

    public int presentScene(Scene scene, boolean save) {
        int index = -1;
        if (save) {
            index = this.scenes.size();
            this.scenes.add(scene);
        }
        this.currentScene = scene;
        scene.setSceneManager(this);
        scene.setDx(0);
        scene.setDy(0);
        scene.setWidth(Main.Width());
        scene.setHeight(Main.Height());
        return index;
    }

    public void presentScene(Scene scene) {
        this.presentScene(scene, false);
    }

    public void mouseMoved(Point pos) {
        if (this.currentScene != null) {
            this.currentScene.mouseMoved(pos);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (this.currentScene != null) {
            this.currentScene.mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (this.currentScene != null) {
            this.currentScene.mouseReleased(e);
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        this.currentScene.mouseWheelMoved(e);
    }

}
