package com.niopullus.NioLib.scene.guiscene;

/**Stores information regarding the size of a GUIElement
 * Created by Owen on 7/18/2016.
 */
public class GUISize extends Size {

    private boolean keepWidth;
    private boolean keepHeight;
    private int fieldWidth;
    private int fieldHeight;
    private int widthGap;
    private int heightGap;

    public GUISize(final int width, final int height, final boolean _keepWidth, final boolean _keepHeight) {
        super(width, height);
        keepWidth = _keepWidth;
        keepHeight = _keepHeight;
        widthGap = 10;
        heightGap = 10;
        fieldWidth = 0;
        fieldHeight = 0;
    }

    public GUISize(final int width, final int height) {
        this(width, height, true, true);
    }

    public GUISize() {
        this(0, 0, false, false);
    }

    public boolean isKeepWidth() {
        return keepWidth;
    }

    public boolean isKeepHeight() {
        return keepHeight;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public int getWidthGap() {
        return widthGap;
    }

    public int getHeightGap() {
        return heightGap;
    }

    public void setKeepWidth(final boolean _keepWidth) {
        keepWidth = _keepWidth;
    }

    public void setKeepHeight(final boolean _keepHeight) {
        keepHeight = _keepHeight;
    }

    public void setWidthGap(final int _widthGap) {
        widthGap = _widthGap;
    }

    public void setHeightGap(final int _heightGap) {
        heightGap = _heightGap;
    }

    public void setFieldWidth(final int _fieldWidth) {
        fieldWidth = _fieldWidth;
    }

    public void setFieldHeight(final int _fieldHeight) {
        fieldHeight = _fieldHeight;
    }

}
