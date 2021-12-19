package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListDictionary<K, T> {
    protected Map<K, List<T>> dict;

    ListDictionary() { dict = new HashMap<K, List<T>>(); }

    public void put(K key, T value) {
        if (dict.containsKey(key)) {
            var list = dict.get(key);
            list.add(value);
            dict.replace(key, list);
        }
        else {
            var list = new ArrayList<T>();
            list.add(value);
            dict.put(key, list);
        }
    }

    public List<T> get(K key) { return dict.getOrDefault(key, new ArrayList<T>()); }

    public boolean containsKey(K key) { return dict.containsKey(key); }

    public void remove(K key, T value) {
        var list = dict.get(key);
        list.remove(value);
        dict.replace(key, list);
    }
}
