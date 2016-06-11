package com.niopullus.NioLib;

import com.niopullus.NioLib.draw.Draw;
import com.niopullus.NioLib.scene.SceneManager;
import com.niopullus.app.Config;
import com.niopullus.app.InitScene;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**Manages the program
 * Created by Owen on 3/5/2016.
 */
public class Main extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private static JFrame jFrame;
    private Thread thread;
    private boolean running;
    private int FPS = 30;
    private long targetTime = 1000 / FPS;
    private BufferedImage image;
    private Graphics2D g;
    private SceneManager sceneManager;
    private FileManager fileManager;
    private Point mousePos;
    private boolean mouseHeld;
    private boolean rightMouseHeld;
    private boolean middleMouseHeld;
    private static final int WIDTH = Config.IMGWIDTH;
    private static final int HEIGHT = Config.IMGHEIGHT;
    private static final double SCALE = Config.WINDOWSCALE;

    public static void main(final String[] args) {
        final JFrame window = new JFrame(Config.WINDOWTITLE);
        jFrame = window;
        window.setContentPane(new Main());
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.pack();
        window.setVisible(true);
        Config.init();
    }

    public Main() {
        super();
        setPreferredSize(new Dimension((int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)));
        setFocusable(Config.WINDOWRESIZABLE);
        requestFocus();
    }

    public boolean getMouseHeld() {
        return mouseHeld;
    }

    public boolean getRightMouseHeld() {
        return rightMouseHeld;
    }

    public boolean getMiddleMouseHeld() {
        return middleMouseHeld;
    }

    public void addNotify() {
        super.addNotify();
        if (this.thread == null) {
            this.thread = new Thread(this);
            addKeyListener(this);
            addMouseListener(this);
            addMouseMotionListener(this);
            addMouseWheelListener(this);
            thread.start();
        }
    }

    public void init() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) this.image.getGraphics();
        running = true;
        sceneManager = new SceneManager(this);
        if (Config.CREATEFOLDER) {
            if (Config.PROMPTFOLDERDIRECTORY) {
                presentSelectDirScene();
            } else {
                Data.setRootDir(FileManager.getJarDir() + "/" + Config.DIRNAME);
                presentInitScene();
            }
        }
    }

    public void presentInitScene() {
        sceneManager.presentScene(new InitScene());
    }

    public void presentSelectDirScene() {
        sceneManager.presentScene(new SelectDirScene());
    }

    public void run() {
        long start;
        long elapsed;
        long wait;
        init();
        while (running) {
            start = System.nanoTime();
            sceneManager.tick();
            sceneManager.draw();
            Draw.display(g);
            drawToScreen();
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            if (wait < 0) {
                wait = 5;
            }
            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void drawToScreen() {
        final Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, Main.getFrameWidth(), Main.getFrameHeight(), null);
        g2.dispose();
    }

    public void keyTyped(final KeyEvent key) {
        //Blank Implementation
    }

    public void keyPressed(final KeyEvent key) {
        sceneManager.keyPress(key);
    }

    public void keyReleased(final KeyEvent key) {
        sceneManager.keyReleased(key);
    }

    public static JFrame getJFrame() {
        return jFrame;
    }

    public static int getFrameWidth() {
        return jFrame.getWidth();
    }

    public static int getFrameHeight() {
        return jFrame.getHeight();
    }

    public static int Width() {
        return Config.IMGWIDTH;
    }

    public static int Height() {
        return Config.IMGHEIGHT;
    }

    public void mouseClicked(final MouseEvent e) {
        //Blank Implementation
    }

    public void mousePressed(final MouseEvent e) {
        sceneManager.mousePressed(e);
        switch (e.getButton()) {
            case 1: mouseHeld = true; break;
            case 2: rightMouseHeld = true; break;
            case 3: middleMouseHeld = true; break;
        }
    }

    public void mouseReleased(final MouseEvent e) {
        sceneManager.mouseReleased(e);
        switch (e.getButton()) {
            case 1: mouseHeld = false; break;
            case 2: rightMouseHeld = false; break;
            case 3: middleMouseHeld = false; break;
        }
    }

    public void mouseEntered(final MouseEvent e) {
        //Blank Implementation
    }

    public void mouseExited(final MouseEvent e) {
        //Blank Implementation
    }

    public void mouseDragged(final MouseEvent e) {
        sceneManager.mouseMoved(e);
        mousePos = e.getPoint();
    }

    public void mouseMoved(final MouseEvent e) {
        sceneManager.mouseMoved(e);
        mousePos = e.getPoint();
    }

    public void mouseWheelMoved(final MouseWheelEvent e) {
        sceneManager.mouseWheelMoved(e);
    }

}
