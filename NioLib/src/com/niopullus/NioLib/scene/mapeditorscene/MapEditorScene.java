package com.niopullus.NioLib.scene.mapeditorscene;

import com.niopullus.NioLib.Data;
import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.NodeHandler;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.dynscene.*;
import com.niopullus.NioLib.scene.dynscene.reference.*;
import com.niopullus.NioLib.scene.dynscene.tile.*;
import com.niopullus.NioLib.utilities.Utilities;
import com.niopullus.app.InitScene;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**Used to design levels
 * Created by Owen on 4/7/2016.
 */
public class MapEditorScene extends Scene implements NodeHandler {

    private Tilemap fgMap;
    private Tilemap bgMap;
    private Tilemap map;
    private Node world;
    private Node universe;
    private Point chosenTileSpace;
    private Point selectedSquare;
    private Point chosenSquare;
    private Point fillingAnchor;
    private int pages;
    private int cols;
    private int page;
    private int activePart;
    private int xDivider;
    private int squareSize;
    private int mapMode;
    private Reference selection;
    private boolean whold;
    private boolean ahold;
    private boolean shold;
    private boolean dhold;
    private String worldName;
    private Font font;
    private int tileSize;
    private int elementCount;

    public MapEditorScene(String name) {
        super();
        final World pack = World.generateWorld(worldName, this);
        elementCount = Ref.getNodeCount() + Ref.getTileCount() + 1;
        fgMap = pack.getFgTilemap();
        bgMap = pack.getBgTilemap();
        universe = pack.getUniverse();
        world = universe.getChild(0);
        chosenTileSpace = null;
        chosenSquare = null;
        selectedSquare = null;
        fillingAnchor = null;
        cols = Main.Height() * 12  / Main.Width();
        pages = (int) Math.ceil(elementCount / ((cols - 2) * 2));
        page = 0;
        whold = false;
        ahold = false;
        shold = false;
        dhold = false;
        worldName = name;
        xDivider = Main.Width() * 5 / 6;
        squareSize = Main.Width() / 16;
        mapMode = 1;
        font = new Font("Bold", Font.BOLD, 30);
        tileSize = fgMap.getTileSize();
    }

    public MapEditorScene() {
        this("Unnamed World");
    }

    public void addChild(final Node node) {
        universe.addChild(node);
    }

    public void removeChild(final Node node) {
        universe.removeChild(node);
    }

    public int getNodeCount() {
        return universe.getChildCount();
    }

    public void parcelDraw(final Canvas canvas) {
        final int mmX = mapMode == 2 ? 0 : squareSize;
        final int ssX = (selectedSquare.x - 1) * squareSize, ssY = selectedSquare.y * squareSize;
        final int csX = (chosenSquare.x - 1) * squareSize, csY = chosenSquare.y * squareSize;
        int id = page * (cols - 2) * 2;
        canvas.o.rect(new Color(172, 172, 172), 0, 0, Main.Width(), Main.Height(), 0);
        canvas.o.rect(new Color(0, 0, 0, 127), xDivider, 0, Main.Width() - xDivider, Main.Height(), 100);
        canvas.o.rect(new Color(18, 255,0), xDivider + ssX, ssY, squareSize, squareSize, 480);
        canvas.o.rect(new Color(18, 255,0), xDivider + mmX, 0, squareSize, squareSize, 0);
        canvas.o.text("Prev Page", Color.BLACK, font, xDivider + squareSize / 2, squareSize * 3 / 2, 1000, 0);
        canvas.o.text("Next Page", Color.BLACK, font, xDivider + squareSize * 3 / 2, squareSize * 3 / 2, 1000, 0);
        canvas.m.text("FG Map", Color.BLACK, font, xDivider + squareSize / 2, squareSize / 2, 1000, 0);
        canvas.m.text("BG Map", Color.BLACK, font, xDivider + squareSize * 3 / 2, squareSize / 2, 1000, 0);
        if (activePart == 1) {
            final int x1 = (fillingAnchor != null ? Utilities.lesser(fillingAnchor.x, chosenTileSpace.x) : chosenTileSpace.x) * tileSize + world.getX();
            final int y1 = (fillingAnchor != null ? Utilities.lesser(fillingAnchor.y, chosenTileSpace.y) : chosenTileSpace.y) * tileSize + world.getY();
            final int x2 = (fillingAnchor != null ? Utilities.greater(fillingAnchor.x, chosenTileSpace.x) : chosenTileSpace.x) * tileSize + world.getX();
            final int y2 = (fillingAnchor != null ? Utilities.greater(fillingAnchor.y, chosenTileSpace.y) : chosenTileSpace.y) * tileSize + world.getY();
            final int width = x2 - x1;
            final int height = y2 - y1;
            canvas.o.rect(new Color(51, 240, 229), x1, y1, width, height, 200);
        } else if (activePart == 2) {
            canvas.o.rect(new Color(51, 240, 229), xDivider + csX, csY, squareSize, squareSize, 500);
        }
        for (int y = cols - 2; y >= 0; y++) {
            for (int x = 1; x <= 2 && id < elementCount; x++) {
                if (id < Ref.getTileCount()) {
                    final TileReference reference = Ref.getTileRef(id);
                    final Tile sample = reference.getSample();
                    canvas.o.parcel(sample, x * squareSize, y * squareSize, squareSize, squareSize, 100, 0);
                } else if (id < Ref.getNodeCount()) {
                    final int nodeID = id - Ref.getTileCount();
                    final NodeReference reference = Ref.getNodeRef(nodeID);
                    final Node sample = reference.getSample();
                    canvas.o.parcel(sample, x * squareSize, y * squareSize, squareSize, squareSize, 100, 0);
                }
                id++;
            }
        }
    }

