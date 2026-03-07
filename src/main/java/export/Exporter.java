package export;

import tests.TestResults;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

public class Exporter {

    public void paraCSV(List<TestResults> resultados, String caminho) {
        File arquivo = new File(caminho);
        File pai = arquivo.getParentFile();

        if(pai != null && !pai.exists()) pai.mkdirs();
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {   
        
        pw.println("cenario,estrutura,tamanho,mediaMs,desvioPadraoMs");
        for(TestResults r: resultados) {
                    pw.printf(Locale.US, "%s,Heap,%d,%.4f,%.4f%n",
                        r.cenario, r.tamanho,
                        r.media(r.temposHeap) / 1e6,
                        r.desvioPadrao(r.temposHeap) / 1e6
                );
                    pw.printf(Locale.US,"%s,TreeMap,%d,%.4f,%.4f%n",
                        r.cenario, r.tamanho, r.media(r.temposTree) / 1e6,
                        r.desvioPadrao(r.temposTree) / 1e6
                    );
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paraJSON(List<TestResults> resultados, String caminho) throws IOException {
        File arquivo = new File(caminho);
        File pai = arquivo.getParentFile();

        if (pai != null && !pai.exists()) pai.mkdirs();
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
            pw.println("[");
            int total = resultados.size() * 2;
            int cont = 0;
            for (TestResults r : resultados) {
                for (String aux : new String[]{"Heap", "TreeMap"}) {
                    long[] tempos = aux.equals("Heap") ? r.temposHeap : r.temposTree;
                    boolean last = ++cont == total;
                    pw.printf(Locale.US,
                        " {\"cenario\":\"%s\",\"estrutura\":\"%s\",\"tamanho\":%d,\"mediaMs\":%.4f,\"desvioPadraoMs\":%.4f}%s%n",
                        r.cenario, aux, r.tamanho,
                        r.media(tempos) / 1e6,
                        r.desvioPadrao(tempos) / 1e6,
                        last ? "" : ","
                    );
                }
            }
            pw.println("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
