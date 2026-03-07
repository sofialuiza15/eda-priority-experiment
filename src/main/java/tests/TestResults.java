package tests;

import java.util.Arrays;

public class TestResults {

    public final String cenario;
    public final int tamanho;
    public final long[] temposHeap;
    public final long[] temposTree;
    public final long[] memoriasHeap; 
    public final long[] memoriasTree; 

    /**
     * Construtor sem memória, usado quando apenas o tempo é medido. Inicializa o cenário, tamanho
     * e vetores de tempo. Os vetores de memória são mantidos null.
     * @param cenario
     * @param tamanho
     * @param temposHeap
     * @param temposTree
     */
    public TestResults(String cenario, int tamanho, long[] temposHeap, long[] temposTree) {
        this.cenario = cenario;
        this.tamanho = tamanho;
        this.temposHeap = temposHeap;
        this.temposTree = temposTree;
        this.memoriasHeap = null;
        this.memoriasTree = null;
    }

    /**
     * Construtor com memória, usado quando tempos e memória são medidos.
     * @param cenario
     * @param tamanho
     * @param temposHeap
     * @param temposTree
     * @param memoriasHeap
     * @param memoriasTree
     */
    public TestResults(String cenario, int tamanho, long[] temposHeap, long[] temposTree, long[] memoriasHeap, long[] memoriasTree) {
        this.cenario = cenario;
        this.tamanho = tamanho;
        this.temposHeap = temposHeap;
        this.temposTree = temposTree;
        this.memoriasHeap = memoriasHeap;
        this.memoriasTree = memoriasTree;
    }
    
    /**
     * Calcula uma média aritmétrica de um vetor com elementos do tipo long.
     * @param valores
     * @return
     */
    public double media(long[] valores) {
        if (valores.length == 0) return 0;  

        long soma = 0;
        for (long v : valores) {
            soma += v;                       
        }
        return (double) soma / valores.length;
    }

    /**
     * Mede quanto os valores variam em relação a média.
     * @param valores
     * @return
     */
    public double desvioPadrao(long[] valores) {
        double m = media(valores);
        double soma = 0;
        for (long v : valores) soma += (v - m) * (v - m);
        return Math.sqrt(soma / (valores.length - 1));
    }
}
