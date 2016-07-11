package com.niopullus.NioLib;

import java.util.ArrayList;
import java.util.List;

/**Handles Integers, Doubles, Strings and Booleans
 * Used to transform a network of data into a text file
 * Created by Owen on 4/12/2016.
 */
public class DataTree implements Crushable {

    private List data;

    public DataTree() {
        this.data = new ArrayList();
    }

    public DataTree(final List data) {
        this.data = data;
    }

    public void setData(final List data) {
        this.data = data;
    }

    public void setData(final Crushable crushable) {
        final DataTree data = crushable.crush();
        this.data = data.get();
    }

    private int addData(final Object object, final DataPath path, final List folder) {
        if (path.count() == 0) {
            folder.add(object);
            return folder.size() - 1;
        } else {
            int folderDir = path.get();
            if (!(folder.get(folderDir) instanceof List)) {
                complain();
                return -1;
            }
            return addData(object, path, (List) folder.get(folderDir));
        }
    }

    public int addData(final Integer i, final int... pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        return addData(i, path, data);
    }

    public int addData(final Integer i) {
        data.add(i);
        return data.size() - 1;
    }

    public int addData(final Double d, final int... pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        return addData(d, path, data);
    }

    public int addData(final Double d) {
        data.add(d);
        return data.size() - 1;
    }

    public int addData(final Boolean b, final int... pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        return addData(b, path, data);
    }

    public int addData(final Boolean b) {
        data.add(b);
        return data.size() - 1;
    }

    public int addData(final String s, final int... pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        return addData(s, path, data);
    }

    public int addData(final String s) {
        data.add(s);
        return data.size() - 1;
    }

    public int addData(final List<? extends Crushable> f, final int... pathcontent) {
        final List list = new ArrayList<>();
        final DataPath path = new DataPath(pathcontent);
        for (Crushable object : f) {
            final DataTree subData = object.crush();
            list.add(subData.get());
        }
        return addData(list, path, data);
    }

    public int addData(final List<? extends Crushable> f) {
        final List list = new ArrayList<>();
        for (Crushable object : f) {
            final DataTree subData = object.crush();
            list.add(subData.get());
        }
        data.add(list);
        return data.size() - 1;
    }

    public int addFolder(final int... pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        return addData(new ArrayList(), path, data);
    }

    public int addFolder() {
        data.add(new ArrayList());
        return data.size() - 1;
    }

    public int addData(final Crushable object, final int... pathcontent) {
        final DataTree _data = object.crush();
        final List folderContent = _data.get();
        final DataPath path = new DataPath(pathcontent);
        return addData(folderContent, path, data);
    }

    public int addObject(final Crushable object) {
        final DataTree _data = object.crush();
        final List folderContent = _data.get();
        addData(folderContent);
        return data.size() - 1;
    }

    private Object get(final DataPath path, final List folder) {
        if (path.count() == 0) {
            return data;
        } else if (path.count() == 1) {
            return folder.get(path.get());
        } else {
            final int folderDir = path.get();
            return get(path, (List) folder.get(folderDir));
        }
    }

    public Object get(final int... pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        return get(path, data);
    }

    public Integer getI(final int... pathcontent) {
        try {
            return (Integer) get(pathcontent);
        } catch (final ClassCastException e) {
            complainType();
        }
        return null;
    }

    public Double getD(final int... pathcontents) {
        try {
            return (Double) get(pathcontents);
        } catch (final ClassCastException e) {
            complainType();
        }
        return null;
    }

    public String getS(final int... pathcontents) {
        try {
            return (String) get(pathcontents);
        } catch (final ClassCastException e) {
            complainType();
        }
        return null;
    }

    public Boolean getB(final int... pathcontents) {
        try {
            return (Boolean) get(pathcontents);
        } catch (final ClassCastException e) {
            complainType();
        }
        return null;
    }

    public List getF(final int... pathcontents) {
        try {
            return (List) get(pathcontents);
        } catch (final ClassCastException e) {
            complainType();
        }
        return null;
    }

