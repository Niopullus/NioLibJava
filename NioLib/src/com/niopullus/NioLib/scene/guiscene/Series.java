package com.niopullus.NioLib.scene.guiscene;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.niopullus.NioLib.Picture;
import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.draw.StringSize;
import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.Scene;

/**Used to display a series of items
 * Created by Owen on 7/14/2016.
 */
public class Series extends SelectableGUIElement {

    private boolean checkBoxes;
    private List<Boolean> checked;
    private GUIElement pageDisplay;
    private int page;
    private int pages;
    private int numCheckable;
    private Integer partsPerPage;
    private Integer selectedItem;
    private Integer checkLimit;
    private Picture check;

    public Series(final String content, final Theme theme, final int fontSize, final GUISize size, final Integer itemsPerPage) {
        super(content, theme, fontSize, size);
        partsPerPage = itemsPerPage;
        updateDimensions();
        updateBackgrounds();
        selectedItem = null;
        pages = 1;
        check = Picture.getPicture("check");
    }

    public Series(final String content, final Theme theme, final int fontSize, final GUISize size) {
        this(content, theme, fontSize, size, 0);
    }

    public Series(final String content, final Theme theme, final int fontSize, final int _itemsPerPage) {
        this(content, theme, fontSize, new GUISize(), _itemsPerPage);
    }

    public void addLine(final String line) {
        super.addLine(line);
        updatePageCount();
        updatePageDisplay();
        if (checkBoxes) {
            checked.add(false);
        }
    }

    public void enableCheckBoxes() {
        checkBoxes = true;
        updateDimensions();
        updateBackgrounds();
        fillChecked();
    }

    public void disableCheckBoxes() {
        checkBoxes = false;
    }

    public List<Boolean> getCheckedIndexes() {
        return checked;
    }

    public void nextPage() {
        if (page + 1 < pages) {
            page++;
            updatePageDisplay();
        }
    }

    public void prevPage() {
        if (page - 1 >= 0) {
            page--;
            updatePageDisplay();
        }
    }

    private void updatePageCount() {
        final int lineCount = getLineCount();
        pages = (int) Math.ceil((double) lineCount / partsPerPage);
    }

    public void determineWidth() {
        super.determineWidth();
        if (checkBoxes) {
            final int width;
            final int fieldWidth;
            final int borderSpacing = getBorderSpacing();
            final int partSize = getPartHeight() - borderSpacing * 2;
            final int widthGap = getWidthGap();
            width = getWidth();
            fieldWidth = getFieldWidth();
            setWidth(width + partSize);
            setFieldWidth(fieldWidth + partSize);
        }
    }

    private void updatePageDisplay() {
        if (pageDisplay != null) {
            pageDisplay.setContent(0, "Page " + (page + 1) + " / " + pages);
        }
    }

    private void fillChecked() {
        checked = new ArrayList<>();
        final int lineCount = getLineCount();
        for (int i = 0; i < lineCount; i++) {
            checked.add(false);
        }
    }

    public void setPageDisplay(final GUIElement element) {
        pageDisplay = element;
        updatePageDisplay();
    }

    private int getPartHeight() {
        final Font font = getFont();
        final int borderSpacing = getBorderSpacing();
        final int lineHeight = StringSize.getStringHeight(font);
        final int heightGap = getHeightGap();
        return lineHeight + heightGap * 2 + borderSpacing * 2;
    }

    public void linePotential() {
        final int height = getHeight();
        final int partHeight = getPartHeight();
        setDisplayLines(height / partHeight);
        setFieldHeight(100);
    }

    public void determineHeight() {
        if (partsPerPage != null) {
            final int partHeight = getPartHeight();
            final int height = partHeight * partsPerPage;
            final int borderSpacing = getBorderSpacing();
            final int fieldHeight = height - borderSpacing * 2;
            setHeight(height);
            setFieldHeight(fieldHeight);
            setDisplayLines(partsPerPage);
        }
    }

    public int getWidthFree() {
        final int partSize = getPartHeight();
        final int borderSpacing = getBorderSpacing();
        final int smallPartSize = partSize - borderSpacing * 2;
        final int widthGap = getWidthGap();
        return super.getWidthFree() - partSize;
    }

    public void moveMouse(final Scene.MousePack pack) {
        final Scene scene = getGUIScene();
        final int x = getX() - getWidth() / 2 + scene.getWidth() / 2;
        final int y = getY() - getHeight() / 2 + scene.getHeight() / 2;
        final int partSize = getPartHeight();
        final int packX = pack.getX();
        final int packY = pack.getY();
        final int relY = packY - y;
        final int selection = relY / partSize;
        if (packY >= y && packY <= y + getHeight() && packX >= x && packX <= x + getWidth()) {
            selectedItem = selection + page * partsPerPage;
        } else {
            selectedItem = null;
            disableOverrideMouse();
        }
    }

