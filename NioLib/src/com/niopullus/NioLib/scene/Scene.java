package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.draw.*;
import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.scene.dynscene.Direction;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**Stores data pertaining a specific group of items to be displayed
 * Created by Owen on 3/5/2016.
 */
public class Scene implements Parcel {

    private SceneManager sceneManager;
    private Scene subscene;
    private Scene superScene;
    private int width;
    private int height;
    private boolean isSubScene;

    public Scene() {
        width = Main.Width();
        height = Main.Height();
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

    public SceneManager getSceneManager() {
        if (superScene == null) {
            return sceneManager;
        } else {
            return superScene.sceneManager;
        }
    }

    public Point getMousePos() {
        final SceneManager sceneManager = getSceneManager();
        return sceneManager.getMousePos();
    }

    public boolean getMouseHeld() {
        final SceneManager sceneManager = getSceneManager();
        return sceneManager.getMouseHeld();
    }

    public boolean getMouseHeldRight() {
        final SceneManager sceneManager = getSceneManager();
        return sceneManager.getRightMouseHeld();
    }

    public boolean getMouseHeldMiddle() {
        final SceneManager sceneManager = getSceneManager();
        return sceneManager.getMiddleMouseHeld();
    }

    public Scene getSuperScene() {
        return superScene;
    }

    public boolean isSubScene() {
        return isSubScene;
    }

    public void setSize(final int width, final int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(final int _width) {
        width = _width;
    }

    public void setHeight(final int _height) {
        height = _height;
    }

    public void setSceneManager(final SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public Scene surfaceScene() {
        if (subscene == null) {
            return this;
        } else {
            return subscene.surfaceScene();
        }
    }

    public final void parcelDraw(final Canvas canvas) {
        drawScene(canvas);
        if (subscene != null) {
            canvas.c(getWidth(), getHeight()).parcel(subscene, 0, 0, 100, 0);
        }
    }

    public void drawScene(final Canvas canvas) {
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
        final SceneManager sceneManager = getSceneManager();
        sceneManager.presentScene(scene);
    }

    public int presentScene(final Scene scene, final boolean save){
        return sceneManager.presentScene(scene, save);
    }

    public void addSubScene(final Scene scene) {
        subscene = scene;
        scene.superScene = this;
        scene.setWidth(getWidth() * 3 / 4);
        scene.setHeight(getHeight() * 3 / 4);
        scene.updateScene();
    }

    public void updateScene() {
        //To be overridden
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
        pack.x = (int) (e.getX() * ((double) Main.Width() / Main.getWindowWidth()));
        pack.y = (int) (Main.Height() - (e.getY() * ((double) Main.Height() / Main.getWindowHeight())));
        scene.mouseMove(pack);
    }

    public final void mousePressed(final MouseEvent e) {
        final Scene scene = surfaceScene();
        final MousePack pack = new MousePack();
        pack.x = (int) (e.getX() * ((double) Main.Width() / Main.getWindowWidth()));
        pack.y = (int) (Main.Height() - (e.getY() * ((double) Main.Height() / Main.getWindowHeight())));
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
        pack.x = (int) (e.getX() * ((double) Main.Width() / Main.getWindowWidth()));
        pack.y = (int) (Main.Height() - (e.getY() * ((double) Main.Height() / Main.getWindowHeight())));
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
        pack.direction = e.getWheelRotation() > 0 ? Direction.N : e.getWheelRotation() < 0 ? Direction.S: null;
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

        public Point getPos() {
            return new Point(x, y);
        }

    }

    public static class MouseWheelPack {

        public Direction direction;
        public int notches;

    }

}
