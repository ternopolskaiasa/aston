package package1;

import java.util.List;

public class MyArrayList<T> {
    private T[] array;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList() {
        this.size = DEFAULT_CAPACITY;
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        array = (T[]) new Object[initialCapacity];
        size = 0;
    }

    public void add(T element) {
        if (size == array.length) {
            resize(array.length * 2);
        }
        array[size++] = element;
    }

    public void addAll(List<T> elements) {
        if (size == array.length) {
            resize(array.length * 2);
        }

        for (int i = 0; i < elements.size() - 1; i++) {
            array[size + i + 1] = elements.get(i);
        }
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return array[index];
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T removedElement = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[--size] = null;
        return removedElement;
    }

    private void resize(int newCapacity) {
        T[] newArray = (T[]) new Object[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }
}
