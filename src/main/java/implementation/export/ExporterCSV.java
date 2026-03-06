package implementation.export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

public class ExporterCSV implements ExporterResult {
    
    @Override
    public void export(List<BenchmarkResult> results, String filePath) throws IOException {
        // 1. Garantir que a pasta 'results' existe antes de criar o arquivo
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        // 2. O try-with-resources abre e fecha o arquivo automaticamente
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            // Cabeçalho
            pw.println("testName,structure,datasize,msSec"); 
            
            for (BenchmarkResult r : results) {
                // Locale.US impede que o número saia com vírgula (ex: 0,75), o que quebraria o Python
                pw.printf(Locale.US, "%s,%s,%d,%.4f%n",
                    r.getTestName(),
                    r.getStructure(),
                    r.getDataSize(),
                    r.getMsSec());
            }
            pw.flush(); // Garante que os dados saiam do buffer para o disco
        }
        // O PrintWriter não precisa de catch aqui porque o método já declara 'throws IOException'
    }
}