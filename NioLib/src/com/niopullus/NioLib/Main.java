package com.niopullus.NioLib;

import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.draw.DrawElement;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.SceneManager;
import com.niopullus.app.Config;
import com.niopullus.app.InitScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

/**Manages the program
 * Created by Owen on 3/5/2016.
 */
public class Main extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private static JFrame jFrame;
    private static Main main;
    private Thread thread;
    private boolean running;
    private int FPS = 30;
    private long targetTime = 1000 / FPS;
    private BufferedImage image;
    private Graphics2D g;
    private SceneManager sceneManager;
    private FileManager fileManager;
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
        main = this;
        setPreferredSize(new Dimension((int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)));
        setFocusable(Config.WINDOWRESIZABLE);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            addMouseListener(this);
            addMouseMotionListener(this);
            addMouseWheelListener(this);
            thread.start();
        }
    }

    public void init() {
        loadImages();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        running = true;
        sceneManager = new SceneManager();
        fileManager = new FileManager();
        presentInitScene();
        Data.init(fileManager);
        setupConfig();
    }

    private void loadImages() {
        Picture.loadPictureFromJar("build.png", "buildMode");
        Picture.loadPictureFromJar("delete.png", "deleteMode");
        Picture.loadPictureFromJar("edit.png", "editMode");
        Picture.loadPictureFromJar("target.png", "target");
        Picture.loadPictureFromJar("check.png", "check");
    }

    private void setupConfig() {
        final String programFilesDir = getProgramFiles();
        final String programFiles = programFilesDir.replace("\\", "/");
        final String folder = programFiles + "/" + Config.DIRNAME;
        final String config = folder + "/" + "config.txt";
        if (!Data.fileExists(config)) {
            determineDir();
        } else {
            final String text = Data.getTextFromFile(config);
            final String dir = text.substring(11);
            Root.init(fileManager, dir);
            presentInitScene();
        }
    }

    private void determineDir() {
        if (Config.PROMPTFOLDERDIRECTORY) {
            presentSelectDirScene();
        } else {
            final String folderDir = Data.getJarFolder();
            final String folder = folderDir + "/" + Config.DIRNAME;
            writeDir(folderDir);
            Root.init(fileManager, folder);
            presentInitScene();
        }
    }

    public static String getProgramFiles() {
        final String OS = System.getProperty("os.name");
        final String formattedOS = OS.toUpperCase();
        if (formattedOS.contains("WIN")) {
            return System.getenv("AppData");
        } else {
            return System.getProperty("user.home") + "/Library/Application Support";
        }
    }

    public static void writeDir(final String folderPath) {
        final String programFilesDir = getProgramFiles();
        final String programFiles = programFilesDir.replace("\\", "/");
        final String configFolder = programFiles + "/" + Config.DIRNAME;
        final String config = configFolder + "/" + "config.txt";
        final String folder = folderPath + "/" + Config.DIRNAME;
        Data.createFolderFromFile(folderPath, Config.DIRNAME);
        Data.createFolderFromFile(folder, "worlds");
        Data.createFolderFromFile(programFiles, Config.DIRNAME);
        Data.createFileFromFile(configFolder, "config.txt");
        Data.writeToFileFromFile(config, "Directory: " + folder, true);
    }

    public void presentInitScene() {
        sceneManager.presentScene(new InitScene());
    }

    public void presentSelectDirScene() {
        sceneManager.presentScene(new SelectDirScene(fileManager));
    }

    public void run() {
        long start;
        long elapsed;
        long wait;
        init();
        while (running) {
            final Canvas canvas = new Canvas();
            final List<DrawElement> drawElements;
            start = System.nanoTime();
            sceneManager.tick();
            sceneManager.parcelDraw(canvas);
            drawElements = canvas.retrieveElements();
            iterateDrawElements(drawElements, g);
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

    public static Scene getCurrentScene() {
        return Main.main.sceneManager.getCurrentScene();
    }

    private void iterateDrawElements(final List<DrawElement> elements, final Graphics2D g) {
        final Comparator<DrawElement> comparator = (final DrawElement o1, final DrawElement o2) -> {
            final Integer z = o1.getTZ();
            return z.compareTo(o2.getTZ());
        };
        elements.sort(comparator);
        for (DrawElement element : elements) {
            element.draw(g);
        }
    }

    private void drawToScreen() {
        final Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, Main.getWindowWidth(), Main.getWindowHeight(), null);
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

    public static int getWindowWidth() {
        return main.getWidth();
    }

    public static int getWindowHeight() {
        return main.getHeight();
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
    }

    public void mouseReleased(final MouseEvent e) {
        sceneManager.mouseReleased(e);
    }

    public void mouseEntered(final MouseEvent e) {
        //Blank Implementation
    }

    public void mouseExited(final MouseEvent e) {
        //Blank Implementation
    }

    public void mouseDragged(final MouseEvent e) {
        if (sceneManager != null) {
            sceneManager.mouseMoved(e);
        }
    }

    public void mouseMoved(final MouseEvent e) {
        if (sceneManager != null) {
            sceneManager.mouseMoved(e);
        }
    }

    public void mouseWheelMoved(final MouseWheelEvent e) {
        if (sceneManager != null) {
            sceneManager.mouseWheelMoved(e);
        }
    }

    //TODO:
    //Fix the file root system for Macs and Windows computers
    //GUIScene editor
    //Registry editor
    //Sketch tiles
    //Custom rendering
    //Easy GUIs
    //Completely redo ListScene (Code, not features)
    //Condense instance variables

}
