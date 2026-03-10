package tests;

import export.Exporter;
import java.util.ArrayList;
import java.util.List;

public class Runner{

    // Configuração dos experimentos

    // Tamanhos de entrada a serem testados
    static final int[] TAMANHOS = { 1_000, 10_000, 100_000, 1_000_000, 10_000_000 };

    //Repetições de warm-up (sem medição) 
    static final int WARMUP = 5;

    // Repetições com medição real(devemos mudar essa quantidade, mas pra quanto?) 
    static final int REPETICOES = 10;

    /**
     * Repetições internas do peek (operação O(1) muito rápida;
     * repetir muitas vezes reduz ruído estatístico).
     */
    static final int REPETICOES_PEEK = 10_000_000;

    public static void main(String[] args) {

        printBanner();

        List<TestResults> todos = new ArrayList<>();

        for (int n : TAMANHOS) {
            printSeparador('=', 70);
            System.out.printf("  TAMANHO DE ENTRADA: %,d elementos%n", n);
            printSeparador('=', 70);

            TestResults insert = TestOnlyInsert.executarInsert(n, WARMUP, REPETICOES);
            rodar("1. Inserção Pura", insert);
            todos.add(insert);

            TestResults remove = TestOnlyRemove.executarRemove(n, WARMUP, REPETICOES);
            rodar("2. Remoção Pura", remove);
            todos.add(remove);

            TestResults peek = TestOnlyPeek.executarPeek(n, WARMUP, REPETICOES, REPETICOES_PEEK);
            rodar("3. Peek Puro", peek);
            todos.add(peek);

            TestResults misto = TestBothInsertRemove.executarMisto(n, WARMUP, REPETICOES);
            rodar("4. Misto (Insert + Remove)", misto);
            todos.add(misto);

            TestResults carga = TestHeavyLoad.executeHeavyLoad(n, WARMUP, REPETICOES);
            rodar("5. Carga Pesada (50% insert / 30% remove / 20% peek)", carga);
            todos.add(carga);
        }

        // Exportação dos resultados 
        Exporter exporter = new Exporter();
        exporter.paraCSV(todos, "results/benchmark.csv");
        exporter.paraJSON(todos, "results/benchmark.json");
        System.out.println("  Resultados exportados para results/benchmark.csv e results/benchmark.json");

        printSeparador('=', 70);
        System.out.println("  Todos os experimentos concluídos.");
        printSeparador('=', 70);
    }

    // Impressão dos resultados
    private static void rodar(String titulo, TestResults r) {
        printSeparador('-', 70);
        System.out.printf("  Teste: %s%n", titulo);
        printSeparador('-', 70);

        imprimirTempo(r);

        if (r.memoriasHeap != null && r.memoriasTree != null) {
            imprimirMemoria(r);
        }

        imprimirVencedor(r);
        System.out.println();
    }

    // Exibe média e desvio-padrão do tempo em ms para ambas as estruturas.
    private static void imprimirTempo(TestResults r) {
        double mediaHeap = r.media(r.temposHeap)        / 1_000_000.0;
        double dpHeap    = r.desvioPadrao(r.temposHeap) / 1_000_000.0;
        double mediaTree = r.media(r.temposTree)        / 1_000_000.0;
        double dpTree    = r.desvioPadrao(r.temposTree) / 1_000_000.0;

        System.out.println("  TEMPO (ms)");
        System.out.printf("    %-12s  média: %10.3f ms  ± %.3f ms%n", "PQHeap",    mediaHeap, dpHeap);
        System.out.printf("    %-12s  média: %10.3f ms  ± %.3f ms%n", "PQTreeMap", mediaTree, dpTree);
    }

    // Exibe uso médio de memória (bytes) para ambas as estruturas.
    private static void imprimirMemoria(TestResults r) {
        double mediaHeap = r.media(r.memoriasHeap);
        double dpHeap    = r.desvioPadrao(r.memoriasHeap);
        double mediaTree = r.media(r.memoriasTree);
        double dpTree    = r.desvioPadrao(r.memoriasTree);

        System.out.println("  MEMÓRIA (bytes)");
        System.out.printf("    %-12s  média: %,14.0f bytes  ± %,.0f%n", "PQHeap",    mediaHeap, dpHeap);
        System.out.printf("    %-12s  média: %,14.0f bytes  ± %,.0f%n", "PQTreeMap", mediaTree, dpTree);
    }

    // Calcula e exibe qual estrutura foi mais rápida e por quanto. 
    private static void imprimirVencedor(TestResults r) {
        double mH = r.media(r.temposHeap);
        double mT = r.media(r.temposTree);

        if (mH < mT) {
            System.out.printf("  ► PQHeap foi mais rápido(a) em %.1f%%%n", (mT - mH) / mT * 100.0);
        } else if (mT < mH) {
            System.out.printf("  ► PQTreeMap foi mais rápido(a) em %.1f%%%n", (mH - mT) / mH * 100.0);
        } else {
            System.out.printf("  ► Empate técnico%n");
        }
    }

    // Utilitários de formatação

    private static void printBanner() {
        printSeparador('*', 70);
        System.out.println("  EDA — Experimento: Fila de Prioridade");
        System.out.println("  PQHeap  vs  PQTreeMap");
        StringBuilder tamanhos = new StringBuilder();
        for (int i = 0; i < TAMANHOS.length; i++) {
            if (i > 0) tamanhos.append(", ");
            tamanhos.append(String.format("%,d", TAMANHOS[i]));
        }
        System.out.printf("  Tamanhos: %s%n", tamanhos);
        System.out.printf("  Warm-up: %d  |  Repetições: %d  |  Peek-reps: %,d%n",
                WARMUP, REPETICOES, REPETICOES_PEEK);
        printSeparador('*', 70);
        System.out.println();
    }

    private static void printSeparador(char c, int len) {
        System.out.println(String.valueOf(c).repeat(len));
    }
}