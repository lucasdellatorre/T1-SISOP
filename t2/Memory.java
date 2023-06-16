import java.util.LinkedList;

public class Memory {
    private LinkedList<Partition> partitions;
    private final int MEMORY_SIZE;
    private int currentMemory;
    private String policy;

    public Memory (int memorySize) {
        MEMORY_SIZE = memorySize;
        currentMemory = MEMORY_SIZE;
        partitions = new LinkedList<>();
        partitions.add(new Hole(MEMORY_SIZE));
        policy = "worst-fit";
    }

    public Memory (int memSize, String policy) {
        MEMORY_SIZE = memSize;
        currentMemory = MEMORY_SIZE;
        partitions = new LinkedList<>();
        partitions.add(new Hole(MEMORY_SIZE));
        this.policy = policy;
    }

    public void alloc(Process process) throws InsufficientMemoryException {
        System.out.println("process.pid" + process.pid);
        if (process.size > this.currentMemory ) {
            throw new InsufficientMemoryException();
        }

        int worstPartitionSize = 0;
        Hole worstHole = null;

        for (Partition partition : partitions) {
            if (partition instanceof Hole) {
                Hole hole = (Hole) partition;
                if (hole.size > worstPartitionSize) {
                    worstPartitionSize = hole.size;
                    worstHole = hole;
                }
            }
        }

        if (worstHole == null) {
            System.err.println("Memory: EMPTY HOLE");
            return;
        }

        worstHole.size -= process.size;
        currentMemory -= process.size;
        partitions.add(process);
    }

    public void free(String pid) {

    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public void printMemoryState() {
        partitions.forEach(System.out::println);
    }
}
