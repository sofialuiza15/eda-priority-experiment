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
        
            // cabeçalho com colunas de tempo e memória
            pw.println("cenario,estrutura,tamanho,mediaMs,desvioPadraoMs,mediaMemoriaBytes,desvioPadraoMemoriaBytes");

            for(TestResults r: resultados) {

                // memória pode ser null quando o teste não a mede (ex: Remoção, Peek)
                double mediaMemHeap = (r.memoriasHeap != null) ? r.media(r.memoriasHeap)        : Double.NaN;
                double dpMemHeap    = (r.memoriasHeap != null) ? r.desvioPadrao(r.memoriasHeap)  : Double.NaN;
                double mediaMemTree = (r.memoriasTree != null) ? r.media(r.memoriasTree)         : Double.NaN;
                double dpMemTree    = (r.memoriasTree != null) ? r.desvioPadrao(r.memoriasTree)  : Double.NaN;

                pw.printf(Locale.US, "%s,Heap,%d,%.4f,%.4f,%.0f,%.0f%n",
                        r.cenario, r.tamanho,
                        r.media(r.temposHeap) / 1e6,
                        r.desvioPadrao(r.temposHeap) / 1e6,
                        mediaMemHeap, dpMemHeap);

                pw.printf(Locale.US,"%s,TreeMap,%d,%.4f,%.4f,%.0f,%.0f%n",
                        r.cenario, r.tamanho,
                        r.media(r.temposTree) / 1e6,
                        r.desvioPadrao(r.temposTree) / 1e6,
                        mediaMemTree, dpMemTree);
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
                    long[] tempos   = aux.equals("Heap") ? r.temposHeap   : r.temposTree;
                    long[] memorias = aux.equals("Heap") ? r.memoriasHeap : r.memoriasTree;
                    boolean last = ++cont == total;

                    // memória pode ser null quando o teste não a mede
                    double mediaMem = (memorias != null) ? r.media(memorias)        : Double.NaN;
                    double dpMem    = (memorias != null) ? r.desvioPadrao(memorias) : Double.NaN;

                    pw.printf(Locale.US,
                        " {\"cenario\":\"%s\",\"estrutura\":\"%s\",\"tamanho\":%d,\"mediaMs\":%.4f,\"desvioPadraoMs\":%.4f,\"mediaMemoriaBytes\":%.0f,\"desvioPadraoMemoriaBytes\":%.0f}%s%n",
                        r.cenario, aux, r.tamanho,
                        r.media(tempos) / 1e6,
                        r.desvioPadrao(tempos) / 1e6,
                        mediaMem, dpMem,
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