import java.util.LinkedList;

public class Memory {
    private LinkedList<Partition> partitions;
    private final int MEMORY_SIZE;
    private int currentMemory;
    private String policy;

    abstract class Partition { int size; }

    private class Process extends Partition {
        public int size;
        public String pid;

        public Process(int size, String pid) {
            this.size = size;
            this.pid = pid;
        }
    }

    private class Hole extends Partition {
        public int size;

        public Hole(int size) {
            this.size = size;
        }
    }

    public Memory (int memorySize) {
        MEMORY_SIZE = memorySize;
        partitions = new LinkedList<>();
        partitions.add(new Hole(MEMORY_SIZE));
        policy = "worst-fit";
    }

    public Memory (int memSize, String policy) {
        MEMORY_SIZE = memSize;
        partitions = new LinkedList<>();
        partitions.add(new Hole(MEMORY_SIZE));
        this.policy = policy;
    }

    public void alloc(String pid, int size) {

    }

    public void free(String pid) {

    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }
}
