package implementation.export;

import java.io.IOException;
import java.util.List;

public interface ExporterResult {
     
    void export(List<BenchmarkResult> results, String filePath) throws IOException;

}
