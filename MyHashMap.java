import lombok.*;

public class MyHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    static class Node<K,V>{
        private final K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node <K, V> [] table;
    private int size;
    private int capacity;

    public MyHashMap(){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity){
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Capacity mustn't be less than 0!");
        }
        this.capacity = (initialCapacity > 0 && (initialCapacity & (initialCapacity - 1)) == 0)
                        ? initialCapacity : (Integer.highestOneBit(initialCapacity) * 2);
        this.table = (Node<K, V>[]) new Node[capacity];
    }

    private int getIndex(K key){
        int hash = key != null ? Math.abs(key.hashCode()) : 0;
        return hash % capacity;
    }

    /**
     * Method to add new element in the map
     *
     * @param key unique key
     * @param value value of the node
     */
    void put(K key, V value){
        int index = getIndex(key);
        Node<K, V> node = table[index];
        if (node == null) {
            table[index] = new Node<>(key, value);
            size++;
            checkResize();
            return;
        }
        
        while (true) {
                if ((key == null && node.key == null) || (key != null && key.equals(node.key))) {
                    node.value = value;
                    return;
                }
                if (node.next == null) {
                    node.next = new Node<>(key, value);
                    size++;
                    checkResize();
                    break;
                }
                node = node.next;
        }
    }

    /**
     * Method to get a particular element by its key
     *
     * @param key unique key
     * @return searched element or null if it wasn't found
     */
    V get(K key){
        int index = getIndex(key);
        Node<K, V> node = table[index];

        while (node != null) {
            if ((key == null && node.key == null) || (key != null && key.equals(node.key))) {
                return node.value;
            }
            node = node.next;
        }

        return null;
    }

    /**
     * Method to delete a particular element from the map by its key
     *
     * @param key unique key
     * @return deleted element or null if such element doesn't exist
     */
    V remove(K key){
        int index = getIndex(key);
        Node<K, V> node = table[index];
        Node<K, V> prev = null;

        while (node != null) {
              if ((key == null && node.key == null) || (key != null && key.equals(node.key))) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next; 
                }
                  
                size--;
                return node.value;
            }

            prev = node;
            node = node.next;
        }

        return null;
    }

    private void checkResize() {
        if ((float) size / capacity >= LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * Method to recalculate the capacity of the map
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCapacity];

        for (Node<K, V> node : table) {
            while (node != null) {
                Node<K, V> next = node.next;
                int newIndex = getIndex(node.key);
                node.next = newTable[newIndex];
                newTable[newIndex] = node;
                node = next;
            }
        }

        table = newTable;
        capacity = newCapacity;
    }
}

