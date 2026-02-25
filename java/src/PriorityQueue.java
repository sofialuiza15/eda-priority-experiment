public class PriorityQueue {

    private Heap heap;

    public PriorityQueue() {
        heap = new MaxHeap();
    }

    public void enqueue(int value) {
        heap.insert(value);
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