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
        ArrayList<Request> requests = Util.readFile(new File("./examples/circularfit.txt"));
        Buddy bud = new Buddy(16);
        bud.allocate(8);
    }
}
