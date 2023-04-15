import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }
    
    public void run() {
        Processo p1 = new Processo("1", 3, new File("src/examples/prog1.asm"));
        Processo p2 = new Processo("2", 0, new File("src/examples/prog2.asm"));
        Processo p3 = new Processo("3", 0, new File("src/examples/prog3.asm"));
        
        Escalonador s;

        LinkedList<Processo> ll = new LinkedList<Processo>();
        ll.add(p1);
        ll.add(p2);
        ll.add(p3);

        s = new EscalonadorSJF(ll);

        s.run();
        
        // Scanner in = new Scanner(System.in);
        
        // String politica = setPolitica(in);
        
        // LinkedList<Processo> filaDeProcessos = new LinkedList<>();

        // System.out.println("***************************************************************");
        // System.out.println("            Bem vindo ao Simulador de Escalonamento. ");
        // System.out.println("***************************************************************");


        // boolean next = true;
        // do  {
        //     try {
        //         System.out.println("");
        //         System.out.println("Opções: ");
        //         System.out.println("");
        //         System.out.println("[0] Sair.");
        //         System.out.println("[1] Escalonar processos utilizando a política de SJF");
        //         System.out.println("[2] Escalonar processos utilizando a política de Round-Robin");
        //         System.out.println("");
        //         System.out.print("Digite a opção desejada: ");
        //         int opcao = sc.next().charAt(0);
        //         sc.nextLine();
        //         switch (opcao) {
        //             case '1':
        //                 System.out.println("Digite o quantum: ");
        //                 int quantum = sc.nextInt();
        //                 roundRobinCP.setQuantum(quantum);

        //                 System.out.println("Digite o caminho do arquivo: ");
        //                 caminho = sc2.nextLine();


        //                 System.out.println("Digite a prioridade sendo: 1 Alta - 2 Média - 3 Baixa");
        //                 int prioridade = sc.nextInt();

        //                 System.out.println("Digite o tempo de chegada: ");
        //                 int tempoChegada = sc.nextInt();


        //                 break;
        //             case '2':
        //                 System.out.println("Escolheu 2");
        //                 break;
        //             case '3':
        //                 System.out.println("Escolheu 3");
        //                 break;
        //             case '0':
        //                 next = false;
        //                 break;
        //             default:
        //                 System.out.println("Opcao invalida! Redigite. ");
        //         }

        //     } catch (InputMismatchException e) {
        //         System.out.println("Erro: Insira apenas números inteiros. ");
        //     } catch (Exception e) {
        //         System.out.println(e.getMessage());
        //     }
        // } while(next);

        // System.out.println("Até breve.");
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
