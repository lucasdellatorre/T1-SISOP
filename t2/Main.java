import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }
    
    public void run() {
        ArrayList<Request> requests = Util.readFile(new File("./examples/worstfit.txt"));
        Memory mem = new Memory(16);
        new VariablePartition(mem, requests).execute();
    }
}
