package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.scene.guiscene.GUIElement;

/**Allows for GUIElements to be present within a DynamicScene
 * Created by Owen on 7/4/16.
 */
public class GUIElementNode extends Node {

    private GUIElement element;

    public GUIElementNode(final String name, final GUIElement _element) {
        super(name);
        element = _element;
    }

    public void parcelDraw(final Canvas canvas) {
        canvas.o.parcel(element, 0, 0, element.getWidth(), element.getHeight(), element.getZ(), 0);
    }

}
