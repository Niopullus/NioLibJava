package com.niopullus.NioLib.scene.mapeditorscene;

import com.niopullus.NioLib.Data;
import com.niopullus.NioLib.draw.Draw;
import com.niopullus.NioLib.draw.DrawElement;
import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.NodeHandler;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.dynscene.*;
import com.niopullus.NioLib.scene.dynscene.reference.*;
import com.niopullus.NioLib.scene.dynscene.tile.*;
import com.niopullus.NioLib.utilities.Utilities;
import com.niopullus.app.Config;
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

    public MapEditorScene(String name) {
        super();
        final World pack = World.generateWorld(worldName, this);
        final int elementCount = Ref.getNodeCount() + Ref.getTileCount();
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

    public void draw() {
        Draw.o.rect(new Color(172, 172, 172), 0, 0, Main.Width(), Main.Height(), 0);
        Draw.o.rect(new Color(0, 0, 0, 127), xDivider, 0, Main.Width() - xDivider, Main.Height(), 100);
        Draw.o.rect(new Color(255, 0, 0, 100), selectedSquare.x * squareSize + xDivider, selectedSquare.y * squareSize, squareSize, squareSize, 480);
        Draw.m.text(xDivider + , (Main.Height() / 2) - (Main.Width() * ((cols - 2) * 2 + 1) / 24), 1000, "Prev Page", new Font("Bold", Font.BOLD, 30), Color.BLACK, DrawElement.MODE_TEXTCENTERED);
        Draw.text(Main.Width() * 23 / 24 - 50 - Main.Width() / 2, (Main.Height() / 2) - (Main.Width() * ((cols - 2) * 2 + 1) / 24), 1000, "Next Page", new Font("Bold", Font.BOLD, 30), Color.BLACK, DrawElement.MODE_TEXTCENTERED);
        Draw.text(Main.Width() * 21 / 24 - 50 - Main.Width() / 2, (Main.Height() / 2) - (Main.Width() * ((cols - 1) * 2 + 1) / 24), 1000, "FG Map", new Font("Bold", Font.BOLD, 30), Color.BLACK, DrawElement.MODE_TEXTCENTERED);
        Draw.text(Main.Width() * 23 / 24 - 50 - Main.Width() / 2, (Main.Height() / 2) - (Main.Width() * ((cols - 1) * 2 + 1) / 24), 1000, "BG Map", new Font("Bold", Font.BOLD, 30), Color.BLACK, DrawElement.MODE_TEXTCENTERED);
        if (this.mm == 0) {
            Draw.rect(Main.Width() * 5 / 6 - 50, (cols - 1) * Main.Width() / 12, Main.Width() / 12, Main.Width() / 12, 500, new Color(255, 196, 46, 100));
        } else if (this.mm == 1) {
            Draw.rect(Main.Width() / 12 + Main.Width() * 5 / 6 - 50, (cols - 1) * Main.Width() / 12, Main.Width() / 12, Main.Width() / 12, 500, new Color(255, 196, 46, 100));
        }
        if (this.choice == 1) {
            if (this.fillingAnchor == null) {
                Draw.rect(this.selectedTile.x * this.fgMap.getTileSize() + this.world.getX(), Main.Height() - ((this.selectedTile.y + 1) * this.fgMap.getTileSize()) - this.world.getY(), this.fgMap.getTileSize(), this.fgMap.getTileSize(), 200, new Color(52, 240, 255, 100));
            } else {
                int x1 = this.fillingAnchor.x, x2 = this.fillingAnchor.x;
                int y1 = this.fillingAnchor.y, y2 = this.fillingAnchor.y;
                if (this.selectionType == 0) {
                    if (this.fillingAnchor.x > this.selectedTile.x) {
                        x1 = this.selectedTile.x;
                        x2 = this.fillingAnchor.x;
                    } else if (this.fillingAnchor.x < this.selectedTile.x) {
                        x1 = this.fillingAnchor.x;
                        x2 = this.selectedTile.x;
                    }
                    if (this.fillingAnchor.y > this.selectedTile.y) {
                        y1 = this.selectedTile.y;
                        y2 = this.fillingAnchor.y;
                    } else if (this.fillingAnchor.y < this.selectedTile.y) {
                        y1 = this.fillingAnchor.y;
                        y2 = this.selectedTile.y;
                    }
                } else if (this.selectionType == 1) {
                    x1 = this.fillingAnchor.x;
                    x2 = this.fillingAnchor.x;
                    y1 = this.fillingAnchor.y;
                    y2 = this.fillingAnchor.y;
                }
                Draw.rect(x1 * this.fgMap.getTileSize() + this.world.getX(), Main.Height() - ((y1) * this.fgMap.getTileSize()) - this.world.getY() - (this.fgMap.getTileSize() * (y2 - y1 + 1)), this.fgMap.getTileSize() * (x2 - x1 + 1), this.fgMap.getTileSize() * (y2 - y1 + 1), 200, new Color(52, 240, 255, 100));
            }
        } else if (choice == 2) {
            Draw.rect(this.selection.x * Main.Width() / 12 + Main.Width() * 5 / 6 - 50, this.selection.y * Main.Width() / 12, Main.Width() / 12, Main.Width() / 12, 500, new Color(74, 255, 64, 100));
        }
        int id = this.page * 16;
        //System.out.println("t" + TileReference.getTileQuant());
        //System.out.println("n" + TileReference.getNodeQuant());
        for (int y = 1; y <= this.cols - 2; y++) {
            for  (int x = 1; x <= 2; x++) {
                if (id <= TileReference.getTileQuant()) {
                    TileReference tile = Reference.getTileRef(id);
                    if (tile != null) {
                        if (!(tile instanceof MultiTileReference)) {
                            Draw.image((int) ((double) Main.Width() * 81 / 96 + ((double) (x - 1) / 12) * Main.Width()) - 50, (int) ((double) Main.Width() / 96 + ((double) (y - 1) / 12) * Main.Width()), 300, Main.Width() / 16, Main.Width() / 16, tile.getImage());
                        } else {
                            MultiTileReference mtr = (MultiTileReference) tile;
                            int size = Utilities.greater(mtr.getWidth(), mtr.getHeight());
                            int smallTileSize = (Main.Width() / 16) / size;
                            int xStart = (size - mtr.getWidth()) * smallTileSize / 2;
                            int yStart = (size - mtr.getHeight()) * smallTileSize / 2;
                            int part = 0;
                            for (int i = 0; i < mtr.getWidth(); i++) {
                                for (int j = 0; j < mtr.getHeight(); j++) {
                                    Draw.image((int) ((double) Main.Width() * 81 / 96 + ((double) (x - 1) / 12) * Main.Width()) - 50 + xStart + (i * smallTileSize), (int) ((double) Main.Width() / 96 + ((double) (y - 1) / 12) * Main.Width()) + yStart + (j * smallTileSize), 300, smallTileSize, smallTileSize, mtr.getImage(0, (mtr.getWidth() * mtr.getHeight()) - part - 1));
                                    part++;
                                }
                            }
                        }
                    }
                    id++;
                } else if (id - TileReference.getTileQuant() - 1 < NodeReference.getNodeQuant()) {
                    NodeReference node = Reference.getNodeRef(id - TileReference.getTileQuant() - 1);
                    if (node != null) {
                        if (node.getSample() instanceof ImageNode || node.getSample() instanceof DynamicImageNode) {
                            BufferedImage image = null;
                            if (node.getSample() instanceof ImageNode) {
                                ImageNode imageNode = (ImageNode) node.getSample();
                                image = imageNode.getImage();
                            } else if (node.getSample() instanceof DynamicImageNode) {
                                DynamicImageNode imageNode = (DynamicImageNode) node.getSample();
                                image = imageNode.getImage();
                            }
                            double drawScale = (double) (Main.Width() / 16) / Utilities.greater(image.getWidth(), image.getHeight());
                            int scaledWidth = (int) (drawScale * image.getWidth());
                            int scaledHeight = (int) (drawScale * image.getHeight());
                            int xrest = ((Main.Width() / 16) - scaledWidth) / 2;
                            int yrest = ((Main.Width() / 16) - scaledHeight) / 2;
                            //System.out.println("x" + ((int) ((double) Main.Width() * 81 / 96 + ((double) (x - 1) / 12) * Main.Width()) - 50 + xrest) + "y" + ((int) ((double) Main.Width() / 96 + ((double) (y - 1) / 12) * Main.Width()) + yrest));
                            //System.out.println("width" + scaledWidth + "hegith" + scaledHeight);
                            Draw.image((int) ((double) Main.Width() * 81 / 96 + ((double) (x - 1) / 12) * Main.Width()) - 50 + xrest, (int) ((double) Main.Width() / 96 + ((double) (y - 1) / 12) * Main.Width()) + yrest, 300, scaledWidth, scaledHeight, image);
                        }
                    }
                    id++;
                }
            }
            this.world.drawNode();
        }
        Draw.rect(this.world.getX() - 50, Main.Height() - (this.world.getY() - 50), 100, 100, 500, Color.RED);
        this.bgMap.draw();
        this.fgMap.draw();
        if (this.getSubscene() != null) {
            this.getSubscene().draw();
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
            case KeyEvent.VK_ESCAPE: addSubScene(new ExitMenu(this)); break;
            case KeyEvent.VK_O: addSubScene(new SaveMenu(this, worldName));
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
