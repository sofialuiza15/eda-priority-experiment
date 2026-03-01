public class Heap {

    private int[] heap;
    private int tail;

    public Heap(int capacity){
        this.heap = new int[capacity];
        this.tail = -1;
    }

    public Heap(int[] array) {
        this.heap = array;
        this.tail = -1;
        this.buildHeap();
    }

    public boolean isEmpty() {
        return this.tail == -1;
    }

    public int left(int index) {
        return 2*index + 1;
    }

    public int right(int index) {
        return 2*index + 2;
    }

    public int parent(int index) {
        return Math.floorDiv(index - 1, 2);
    }

    public void add(int n) {
        if (tail >= (heap.length - 1)) {
            resize();
        }

        this.heap[++tail] = n;
        
        int i = tail;
        while (i > 0 && this.heap[parent(i)] < this.heap[i]) {
            int aux = this.heap[i];
            this.heap[i] = this.heap[parent(i)];
            this.heap[parent(i)] = aux;
            i = parent(i);
        }
    }

    public void buildHeap(){
        for (int i = parent(this.tail); i >= 0; i--) {
            heapify(i);
        }
    }

    private void heapify(int index) {
        if (isLeaf(index) || !isValidIndex(index)) 
            return;
        
        int index_max = max_index(index, left(index), right(index));
        
        if (index_max != index) {
                swap(index, index_max);
                heapify(index_max);
        }
    }

    public int remove() {
        if (isEmpty()) throw new RuntimeException("Empty");
        int element = this.heap[0];
        this.heap[0] = this.heap[tail];
        this.tail -= 1;

        this.heapify(0);

        return element;
    }

    public int peek() {
        if (isEmpty()) throw new NoSuchElementException("Fila vazia!");
        return this.heap[0];
    }

    private int max_index(int index, int left, int right) {
        if (this.heap[index] > this.heap[left]) {
            if (isValidIndex(right)) {
                if (this.heap[index] < this.heap[right])
                    return right;
            }
            return index;
        
        } else {
            if (isValidIndex(right)) {
                if (this.heap[left] < this.heap[right])
                    return right;
            } 
            
            return left;
        }
    }
    
    private void resize() {
        int[] novoHeap = new int[this.heap.length * 2];
        for (int i = 0; i <= tail; i++)
            novoHeap[i] = this.heap[i];
        
        this.heap = novoHeap;
    }
    
        public int size() {
            return this.tail + 1;
        }

    //métodos auxiliares

    private boolean isValidIndex(int index) {
        return index >= 0 && index <= tail;
    }
    
    private boolean isLeaf(int index) {
        return index > parent(tail) && index <= tail; 
    } 

    private void swap(int i, int j) {
        int aux = this.heap[i];
        this.heap[i] = this.heap[j];
        this.heap[j] = aux;
    }
}