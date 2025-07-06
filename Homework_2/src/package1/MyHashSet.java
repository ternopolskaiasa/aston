package package1;

import java.util.HashMap;

public class MyHashSet<T> {
    private HashMap<T, Boolean> map;

    public MyHashSet() {
        this.map = new HashMap<>();
    }

    public boolean add(T element) {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }

        if (map.containsKey(element)) {
            return false;
        }

        map.put(element, true);
        return true;
    }

    public boolean remove(T element) {
        return map.remove(element) != null;
    }
}
