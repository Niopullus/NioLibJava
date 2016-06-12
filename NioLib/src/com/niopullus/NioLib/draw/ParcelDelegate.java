package com.niopullus.NioLib.draw;

/**Enables Parcels to integrate other Parcels
 * Created by Owen on 6/11/2016.
 */
public class ParcelDelegate extends Draw.DrawDelegate {

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private boolean init;

    public ParcelDelegate() {
        super();
        minX = 0;
        maxX = 0;
        minY = 0;
        maxY = 0;
        init = false;
    }

    public void subParcel(final Parcel parcel) {
        parcel.integrate(this);
    }

    public void addElement(final DrawElement element) {
        super.addElement(element);
        if (!init) {
            minX = element.getDx1();
            maxX = element.getDx2();
            minY = element.getDy1();
            maxY = element.getDy2();
            init = true;
            return;
        }
        if (element.getDx1() < minX) {
            minX = element.getDx1();
        }
        if (element.getDx2() > maxX) {
            maxX = element.getDx2();
        }
        if (element.getDy1() < minY) {
            minY = element.getDy1();
        }
        if (element.getDy2() > maxY) {
            maxY = element.getDy2();
        }
    }

    public int getWidth() {
        return maxX - minX;
    }

    public int getHeight() {
        return maxY - minY;
    }

}
