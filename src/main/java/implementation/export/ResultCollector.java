package implementation.export;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultCollector {

    private final List<BenchmarkResult> results = new ArrayList<>();

    public void record(String cenario, String estrutura, int tamanho, long elapsedNanos) {
        results.add(new BenchmarkResult(cenario, estrutura, tamanho, elapsedNanos));
    }

    public List<BenchmarkResult> getResults() {
        return Collections.unmodifiableList(results);
    }

    public void clear() {
        results.clear();
    }

    public int size() {
        return results.size();
    }

    public void imprimirResumo() {
        System.out.println("Total de resultados coletados: " + results.size());
        for (BenchmarkResult r : results) {
            System.out.println(r.toString());
        }
    }
}