    private void complainType() {
        Log.doc("AN ATTEMPT WAS MADE TO TYPE-CALL AN OBJECT NOT OF THE SPECIFIED TYPE", "NioLib", LogManager.LogType.ERROR);
    }

    public List get() {
        return data;
    }

    private String compress(final List folder) {
        String data = "";
        for (Object o : folder) {
            if (o instanceof ArrayList) {
                data += ",f(" + compress((List) o) + ")";
            } else {
                char type = 'i';
                if (o instanceof Double) {
                    type = 'd';
                } else if (o instanceof Boolean) {
                    type = 'b';
                } else if (o instanceof String) {
                    type = 's';
                }
                data += "," + type + "(" + o.toString() + ")";
            }
        }
        return data;
    }

    public String compress() {
        return compress(data);
    }

    private static int endFinder(final String input, final int start) {
        int end = -1;
        int open = 0;
        for (int i = start; i < input.length(); i++) {
            if (input.charAt(i) == ')') {
                if (open > 0) {
                    open--;
                } else {
                    end = i;
                    break;
                }
            } else if (input.charAt(i) == '(') {
                open++;
            }
        }
        if (end == -1) {
            complainParenthesis();
        }
        return end;
    }

    private static List decompresshelper(final String input) {
        int pos = 0;
        final List result = new ArrayList();
        while (pos < input.length()) {
             final char currentChar = input.charAt(pos);
             if (currentChar == ',') {
                 pos++;
             } else if (currentChar == 'f') {
                 final int startPos = pos + 2;
                 final int endPos = endFinder(input, startPos);
                 result.add(decompresshelper(input.substring(startPos, endPos)));
                 pos = endPos + 1;
             } else if (currentChar == 'i') {
                 final int startPos = pos + 2;
                 final int endPos = endFinder(input, startPos);
                 result.add(Integer.parseInt(input.substring(startPos, endPos)));
                 pos = endPos + 1;
             } else if (currentChar == 'd') {
                 final int startPos = pos + 2;
                 final int endPos = endFinder(input, startPos);
                 result.add(Double.parseDouble(input.substring(startPos, endPos)));
                 pos = endPos + 1;
             } else if (currentChar == 'b') {
                 final int startPos = pos + 2;
                 final int endPos = endFinder(input, startPos);
                 result.add(Boolean.parseBoolean(input.substring(startPos, endPos)));
                 pos = endPos + 1;
             } else if (currentChar == 's') {
                 final int startPos = pos + 2;
                 final int endPos = endFinder(input, startPos);
                 result.add(input.substring(startPos, endPos));
                 pos = endPos + 1;
             } else {
                 complainUSymbol();
                 break;
             }
        }
        return result;
    }

    public static DataTree decompress(final String input) {
        final DataTree result = new DataTree();
        result.setData(decompresshelper(input));
        return result;
    }

    private int getSize(final DataPath path, List folder) {
        if (path.count() == 0) {
            return folder.size();
        } else {
            int folderDir = path.get();
            if (!(folder.get(folderDir) instanceof ArrayList)) {
                complain();
            }
            return getSize(path, (ArrayList) folder.get(folderDir));
        }
    }

    public int getSize(final int... pathcontents) {
        final DataPath path = new DataPath(pathcontents);
        return getSize(path, data);
    }

    public int getSize() {
        return data.size();
    }

    private List copy(final List data) {
        final List copy = new ArrayList();
        for (Object o : data) {
            if (o instanceof List) {
                copy.add(copy((List) o));
            } else {
                copy.add(o);
            }
        }
        return copy;
    }

    public DataTree copy() {
        return new DataTree(copy(data));
    }

    public DataTree crush() {
        return this;
    }

    private void complain() {
        Log.doc("FAILED TO ACCESS OR SET WITHIN DATATREE", "NioLib", LogManager.LogType.ERROR);
    }

    private static void complainParenthesis() {
        Log.doc("COULD NOT MATCH PARENTHESIS", "NioLib", LogManager.LogType.ERROR);
    }

    private static void complainUSymbol() {
        Log.doc("FOUND UNIDENTIFIED SYMBOL", "NioLib", LogManager.LogType.ERROR);
    }

}
