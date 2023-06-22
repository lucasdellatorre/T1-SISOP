import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }
    
    public void run() {
        test4();
    }

    public void test1() {
        ArrayList<Request> requests = Util.readFile(new File("./examples/worstfit.txt"));
        Memory mem = new Memory(16);
        new VariablePartition(mem, requests).execute();
    }
    
    public void test2() {
        ArrayList<Request> requests = Util.readFile(new File("./examples/ex1.txt"));
        Memory mem = new Memory(64);
        new VariablePartition(mem, requests).execute();
    }

    public void test3() {
        ArrayList<Request> requests = Util.readFile(new File("./examples/circularfit.txt"));
        Memory mem = new Memory(16, "circular-fit");
        new VariablePartition(mem, requests).execute();
    }
    
    public void test4() {
            ArrayList<Request> requests = Util.readFile(new File("./examples/ex1.txt"));
            BuddyMem mem = new BuddyMem(64);
            for (Request request : requests) {
                    if (request.getCommand() == "IN") {
                        mem.insert(request.getPSize(), request.getPid());
                    } else if (request.getCommand() == "OUT") {
                        mem.remove(request.getPid());
                    } else {
                        System.err.println("VariablePartition: command name error");
                    }
                    mem.print();
                    System.out.println();
            }
    }
}
