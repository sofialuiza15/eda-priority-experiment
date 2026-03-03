package tests;

import java.util.Arrays;

public class TestResults {

    public final String cenario;
    public final int tamanho;
    public final long[] temposHeap;
    public final long[] temposTree;
    public final long[] memoriasHeap; 
    public final long[] memoriasTree; 

    public TestResults(String cenario, int tamanho, long[] temposHeap, long[] temposTree) {
        this(cenario, tamanho, temposHeap, temposTree, null, null);
    }

    public TestResults(String cenario, int tamanho, long[] temposHeap, long[] temposTree, long[] memoriasHeap, long[] memoriasTree) {
        this.cenario = cenario;
        this.tamanho = tamanho;
        this.temposHeap = temposHeap;
        this.temposTree = temposTree;
        this.memoriasHeap = memoriasHeap;
        this.memoriasTree = memoriasTree;
    }
    
    public double media(long[] valores) {
        if (valores.length == 0) return 0;  

        long soma = 0;
        for (long v : valores) {
            soma += v;                       
        }
        return (double) soma / valores.length;
    }

    public double desvioPadrao(long[] valores) {
        double m = media(valores);
        double soma = 0;
        for (long v : valores) soma += (v - m) * (v - m);
        return Math.sqrt(soma / (valores.length - 1));
    }
}
