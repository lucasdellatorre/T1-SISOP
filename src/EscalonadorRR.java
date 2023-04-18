import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;

/*
  1- Recebe os processos da Main e os adiciona na notStartedQueue 
   e ve quais iniciam no tempo atual e os que acabam o bloqueio nesse tempo
   , esses tira da notStartedQueue e adiciona na readyQueue.
  2- Testa se o tempo do processo atual no processador(quantum) já acabou, caso tenha,
  adiciona-lo.
  3- Ordena os processos pela ordem de prioridade deles
  4- Escalonar o próximo processo ou continuar com o atual.
  5- Ler sua póxima linha e caso necessário mudar o seu estados e o desescalonar
*/
public class EscalonadorRR extends Escalonador {
    private int time;
    private LinkedList<Processo> blockedQueue;
    private LinkedList<Processo> readyQueue;
    private LinkedList<Processo> notStartedQueue;
    private Processo runningProcess;
    private Parser parser;

    public EscalonadorRR(LinkedList<Processo> processes) {
        this.readyQueue = new LinkedList<>();
        this.notStartedQueue = processes;
        this.blockedQueue = new LinkedList<>();
        this.runningProcess = null;
        this.parser = new Parser(null);
    }

    @Override
    int run() {
        int quantumTime = 0;
        while (this.readyQueue.size() > 0 || this.blockedQueue.size() > 0 || notStartedQueue.size() > 0
                || this.runningProcess != null) {
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

            if (this.runningProcess != null && this.runningProcess.getQuantum() == quantumTime) {
                quantumTime = 0;
                this.readyQueue.add(this.runningProcess);
                this.runningProcess.setEstado(Estado.READY);
                this.runningProcess = null;
            }

            this.readyQueue = sortProcessesByPriority(this.readyQueue);

            if (this.readyQueue.size() != 0 || this.runningProcess != null) {
                int firstPriority = 3;
                if (this.readyQueue.size() > 0)
                    firstPriority = this.readyQueue.getFirst().getPriority();

                if (this.runningProcess == null || this.runningProcess.getPriority() > firstPriority) {
                    if (this.runningProcess != null) {
                        this.readyQueue.add(this.runningProcess);
                        this.runningProcess.setEstado(Estado.READY);
                    }
                    quantumTime = 0;
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
                System.out.println("Running Process: " + this.runningProcess.getPid() + " Time: " + time
                        + " Quantum time: " + quantumTime);
                System.out.println("===============================\n\n");
                int status = parser.parseNextLine();
                this.runningProcess.setProcessingTime();
                quantumTime++;
                // System.out.println("Status: " + status);
                if (status == -1) {
                    // System.out.println("Entrei no -1");
                    this.runningProcess.setEstado(Estado.FINISHED);
                    this.runningProcess.setTurnarround((time + 1) - this.runningProcess.getStartTime());
                    this.runningProcess = null;
                    quantumTime = 0;
                } else if (status == 1) {
                    // System.out.println("Entrei no 1");
                    this.runningProcess.setEstado(Estado.BLOCKED);
                    Random random = new Random();
                    this.runningProcess.setBlockedTime(random.nextInt(3) + 8);
                    System.out.println("pid: " + this.runningProcess.getPid() + " Blocked Time: " + this.runningProcess.getBlockedTime());
                    System.out.println();
                    this.blockedQueue.add(this.runningProcess);
                    this.runningProcess = null;
                    quantumTime = 0;
                }
            }

            for (Processo process : this.readyQueue) {
                process.setWaitingTime();
            }

            time++;
        }
        return 1;
    }

    public LinkedList<Processo> sortProcessesByPriority(LinkedList<Processo> queue) {
        return new LinkedList<Processo>(queue.stream()
                .sorted(Comparator.comparing((Processo p) -> (p.getPriority())))
                .collect(Collectors.toList()));
    }
}