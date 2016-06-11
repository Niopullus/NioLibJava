package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.dynscene.Dir;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**Stores data pertaining a specific group of items to be displayed
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

    public Scene() {
        this.dx = 0;
        this.dy = 0;
        this.width = Main.Width();
        this.height = Main.Height();
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

    public Point getMousePos() {
        return sceneManager.getMousePos();
    }

    public boolean getMouseHeld() {
        return sceneManager.getMouseHeld();
    }

    public boolean getMouseHeldRight() {
        return sceneManager.getRightMouseHeld();
    }

    public boolean getMouseHeldMiddle() {
        return sceneManager.getMiddleMouseHeld();
    }

    public Scene getSuperScene() {
        return superScene;
    }

    public boolean isSubScene() {
        return isSubScene;
    }

    public void setSize(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public void setDx(final int dx) {
        this.dx = dx;
    }

    public void setDy(final int dy) {
        this.dy = dy;
    }

    public void setSceneManager(final SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void setOffSet(final int dx, final int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    private Scene surfaceScene() {
        if (subscene == null) {
            return this;
        } else {
            return subscene.surfaceScene();
        }
    }

    public void draw() {
        //To be overridden
    }

    public final void tock() {
        final Scene scene = surfaceScene();
        scene.tick();
    }

    public void tick() {
        //To be overridden
    }

    public void presentScene(final Scene scene) {
        sceneManager.presentScene(scene);
    }

    public int presentScene(final Scene scene, final boolean save){
        return sceneManager.presentScene(scene, save);
    }

    public void addSubScene(final Scene scene) {
        subscene = scene;
        scene.superScene = this;
    }

    public void closeSubScene() {
        superScene.removeSubScene();
    }

    public void removeSubScene() {
        subscene = null;
    }

    public final void fkeyPress(final KeyEvent e) {
        final Scene scene = surfaceScene();
        final KeyPack pack = new KeyPack();
        pack.code = e.getKeyCode();
        pack.letter = e.getKeyChar();
        scene.keyPress(pack);
    }

    public final void fkeyReleased(final KeyEvent e) {
        final Scene scene = surfaceScene();
        final KeyPack pack = new KeyPack();
        pack.code = e.getKeyCode();
        pack.letter = e.getKeyChar();
        scene.keyReleased(pack);
    }

    public final void mouseMoved(final MouseEvent e) {
        final Scene scene = surfaceScene();
        final MousePack pack = new MousePack();
        pack.x = e.getX();
        pack.y = e.getY();
        scene.mouseMove(pack);
    }

    public final void mousePressed(final MouseEvent e) {
        final Scene scene = surfaceScene();
        final MousePack pack = new MousePack();
        pack.x = e.getX();
        pack.y = e.getY();
        if (e.getButton() == 1) {
            scene.mousePress(pack);
        } else if (e.getButton() == 2) {
            scene.mousePressRight(pack);
        } else if (e.getButton() == 3) {
            scene.mousePressMiddle(pack);
        }
    }

    public final void mouseReleased(final MouseEvent e) {
        final Scene scene = surfaceScene();
        final MousePack pack = new MousePack();
        pack.x = e.getX();
        pack.y = e.getY();
        if (e.getButton() == 1) {
            scene.mouseRelease(pack);
        } else if (e.getButton() == 2) {
            scene.mouseReleaseRight(pack);
        } else if (e.getButton() == 3) {
            scene.mouseReleaseMiddle(pack);
        }
    }

    public final void mouseWheelMoved(final MouseWheelEvent e) {
        final Scene scene = surfaceScene();
        final MouseWheelPack pack = new MouseWheelPack();
        pack.direction = e.getWheelRotation() > 0 ? Dir.N : e.getWheelRotation() < 0 ? Dir.S: null;
        pack.notches = Math.abs(e.getWheelRotation());
        scene.mouseWheelMove(pack);
    }

    public void keyPress(final KeyPack pack) {
        //To be overridden
    }

    public void keyReleased(final KeyPack key) {
        //To be overridden
    }

    public void mouseMove(final MousePack pack) {
        //To be overridden
    }

    public void mousePress(final MousePack pack) {
        //To be overridden
    }

    public void mouseRelease(final MousePack pack) {
        //To be overridden
    }

    public void mousePressRight(final MousePack pack) {
        //To be overridden
    }

    public void mouseReleaseRight(final MousePack pack) {
        //To be overridden
    }

    public void mousePressMiddle(final MousePack pack) {
        //To be overridden
    }

    public void mouseReleaseMiddle(final MousePack pack) {
        //To be overridden
    }

    public void mouseWheelMove(final MouseWheelPack pack) {
        //To be overridden
    }

    public static class KeyPack {

        public char letter;
        public int code;

    }

    public static class MousePack {

        public int x;
        public int y;

    }

    public static class MouseWheelPack {

        public Dir direction;
        public int notches;

    }

}
