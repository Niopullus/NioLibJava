package com.niopullus.NioLib.utilities;

import com.niopullus.NioLib.DataPath;
import com.niopullus.NioLib.Log;
import com.niopullus.NioLib.LogManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**An ArrayList that works on multiple dimensions
 * When assigning for a dimension that hasn't been
 * instantiated, it will be done automatically.
 * Created by Owen on 5/17/2016.
 */
public class MDArrayList<T> {

    private List content;
    private int layers;
    private final static Object placeHolder = new Object();

    public MDArrayList(final int layers) {
        this.layers = layers;
    }

    public int getLayersCount() {
        return layers;
    }

    private T get(final DataPath path, final List folder) {
        if (path.count() == 1) {
            final int pathIndex = path.get();
            return (T) folder.get(pathIndex);
        } else {
            final int pathIndex = path.get();
            if (pathIndex < folder.size()) {
                final Object taken = folder.get(pathIndex);
                if (taken != null) {
                    final List newFolder = (List) taken;
                    return get(path, newFolder);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    public T get(final int... pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        if (path.count() != layers) {
            complain();
            return null;
        }
        return get(path, content);
    }

    private void set(final T object, final DataPath path, final List folder) {
        if (path.count() == 1) {
            final int pathIndex = path.get();
            fillFolder(folder, pathIndex);
            folder.set(pathIndex, object);
        } else {
            final int pathIndex = path.get();
            final List newFolder = safeGet(folder, pathIndex);
            set(object, path, newFolder);
        }
    }

    public void set(final T object, final int... pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        if (path.count() != layers) {
            complain();
            return;
        }
        set(object, path, content);
    }

    private void add(final T object, final DataPath path, final List folder) {
        if (path.count() == 0) {
            folder.add(object);
        } else {
            final int pathIndex = path.get();
            final List newFolder = safeGet(folder, pathIndex);
            set(object, path, newFolder);
        }
    }

    public void add(final T object, final int...pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        if (path.count() != layers - 1) {
            complain();
            return;
        }
        add(object, path, content);
    }

    private T remove(final DataPath path, final List folder) {
        if (path.count() == 1) {
            final int pathIndex = path.get();
            final T object = (T) folder.remove(pathIndex);
            for (int i = pathIndex; i >= 0 && folder.get(i) == placeHolder; i--) {
                folder.remove(i);
            }
            return object;
        } else {
            final int pathIndex = path.get();
            if (pathIndex < folder.size()) {
                final Object taken = folder.get(pathIndex);
                if (taken instanceof List) {
                    final List newFolder = (List) taken ;
                    return remove(path, newFolder);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    public T remove(final int... pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        if (path.count() != layers) {
            complain();
            return null;
        }
        return remove(path, content);
    }

    private void remove(final T object, final DataPath path, final List folder) {
        if (path.count() == 0) {
            final int foundIndex = Collections.binarySearch(folder, object);
            if (foundIndex != -1) {
                folder.remove(foundIndex);
                for (int i = foundIndex; i >= 0 && folder.get(i) == placeHolder; i--) {
                    folder.remove(i);
                }
            }
        } else {
            final int pathIndex = path.get();
            if (pathIndex < folder.size()) {
                final Object taken = folder.get(pathIndex);
                if (taken instanceof List) {
                    final List newFolder = (List) taken ;
                    remove(path, newFolder);
                }
            }
        }
    }

    public void remove(final T object, final int...pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        if (path.count() != layers - 1) {
            complain();
            return;
        }
        remove(object, path, content);
    }

    private List safeGet(final List folder, final int index) {
        fillFolder(folder, index);
        Object taken = folder.get(index);
        if (taken == null) {
            final List createdFolder = new ArrayList();
            folder.set(index, createdFolder);
            taken = createdFolder;
        }
        return (List) taken;
    }

    private void fillFolder(final List folder, final int end) {
        for (int i = folder.size(); i <= end; i++) {
            folder.set(i, placeHolder);
        }
    }

    private void complain() {
        Log.doc("EXCEPTION WHEN TRYING TO USE MDARRAY", "NioLib", LogManager.LogType.ERROR);
    }

}
