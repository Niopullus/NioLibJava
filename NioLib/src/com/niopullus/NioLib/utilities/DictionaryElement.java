package com.niopullus.NioLib.utilities;

/**
 * Created by Owen on 3/5/2016.
 */
public class DictionaryElement<T> implements Comparable {

    private String key;
    private T content;

    public DictionaryElement(String key, T content) {
        this.key = key;
        this.content = content;
    }

    public int compareTo(Object o) {
        if (!(o instanceof String)) {
            try {
                throw new UncomparableException();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String s = (String) o;
        return this.getKey().compareTo(s);
    }

    public String getKey() {
        return key;
    }

    public T getContent() {
        return content;
    }

}
