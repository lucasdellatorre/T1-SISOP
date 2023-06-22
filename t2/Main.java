import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }
    
    public void run() {
        Scanner in = new Scanner(System.in);
        boolean next = true;
        System.out.println("***************************************************************");
        System.out.println("            Bem vindo ao Simulador de alocacao de memoria. ");
        System.out.println("***************************************************************");
        do {
            System.out.println("Digite o caminho do arquivo (exemplo: ./examples/worstfit.txt): ");
            String caminho = in.nextLine();
            ArrayList<Request> requests = Util.readFile(new File(caminho));
            System.out.println("Digite o tamanho da memoria: ");
            int memorySize = leInteiro(in);
            if (!Util.isPowerOfTwo(memorySize)) {
                System.out.println("Invalid memory size, must be a power of 2");
                System.exit(1);
            }
            System.out.println("Digite a politica de alocacao ( worst-fit | circular-fit | buddy ): ");
            String politica = in.nextLine().toLowerCase().trim();
            if (politica.equals("buddy")) {
                new Buddy(new BuddyMem(memorySize), requests).execute();
            } else {
                Memory mem = new Memory(memorySize, politica);
                new VariablePartition(mem, requests).execute();
            }
            System.out.print("Voce gostaria de executar mais uma vez?(S/N): ");
            String simNao = in.nextLine().toLowerCase();
            next = simNao.equals("s") || simNao.equals("sim");
        } while (next);
        System.out.println("Até breve.");
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

    private int leInteiro(Scanner in) {
        int numero = -1;
        boolean ok = false;
        while (!ok) {
            try {
                numero = in.nextInt();
                ok = true;
            } catch (Exception e) {
                System.out.print("Entrada invalida. Digite um numero inteiro: ");
            } finally {
                in.nextLine();
            }
        }
        return numero;
    }
}
