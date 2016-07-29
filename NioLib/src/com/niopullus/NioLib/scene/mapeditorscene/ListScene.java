package com.niopullus.NioLib.scene.mapeditorscene;

import com.niopullus.NioLib.Crushable;
import com.niopullus.NioLib.DataTree;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.dynscene.Node;
import com.niopullus.NioLib.scene.dynscene.tile.Tile;
import com.niopullus.NioLib.scene.dynscene.tile.Tilemap;
import com.niopullus.NioLib.scene.guiscene.*;
import com.niopullus.NioLib.scene.guiscene.Button;
import com.niopullus.NioLib.scene.guiscene.Label;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**Used to display information regarding selections and Tile/Node data
 * Created by Owen on 7/13/2016.
 */
public class ListScene extends GUIScene {

    private int mode;
    private Object data;
    private String title;
    private ListScene prevScene;
    private Button backButton;
    private Button prevButton;
    private Button nextButton;
    private Button activate;
    private Button select;
    private int page;
    private int pages;
    private Series itemDisplay;
    private Object originalObject;
    private List<Integer> indexes;
    private DataTree originalData;
    private TextBox textBox;
    private int editType;

    public ListScene(final ListScene _prevScene, final int _mode, final Object _data, final Object _originalObject, final DataTree _originalData, final List<Integer> _index) {
        super();
        data = _data;
        prevScene = _prevScene;
        mode = _mode;
        page = 0;
        pages = 0;
        originalObject = _originalObject;
        indexes = _index;
        originalData = _originalData;
        setupItems();
    }

    private void setupElements() {
        final Theme theme = new Theme();
        prevButton = new Button("Previous Page", theme, 40);
        nextButton = new Button("Next Page", theme, 40);
        backButton = new Button("Back", theme, 40);
        backButton.setPosition(-getWidth() / 2 + 350, getHeight() / 2 - backButton.getHeight() * 2 - 40);
        prevButton.setPosition(-getWidth() / 2 + 450, -getHeight() / 2 + backButton.getHeight() * 2 + 40);
        nextButton.setPosition(getWidth() / 2 - 450, -getHeight() / 2 + backButton.getHeight() * 2 + 40);
        addElement(backButton);
        addElement(prevButton);
        addElement(nextButton);
    }

    private void setupItems() {
        if (mode == 1 || mode == 2 || mode == 3 || mode == 4) {
            setup1();
        } else if (mode == 5) {
            setup2();
        } else if (mode == 6) {
            setup3();
        }
    }

    private void setup1() {
        final Theme theme = new Theme();
        final List<Object> items = (List<Object>) data;
        final List<String> strings = dataToStrings(items);
        final Label pageLabel = new Label("", theme, 40);
        select = new Button("Select", theme, 40, new GUISize(300, 0, true, false));
        itemDisplay = new Series(null, theme, 40, new GUISize(500, 0, true, false), 5);
        pageLabel.setPosition(0, 280);
        activate = new Button((mode == 3 ? "Edit" : mode == 4 ? "Delete" : null), theme, 40, new GUISize(300, 0, true, false));
        select.setPosition(-170, -350);
        activate.setPosition(170, -350);
        itemDisplay.setPageDisplay(pageLabel);
        itemDisplay.setPosition(0, 0);
        itemDisplay.enableCheckBoxes();
        itemDisplay.setBorderColor(Color.BLUE);
        if (mode == 1 || mode == 2 || mode == 3) {
            itemDisplay.setCheckLimit(1);
        }
        for (String line : strings) {
            itemDisplay.addLine(line);
        }
        setupElements();
        if (strings.size() >= 1) {
            itemDisplay.setContent(0, strings.get(0));
        }
        addElement(pageLabel);
        addElement(itemDisplay);
        addElement(activate);
        addElement(select);
    }

