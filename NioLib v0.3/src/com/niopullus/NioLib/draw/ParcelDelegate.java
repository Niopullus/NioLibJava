package com.niopullus.NioLib.draw;

/**
 * Created by Owen on 6/11/2016.
 */
public class ParcelDelegate {

    public void subParcel(final Parcel parcel) {
        parcel.integrate(this);
    }

}
