package com.niopullus.NioLib;

import java.util.ArrayList;

/**
 * Created by Owen on 4/12/2016.
 */
public class DataTree {

    private ArrayList data;

    public DataTree() {
        this.data = new ArrayList();
    }

    public DataTree(ArrayList data) {
        this.data = data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }

    private int addData(Object o, DataPath path, ArrayList folder) {
        if (path.count() == 0) {
            folder.add(o);
            return folder.size() - 1;
        } else {
            int folderDir = path.get();
            if (!(folder.get(folderDir) instanceof ArrayList)) {
                System.out.println("FAILED TO ACCESS FOLDER IN DATA TREE");
                return -1;
            }
            return addData(o, path, (ArrayList) folder.get(folderDir));
        }
    }

    public int addData(Integer i, DataPath path) {
        return addData(i, path, this.data);
    }

    public int addData(Integer i) {
        this.data.add(i);
        return this.data.size() - 1;
    }

    public int addData(Double d, DataPath path) {
        return addData(d, path, this.data);
    }

    public int addData(Double d) {
        this.data.add(d);
        return this.data.size() - 1;
    }

    public int addData(String s, DataPath path) {
        return addData(s, path, this.data);
    }

    public int addData(String s) {
        this.data.add(s);
        return this.data.size() - 1;
    }

    public int addData(ArrayList f, DataPath path) {
        return addData(f, path, this.data);
    }

    public int addData(ArrayList f) {
        this.data.add(f);
        return this.data.size() - 1;
    }

    public int addFolder(DataPath path) {
        return addData(new ArrayList(), path, this.data);
    }

    public int addFolder() {
        this.data.add(new ArrayList());
        return this.data.size() - 1;
    }

    private Object get(DataPath path, ArrayList folder) {
        if (path.count() == 0) {
            return this.data;
        } else if (path.count() == 1) {
            return folder.get(path.get());
        } else {
            int folderDir = path.get();
            return get(path, (ArrayList) folder.get(folderDir));
        }
    }

    public Object get(DataPath path) {
        return get(path, this.data);
    }

    public Object get() {
        return this.data;
    }

    private String compress(ArrayList folder) {
        String data = "";
        for (Object o : folder) {
            if (o instanceof ArrayList) {
                data += ",f(" + compress((ArrayList) o) + ")";
            } else {
                char type = 'i';
                if (o instanceof Double) {
                    type = 'd';
                } else if (o instanceof String) {
                    type = 's';
                }
                data += "," + type + "(" + o.toString() + ")";
            }
        }
        return data;
    }

    public String compress() {
        return compress(this.data);
    }

    private static int endFinder(String input, int start) {
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
            System.out.println("ERROR LOADING DATA (COULD NOT MATCH PARENTHESIS)");
        }
        return end;
    }

    private static ArrayList decompresshelper(String input) {
        int pos = 0;
        ArrayList result = new ArrayList();
        while (pos < input.length()) {
             char currentChar = input.charAt(pos);
             if (currentChar == ',') {
                 pos++;
             } else if (currentChar == 'f') {
                 int startPos = pos + 2;
                 int endPos = endFinder(input, startPos);
                 result.add(decompresshelper(input.substring(startPos, endPos)));
                 pos = endPos + 1;
             } else if (currentChar == 'i') {
                 int startPos = pos + 2;
                 int endPos = endFinder(input, startPos);
                 result.add(Integer.parseInt(input.substring(startPos, endPos)));
                 pos = endPos + 1;
             } else if (currentChar == 'd') {
                 int startPos = pos + 2;
                 int endPos = endFinder(input, startPos);
                 result.add(Double.parseDouble(input.substring(startPos, endPos)));
                 pos = endPos + 1;
             } else if (currentChar == 's') {
                 int startPos = pos + 2;
                 int endPos = endFinder(input, startPos);
                 result.add(input.substring(startPos, endPos));
                 pos = endPos + 1;
             } else {
                 System.out.println("ERROR LOADING DATA (UNIDENTIFIED SYMBOL)");
                 break;
             }
        }
        return result;
    }

    public static DataTree decompress(String input) {
        DataTree result = new DataTree();
        result.setData(decompresshelper(input));
        return result;
    }

    private int getSize(DataPath path, ArrayList folder) {
        if (path.count() == 0) {
            return folder.size();
        } else {
            int folderDir = path.get();
            if (!(folder.get(folderDir) instanceof ArrayList)) {
                System.out.println("FAILED TO ACCESS FOLDER IN DATA TREE");
            }
            return getSize(path, (ArrayList) folder.get(folderDir));
        }
    }

    public int getSize(DataPath path) {
        return getSize(path, this.data);
    }

    public int getSize() {
        return this.data.size();
    }

}
