package tests;

import implementation.PQHeap;
import implementation.PQTreeMap;

import java.util.Random;
public class TestHeavyLoad {

    public static TestResults executeHeavyLoad(int n, int warmup, int repeticoes) {

        long[] temposHeap = new long[repeticoes];
        long[] temposTree = new long[repeticoes];
        
        // Precisamos desses dados para impedir que o custo do random seja contabilizado
        // usarem a divisão 50% insert, 30% remove, 20% peek
        int[] operacoes = new int[n];
        int[] valores = new int[n];
        
        Random rOperacoes = new Random(42);
        Random rValores = new Random(42);
        for(int i = 0; i < n; i++) {
            int v = rOperacoes.nextInt(10);
            if (v < 5) {
                operacoes[i] = 0; // insert
            } else if (v < 8) {
                operacoes[i] = 1; // remove
            } else {
                operacoes[i] = 2; // peek
            }

            valores[i] = rValores.nextInt(n);
        }

        for(int w = 0; w < warmup; w++) {
            rodarHeap(new PQHeap(), operacoes, valores, n);
            rodarTreeMap(new PQTreeMap(), operacoes, valores, n);
        }

        for(int rep = 0; rep < repeticoes; rep++) {
            PQHeap heap = new PQHeap();
            long ini = System.nanoTime();
            rodarHeap(heap, operacoes, valores, n);
            temposHeap[rep] = System.nanoTime() - ini;

            // implemente para TreeMap
            PQTreeMap tree = new PQTreeMap();
            rodarTreeMap(tree, operacoes, valores, n);
            temposTree[rep] = System.nanoTime() - ini; 
        }

        return new TestResults("Carga Pesada", n, temposHeap, temposTree);

    }

    private static void rodarHeap(PQHeap heap, int[] operacoes, int[] valores, int n) {
        for (int i = 0; i < n; i++) {
            switch (operacoes[i]) {
                case 0 -> heap.enqueue(valores[i]);
                case 1 -> {if (!heap.isEmpty()) heap.dequeue();}
                case 2 -> {if (!heap.isEmpty()) heap.peek();}
            }
        }
    }
    
    private static void rodarTreeMap(PQTreeMap tree, int[] operacoes, int[] valores, int n) {
        for (int i = 0; i < n; i++) {
            switch (operacoes[i]) {
                case 0 -> tree.insert(valores[i]);
                case 1 -> {if (!tree.isEmpty()) tree.remove();}
                case 2 -> {if (!tree.isEmpty()) tree.peek();}
            }
        }
    }
}