    private void setup2() {
        final Theme theme = new Theme();
        String type = null;
        final String title;
        final Label titleLabel;
        final List<Object> items = (List<Object>) data;
        final int index = indexes.get(0);
        final Object item = items.get(index);
        if (item instanceof Integer) {
            type = "Integer";
            editType = 1;
        } else if (item instanceof Double) {
            type = "Double";
            editType = 2;
        } else if (item instanceof Boolean) {
            type = "Boolean";
            editType = 3;
        } else if (item instanceof String) {
            type = "String";
            editType = 4;
        }
        textBox = new TextBox(item.toString(), theme, 40, 500, 1);
        title = "Edit " + type;
        titleLabel = new Label(title, theme, 40);
        titleLabel.setPosition(0, 300);
        textBox.setPosition(0, 0);
        activate = new Button("Save", theme, 40);
        activate.setPosition(0, -200);
        addElement(titleLabel);
        addElement(textBox);
        addElement(activate);
    }

    public void setup3() {
        final Theme theme = new Theme();
        final Label confirm = new Label("Are you sure that you", theme, 40);
        final List<Object> items = (List<Object>) data;
        final List<Object> deleteObjects = new ArrayList<>();
        final List<String> strings;
        setupElements();
        confirm.addLine("would like to delete these items?");
        for (int i : indexes) {
            deleteObjects.add(items.get(i));
        }
        strings = dataToStrings(deleteObjects);
        itemDisplay = new Series(null, theme, 40, new GUISize(500, 0, true, false), 5);
        activate = new Button("Yes", theme, 40, new GUISize(200, 0, true, false));
        select = new Button("No", theme, 40, new GUISize(200, 0, true, false));
        for (String item : strings) {
            itemDisplay.addLine(item);
        }
        confirm.setPosition(0, 300);
        activate.setPosition(-130, -300);
        select.setPosition(130, -300);
        itemDisplay.setPosition(0, 0);
        addElement(confirm);
        addElement(activate);
        addElement(select);
        addElement(itemDisplay);
    }

    private void error(final String errorMsg) {
        addSubScene(new ErrorScene(errorMsg));
    }

    private List<String> dataToStrings(final List<Object> items) {
        final List<String> result = new ArrayList<>();
        for (Object object : items) {
            if (object instanceof List) {
                result.add("Folder");
            } else if (object instanceof Integer) {
                result.add("I: " + object.toString());
            } else if (object instanceof Double) {
                result.add("D: " + object.toString());
            } else if (object instanceof Boolean) {
                result.add("B: " + object.toString());
            } else if (object instanceof String) {
                result.add("S: " + object.toString());
            } else if (object instanceof Tile) {
                final Tile tile = (Tile) object;
                result.add(tile.getName());
            } else if (object instanceof Node) {
                final Node node = (Node) object;
                result.add(node.getName());
            }
        }
        return result;
    }

    private boolean objectCrushPart(final Object object) {
        return object instanceof Integer || object instanceof Double || object instanceof Boolean || object instanceof String;
    }

