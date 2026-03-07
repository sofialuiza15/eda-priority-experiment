package tests;

import java.util.Random;

import implementation.PQHeap;
import implementation.PQTreeMap;

public class TestBothInsertRemove {

    /**
     * Executa o teste de operações mistas (inserção + remoção).
     * Simula uso real: popula metade da fila inicialmente e depois
     * intercala inserções e remoções de forma equilibrada (50/50),
     * refletindo sistemas onde novos elementos chegam enquanto outros
     * são processados continuamente, como no nosso plano de projeto.
     *
     * @param n Número de operações mistas (cada operação = 1 insert + 1 remove)
     * @param warmup Número de execuções de aquecimento sem medição temporal
     * @param repeticoes Número de repetições do teste
     * @return TestResults resultado do teste
     */
    public static TestResults executarMisto(int n, int warmup, int repeticoes) {

        long[] temposHeap = new long[repeticoes];
        long[] temposTree = new long[repeticoes];

        System.out.println("[TestBothInsertRemove] Preparando dados com " + n + " elementos...");
        System.out.println("Warm-up com " + warmup + " repetições");

        // semente fixa garante reprodutibilidade
        Random random = new Random(42);

        // dados para a população inicial (metade da fila)
        int metade = n / 2;
        int[] dadosIniciais = new int[metade];
        for (int i = 0; i < metade; i++) {
            dadosIniciais[i] = random.nextInt(n);
        }

        // dados para as operações mistas
        int[] dadosMistos = new int[n];
        for (int i = 0; i < n; i++) {
            dadosMistos[i] = random.nextInt(n);
        }

        // warm-up: mesma lógica do teste, sem medir tempo
        for (int w = 0; w < warmup; w++) {

            // heap: população inicial fora da medição
            PQHeap heap = new PQHeap();
            for (int i = 0; i < metade; i++) {
                heap.enqueue(dadosIniciais[i]);
            }
            // operações mistas intercaladas
            for (int i = 0; i < n; i++) {
                heap.enqueue(dadosMistos[i]);
                if (!heap.isEmpty()) {
                    heap.dequeue();
                }
            }

            // treemap: população inicial fora da medição
            PQTreeMap tree = new PQTreeMap();
            for (int i = 0; i < metade; i++) {
                tree.insert(dadosIniciais[i]);
            }
            // operações mistas intercaladas
            for (int i = 0; i < n; i++) {
                tree.insert(dadosMistos[i]);
                if (!tree.isEmpty()) {
                    tree.remove();
                }
            }
        }

        // medição real
        for (int rep = 0; rep < repeticoes; rep++) {

            System.out.println(" - Repetição " + (rep + 1) + "/" + repeticoes);

            // --- PQHeap ---
            // população inicial fora da medição (não faz parte do cenário avaliado)
            PQHeap heap = new PQHeap();
            for (int i = 0; i < metade; i++) {
                heap.enqueue(dadosIniciais[i]);
            }

            long inicioHeap = System.nanoTime();
            for (int i = 0; i < n; i++) {
                heap.enqueue(dadosMistos[i]);
                if (!heap.isEmpty()) {
                    heap.dequeue();
                }
            }
            long fimHeap = System.nanoTime();
            temposHeap[rep] = fimHeap - inicioHeap;

            // --- PQTreeMap ---
            // população inicial fora da medição
            PQTreeMap tree = new PQTreeMap();
            for (int i = 0; i < metade; i++) {
                tree.insert(dadosIniciais[i]);
            }

            long inicioTree = System.nanoTime();
            for (int i = 0; i < n; i++) {
                tree.insert(dadosMistos[i]);
                if (!tree.isEmpty()) {
                    tree.remove();
                }
            }
            long fimTree = System.nanoTime();
            temposTree[rep] = fimTree - inicioTree;
        }

        return new TestResults("Operações Mistas", n, temposHeap, temposTree);
    }
}
