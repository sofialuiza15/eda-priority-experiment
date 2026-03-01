package implementation;

public class PQHeap {

    private Heap heap;

    public PQHeap() {
        heap = new Heap(10);
    }

    public void enqueue(int value) {
        heap.add(value);
    }

    public int dequeue() {
        return heap.remove();
    }

    public int peek() {
        return heap.peek();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }
}
