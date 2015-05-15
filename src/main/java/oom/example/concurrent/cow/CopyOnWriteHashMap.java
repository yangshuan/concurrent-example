package oom.example.concurrent.cow;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This is a simple copy-on-write HashMap
 * Created by yangshuan on 5/15/15.
 */
public class CopyOnWriteHashMap<K, V> implements Map<K, V> {
    private HashMap<K, V> map;

    private final Lock lock = new ReentrantLock();

    public CopyOnWriteHashMap() {
        this.map = new HashMap<>();
    }

    public CopyOnWriteHashMap(HashMap<K, V> map) {
        this.map = map;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        lock.lock();
        try {
            HashMap<K, V> newMap = new HashMap<>(map);
            V val = newMap.put(key, value);
            map = newMap;
            return val;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V remove(Object key) {
        lock.lock();
        try {
            HashMap<K, V> newMap = new HashMap<>(map);
            V val = newMap.remove(key);
            map = newMap;
            return val;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (m == null) {
            return;
        }
        lock.lock();
        try {
            map = new HashMap<>(m);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        lock.lock();
        try {
            HashMap<K, V> newMap = new HashMap<>();
            newMap.clear();
            map = newMap;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }
}
