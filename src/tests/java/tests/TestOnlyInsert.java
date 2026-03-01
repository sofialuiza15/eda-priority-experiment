package tests;
import java.util.Random;

import implementation.PQHeap;
import implementation.PQTreeMap;

public class TestOnlyInsert {

    /**
     * Executa o teste de inserções puras.
     * Mede tempo de inserir os elementos randomizados.
     *
     * @param n Número de elementos que depois serão determinados randomicamente
     * @param warmup Número de execuções de aquecimento que não têm medição temporal
     * @param repeticoes Número de repetições do teste
     * @return TestResults resultado do teste
     */
    public static TestResults executarInsert(int n, int warmup, int repeticoes) {

        long[] temposHeap = new long[repeticoes];
        long[] temposTree = new long[repeticoes];
        long[] memoriasHeap = new long[repeticoes];
        long[] memoriasTree = new long[repeticoes];

        System.out.println("[TestOnlyInsert] Preparando dados com " + n + " elementos...");
        System.out.println("Warm-up com " + warmup + " repetições");

        // conjunto com tamanho fixo de dados gerados de forma randomica
        Random random = new Random(42);
        int[] dados = new int[n];
        for (int i = 0; i < n; i++) {
            dados[i] = random.nextInt(n);
        }

        // warm-up é feito para aquecer o método a ser avaliado para ser analisado em sua capacidade máxima
        // o warm-up não é medido temporalmente
        for (int w = 0; w < warmup; w++) {

            //fila de prioridade com heap

            PQHeap heap = new PQHeap();
            for (int i = 0; i < n; i++) {
                heap.enqueue(dados[i]);
            }

            //fila de prioridade com treemap

            PQTreeMap tree = new PQTreeMap();
            for (int i = 0; i < n; i++) {
                tree.insert(dados[i]);
            }
        }

        Runtime rt = Runtime.getRuntime();

        // etapa de medição temporal do método de inserção

        for (int rep = 0; rep < repeticoes; rep++) {

            System.out.println(" - Repetição " + (rep + 1) + "/" + repeticoes);
            
            // ===== Heap =====

            System.gc(); // limpar variáveis sem referência e coisas inúteis
            long antesHeap = rt.totalMemory() - rt.freeMemory(); // medir a memória ocupada atualmente

            //testando inserção com medição temporal

            PQHeap heap = new PQHeap();

            long inicioHeap = System.nanoTime();
            for (int i = 0; i < n; i++) {
                heap.enqueue(dados[i]);
            }
            long fimHeap = System.nanoTime();
            temposHeap[rep] = fimHeap - inicioHeap;
            
            // medição da ocupação de memória da PQHeap com ela já populada acima, mede o estado atual.
            System.gc();
            long depoisHeap = rt.totalMemory() - rt.freeMemory(); // memória ocupada depois da operação de inserção
            
            memoriasHeap[rep] = depoisHeap - antesHeap; // memória efetivamente utilizada na operação de inserção com n elementos

            // descarta para liberar antes de medir a Tree
            heap = null;

            // ===== TreeMap =====

            System.gc();
            long antesTree = rt.totalMemory() - rt.freeMemory();

            //testando inserção com medição temporal

            PQTreeMap tree = new PQTreeMap();

            long inicioTree = System.nanoTime();
            for (int i = 0; i < n; i++) {
                tree.insert(dados[i]);
            }
            long fimTree = System.nanoTime();
            temposTree[rep] = fimTree - inicioTree;

            System.gc();
            long depoisTree = rt.totalMemory() - rt.freeMemory();

            // medição da ocupação de memória da PQTreeMap com ela já populada acima, mede o estado atual.
            memoriasTree[rep] = depoisTree - antesTree;
            tree = null;
        }

        return new TestResults("Inserção Pura", n, temposHeap, temposTree, memoriasHeap, memoriasTree);
    }
}