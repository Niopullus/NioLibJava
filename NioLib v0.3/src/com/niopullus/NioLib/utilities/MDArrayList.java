package com.niopullus.NioLib.utilities;

import com.niopullus.NioLib.DataPath;
import com.niopullus.NioLib.Log;
import com.niopullus.NioLib.LogManager;
import com.niopullus.NioLib.scene.dynscene.Dir;

import java.util.*;

import static com.niopullus.NioLib.utilities.Utilities.placeHolder;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**An ArrayList that works on multiple dimensions
 * When assigning for a dimension that hasn't been
 * instantiated, it will be done automatically.
 * Created by Owen on 5/17/2016.
 */
public class MDArrayList<T> {

    private Directory content;
    private List<LayerStatus> layerStatuses;
    private int layers;
    private boolean enablePlaceHolder;

    public MDArrayList(final int layers) {
        this.layers = layers;
        this.enablePlaceHolder = false;
        this.content = new Directory();
        this.layerStatuses = new ArrayList<>();
        setupStatuses();
    }

    private void setupStatuses() {
        for (int i = 0; i < layerStatuses.size(); i++) {
            layerStatuses.set(i, new LayerStatus());
        }
    }

    public int getLayersCount() {
        return layers;
    }

    private Object get(final DataPath path, final Directory folder) {
        if (path.count() == 1) {
            final int pathIndex = path.get();
            final Object object = folder.get(pathIndex);
            if (object == Utilities.placeHolder) {
                return null;
            } else {
                return object;
            }
        } else {
            final int pathIndex = path.get();
            if (pathIndex < folder.size()) {
                final Object taken = folder.get(pathIndex);
                if (taken != null) {
                    final Directory newFolder = (Directory) taken;
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
        return (T) get(path, content);
    }

    public MDArrayList<T> getSubList(final int... pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        final MDArrayList<T> result;
        if (path.count() > layers - 1) {
            complain();
            return null;
        }
        result = new MDArrayList<>(layers - path.count());
        result.content = (Directory) get(path, content);
        return result;
    }

    private void set(final Object object, final DataPath path, final Directory folder, final int layer) {
        if (path.count() == 1) {
            final int pathIndex = path.get();
            final LayerStatus layerStatus;
            fillFolder(folder, pathIndex);
            layerStatus = layerStatuses.get(layer);
            if (layerStatus.restricted && pathIndex < layerStatus.size) {
                folder.set(pathIndex, object);
            }
        } else {
            final int pathIndex = path.get();
            final Directory newFolder = safeGet(folder, pathIndex, layer + 1);
            set(object, path, newFolder, layer + 1);
        }
    }

    public void set(final Object object, final int... pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        if (path.count() != layers) {
            complain();
            return;
        }
        set(object, path, content, 1);
    }

    public void set(final List<T> list, final int... pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        final Directory dir = Directory.fromList(list);
        dir.layer = path.count() - 1;
        if (path.count() != layers - 1) {
            complain();
            return;
        }
        set(dir, path, content, 1);
    }

    public void set(final MDArrayList<T> list, final int... pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        final Directory dir = Directory.fromMDArrayList(list, path.count());
        dir.layer = path.count() - 1;
        if (path.count() != layers - list.layers) {
            complain();
            return;
        }
        set(dir, path, content, 1);
    }

    private void add(final Object object, final DataPath path, final Directory folder, final int layer) {
        if (path.count() == 0) {
            final LayerStatus layerStatus = layerStatuses.get(layer);
            if (layerStatus.restricted && folder.size() < layerStatus.size) {
                folder.add(object);
            }
        } else {
            final int pathIndex = path.get();
            final Directory newFolder = safeGet(folder, pathIndex, layer + 1);
            add(object, path, newFolder, layer + 1);
        }
    }

    public void add(final T object, final int...pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        if (path.count() != layers - 1) {
            complain();
            return;
        }
        add(object, path, content, 1);
    }

    public void add(final List<T> list, final int...pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        final Directory dir = Directory.fromList(list);
        dir.layer = path.count();
        if (path.count() != layers - 2) {
            complain();
            return;
        }
        add(dir, path, content, 1);
    }

    public void add(final MDArrayList<T> list, final int...pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        final Directory dir = Directory.fromMDArrayList(list, path.count());
        if (path.count() != layers - list.layers - 1) {
            complain();
            return;
        }
        add(dir, path, content, 1);
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

    public int getSize(final int layer) {
        final LayerStatus status = layerStatuses.get(layer);
        return status.size;
    }

    public boolean isRestricted(final int layer) {
        final LayerStatus status = layerStatuses.get(layer);
        return status.restricted;
    }

    public int getRestriction(final int layer) {
        final LayerStatus status = layerStatuses.get(layer);
        return status.size;
    }

    public void restrict(final int layer, final int restriction) {
        final LayerStatus status = layerStatuses.get(layer);
        status.restricted = true;
        status.size = restriction;
    }

    public void unrestrict(final int layer) {
        final LayerStatus status = layerStatuses.get(layer);
        status.restricted = false;
        status.size = 0;
    }

    public void layerCheck(final MDArrayList list) {
        if (list.layers != layers) {
            complainLayerCount();
        }
    }

    public void layerCheck(final int layers) {
        if (this.layers != layers) {
            complainLayerCount();
        }
    }

    private Directory safeGet(final List folder, final int index, final int layer) {
        fillFolder(folder, index);
        Object taken = folder.get(index);
        if (taken == null) {
            final Directory createdFolder = new Directory();
            createdFolder.layer = layer;
            folder.set(index, createdFolder);
            taken = createdFolder;
        }
        return (Directory) taken;
    }

    private void fillFolder(final List folder, final int end) {
        if (end - folder.size() < 0 && !enablePlaceHolder) {
            complain();
            return;
        }
        for (int i = folder.size(); i <= end; i++) {
            folder.set(i, Utilities.placeHolder);
        }
    }

    private void complain() {
        Log.doc("EXCEPTION WHEN TRYING TO USE MDARRAY", "NioLib", LogManager.LogType.ERROR);
    }

    private void complainLayerCount() {
        Log.doc("ATTEMPTED TO ASSIGN A MDARRAYLIST TO A LOCATION WHERE IT IS WARRANTED THAT THE CURRENT AND OVERRIDING LISTS CONTAIN AN EQUAL AMOUNT OF LAYERS", "NioLib", LogManager.LogType.ERROR);
    }

    private static class Directory implements List {

        private List content;
        private int layer;

        public Directory() {
            content = new ArrayList<>();
        }

        public int getSizeofLayer(final int layer) {
            if (layer == 0) {
                int max = 0;
                for (Object o : content) {
                    if (o instanceof List) {
                        final List list = (List) o;
                        if (max < list.size()) {
                            max = list.size();
                        }
                    }
                }
                return max;
            } else {
                int max = 0;
                for (Object o : content) {
                    if (o instanceof Directory) {
                        final Directory dir = (Directory) o;
                        final int listSize = dir.getSizeofLayer(layer - 1);
                        if (max < listSize) {
                            max = listSize;
                        }
                    }
                }
                return max;
            }
        }

        public int getLayer() {
            return layer;
        }

        public void setLayer(final int layer) {
            this.layer = layer;
        }

        public static Directory fromList(final List list) {
            final Directory result = new Directory();
            result.content = list;
            return result;
        }

        private static Directory convertList(final List list, final int layer) {
            final Directory directory = new Directory();
            directory.content = list;
            directory.layer = layer;
            for (int i = 0; i < directory.size(); i++) {
                final Object o = directory.get(i);
                if (o instanceof ArrayList) {
                    directory.set(i, convertList((ArrayList) o, layer + 1));
                }
            }
            return directory;
        }

        public static Directory fromMDArrayList(final MDArrayList list, final int layer) {
            return convertList(list.content, layer);
        }

        public int size() {
            return content.size();
        }

        public boolean isEmpty() {
            return content.isEmpty();
        }

        public boolean contains(Object o) {
            return content.contains(o);
        }

        public Iterator iterator() {
            return content.iterator();
        }

        public Object[] toArray() {
            return content.toArray();
        }

        public boolean add(Object o) {
            return content.add(o);
        }

        public boolean remove(Object o) {
            return content.remove(o);
        }

        public boolean addAll(Collection c) {
            return content.addAll(c);
        }

        public boolean addAll(int index, Collection c) {
            return content.addAll(index, c);
        }

        public void clear() {
            content.clear();
        }

        public Object get(int index) {
            return content.get(index);
        }

        public Object set(int index, Object element) {
            return content.set(index, element);
        }

        public void add(int index, Object element) {
            content.add(index, element);
        }

        public Object remove(int index) {
            return content.remove(index);
        }

        public int indexOf(Object o) {
            return content.indexOf(o);
        }

        public int lastIndexOf(Object o) {
            return content.lastIndexOf(o);
        }

        public ListIterator listIterator() {
            return content.listIterator();
        }

        public ListIterator listIterator(int index) {
            return content.listIterator(index);
        }

        public List subList(int fromIndex, int toIndex) {
            return content.subList(fromIndex, toIndex);
        }

        public boolean retainAll(Collection c) {
            return content.retainAll(c);
        }

        public boolean removeAll(Collection c) {
            return content.retainAll(c);
        }

        public boolean containsAll(Collection c) {
            return content.containsAll(c);
        }

        public Object[] toArray(Object[] a) {
            return content.toArray(a);
        }

    }

    private class LayerStatus {

        public boolean restricted;
        public int size;

    }

}
