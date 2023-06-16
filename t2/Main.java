import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }
    
    public void run() {
        ArrayList<Request> requests = Util.readFile(new File("./examples/ex1.txt"));
        requests.forEach(System.out::println);
    }
}
