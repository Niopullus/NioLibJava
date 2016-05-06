package com.niopullus.NioLib;

import com.niopullus.NioLib.scene.SceneManager;
import com.niopullus.NioLib.scene.dynscene.tile.TileReference;
import com.niopullus.app.Config;
import com.niopullus.app.InitScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Owen on 3/5/2016.
 */
public class Main extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private static JFrame jFrame;
    private static final int WIDTH = Config.IMGWIDTH;
    private static final int HEIGHT = Config.IMGHEIGHT;
    private static final double SCALE = Config.WINDOWSCALE;
    private Thread thread;
    private boolean running;
    private int FPS = 30;
    private long targetTime = 1000 / FPS;
    private BufferedImage image;
    private Graphics2D g;
    private SceneManager sceneManager;

    public static void main(String[] args) {
        JFrame window = new JFrame(Config.WINDOWTITLE);
        jFrame = window;
        window.setContentPane(new Main());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.pack();
        window.setVisible(true);
        new File("C:\\" + Config.DIRNAME).mkdir();
        new File("C:\\" + Config.DIRNAME + "\\" + "worlds").mkdir();
        Config.init();
    }

    public Main() {
        super();
        setPreferredSize(new Dimension((int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)));
        setFocusable(Config.WINDOWRESIZABLE);
        requestFocus();
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
        this.image = new BufferedImage(this.WIDTH, this.HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.g = (Graphics2D) this.image.getGraphics();
        this.running = true;
        this.sceneManager = new SceneManager(this);
        this.sceneManager.presentScene(new InitScene());
    }

    public void run() {
        init();
        long start;
        long elapsed;
        long wait;
        while (this.running) {
            start = System.nanoTime();
            this.tick();
            Draw.init();
            this.draw();
            Draw.display(g);
            this.drawToScreen();
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

    private void tick() {
        this.sceneManager.tick();
    }

    private void draw() {
        this.sceneManager.draw();
    }

    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(this.image, 0, 0, Main.getFrameWidth(), Main.getFrameHeight(), null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent key) {

    }

    public void keyPressed(KeyEvent key) {
        this.sceneManager.keyPress(key);
    }

    public void keyReleased(KeyEvent key) {
        this.sceneManager.keyReleased(key);
    }

    public static JFrame getjFrame() {
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.sceneManager.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.sceneManager.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.sceneManager.mouseMoved(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.sceneManager.mouseMoved(e.getPoint());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        this.sceneManager.mouseWheelMoved(e);
    }

}
