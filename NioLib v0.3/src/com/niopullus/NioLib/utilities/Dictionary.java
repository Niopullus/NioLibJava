package com.niopullus.NioLib.utilities;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Owen on 3/5/2016.
 */
public class Dictionary<T> {

    ArrayList elements;

    public Dictionary() {
        this.elements = new ArrayList<DictionaryElement<T>>();
    }

    public void add(String key, T element) {
        elements.add(new DictionaryElement<T>(key, element));
    }

    public void remove(String key) {
        int index = Collections.binarySearch(this.elements, key);
        this.elements.remove(index);
        Collections.sort(this.elements);
    }

    public T get(String key) {
        int index = Collections.binarySearch(this.elements, key);
        return (T) this.elements.get(index);
    }

}
