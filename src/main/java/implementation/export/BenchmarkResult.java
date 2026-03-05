package implementation.export;

public class BenchmarkResult {
    private final String testName;
    private final String structure; 
    private final int dataSize; 
    private final long nanoSec; 

    public BenchmarkResult(String testName, String structure, int dataSize, long nanoSec){
            this.testName = testName;
            this.structure = structure;
            this.dataSize = dataSize;
            this.nanoSec = nanoSec;
    }
    
    public String getTestName() {
        return testName;
    }

    public String getStructure() {
        return structure;
    }

    public int getDataSize() {
        return dataSize;
    }

    public long getNanoSec() {
        return nanoSec;
    }

    public double getMsSec() {
        return nanoSec / 1000000.0;
    }

    @Override
    public String toString(){
        return String.format("[%s | %s | size = %d | %.4f ms]",
        testName, structure, dataSize, getMsSec());
    }
}
