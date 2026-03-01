package tests;

import implementation.PQHeap;
import implementation.PQTreeMap;
import java.util.Random;

// os imports estavam errados, to consertando a estrutura !! sofia: vê se deu certo
public class TestOnlyRemove {
    
    /**
     * Executa o teste de remoções puras.
     * Prepara estruturas cheias e mede apenas o tempo de remover todos os elementos.
     * 
     * @param n Número de elementos a serem removidos
     * @param repeticoes Número de repetições do teste
     * @return ResultadoTeste com estatísticas de tempo
     */
    public static TestResults executar(int n, int repeticoes) {
        long[] temposHeap = new long[repeticoes];
        long[] temposTree = new long[repeticoes];
        
        System.out.println("  [TestOnlyRemove] Preparando dados com " + n + " elementos...");
        
        // gerar conjunto de dados fixo (mesmos valores para ambos os testes)
        Random random = new Random(42); // seed fixo para reprodutibilidade

        int[] dados = new int[n];
        for (int i = 0; i < n; i++) {
            dados[i] = random.nextInt(n);
        }
        
        for (int rep = 0; rep < repeticoes; rep++) {
            System.out.println("    Repetição " + (rep + 1) + "/" + repeticoes);
            
            //testando o heap
            PQHeap heap = new PQHeap();
            
            // Preparando a estrutura 
            for (int i = 0; i < n; i++) {
                heap.enqueue(dados[i]);
            }
            
            // mede o tempo de remoção
            long inicioHeap = System.nanoTime();
            while (!heap.isEmpty()) {
                heap.dequeue();
            }
            long fimHeap = System.nanoTime();
            temposHeap[rep] = fimHeap - inicioHeap;
            
//----------------- separando pra entender melhor
            // teste com treemap
            PQTreeMap tree = new PQTreeMap();
            
            // Preparar estrutura (FORA da medição)
            for (int i = 0; i < n; i++) {
                tree.insert(dados[i]);
            }
        
            // mede o tempo de remoção
            long inicioTree = System.nanoTime();
            while (tree.size() > 0) {
                tree.remove();
            }
            long fimTree = System.nanoTime();
            temposTree[rep] = fimTree - inicioTree;
        }
        
        return new TestResults("Remoção Pura", n, temposHeap, temposTree);
    }
}