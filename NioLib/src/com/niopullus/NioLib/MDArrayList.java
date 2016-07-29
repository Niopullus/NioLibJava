package com.niopullus.NioLib;

import static com.niopullus.NioLib.Utilities.placeHolder;

import java.util.*;

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
        this.enablePlaceHolder = true;
        this.content = new Directory();
        this.layerStatuses = new ArrayList<>();
        setupStatuses();
    }

    public MDArrayList(final int layers, final int... dimensions) {
        this(layers);
        for (int i = 0; i < dimensions.length; i++) {
            final int dim = dimensions[i];
            final LayerStatus status = getLayerStatus(i + 1);
            status.restricted = true;
            status.size = dim;
        }
    }

    private void setupStatuses() {
        for (int i = 1; i <= layers; i++) {
            layerStatuses.add(new LayerStatus());
        }
    }

    public int getLayersCount() {
        return layers;
    }

    public LayerStatus getLayerStatus(final int layer) {
        return layerStatuses.get(layer - 1);
    }

    private Object get(final DataPath path, final Directory folder, final int layer) {
        if (path.count() == 1) {
            final int pathIndex = path.get();
            final LayerStatus layerStatus = getLayerStatus(layer);
            if (validDirSetGet(folder.size(), pathIndex, layer)) {
                final Object object = folder.get(pathIndex);
                if (object == placeHolder) {
                    return null;
                } else {
                    return object;
                }
            } else {
                return null;
            }
        } else {
            final int pathIndex = path.get();
            if (validDirSetGet(folder.size(), pathIndex, layer)) {
                final Object taken = folder.get(pathIndex);
                if (taken != null) {
                    final Directory newFolder = (Directory) taken;
                    return get(path, newFolder, layer + 1);
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
        return (T) get(path, content, 1);
    }

    public MDArrayList<T> getSubList(final int... pathcontent) {
        final DataPath path = new DataPath(pathcontent);
        final MDArrayList<T> result;
        if (path.count() > layers - 1) {
            complain();
            return null;
        }
        result = new MDArrayList<>(layers - path.count());
        result.content = (Directory) get(path, content, 1);
        return result;
    }

    private void set(final Object object, final DataPath path, final Directory folder, final int layer) {
        if (path.count() == 1) {
            final int pathIndex = path.get();
            final LayerStatus layerStatus;
            fillFolder(folder, pathIndex);
            if (validDirSetGet(folder.size(), pathIndex, layer)) {
                folder.set(pathIndex, object);
            }
        } else {
            final int pathIndex = path.get();
            if (validDirFGet(pathIndex, layer)) {
                final Directory newFolder = safeGet(folder, pathIndex, layer + 1);
                set(object, path, newFolder, layer + 1);
            }
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
            final LayerStatus layerStatus = getLayerStatus(layer);
            if (validDirAdd(folder.size(), layer)) {
                folder.add(object);
            }
        } else {
            final int pathIndex = path.get();
            if (validDirFGet(pathIndex, layer)) {
                final Directory newFolder = safeGet(folder, pathIndex, layer + 1);
                add(object, path, newFolder, layer + 1);
            }
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

    private T remove(final DataPath path, final List folder, final int layer) {
        if (path.count() == 1) {
            final int pathIndex = path.get();
            if (validDirSetGet(folder.size(), pathIndex, layer)) {
                final T object = (T) folder.remove(pathIndex);
                int i = pathIndex;
                do {
                    folder.remove(i);
                    i--;
                } while(i >= 0 && folder.get(i) == placeHolder);
                return object;
            } else {
                return null;
            }
        } else {
            final int pathIndex = path.get();
            if (validDirSetGet(folder.size(), pathIndex, layer)) {
                final Object taken = folder.get(pathIndex);
                if (taken instanceof List) {
                    final List newFolder = (List) taken ;
                    return remove(path, newFolder, layer + 1);
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
        return remove(path, content, 1);
    }

    private void remove(final T object, final DataPath path, final List folder, final int layer) {
        if (path.count() == 0) {
            final int foundIndex = Collections.binarySearch(folder, object);
            if (foundIndex != -1 && validDirSetGet(folder.size(), foundIndex, layer)) {
                folder.remove(foundIndex);
                for (int i = foundIndex; i >= 0 && folder.get(i) == placeHolder; i--) {
                    folder.remove(i);
                }
            }
        } else {
            final int pathIndex = path.get();
            if (validDirSetGet(folder.size(), pathIndex, layer)) {
                final Object taken = folder.get(pathIndex);
                if (taken instanceof List) {
                    final List newFolder = (List) taken ;
                    remove(path, newFolder, layer + 1);
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
        remove(object, path, content, 1);
    }

    public int getSize(final int layer) {
        final LayerStatus status = getLayerStatus(layer);
        if (status.restricted) {
            return status.size;
        } else {
            return getSizeOfLayer(layer);
        }
    }

    public boolean isRestricted(final int layer) {
        final LayerStatus status = getLayerStatus(layer);
        return status.restricted;
    }

    public int getRestriction(final int layer) {
        final LayerStatus status = getLayerStatus(layer);
        return status.size;
    }

    public void restrict(final int layer, final int restriction) {
        final LayerStatus status = getLayerStatus(layer);
        status.restricted = true;
        status.size = restriction;
    }

    public void unrestrict(final int layer) {
        final LayerStatus status = getLayerStatus(layer);
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
        if (taken == null || taken == placeHolder) {
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
            folder.add(placeHolder);
        }
    }

    private boolean validDirAdd(final int folderSize, final int layer) {
        return validDir(folderSize, 0, layer, true, true);
    }

    private boolean validDirSetGet(final int folderSize, final int pathIndex, final int layer) {
        return validDir(folderSize, pathIndex, layer, false, false);
    }

    private boolean validDirFGet(final int pathIndex, final int layer) {
        return validDir(0, pathIndex, layer, true, false);
    }

    private boolean validDir(final int folderSize, final int pathIndex, final int layer, final boolean ignoreFS, final boolean addMode) {
        final LayerStatus status = getLayerStatus(layer);
        final boolean b1 = pathIndex < folderSize;
        final boolean b2 = !status.restricted || (!addMode ? pathIndex < status.size : folderSize < status.size);
        return (b1 || ignoreFS) && b2;
    }

    private void complain() {
        final Thread currentThread = Thread.currentThread();
        final StackTraceElement[] elements = currentThread.getStackTrace();
        System.out.println("EXCEPTION WHEN TRYING TO USE MDARRAY");
        for (StackTraceElement element : elements) {
            System.out.println(element);
        }
    }

    public String toString() {
        return content.toString();
    }

    private void complainLayerCount() {
        System.out.println("ATTEMPTED TO ASSIGN A MDARRAYLIST TO A LOCATION WHERE IT IS WARRANTED THAT THE" + " CURRENT AND OVERRIDING LISTS CONTAIN AN EQUAL AMOUNT OF LAYERS");
    }

    public int getSizeOfLayer(final int layer) {
        return getSizeOfLayer(content, 1, layer);
    }

    public int getSizeOfLayer(final List folder, final int curLayer, final int targetLayer) {
        int result = 0;
        if (curLayer == targetLayer) {
            return folder.size();
        } else {
            for (Object object: folder) {
                if (object instanceof List) {
                    final List list = (List) object;
                    int localMax = getSizeOfLayer(list, curLayer + 1, targetLayer);
                    if (localMax > result) {
                        result = localMax;
                    }
                }
            }
        }
        return result;
    }

    private static class Directory implements List {

        private List content;
        private int layer;

        public Directory() {
            content = new ArrayList<>();
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

        public String toString() {
            return content.toString();
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

        public LayerStatus() {
            restricted = false;
            size = 0;
        }

    }

}
