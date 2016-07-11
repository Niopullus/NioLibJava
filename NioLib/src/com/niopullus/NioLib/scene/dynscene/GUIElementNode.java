package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.scene.guiscene.GUIElement;

/**Allows for GUIElements to be present within a DynamicScene
 * Created by Owen on 7/4/16.
 */
public class GUIElementNode extends Node {

    private GUIElement element;

    public GUIElementNode(final String name, final GUIElement element) {
        super(name);
        setup(element);
    }

    public GUIElementNode() {
        this("unnamed node", null);
    }

    public void init(final Node node) {
        super.init(node);
        if (node instanceof GUIElementNode) {
            final GUIElementNode guiElementNode = (GUIElementNode) node;
            setup(guiElementNode.element);
        }
    }

    public void setup(final GUIElement _element) {
        element = _element;
    }

    public void parcelDraw(final Canvas canvas) {
        canvas.o.parcel(element, 0, 0, element.getWidth(), element.getHeight(), element.getZ(), 0, 1);
    }

}
