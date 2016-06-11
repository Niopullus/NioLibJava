package com.niopullus.NioLib.draw;

import com.niopullus.NioLib.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**Stores, sorts and draws DrawElements
 * Created by Owen on 3/30/2016.
 */
public class DrawManager {

    private List<DrawElement> elements;

    public DrawManager() {
        elements = new ArrayList<>();
    }

    public void add(final DrawElement element) {
        if (isVisible(element)) {
            elements.add(element);
        }
    }

    public void display(final Graphics2D g) {
        final Comparator<DrawElement> comparator = (final DrawElement o1, final DrawElement o2) -> {
                final Integer z = o1.getZ();
                return z.compareTo(o2.getZ());
        };
        elements.sort(comparator);
        for (DrawElement element : elements) {
            element.draw(g);
        }
        elements = new ArrayList<>();
    }

    private boolean isVisible(final DrawElement element) {
        final boolean cond1 = element.getDx1() < Main.Width();
        final boolean cond2 = element.getDy1() < Main.Height();
        final boolean cond3 = element.getDx2() > 0;
        final boolean cond4 = element.getDy2() > 0;
        return cond1 && cond2 && cond3 && cond4;
    }

}
