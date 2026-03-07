package export;

import tests.TestResults;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

/**Classe responsável por exportar os dados obtidos através dos testes 
para arquivos CSV e JSON, a fim de possibilitar plotagem dos gráficos"
*/
public class Exporter {

    /**
     * Método responsável por exportar os dados em formato .csv
     * @param resultados representa uma lista com todos os testes armazenados 
     * @param caminho o caminho para a criação do arquivo contendo os resultados
     * exportados para o formato solicitado
     */
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

    /**
     * Método responsável por exportar os dados em formato .json
     * @param resultados representa uma lista com todos os testes armazenados
     * @param caminho o caminho para a criação do arquivo contendo os resultados
     * exportados para o formato solicitado
     */
    public void paraJSON(List<TestResults> resultados, String caminho) {
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
