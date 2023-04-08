import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }
    
    public void run() {
        Scanner in = new Scanner(System.in);

        String politica = setPolitica(in);
        
        boolean next = true;
        do {
            System.out.print("Adicione o nome do arquivo que contem o processo: ");
            String process = in.nextLine();
            System.out.print("Qual o instante de tempo em que ele chega: ");
            int instante = leInteiro(in);        
            System.out.print("Voce gostaria de adicionar mais um arquivo (S/N): "); 
            String simNao = in.nextLine().toLowerCase();
            while (!validaSimENao(simNao)) {
                System.out.print("String invalida, digite apenas S ou N.");
                simNao = in.nextLine().toLowerCase();
            }
            next = simNao.equals("s") || simNao.equals("sim");
        } while (next);
    }

    public String setPolitica(Scanner in) {
        System.out.print("Qual politica de escalonamento voce gostaria de usar (RR ou SJF): ");
        String politica = in.nextLine();
        if(!validaPolitica(politica)) {
            System.out.println("Politica digitada nao existe, por favor digite uma válida");
            return setPolitica(in);
        }
        return politica;
    }

    private boolean validaPolitica(String politica) {
        politica = politica.toLowerCase();
        return politica.equals("rr") || politica.equals("sjf");
    }

    private boolean validaSimENao(String str) {
        str = str.toLowerCase();
        return str.equals("s") || str.equals("n") || str.equals("sim") || str.equals("nao") || str.equals("não");
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