    public void mousePress(final Scene.MousePack pack) {
        final Scene scene = getGUIScene();
        final int packX = pack.getX();
        final int packY = pack.getY();
        if (checkBoxes) {
            final int x = getX() - getWidth() / 2 + scene.getWidth() / 2;
            final int y = getY() - getHeight() / 2 + scene.getHeight() / 2;
            if (packY >= y && packY <= y + getHeight() && packX >= x && packX <= x + getWidth()) {
                checkBox();
            }
        }
    }

    private void checkBox() {
        toggleCheck();
        if (checkLimit != null) {
            final int lineCount = getLineCount();
            final int numChecked = getNumChecked();
            final int diff = checkLimit - numChecked;
            if (diff < 0) {
                int changed = 0;
                for (int i = 0; i < lineCount; i++) {
                    final boolean isChecked = checked.get(i);
                    if (isChecked && i != selectedItem) {
                        checked.set(i, false);
                        changed++;
                        if (changed >= -diff) {
                            break;
                        }
                    }
                }
            }
        }
    }

    public List<Integer> getChecked() {
        final List<Integer> result = new ArrayList<>();
        for (int i = 0; i < checked.size(); i++) {
            if (checked.get(i)) {
                result.add(i);
            }
        }
        return result;
    }

    public int getNumChecked() {
        final int lineCount = getLineCount();
        int total = 0;
        for (int i = 0; i < lineCount; i++) {
            final boolean isChecked = checked.get(i);
            if (isChecked) {
                total++;
            }
        }
        return total;
    }

    private void toggleCheck() {
        final int lineCount = getLineCount();
        if (selectedItem < lineCount) {
            final boolean curState = checked.get(selectedItem);
            checked.set(selectedItem, !curState);
        }
    }

    public void setCheckLimit(final int limit) {
        checkLimit = limit;
    }

    public void parcelDraw(final Canvas canvas) {
        final Font font = getFont();
        final int textHeight = StringSize.getStringHeight(font);
        final int fieldHeight = getFieldHeight();
        final int heightGap = getHeightGap();
        final int borderSpacing = getBorderSpacing();
        final boolean selected = getSelected();
        final int width = getWidth();
        final int fieldWidth = getFieldWidth();
        final int displayLines = getDisplayLines();
        final int partSize = getPartHeight();
        final int lineCount = getLineCount();
        final int widthGap = getWidthGap();
        final int smallPartSize = partSize - borderSpacing * 2;
        final int spaceFreePerLine = fieldWidth - widthGap * 2 - (checkBoxes ? smallPartSize : 0);
        final int tinyPartSize = (int) (smallPartSize * 0.7);
        final int partDiff = (smallPartSize - tinyPartSize) / 2;
        Background bg;
        Background border;
        Color textColor;
        int yPos = 0;
        for (int i = 0; i < displayLines; i++) {
            final int xPos;
            final int rhGap = (partSize - borderSpacing * 2 - heightGap * 2 - textHeight) / 2;
            final String displayLine;
            final int lineNum = i + page * partsPerPage;
            textColor = !selected ? getTextColor() : getSelectedTextColor();
            if (selectedItem != null && lineNum == selectedItem) {
                bg = getSelectedBG();
                border = getSelectedBorderBG();
            } else {
                bg = getBG();
                border = getBorderBG();
            }
            canvas.o.parcel(border, 0, yPos, width, partSize, 20, 0, 1);
            canvas.o.parcel(bg, getBorderSpacing(), yPos + getBorderSpacing(), fieldWidth, smallPartSize, 30, 0, 1);
            if (lineNum < lineCount) {
                final String line = getLineDisplay(lineNum);
                final List<Integer> displayChars = getDisplayChars();
                final Integer lineChars = displayChars.get(lineNum);
                if (lineChars != null) {
                    displayLine = line.substring(0, lineChars + 1) + "...";
                } else {
                    displayLine = line;
                }
                xPos = calcXPos(displayLine, spaceFreePerLine) + (checkBoxes ? smallPartSize : 0);
                if (checkBoxes) {
                    canvas.o.rect(Color.BLACK, borderSpacing, yPos + borderSpacing, smallPartSize, smallPartSize, 50);
                    canvas.o.rect(Color.WHITE, borderSpacing + partDiff, yPos + borderSpacing + partDiff, tinyPartSize, tinyPartSize, 50);
                    if (checked.get(lineNum)) {
                        canvas.m.sketch(check, borderSpacing + smallPartSize / 2, yPos + borderSpacing + smallPartSize / 2, 75, 75, 75);
                    }
                }
                if (displayLine != null) {
                    canvas.o.text(displayLine, textColor, font, xPos, yPos + borderSpacing + heightGap + rhGap, 40, 0, 1);
                }
            }
            yPos += partSize;
        }
    }

    public void select() {
        super.select();
        enableOverrideMouse();
    }

}
