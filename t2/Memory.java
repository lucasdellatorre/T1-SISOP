import java.util.LinkedList;

public class Memory {
    private LinkedList<Partition> partitions;
    private final int MEMORY_SIZE;
    private int currentMemory;
    private String policy;
    private int nextIndex = 0;

    public Memory(int memorySize) {
        MEMORY_SIZE = memorySize;
        if (!validateMemorySize(MEMORY_SIZE)) throw new Error("Invalid memory size, must be a pow of 2");
        currentMemory = MEMORY_SIZE;
        partitions = new LinkedList<>();
        partitions.add(new Hole(MEMORY_SIZE));
        policy = "worst-fit";
    }

    public Memory(int memSize, String policy) {
        MEMORY_SIZE = memSize;
        if (!validateMemorySize(MEMORY_SIZE)) throw new Error("Invalid memory size, must be a pow of 2");
        currentMemory = MEMORY_SIZE;
        partitions = new LinkedList<>();
        partitions.add(new Hole(MEMORY_SIZE));
        this.policy = policy;
    }

    private boolean validateMemorySize(int x) {
        return (x != 0) && ((x & (x - 1)) == 0);
    }

    public void alloc(Process process) throws InsufficientMemoryException {
        if (process.size > this.currentMemory) {
            throw new InsufficientMemoryException();
        }

        switch (this.policy) {
            case "worst-fit":
                worstFit(process);
                break;
            case "circular-fit":
                circularFit(process);
                break;
            default:
                System.err.println("Memory allocation: Wrong Policy Name");
                break;
        }
    }

    private void worstFit(Process process) {
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

        int holeIndex = partitions.indexOf(worstHole);
    
        worstHole.size -= process.size;
        if (worstHole.size == 0) {
            partitions.remove(worstHole);
        }
        currentMemory -= process.size;
        System.out.println("Process add: " + process + ", currentMem: " + currentMemory);
        partitions.add(holeIndex, process);
    }

    private void circularFit(Process process) {
        int index = nextIndex;
        Hole hole = null;
        System.out.println("index: " + index);
        do {
            Partition partition = partitions.get(index);
            if (partition instanceof Hole) {
                Hole currentHole = (Hole) partition;
                if (currentHole.size >= process.size) {
                    hole = currentHole;
                    break;
                }
            }
            if(index == partitions.size() - 1) {
                index = 0;
            } else {
                index++;
            }
            System.out.println("index: " + index);
        } while (index != nextIndex);

        if (hole == null) {
            System.err.println("Memory: EMPTY HOLE");
            return;
        }

        hole.size -= process.size;
        currentMemory -= process.size;
        System.out.println("Process add: " + process + ", currentMem: " + currentMemory);
        partitions.add(index, process);
        if (hole.size == 0) {
            partitions.remove(hole);
        }
        nextIndex = (index + 1 == partitions.size()) ? 0 : index + 1;
        System.out.println("nextIndex: " + nextIndex);
    }

    public void free(String pid) {
        Process process = null;
        for (Partition partition : partitions) {
            if (partition instanceof Process) {
                Process candidateProcess = (Process) partition;
                if (candidateProcess.pid.equals(pid)) {
                    process = (Process) partition;
                }
            }
        }
        if (process == null) {
            System.err.println("Memory: Process not found");
            return;
        }

        int processIndex = partitions.indexOf(process);
        partitions.remove(process);

        Partition leftPartition = processIndex > 0 ? partitions.get(processIndex - 1) : null;
        Partition rightPartition = processIndex < partitions.size() ? partitions.get(processIndex) : null;

        if (leftPartition instanceof Hole && rightPartition instanceof Hole) {
            Hole leftHole = (Hole) leftPartition;
            Hole rightHole = (Hole) rightPartition;
            leftHole.size += process.size + rightHole.size;
            partitions.remove(rightHole);
            if (processIndex < nextIndex) {
                nextIndex-=2;
            }
        } else if (leftPartition instanceof Hole) {
            Hole leftHole = (Hole) leftPartition;
            leftHole.size += process.size;
            if (processIndex < nextIndex) {
                nextIndex--;
            }
        } else if (rightPartition instanceof Hole) {
            Hole rightHole = (Hole) rightPartition;
            rightHole.size += process.size;
            if (processIndex < nextIndex) {
                nextIndex--;
            }
        } else {
            partitions.add(processIndex, new Hole(process.size));
        }

        currentMemory += process.size;
        System.out.println("Process removed: " + process + ", currentMem: " + currentMemory);
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public void printMemoryState() {
        System.out.println("=============== MEMORY STATE ================");
        partitions.forEach(System.out::println);
        System.out.println("=============================================");
    }
}
