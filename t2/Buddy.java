import java.util.ArrayList;

public class Buddy{
    private ArrayList<Request> requests;
    private BuddyMem memory;
    
    public Buddy(BuddyMem memory, ArrayList<Request> requests) {
        this.memory = memory;
        this.requests = requests;
    }

    public void execute() {
        try {
            for (Request request : requests) {
                if (request.getCommand() == "IN") {
                    memory.insert(request.getPSize(), request.getPid());
                } else if (request.getCommand() == "OUT") {
                    memory.remove(request.getPid());
                } else {
                    System.err.println("Buddy: command name error");
                }
                memory.printMemoryState();
            }
        } catch (InsufficientMemoryException ime) {
            System.err.println(ime.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
