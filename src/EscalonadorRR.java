import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.ListIterator;

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
    private LinkedList<Processo> finishedQueue;
    private Processo runningProcess;
    private Parser parser;

    public EscalonadorRR(LinkedList<Processo> processes) {
        this.readyQueue = new LinkedList<>();
        this.notStartedQueue = processes;
        this.blockedQueue = new LinkedList<>();
        this.finishedQueue = new LinkedList<>();
        this.runningProcess = null;
        this.parser = new Parser(null);
    }

    @Override
    int run() {
        int quantumTime = 0;
        while (this.readyQueue.size() > 0 || this.blockedQueue.size() > 0 || notStartedQueue.size() > 0 || this.runningProcess != null) {
            ListIterator<Processo> notStartedIterator = notStartedQueue.listIterator();
            while (notStartedIterator.hasNext()) {
                Processo process = notStartedIterator.next();
                // retira o processo com o tempo atual da fila de 'not started' para fila de 'ready' 
                if (process.getStartTime() == time) {
                    readyQueue.add(process);
                    notStartedIterator.remove();
                    process.setEstado(Estado.READY);
                }
            }

            ListIterator<Processo> blockedIterator = blockedQueue.listIterator();
            while (blockedIterator.hasNext()) {
                Processo process = blockedIterator.next();
                process.decreaseIOTime();
                boolean isIOFinished = process.getBlockedTime() == 0;
                if (isIOFinished) {
                    readyQueue.add(process);
                    blockedIterator.remove();
                    process.setEstado(Estado.READY);
                }
            }

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

                printSchedulerCurrentState();

                int status = parser.parseNextLine();
                this.runningProcess.setProcessingTime();
                quantumTime++;
                // System.out.println("Status: " + status);
                if (status == -1) {
                    // System.out.println("Entrei no -1");
                    this.runningProcess.setEstado(Estado.FINISHED);
                    this.runningProcess.setTurnarround((time + 1) - this.runningProcess.getStartTime());
                    this.finishedQueue.add(this.runningProcess);
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

    public void printSchedulerCurrentState() {
        System.out.println("===============================\nReady Queue: ");
        Util.printList(this.readyQueue);
        System.out.println("===============================\nBlocked Queue: ");
        Util.printList(this.blockedQueue);
        System.out.println("===============================\nNot Started Queue:");
        Util.printList(this.notStartedQueue);
        System.out.println("===============================\nFinished Queue:");
        Util.printList(this.finishedQueue);
        System.out.println("===============================");
        System.out.println("Running Process: " + this.runningProcess.getPid() + " Time: " + time);
        System.out.println("===============================\n\n");
    }

    public LinkedList<Processo> sortProcessesByPriority(LinkedList<Processo> queue) {
        return new LinkedList<Processo>(queue.stream()
                .sorted(Comparator.comparing((Processo p) -> (p.getPriority())))
                .collect(Collectors.toList()));
    }
}
