package tests;

import implementation.PQHeap;
import implementation.PQTreeMap;

import java.util.Random;
import java.util.random.*;
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

        // para Eduarda implementar esses métodos
        for(int w = 0; w < warmup; w++) {
            rodarHeap(new PQHeap(), operacoes, valores, n);
            rodarTreeMap(new PQTreeMap(), operacoes, valores, n);
        }

        for(int rep = 0; rep < repeticoes; rep++) {
            PQHeap heap = new PQHeap();
            long ini = System.nanoTime();
            rodarHeap(heap, operacoes, valores, n);
            temposHeap[rep] = Sytem.nanoTime() - ini;

            // implemente para TreeMap
            
        }


    }
    
}
