package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.guiscene.SelectableGUIElement;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Created by Owen on 3/5/2016.
 */
public class Scene {

    private SceneManager sceneManager;
    private Scene subscene;
    private Scene superScene;
    private int width;
    private int height;
    private int dx;
    private int dy;
    private boolean isSubScene;
    private Point mousePos;
    private boolean mouseHeld;
    private boolean mouseHeldRight;
    private boolean mouseHeldMiddle;

    public Scene() {
        this.dx = 0;
        this.dy = 0;
        this.width = Main.Width();
        this.height = Main.Height();
    }

    public Scene(Scene superScene) {
        this.superScene = superScene;
        this.isSubScene = true;
        this.dx = this.superScene.width / 6;
        this.dy = this.superScene.height / 6;
        this.setWidth(this.superScene.width * 2 / 3);
        this.setHeight(this.superScene.height * 2 / 3);
    }

    public void draw() {

    }

    public void tick() {

    }

    public void keyPress(KeyEvent key) {

    }

    public void keyReleased(KeyEvent key) {

    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void presentScene(Scene scene) {
        if (this.superScene != null) {
            this.superScene.presentScene(scene);
        } else {
            this.sceneManager.presentScene(scene);
        }
    }

    public int presentScene(Scene scene, boolean save){
        if (save) {
            return this.sceneManager.presentScene(scene, true);
        }
        this.sceneManager.presentScene(scene);
        return -1;
    }

    public void addSubScene(Scene scene) {
        this.subscene = scene;
        this.subscene.subSceneUpdate();
        scene.superScene = this;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setOffSet(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public Scene getSubscene() {
        return subscene;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public boolean isSubScene() {
        return isSubScene;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void closeSubScene() {
        this.superScene.removeSubScene();
    }

    public void removeSubScene() {
        this.subscene = null;
    }

    public final void fkeyPress(KeyEvent key) {
        if (this.getSubscene() == null) {
            this.keyPress(key);
        } else {
            this.getSubscene().keyPress(key);
        }
    }

    public final void fkeyReleased(KeyEvent key) {
        if (this.getSubscene() == null) {
            this.keyReleased(key);
        } else {
            this.getSubscene().keyReleased(key);
        }
    }

    public Scene getSuperScene() {
        return this.superScene;
    }

    public void subSceneUpdate() {


    }

    public final void mouseMoved(Point pos) {
        if (this.subscene != null) {
            this.getSubscene().mouseMoved(pos);
        } else {
            this.mousePos = pos;
            this.mouseMove();
        }
    }

    public final void mousePressed(MouseEvent e) {
        if (this.subscene != null) {
            this.getSubscene().mousePressed(e);
        } else {
            if (e.getButton() == 1) {
                this.mouseHeld = true;
                this.mousePress();
            } else if (e.getButton() == 2) {
                this.mouseHeldRight = true;
                this.mousePressRight();
            } else if (e.getButton() == 3) {
                this.mouseHeldMiddle = true;
                this.mousePressMiddle();
            }
        }
    }

    public final void mouseReleased(MouseEvent e) {
        if (this.subscene != null) {
            this.getSubscene().mouseReleased(e);
        } else {
            if (e.getButton() == 1) {
                this.mouseRelease();
                this.mouseHeld = false;
            } else if (e.getButton() == 2) {
                this.mouseReleaseRight();
                this.mouseHeldRight = false;
            } else if (e.getButton() == 3) {
                this.mouseReleaseMiddle();
                this.mouseHeldMiddle = false;
            }
        }
    }

    public void mouseMove() {

    }

    public void mousePress() {

    }

    public void mouseRelease() {

    }

    public void mousePressRight() {

    }

    public void mouseReleaseRight() {

    }

    public void mousePressMiddle() {

    }

    public void mouseReleaseMiddle() {

    }

    public Point getMousePos() {
        if (this.mousePos != null) {
            return new Point(this.mousePos.x * Main.Width() / Main.getFrameWidth() + this.dx, this.mousePos.y * Main.Height() / Main.getFrameHeight() + this.dy);
        } else {
            return new Point(0,0);
        }
    }

    public boolean getMouseHeld() {
        return this.mouseHeld;
    }

    public void setMousePos(Point p) {
        this.mousePos = p;
    }

    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public boolean getMouseHeldRight() {
        return this.mouseHeldRight;
    }

    public boolean getMouseHeldMiddle() {
        return this.mouseHeldMiddle;
    }

}
