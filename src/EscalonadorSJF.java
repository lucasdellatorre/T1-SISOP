import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.ListIterator;

/*
    1- Cria os processos quando os recebe da Main, caso algum processo não inicie no tempo 0, ele só é criado em seu tempo
    2- Ordena a Fila de processos prontos
    3- Escalona o primeiro processo da fila
    4- Vai lendo o processo até ele acabar ou até chegar um processo com tempo menor do que o tempo faltando do atual, caso isso aconte o 
    escalonador para de escalonar o atual e escalona o menor. O Atual entra de volta da fila.
*/
public class EscalonadorSJF extends Escalonador {
    private int time;
    private LinkedList<Processo> blockedQueue;
    private LinkedList<Processo> readyQueue;
    private LinkedList<Processo> notStartedQueue;
    private LinkedList<Processo> finishedQueue;
    private Processo runningProcess;
    private Parser parser;

    public EscalonadorSJF(LinkedList<Processo> processes) {
        this.readyQueue = new LinkedList<>();
        this.blockedQueue = new LinkedList<>();
        this.finishedQueue = new LinkedList<>();
        this.notStartedQueue = processes;
        this.runningProcess = null;
        this.parser = new Parser(null);
    }

    @Override
    int run() {
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

            this.readyQueue = sortProcessesBySize(readyQueue);

            printSchedulerCurrentState();
            
            if (this.readyQueue.size() != 0 || this.runningProcess != null) {
                int firstTime = Integer.MAX_VALUE;
                if (this.readyQueue.size() > 0) firstTime = this.readyQueue.getFirst().getExecutionTime();

                if (this.runningProcess == null || this.runningProcess.getExecutionTime() > firstTime) {
                    if (this.runningProcess != null) {
                        this.readyQueue.add(this.runningProcess);
                        this.runningProcess.setEstado(Estado.READY);
                    }
                    this.runningProcess = this.readyQueue.getFirst();
                    this.readyQueue.pop();
                    this.runningProcess.setEstado(Estado.RUNNING);
                    parser.setProcess(this.runningProcess);
                }

                int status = parser.parseNextLine();
                this.runningProcess.decreaseExecutionTime();
                this.runningProcess.setProcessingTime(); // incrementa o tempo de processamento

                // se o programa acabou vai para a fila de terminados e contabiliza o turnarround (tempo que o programa de fato rodou)
                if (status == -1 || this.runningProcess.getExecutionTime() == 0) {
                    this.runningProcess.setEstado(Estado.FINISHED);
                    this.runningProcess.setTurnarround((time + 1) - this.runningProcess.getStartTime());
                    this.finishedQueue.add(this.runningProcess);
                    this.runningProcess = null;
                // se o programa saiu para i/o
                } else if (status == 1) {
                    this.runningProcess.setEstado(Estado.BLOCKED);
                    this.runningProcess.setBlockedTime(new Random().nextInt(3) + 8);
                    this.blockedQueue.add(this.runningProcess);
                    this.runningProcess = null;
                }
            }

            // incrementa o tempo de espera de todos os processos que não foram executados ainda na fila de prontos
            for (Processo process : this.readyQueue) {
                process.setWaitingTime();
            }

            time++;
            System.out.println("\n\n");
        }
        return 0;
    }
    
    public void printSchedulerCurrentState() {
        String runningProc = this.runningProcess != null ? Integer.toString(this.runningProcess.getPid()) : "null";
        System.out.println("===============================\nReady Queue: ");
        Util.printList(this.readyQueue);
        System.out.println("===============================\nBlocked Queue: ");
        Util.printList(this.blockedQueue);
        System.out.println("===============================\nNot Started Queue:");
        Util.printList(this.notStartedQueue);
        System.out.println("===============================\nFinished Queue:");
        Util.printList(this.finishedQueue);
        System.out.println("===============================");
        System.out.println("Running Process: " + runningProc + " Time: " + time);
        System.out.println("===============================");
    }

    public LinkedList<Processo> sortProcessesBySize(LinkedList<Processo> queue) {
        return new LinkedList<Processo>(queue.stream()
        .sorted(Comparator.comparing((Processo p) -> (p.getExecutionTime())))
        .collect(Collectors.toList())
        );
    }
}
