package java.src.PQTreeMap;

import java.util.*;

public class PQTreeMap {

    private TreeMap<Integer, Integer> treemap;

    public PQTreeMap() {
        this.treemap = new TreeMap<>();
    }

    // (permite repetidos)
    public void insert(int element) {
        treemap.put(element, treemap.getOrDefault(element, 0) + 1);
    }

    // remove o MAIOR elemento (max-heap behavior)
    public Integer remove() {
        if (treemap.isEmpty()) {
            throw new NoSuchElementException("Fila vazia");
        }

        Map.Entry<Integer, Integer> last = treemap.lastEntry();
        Integer element = last.getKey();

        if (last.getValue() > 1) {
            treemap.put(element, last.getValue() - 1);
        } else {
            treemap.remove(element);
        }

        return element;
    }

    // consulta o maior
    public Integer peek() {
        if (treemap.isEmpty()) {
            throw new NoSuchElementException("Fila vazia");
        }

        return treemap.lastKey();
    }

    public int size() {
        return treemap.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}