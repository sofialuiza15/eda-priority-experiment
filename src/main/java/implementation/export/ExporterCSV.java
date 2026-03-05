package implementation.export;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ExporterCSV implements ExporterResult {
    
    @Override
    public void export(List<BenchmarkResult> results, String filePath) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("testName,structure,datasize,msSec");
            for (BenchmarkResult r : results) {
                pw.printf("%s,%s,%d,%.4f%n",
                    r.getTestName(),
                    r.getStructure(),
                    r.getDataSize(),
                    r.getMsSec());
            }
        }
    }
}
