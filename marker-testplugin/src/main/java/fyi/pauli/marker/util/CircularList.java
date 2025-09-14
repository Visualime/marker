package fyi.pauli.marker.util;

import java.util.List;

public class CircularList<T> {
    private final List<T> list;
    private int index = 0;

    public enum CycleMode {
        LEFT, RIGHT
    }

    public CircularList(List<T> list) {
        if (list.isEmpty()) throw new IllegalArgumentException("List must not be empty");
        this.list = list;
    }

    public T next() {
        index = (index + 1) % list.size();
        return list.get(index);
    }

    public T previous() {
        // ((index - 1) % size + size) % size  -> immer positiver Index
        index = (index - 1 + list.size()) % list.size();
        return list.get(index);
    }

    public T current() {
        return list.get(index);
    }
}

