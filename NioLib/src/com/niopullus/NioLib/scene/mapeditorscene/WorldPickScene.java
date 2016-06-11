package com.niopullus.NioLib.scene.mapeditorscene;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.guiscene.Button;
import com.niopullus.NioLib.scene.guiscene.GUIScene;
import com.niopullus.NioLib.scene.guiscene.Label;
import com.niopullus.NioLib.scene.guiscene.SelectionBox;
import com.niopullus.app.Config;
import com.niopullus.app.InitScene;
import com.sun.javaws.Launcher;

import java.awt.Color;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * Created by Owen on 4/13/2016.
 */
public class WorldPickScene extends GUIScene {

    private Label chooseWorldLabel = new Label("Select World", 0, 300, 600, 150);
    private SelectionBox worlds = new SelectionBox(0, 0, 600, 100);
    private Button submit = new Button("Submit", 0, -200, 300, 100);
    private Button back = new Button("Back", -400, 300, 100, 50);

    public WorldPickScene() {
        super();
        //System.out.println(new File("E:\\Directories\\Spaghetti In Space\\out\\artifacts\\NioLib_jar\\NioLib.jar").isFile());
        setBackground(new ColorBackground(Main.Width(), Main.Height(), new Color(61, 179, 255)));
        File worldsFolder = new File("C:\\" + Config.DIRNAME + "\\worlds");
        File[] files = worldsFolder.listFiles();
        for (File world : files) {
            worlds.addSelection(world.getName());
        }
        /**
        worldsFolder = new File("META-INF");
        if (worldsFolder.exists()) {
            files = worldsFolder.listFiles();
            for (File world : files) {
                worlds.addSelection(world.getName());
            }
        }
         **/
        try {
            final String path = "builtinworlds";
            String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            jarPath = jarPath.replace('/', '\\');
            jarPath = jarPath.replace("%20", " ");
            final File jarFile = new File(jarPath.substring(1));
            if (jarFile.isFile()) {  // Run with JAR file
                final JarFile jar = new JarFile(jarFile);
                final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
                while(entries.hasMoreElements()) {
                    final String name = entries.nextElement().getName();
                    if (name.startsWith(path + "/") && name.length() > path.length() + 1) { //filter according to the path
                        worlds.addSelection(name.substring(path.length() + 1));
                    }
                }
                jar.close();
            } else { // Run with IDE
                final URL url = Launcher.class.getResource("/" + path);
                if (url != null) {
                    try {
                        final File apps = new File(url.toURI());
                        for (File app : apps.listFiles()) {
                            worlds.addSelection(app.getName());
                        }
                    } catch (URISyntaxException ex) {
                        // never happens
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        worlds.addSelection("Create new world");
        chooseWorldLabel.setFontSize(75);
        submit.setColor(Color.ORANGE);
        submit.setSelectedColor(Color.orange);
        worlds.setZ(1000);
        addElement(chooseWorldLabel);
        addElement(worlds);
        addElement(submit);
        addElement(back);
    }

    public void buttonActivate(int index) {
        if (index == 1) {
            if (this.worlds.getContent().equals("Create new world")) {
                presentScene(new MapEditorScene());
            } else {
                presentScene(new MapEditorScene(this.worlds.getContent()));
            }
        } else if (index == 2) {
            presentScene(new InitScene());
        }
    }

}
