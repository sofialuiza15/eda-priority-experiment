package implementation.export;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ExporterJSON implements ExporterResult {
    @Override
    public void export(List<BenchmarkResult> results, String filePath) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("[");
            for (int i = 0; i < results.size(); i++) {
                BenchmarkResult r = results.get(i);
                pw.printf(
                    "  {\"testName\":\"%s\",\"structure\":\"%s\"," +
                    "\"dataSize\":%d,\"elapsedMs\":%.4f}%s%n",
                    r.getTestName(),
                    r.getStructure(),
                    r.getDataSize(),
                    r.getMsSec(),
                    i < results.size() - 1 ? "," : ""
                );
                pw.println("]");
            }
        }
    }
    
}