    public void tick() {
        final Point mousePoint = getMousePos();
        if (ahold) {
            world.moveX(15);
        } else if (dhold) {
            world.moveX(-15);
        }
        if (whold) {
            world.moveY(-15);
        } else if (shold) {
            world.moveY(15);
        }
        if ((ahold || dhold || shold || whold) && activePart == 0) {
            updateChosenSquare(mousePoint.x, mousePoint.y);
        }
    }

    public void keyPress(final KeyPack pack) {
        switch (pack.code) {
            case KeyEvent.VK_A: ahold = true; break;
            case KeyEvent.VK_S: shold = true; break;
            case KeyEvent.VK_W: whold = true; break;
            case KeyEvent.VK_D: dhold = true; break;
            case KeyEvent.VK_ESCAPE: addSubScene(new ExitMenu()); break;
            case KeyEvent.VK_O: addSubScene(new SaveMenu(worldName));
        }
    }

    public void keyReleased(final KeyPack pack) {
        switch (pack.code) {
            case KeyEvent.VK_A: ahold = false; break;
            case KeyEvent.VK_S: shold = false; break;
            case KeyEvent.VK_W: whold = false; break;
            case KeyEvent.VK_D: dhold = false; break;
        }
    }

    public void saveMap(final Data.DataRoot root) {
        World world = new World();
        world.setName(worldName);
        world.setFgTilemap(fgMap);
        world.setBgTilemap(bgMap);
        world.setUniverse(universe);
        World.saveWorld(world, root);
        presentScene(new InitScene());
    }

    public void exit() {
        presentScene(new InitScene());
    }

    public void mouseMove(final MousePack pack) {
        if (pack.x < xDivider) {
            activePart = 1;
            updateChosenSquare(pack.x, pack.y);
        } else {
            activePart = 2;
            chosenSquare = new Point();
            chosenSquare.x = pack.x - xDivider / squareSize;
            chosenSquare.y = pack.y / squareSize;
        }
    }

    private void updateChosenSquare(final int x, final int y) {
        chosenTileSpace.x = Math.floorDiv(-world.getX() + x, fgMap.getTileSize());
        chosenTileSpace.y = Math.floorDiv(-world.getY() + y, fgMap.getTileSize());
    }

    public void mousePress(final MousePack pack) {
        if (pack.x < xDivider) {
            fillingAnchor = new Point(chosenTileSpace);
        } else if (selectedSquare.y > cols - 2) {
            final int selectionIndex = cols * 2 * page + chosenSquare.y * 2 + chosenSquare.x;
            if (selectionIndex < Ref.getTileCount()) {
                selection = Ref.getTileRef(selectionIndex);
            } else if (selectionIndex < Ref.getNodeCount()) {
                final int nodeSelectionIndex = selectionIndex - Ref.getTileCount();
                selection = Ref.getNodeRef(nodeSelectionIndex);
            }
        } else {
            if (chosenSquare.y == cols - 2)  {
                if (chosenSquare.y == cols - 1) {
                    if (chosenSquare.x == 0) {
                        page--;
                        selectedSquare = null;
                    }
                } else if (chosenSquare.x == 1) {
                    if (page + 1 < pages - 1) {
                        page++;
                        selectedSquare = null;
                    }
                }
            } else if (chosenSquare.y == cols - 1) {
                if (chosenSquare.x == 0) {
                    map = fgMap;
                    mapMode = 1;
                } else if (chosenSquare.x == 1) {
                    map = bgMap;
                    mapMode = 2;
                }
            }
        }
    }

    public void mouseRelease(final MousePack pack) {
        if (pack.x < xDivider && fillingAnchor != null && chosenTileSpace != null && activePart == 1) {
            if (selection instanceof TileReference) {
                final TileReference reference = (TileReference) selection;
                int x1 = fillingAnchor.x, x2 = fillingAnchor.x;
                int y1 = fillingAnchor.y, y2 = fillingAnchor.y;
                if (fillingAnchor.x > chosenTileSpace.x) {
                    x1 = chosenTileSpace.x;
                    x2 = fillingAnchor.x;
                } else if (fillingAnchor.x < chosenTileSpace.x) {
                    x1 = fillingAnchor.x;
                    x2 = chosenTileSpace.x;
                }
                if (fillingAnchor.y > chosenTileSpace.y) {
                    y1 = chosenTileSpace.y;
                    y2 = fillingAnchor.y;
                } else if (fillingAnchor.y < chosenTileSpace.y) {
                    y1 = fillingAnchor.y;
                    y2 = chosenTileSpace.y;
                }
                for (int i = x1; i <= x2; i++) {
                    for (int j = y1; j <= y2; j++) {
                        final Tile tile = reference.getSampleCopy();
                        if (!(selection instanceof MultiTileReference)) {
                            map.setTile(tile, i, j);
                        } else {
                            map.setMultiTile((MultiTile) tile, i, j);
                        }
                    }
                }
            }
        }
    }

}
