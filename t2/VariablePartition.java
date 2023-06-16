import java.util.ArrayList;

public class VariablePartition {
    private ArrayList<Request> requests;
    private Memory memory;
    
    public VariablePartition(Memory memory, ArrayList<Request> requests) {
        this.memory = memory;
        this.requests = requests;
    }

    public void execute() {
        try {
            for (Request request : requests) {
                if (request.getCommand() == "IN") {
                    memory.alloc(new Process(request.getPid(), request.getPSize()));
                } else if (request.getCommand() == "OUT") {
                    memory.free(request.getPid());
                }
            }
        } catch (InsufficientMemoryException ime) {
            System.err.println(ime.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        memory.printMemoryState();
    }
}
