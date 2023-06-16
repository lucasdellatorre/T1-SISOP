import java.util.ArrayList;

public class VariablePartition {
    private ArrayList<Request> requests;
    private Memory memory;
    
    public VariablePartition(Memory memory, ArrayList<Request> requests) {
        this.memory = memory;
        this.requests = requests;
    }

    public void execute() {
        for (Request request : requests) {
            if (request.getCommand() == "IN") {
                memory.alloc(request.getPid(), request.getPSize());
            } else if (request.getCommand() == "OUT") {
                memory.free(request.getPid());
            }
        }
    }
}
