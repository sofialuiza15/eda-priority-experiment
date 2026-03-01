package tests;

import implementation.PQHeap;
import implementation.PQTreeMap;
import java.util.Random;

public class TestOnlyPeek {

    /**
     * Executa o teste de inserções puras.
     * Mede tempo de inserir os elementos randomizados.
     *
     * @param n Número de elementos que depois serão determinados randomicamente
     * @param warmup Número de execuções de aquecimento que não têm medição temporal
     * @param repeticoes Número de repetições do teste
     * @param repeticoesPeek Nesse teste em específico mostra-se necessário aumentar as repetições da operação peek, que por ser muito rápida, a medição temporal torna-se muito instável
     * @return TestResults resultado do teste
     */
    public static TestResults executarPeek(int n, int warmup, int repeticoes, int repeticoesPeek) {

        long[] temposHeap = new long[repeticoes];
        long[] temposTree = new long[repeticoes];

        System.out.println("  [TestOnlyPeek] Preparando dados com " + n + " elementos...");
        System.out.println("  Warm-up: " + warmup);

        //conjunto com tamanho fixo de dados gerados de forma randomica
        Random random = new Random(42);
        int[] dados = new int[n];
        for (int i = 0; i < n; i++) {
            dados[i] = random.nextInt(n);
        }

        //warm-up é feito para aquecer o método a ser avaliado para ser analisado em sua capacidade máxima
        //o warm-up não é medido temporalmente

        for (int w = 0; w < warmup; w++) {

            //inicializando e montando a estrutura

            PQHeap heap = new PQHeap();

            for (int i = 0; i < n; i++) {
                heap.enqueue(dados[i]);
            }

            for (int i = 0; i < repeticoesPeek; i++) {
                heap.peek();
            }
        }

        for (int w = 0; w < warmup; w++) {

            //inicializando e montando a estrutura

            PQTreeMap tree = new PQTreeMap();

            for (int i = 0; i < n; i++) {
                tree.insert(dados[i]);
            }

            for (int i = 0; i < repeticoesPeek; i++) {
                tree.peek();
            }
        }

        //etapa de medição temporal do método de inserção

        for (int rep = 0; rep < repeticoes; rep++) {

            System.out.println(" - Repetição " + (rep + 1) + "/" + repeticoes);

            //testando peek em PQHeap com medição temporal
            //inicializando e montando a estrutura
            
            PQHeap heap = new PQHeap();

            for (int i = 0; i < n; i++) {
                heap.enqueue(dados[i]);
            }

            long inicioHeap = System.nanoTime();
            for (int i = 0; i < repeticoesPeek; i++) {
                heap.peek();
            }
            long fimHeap = System.nanoTime();
            temposHeap[rep] = fimHeap - inicioHeap;

            //testando peek em PQTreeMap com medição temporal
            //inicializando e montando a estrutura
            
            PQTreeMap tree = new PQTreeMap();

            for (int i = 0; i < n; i++) {
                tree.insert(dados[i]);
            }

            long inicioTree = System.nanoTime();
            for (int i = 0; i < repeticoesPeek; i++) {
                tree.peek();
            }
            long fimTree = System.nanoTime();
            temposTree[rep] = fimTree - inicioTree;
        }

        return new TestResults("Peek Puro", n, temposHeap, temposTree);
    }
}
