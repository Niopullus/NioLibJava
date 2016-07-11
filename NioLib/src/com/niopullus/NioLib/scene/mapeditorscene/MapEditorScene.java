package com.niopullus.NioLib.scene.mapeditorscene;

import com.niopullus.NioLib.Picture;
import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.draw.Parcel;
import com.niopullus.NioLib.scene.NodeHandler;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.dynscene.*;
import com.niopullus.NioLib.scene.dynscene.reference.*;
import com.niopullus.NioLib.scene.dynscene.tile.*;
import com.niopullus.NioLib.Utilities;
import com.niopullus.NioLib.scene.guiscene.*;
import com.niopullus.NioLib.scene.guiscene.Label;
import com.niopullus.app.InitScene;

import java.awt.*;
import java.awt.event.KeyEvent;

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
    private Point chosenSquare;
    private Point fillingAnchor;
    private int pages;
    private int cols;
    private int page;
    private int activePart;
    private int xDivider;
    private int yDivider;
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
    private int panelSquareSize;
    private Label label;
    private Picture buildMode;
    private Picture editMode;
    private Picture deleteMode;
    private int mode;

    public MapEditorScene(final World _world) {
        super();
        _world.setScene(this);
        elementCount = Ref.getNodeCount() + Ref.getTileCount() - 1;
        fgMap = _world.getFgTilemap();
        bgMap = _world.getBgTilemap();
        universe = _world.getUniverse();
        world = universe.getChild(0);
        chosenTileSpace = null;
        chosenSquare = null;
        fillingAnchor = null;
        cols = Main.Height() * 12  / Main.Width();
        pages = (int) Math.ceil(elementCount / ((cols - 2) * 2));
        page = 0;
        whold = false;
        ahold = false;
        shold = false;
        dhold = false;
        worldName = _world.getName();
        xDivider = Main.Width() * 5 / 6;
        squareSize = Main.Width() / 16;
        mapMode = 1;
        font = new Font("Bold", Font.BOLD, 30);
        tileSize = fgMap.getTileSize();
        panelSquareSize = Main.Width() / 12;
        map = fgMap;
        buildMode = Picture.getPicture("buildMode");
        editMode = Picture.getPicture("editMode");
        deleteMode = Picture.getPicture("deleteMode");
        mode = 1;
        yDivider = cols * panelSquareSize;
    }

    public MapEditorScene() {
        this(World.generate("Unnamed World"));
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

    public void drawScene(final Canvas canvas) {
        final int mmX = mapMode == 1 ? 0 : panelSquareSize;
        final int msX = mode == 1? 0: mode == 2 ? 150 : 300;
        int id = page * (cols - 2) * 2;
        canvas.o.rect(new Color(172, 172, 172), 0, 0, Main.Width(), Main.Height(), 0);
        canvas.o.rect(new Color(0, 0, 0, 127), xDivider, 0, Main.Width() - xDivider, yDivider, 100);
        canvas.o.rect(new Color(18, 255,0), xDivider + mmX, 0, panelSquareSize, panelSquareSize, 0);
        canvas.o.rect(new Color(0, 0, 0, 127), 0, 0, 450, 150, 20);
        canvas.o.rect(new Color(51, 240, 229), msX, 0, 150, 150, 30);
        canvas.m.text("Prev Page", Color.BLACK, font, xDivider + panelSquareSize / 2, panelSquareSize * 3 / 2, 1000, 0, 1);
        canvas.m.text("Next Page", Color.BLACK, font, xDivider + panelSquareSize * 3 / 2, panelSquareSize * 3 / 2, 1000, 0, 1);
        canvas.m.text("FG Map", Color.BLACK, font, xDivider + panelSquareSize / 2, panelSquareSize / 2, 1000, 0, 1);
        canvas.m.text("BG Map", Color.BLACK, font, xDivider + panelSquareSize * 3 / 2, panelSquareSize / 2, 1000, 0, 1);
        canvas.o.parcel(fgMap, 0, 0, 10, 0);
        canvas.o.parcel(bgMap, 0, 0, 10, 0);
        canvas.o.parcel(universe, 0, 0, 20, 0);
        canvas.o.sketch(buildMode, 10, 10, 130, 130, 50, 0);
        canvas.o.sketch(editMode, 160, 10, 130, 130, 50, 0);
        canvas.o.sketch(deleteMode, 310, 10, 130, 130, 50, 0);
        if (mode == 1) {
            canvas.o.rect(new Color(0, 0, 0, 127), 0, Main.Height() - 200, 200, 200, 100);
            if (selection != null) {
                final Rectangle pos = getDrawLocation(selection, 160);
                canvas.o.parcel((Parcel) selection.getSample(), 20 + pos.x, Main.Height() - 180 + pos.y, pos.width, pos.height, 500, 0, 1);
            }
        }
        if (activePart == 1) {
            if (mode == 1) {
                if (selection instanceof TileReference) {
                    final int x1 = (fillingAnchor != null ? Utilities.lesser(fillingAnchor.x, chosenTileSpace.x) : chosenTileSpace.x) * tileSize + world.getX();
                    final int y1 = (fillingAnchor != null ? Utilities.lesser(fillingAnchor.y, chosenTileSpace.y) : chosenTileSpace.y) * tileSize + world.getY();
                    final int x2 = (fillingAnchor != null ? Utilities.greater(fillingAnchor.x, chosenTileSpace.x + 1) : chosenTileSpace.x + 1) * tileSize + world.getX();
                    final int y2 = (fillingAnchor != null ? Utilities.greater(fillingAnchor.y, chosenTileSpace.y + 1) : chosenTileSpace.y + 1) * tileSize + world.getY();
                    final int width = x2 - x1;
                    final int height = y2 - y1;
                    canvas.o.rect(new Color(51, 240, 229, 100), x1, y1, width, height, 200);
                } else if (selection instanceof NodeReference) {
                    final Point mousePos = getMousePos();
                    final Node node = (Node) selection.getSample();
                    canvas.m.parcel(node, mousePos.x, mousePos.y, node.getWidth(), node.getHeight(), 40, 0, 0.3f);
                }
            }
        } else if (activePart == 2) {
            if (label != null) {
                canvas.o.parcel(label, label.getX() - label.getWidth(), label.getY(), 600, 0);
            }
        } else if (activePart == 3) {
            if (label != null) {
                canvas.o.parcel(label, label.getX(), label.getY(), 600, 0);
            }
        }
        for (int y = cols - 1; y >= 0; y--) {
            for (int x = 1; x <= 2 && id < elementCount; x++) {
                final int elementSize = (int) (panelSquareSize * 0.8);
                final int elementX = (int) ((x - 0.9) * panelSquareSize + xDivider);
                final int elementY = (int) ((y + 0.1) * panelSquareSize);
                final Reference ref = getSelection(id);
                final Parcel sample = (Parcel) ref.getSample();
                final Rectangle pos = getDrawLocation(ref, elementSize);
                final int xOffset = pos.x;
                final int yOffset = pos.y;
                final int sampleWidth = pos.width;
                final int sampleHeight = pos.height;
                canvas.o.parcel(sample, elementX + xOffset, elementY + yOffset, sampleWidth, sampleHeight, 100, 0, 1);
                id++;
            }
        }
    }

    private Rectangle getDrawLocation(final Reference ref, final int elementSize) {
        int sampleWidth = 0;
        int sampleHeight = 0;
        int xOffset = 0;
        int yOffset = 0;
        Parcel sample = null;
        if (ref instanceof TileReference) {
            sampleWidth = elementSize;
            sampleHeight = elementSize;
        } else if (ref instanceof NodeReference) {
            final Node node = (Node) ref.getSample();
            if (node.getWidth() > node.getHeight()) {
                sampleWidth = elementSize;
                sampleHeight = (int) (((double) elementSize / node.getWidth()) * node.getHeight());
                yOffset = (elementSize - sampleHeight) / 2;
            } else if (node.getHeight() > node.getWidth()) {
                sampleHeight = elementSize;
                sampleWidth = (int) (((double) elementSize / node.getHeight()) * node.getWidth());
                xOffset = (elementSize - sampleWidth) / 2;
            } else {
                sampleWidth = elementSize;
                sampleHeight = elementSize;
            }
        }
        return new Rectangle(xOffset, yOffset, sampleWidth, sampleHeight);
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
        if ((ahold || dhold || shold || whold) && activePart == 1) {
            updateChosenTileSpace(mousePoint.x, mousePoint.y);
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

    public void saveMap(final String name) {
        World world = new World();
        world.setName(name);
        world.setFgTilemap(fgMap);
        world.setBgTilemap(bgMap);
        world.setUniverse(universe);
        World.saveWorld(world);
        presentScene(new InitScene());
    }

    public void exit() {
        presentScene(new InitScene());
    }

    public void mouseMove(final MousePack pack) {
        if (pack.x > xDivider && pack.y < yDivider) {
            final Reference reference;
            final int selectionIndex;
            activePart = 2;
            chosenSquare = new Point();
            chosenSquare.x = (pack.x - xDivider) / panelSquareSize;
            chosenSquare.y = pack.y / panelSquareSize;
            selectionIndex = cols * 2 * page + (cols - chosenSquare.y - 1) * 2 + chosenSquare.x;
            reference = getSelection(selectionIndex);
            if (reference != null) {
                label = new Label(reference.getName(), new Theme(), 40);
                label.setX(pack.x);
                label.setY(pack.y);
            } else {
                label = null;
            }
        } else if (pack.x < 450 && pack.y < 150) {
            activePart = 3;
            if (pack.x < 150) {
                label = new Label("Build Mode", new Theme(), 40);
            } else if (pack.x > 150 && pack.x < 300) {
                label = new Label("Edit Mode", new Theme(), 40);
            } else {
                label = new Label("Delete Mode", new Theme(), 40);
            }
            label.setX(pack.x);
            label.setY(pack.y);
        } else {
            activePart = 1;
            updateChosenTileSpace(pack.x, pack.y);
        }
    }

    private void updateChosenTileSpace(final int x, final int y) {
        chosenTileSpace = new Point();
        chosenTileSpace.x = Math.floorDiv(-world.getX() + x, fgMap.getTileSize());
        chosenTileSpace.y = Math.floorDiv(-world.getY() + y, fgMap.getTileSize());
    }

    private Reference getSelection(final int index) {
        final int nodeID = index - Ref.getTileCount() + 1;
        if (index < Ref.getTileCount() - 1) {
            return Ref.getTileRef(index + 1);
        } else if (nodeID < Ref.getNodeCount()) {
            return Ref.getNodeRef(nodeID);
        }
        return null;
    }

    public void mousePress(final MousePack pack) {
        if (activePart == 2) {
            if (chosenSquare.y >= 2) {
                final int selectionIndex = cols * 2 * page + (cols - chosenSquare.y - 1) * 2 + chosenSquare.x;
                selection = getSelection(selectionIndex);
            } else {
                if (chosenSquare.y == 1)  {
                    if (chosenSquare.x == 0) {
                        if (page - 1 >= 0) {
                            page--;
                        }
                    } else if (chosenSquare.x == 1) {
                        if (page + 1 < pages - 1) {
                            page++;
                        }
                    }
                } else if (chosenSquare.y == 0) {
                    if (chosenSquare.x == 0) {
                        map = fgMap;
                        mapMode = 1;
                    } else if (chosenSquare.x == 1) {
                        map = bgMap;
                        mapMode = 2;
                    }
                }
            }
        } else if (activePart == 1) {
            if (mode == 1) {
                if (selection instanceof TileReference) {
                    fillingAnchor = new Point(chosenTileSpace);
                } else if (selection instanceof NodeReference) {
                    final NodeReference nodeReference = (NodeReference) selection;
                    final Node node = nodeReference.getSampleCopy();
                    node.setPosition(pack.x - world.getX() - node.getWidth() / 2, pack.y - world.getY() - node.getHeight() / 2);
                    world.addChild(node);
                }
            }
        } else if (activePart == 3) {
            if (pack.x < 150) {
                mode = 1;
            } else if (pack.x > 150 && pack.x < 300) {
                mode = 2;
            } else {
                mode = 3;
            }
        }
    }

    public void mouseRelease(final MousePack pack) {
        if ((pack.x < xDivider || pack.y > yDivider) && fillingAnchor != null && chosenTileSpace != null && activePart == 1) {
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
            fillingAnchor = null;
        }
    }

}
