import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class EscalonadorRR extends Escalonador {
  private int time;
  private LinkedList<Processo> blockedQueue;
  private LinkedList<Processo> readyQueue;
  private LinkedList<Processo> notStartedQueue;
  private Processo runningProcess;
  private int quantum;

  public EscalonadorRR(LinkedList<Processo> processes, int quantum) {
    this.readyQueue = new LinkedList<>();
    this.notStartedQueue = processes;
    this.runningProcess = null;
    this.quantum = quantum;
  }

  @Override
  int run() {

    return 1;
    // implementar lógica round-robin
  }
}