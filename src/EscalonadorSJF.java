import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;

/*
    1- Cria os processos quando os recebe da Main, caso algum processo não inicie no tempo 0, ele só é criado em seu tempo
    2- Ordena a Fila de processos prontos
    3- Eslacona o primeiro processo da fila
    4- Vai lendo o processo até ele acabar ou até chegar um processo com tempo menor do que o tempo faltando do atual, caso isso aconte o 
    escalonador para de escalonar o atual e escalona o menor. O Atual entra de volta da fila.
*/
public class EscalonadorSJF extends Escalonador {
    private int time;
    private LinkedList<Processo> blockedQueue;
    private LinkedList<Processo> readyQueue;
    private LinkedList<Processo> notStartedQueue;
    private Processo runningProcess;
    private Parser parser;

    public EscalonadorSJF(LinkedList<Processo> processes) {
        this.readyQueue = new LinkedList<>();
        this.blockedQueue = new LinkedList<>();
        this.notStartedQueue = processes;
        this.runningProcess = null;
        this.parser = new Parser(null);
    }

    @Override
    int run() {
        while (this.readyQueue.size() > 0 || this.blockedQueue.size() > 0 || notStartedQueue.size() > 0 || this.runningProcess != null) {
            LinkedList<Processo> aux;
            aux = new LinkedList<Processo>(notStartedQueue);
            for (Processo process : notStartedQueue) {
                // System.out.println("Not Started Process: " + process.getPid());
                if (process.getStartTime() == time) {
                    readyQueue.add(process);
                    aux.remove(process);
                    process.setEstado(Estado.READY);
                }
            }
            this.notStartedQueue = new LinkedList<>(aux);
            
            aux = new LinkedList<Processo>(blockedQueue);
            for (Processo process : blockedQueue) {
                // System.out.println("Blocked Process: " + process.getPid());
                process.setBlockedTime(process.getBlockedTime() - 1);
                if (process.getBlockedTime() == 0) {
                    readyQueue.add(process);
                    aux.remove(process);
                    process.setEstado(Estado.READY);
                }
            }

            this.blockedQueue = new LinkedList<>(aux);
            this.readyQueue = sortProcessesBySize(readyQueue);
            
            if (this.readyQueue.size() != 0 || this.runningProcess != null) {
                int firstTime = Integer.MAX_VALUE;
                if (this.readyQueue.size() > 0) firstTime = this.readyQueue.getFirst().getIntuctionsSize();

                if (this.runningProcess == null || this.runningProcess.getIntuctionsSize() > firstTime) {
                    if (this.runningProcess != null) {
                        this.readyQueue.add(this.runningProcess);
                        this.runningProcess.setEstado(Estado.READY);
                    }
                    this.runningProcess = this.readyQueue.getFirst();
                    this.readyQueue.pop();
                    this.runningProcess.setEstado(Estado.RUNNING);
                    parser.setProcess(this.runningProcess);
                }

                System.out.println("===============================\nReady Queue: ");
                Util.printList(this.readyQueue);
                System.out.println("===============================\nBlocked Queue: ");
                Util.printList(this.blockedQueue);
                System.out.println("===============================\nNot Started Queue:");
                Util.printList(this.notStartedQueue);
                System.out.println("===============================");
                System.out.println("Running Process: " + this.runningProcess.getPid() + " Time: " + time);
                System.out.println("===============================\n\n");
                int status = parser.parseNextLine();
                this.runningProcess.setProcessingTime();
                // System.out.println("Status: " + status);
                if (status == -1) {
                    // System.out.println("Entrei no -1");
                    this.runningProcess.setEstado(Estado.FINISHED);
                    this.runningProcess.setTurnarround((time + 1) - this.runningProcess.getStartTime());
                    this.runningProcess = null;
                } else if (status == 1) {
                    // System.out.println("Entrei no 1");
                    this.runningProcess.setEstado(Estado.BLOCKED);
                    Random random = new Random();
                    this.runningProcess.setBlockedTime(random.nextInt(3) + 8);
                    System.out.println("pid: " + this.runningProcess.getPid() + " Blocked Time: " + this.runningProcess.getBlockedTime());
                    System.out.println();
                    this.blockedQueue.add(this.runningProcess);
                    this.runningProcess = null;
                }
            }

            for (Processo process : this.readyQueue) {
                process.setWaitingTime();
            }

            time++;
        }
        return 0;
    }

    public LinkedList<Processo> sortProcessesBySize(LinkedList<Processo> queue) {
        return new LinkedList<Processo>(queue.stream()
        .sorted(Comparator.comparing((Processo p) -> (p.getIntuctionsSize() - p.getPc())))
        .collect(Collectors.toList())
        );
    }
}
