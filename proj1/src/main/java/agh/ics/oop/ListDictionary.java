package agh.ics.oop;

import java.util.*;

public class ListDictionary<K, T> {
    protected Map<K, HashSet<T>> dict;

    ListDictionary() { dict = new HashMap<>(); }

    public void put(K key, T value) {
        if (dict.containsKey(key)) {
            var list = dict.get(key);
            list.add(value);
            dict.replace(key, list);
        }
        else {
            var list = new HashSet<T>();
            list.add(value);
            dict.put(key, list);
        }
    }

    public HashSet<T> get(K key) { return dict.getOrDefault(key, new HashSet<>()); }

    public boolean containsKey(K key) { return dict.containsKey(key); }

    public void remove(K key, T value) {
        var list = dict.get(key);
        list.remove(value);
        if (!list.isEmpty()) dict.replace(key, list);
        else dict.remove(key);
    }

    public List<T> values() {
        List<T> output = new LinkedList<>();
        Collection<HashSet<T>> lists= dict.values();
        for (HashSet<T> list : lists) output.addAll(list);
        return output;
    }

    public Set<K> keys() { return dict.keySet(); }
}
