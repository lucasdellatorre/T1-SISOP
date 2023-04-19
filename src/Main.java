import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        LinkedList<Processo> ll = new LinkedList<Processo>();

        boolean next = true;
        do {

            System.out.println("***************************************************************");
            System.out.println("            Bem vindo ao Simulador de Escalonamento. ");
            System.out.println("***************************************************************");
            System.out.println("- Adicione um novo processo");
            do {
                System.out.println("Digite o caminho do arquivo: ");
                String caminho = in.nextLine();
                System.out.println("Digite o tempo de chegada: ");
                int tempoChegada = leInteiro(in);
                ll.add(new Processo(tempoChegada, new File(caminho)));
                System.out.print("Voce gostaria de adicionar mais um arquivo (S/N): ");
                String simNao = in.nextLine().toLowerCase();
                while (!validaSimENao(simNao)) {
                    System.out.print("String invalida, digite apenas S ou N.");
                    simNao = in.nextLine().toLowerCase();
                }
                next = simNao.equals("s") || simNao.equals("sim");
            } while (next);

            String politica = setPolitica(in);

            if (politica.equals("rr")) {
                for (Processo p : ll) {
                    System.out.println(p);
                    System.out.println("Digite o quantum: ");
                    int quantum = leInteiro(in);
                    p.setQuantum(quantum);
                    System.out.println("Digite a prioridade sendo: 1 Alta - 2 Média - 3 Baixa");
                    int prioridade = leInteiro(in);
                    p.setPriority(prioridade);
                }
                new EscalonadorRR(ll).run();
            } else {
                for (Processo p : ll) {
                    System.out.println(p);
                    System.out.println("Digite o tempo de execução: ");
                    int tempoExecucao = leInteiro(in);
                    p.setExecutionTime(tempoExecucao);
                }
                new EscalonadorSJF(ll).run();
            }
            System.out.println("Finalizado!");
            for (Processo process : ll) {
                System.out.println("\n===============================");
                System.out.println(process);
                System.out.println("Waiting Time: " + process.getWaitingTime());
                System.out.println("Processing Time: " + process.getProcessingTime());
                System.out.println("Turnaround Time: " + process.getTurnarround());
                System.out.println("===============================\n");
            }
            System.out.println("Gostaria de rodar novamente?: (s/n)");
            String simNao = in.nextLine().toLowerCase();
            while (!validaSimENao(simNao)) {
                System.out.print("String invalida, digite apenas S ou N.");
                simNao = in.nextLine().toLowerCase();
            }
            next = simNao.equals("s") || simNao.equals("sim");
        } while (next);

        System.out.println("Até breve.");
    }

    public String setPolitica(Scanner in) {
        System.out.print("Qual politica de escalonamento voce gostaria de usar (RR ou SJF): ");
        String politica = in.nextLine();
        if (!validaPolitica(politica)) {
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
