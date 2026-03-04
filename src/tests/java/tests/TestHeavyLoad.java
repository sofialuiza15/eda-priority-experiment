package tests;

import implementation.PQHeap;
import implementation.PQTreeMap;

import java.util.Random;
public class TestHeavyLoad {

    /**
     * Executa os testes de "carga pesada"
     * Testes reais com entradas gravadas
     * 
     * @param n Número de elementos que depois serão determinados randomicamente
     * @param warmup Número de execuções de aquecimento que não têm medição temporal
     * @param repeticoes Número de repetições do teste
     * @return
     */
    public static TestResults executeHeavyLoad(int n, int warmup, int repetitions) {

        long[] temposHeap = new long[repetitions];
        long[] temposTree = new long[repetitions];
        
        // Precisamos desses dados para impedir que o custo do random seja contabilizado
        // usaremos a divisão 50% insert, 30% remove, 20% peek
        int[] operations = new int[n];
        int[] values = new int[n];
        
        // Conjunto com tamanho fixo de dados gerados de forma randomica
        Random rOperations = new Random(42);
        Random rValues = new Random(42);
        for(int i = 0; i < n; i++) {
            int v = rOperations.nextInt(10);
            if (v < 5) {
                operations[i] = 0; // insert, números de 0 a 4
            } else if (v < 8) {
                operations[i] = 1; // remove, números de 5 a 7
            } else {
                operations[i] = 2; // peek, números de 8 a 9
            }

            values[i] = rValues.nextInt(n);
        }

        // Aquecimento do warmup
        for(int w = 0; w < warmup; w++) {
            rodarHeap(new PQHeap(), operations, values, n);
            rodarTreeMap(new PQTreeMap(), operations, values, n);
        }

        // Aqui o tempo do Heap e da TreeMap começam a ser gravados
        // O warmup não é medido temporariamente 
        for(int rep = 0; rep < repetitions; rep++) {
            PQHeap heap = new PQHeap();
            long ini = System.nanoTime();
            rodarHeap(heap, operations, values, n);
            temposHeap[rep] = System.nanoTime() - ini;

            PQTreeMap tree = new PQTreeMap();
            long iniTree = System.nanoTime();
            rodarTreeMap(tree, operations, values, n);
            temposTree[rep] = System.nanoTime() - iniTree; 
        }

        return new TestResults("Carga Pesada", n, temposHeap, temposTree);

    }

    /**
     * Método interno para analisar o caso que deve operar
     * 
     * @param heap Estrutura a ser comparada
     * @param operations Array de operações guardadas
     * @param values Array de valores guardados
     * @param n Número de repetições do teste
     */
    private static void rodarHeap(PQHeap heap, int[] operations, int[] values, int n) {
        for (int i = 0; i < n; i++) {
            switch (operations[i]) {
                case 0 -> heap.enqueue(values[i]);
                case 1 -> {if (!heap.isEmpty()) heap.dequeue();}
                case 2 -> {if (!heap.isEmpty()) heap.peek();}
            }
        }
    }
    
    /**
     * Método interno para analisar o caso que deve operar
     * 
     * @param TreeMap Estrutura a ser comparada
     * @param operations Array de operações guardadas
     * @param values Array de valores guardados
     * @param n Número de repetições do teste
     */
    private static void rodarTreeMap(PQTreeMap tree, int[] operations, int[] values, int n) {
        for (int i = 0; i < n; i++) {
            switch (operations[i]) {
                case 0 -> tree.insert(values[i]);
                case 1 -> {if (!tree.isEmpty()) tree.remove();}
                case 2 -> {if (!tree.isEmpty()) tree.peek();}
            }
        }
    }
}