    public void buttonActivate(final Button b) {
        final Scene superScene = getSuperScene();
        if (b == backButton) {
            if (prevScene != null) {
                superScene.addSubScene(prevScene);
            } else {
                closeSubScene();
            }
        } else if (b == prevButton) {
            itemDisplay.prevPage();
        } else if (b == nextButton) {
            itemDisplay.nextPage();
        } else if (b == activate || b == select) {
            if (mode == 3 || mode == 4) {
                final List<Integer> checkIndexes = itemDisplay.getChecked();
                final List<Object> curData = (List<Object>) data;
                final int numSelected = itemDisplay.getNumChecked();
                if (b == activate) {
                    if (mode == 3) {
                        if (numSelected == 1) {
                            final int index = checkIndexes.get(0);
                            final Object item = curData.get(index);
                            if (objectCrushPart(item)) {
                                final List<Integer> list = new ArrayList<>();
                                list.add(index);
                                superScene.addSubScene(new ListScene(this, 5, curData, originalObject, originalData, list));
                            } else {
                                error("This item is not editable");
                            }
                        } else {
                            error("Too many / too few items selected!");
                        }
                    } else if (mode == 4) {
                        if (numSelected >= 1) {
                            if (curData.get(0) instanceof Tile || curData.get(0) instanceof Node) {
                                final List<Object> items = new ArrayList<>();
                                final List<Integer> nums = new ArrayList<>();
                                for (int i : checkIndexes) {
                                    items.add(curData.get(i));
                                }
                                for (int i = 0; i < items.size(); i++) {
                                    nums.add(i);
                                }
                                superScene.addSubScene(new ListScene(this, 6, items, items, originalData, nums));
                            } else {
                                superScene.addSubScene(new ListScene(this, 6, curData, originalObject, originalData, checkIndexes));
                            }
                        } else {
                            error("Too many / too few items selected!");
                        }
                    }
                } else if (b == select) {
                    if (numSelected == 1) {
                        final int index = checkIndexes.get(0);
                        final Object item = curData.get(index);
                        List<Object> subData = null;
                        final Object oObject = originalObject == null ? item : originalObject;
                        boolean cont = true;
                        if (item instanceof Crushable) {
                            final Crushable crushable = (Crushable) item;
                            final DataTree dataTree = crushable.crush();
                            subData = dataTree.get();
                        } else if (item instanceof List) {
                            subData = (List) item;
                        } else {
                            cont = false;
                            error("This item is not selectable");
                        }
                        if (cont) {
                            final DataTree oData = originalData == null ? new DataTree(subData) : originalData;
                            superScene.addSubScene(new ListScene(this, mode, subData, oObject, oData, null));
                        }
                    } else {
                        error("Too many / too few items selected!");
                    }
                }
            } else if (mode == 5) {
                if (b == activate) {
                    final int index = indexes.get(0);
                    final List<Object> items = (List<Object>) data;
                    final Object parsed = parseObject(textBox.getContent());
                    if (parsed == null) {
                        error("Failed to parse input");
                    } else {
                        items.set(index, parsed);
                        final boolean fail = reloadObject(originalObject, originalData);
                        if (fail) {
                            error("Failed to save object");
                        } else {
                            closeSubScene();
                        }
                    }
                }
            } else if (mode == 6) {
                if (b == activate) {
                    final List<Object> items = (List<Object>) data;
                    if (items.size() < 1) {
                        closeSubScene();
                    } else {
                        if (items.get(0) instanceof Tile || items.get(0) instanceof Node) {
                            for (Object object : items) {
                                if (object instanceof Node) {
                                    final Node node = (Node) object;
                                    node.removeFromParent();
                                } else if (object instanceof Tile) {
                                    final Tile tile = (Tile) object;
                                    final Tilemap map = tile.getTilemap();
                                    final Point pos = tile.getTileMapPos();
                                    map.setTile(null, pos.x, pos.y);
                                }
                            }
                        } else {
                            for (int i = indexes.size() - 1; i >= 0; i--) {
                                items.remove(i);
                            }
                            final boolean fail = reloadObject(originalObject, originalData);
                            if (fail) {
                                error("Failed to save object");
                            } else {
                                closeSubScene();
                            }
                        }
                    }
                    closeSubScene();
                } else if (b == select) {
                    closeSubScene();
                }
            }
        }
    }

    private Object parseObject(final String text) {
        try {
            if (editType == 1) {
                return Integer.parseInt(text);
            } else if (editType == 2) {
                return Double.parseDouble(text);
            } else if (editType == 3) {
                return Boolean.parseBoolean(text);
            } else if (editType == 4) {
                return text;
            }
        } catch (final NumberFormatException e) {
            return null;
        }
        return null;
    }

    private boolean reloadObject(final Object original, final DataTree newData) {
        boolean result = false;
        try {
            if (original instanceof Tile) {
                final Tile tile = (Tile) original;
                final Tile targetTile = tile.uncrush(newData);
                final Tilemap tilemap = tile.getTilemap();
                final Point pos = tile.getTileMapPos();
                tilemap.setTile(targetTile, pos.x, pos.y);
            } else if (original instanceof Node) {
                final Node node = (Node) original;
                final Node targetNode = node.uncrush(newData, node.getScene());
                final Node parent = node.getParent();
                parent.removeChild(node);
                parent.addChild(targetNode);
            }
        } catch (final Exception e) {
            result = true;
        }
        return result;
    }

}
