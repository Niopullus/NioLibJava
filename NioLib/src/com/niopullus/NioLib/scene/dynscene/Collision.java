package com.niopullus.NioLib.scene.dynscene;

/**Keeps track of the two objects involved in the collision and other aspects
 * Created by Owen on 4/3/2016.
 */
public class Collision {

    private CollideData data1;
    private CollideData data2;
    private boolean passive;
    private Direction dir;

    public Collision(final CollideData _data1, final CollideData _data2, final Direction _dir, final boolean _passive) {
        data1 = _data1;
        data2 = _data2;
        dir = _dir;
        passive = _passive;
    }

    public CollideData getCauser() {
        return data1;
    }

    public CollideData getVictim() {
        return data2;
    }

    public Direction getDir() {
        return dir;
    }

    public boolean isPassive() {
        return passive;
    }

    public boolean involves(final String name) {
        final String name1 = data1.getName();
        final String name2 = data2.getName();
        final boolean name1Match = name1.equals(name);
        final boolean name2Match = name2.equals(name);
        return name1Match || name2Match;
    }

    public boolean involves(final String name1, final String name2) {
        final String dataName1 = data1.getName();
        final String dataName2 = data2.getName();
        final boolean match1 = name1.equals(dataName1) && name2.equals(dataName2);
        final boolean match2 = name1.equals(dataName2) && name1.equals(dataName1);
        return match1 || match2;
    }

    public boolean causedBy(final String name) {
        final String name1 = data1.getName();
        return name1.equals(name);
    }

    public boolean isVictim(final String name) {
        final String name2 = data2.getName();
        return name2.equals(name);
    }

}
