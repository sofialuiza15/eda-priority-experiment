package tests;

import implementation.export.ResultCollector;
import implementation.export.ExporterCSV;
import implementation.export.ExporterJSON;

public class ExperimentRunner {

    static final int[] TAMANHOS       = {1_000, 10_000, 100_000, 1_000_000};
    static final int   WARMUP         = 3;
    static final int   REPETICOES     = 5;
    static final int   REPETICOES_PEEK = 10_000;

    public static void main(String[] args) throws Exception {

        ResultCollector collector = new ResultCollector();

        for (int n : TAMANHOS) {
            System.out.println("\n Tamanho: " + n);

            // chama cada teste exatamente como estão, sem modificação
            coletarResultado(collector, TestOnlyInsert.executarInsert(n, WARMUP, REPETICOES));
            coletarResultado(collector, TestOnlyRemove.executarRemove(n, WARMUP, REPETICOES));
            coletarResultado(collector, TestOnlyPeek.executarPeek(n, WARMUP, REPETICOES, REPETICOES_PEEK));
            coletarResultado(collector, TestBothInsertRemove.executarMisto(n, WARMUP, REPETICOES));
            coletarResultado(collector, TestHeavyLoad.executeHeavyLoad(n, WARMUP, REPETICOES));
        }

        // exporta tudo
        new java.io.File("results/").mkdirs();
        new ExporterCSV().export(collector.getResults(),  "results/benchmark.csv");
        new ExporterJSON().export(collector.getResults(), "results/benchmark.json");

        System.out.println("\nExportado! Total de registros: " + collector.size());
        collector.imprimirResumo();
    }

    // extrai os dados do TestResults e registra no collector
    private static void coletarResultado(ResultCollector collector, TestResults resultado) {
        for (int i = 0; i < resultado.temposHeap.length; i++) {
            collector.record(resultado.cenario, "PQHeap",    resultado.tamanho, resultado.temposHeap[i]);
            collector.record(resultado.cenario, "PQTreeMap", resultado.tamanho, resultado.temposTree[i]);
        }
    }
}
