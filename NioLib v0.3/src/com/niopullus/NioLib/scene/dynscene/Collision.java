package com.niopullus.NioLib.scene.dynscene;

/**
 * Created by Owen on 4/3/2016.
 */
public class Collision {

    private CollideData data1;
    private CollideData data2;
    private boolean passive;
    private Dir dir;

    public Collision(CollideData data1, CollideData data2, Dir dir, boolean passive) {
        this.data1 = data1;
        this.data2 = data2;
        this.dir = dir;
        this.passive = passive;
    }

    public boolean involves(String name) {
        return data1.getName().equals(name);
    }

    public boolean involves(String name1, String name2) {
        return data1.getName().equals(name1) && data2.getName().equals(name2) || data2.getName().equals(name1) && data1.getName().equals(name2);
    }

    public boolean causedBy(String name) {
        return data1.getName().equals(name);
    }

    public boolean isVictim(String name) {
        return data2.getName().equals(name);
    }

    public boolean isPassive() {
        return this.passive;
    }

    public CollideData getCauser() {
        return data1;
    }

    public CollideData getVictim() {
        return data2;
    }

    public Dir getDir() {
        return this.dir;
    }

}
