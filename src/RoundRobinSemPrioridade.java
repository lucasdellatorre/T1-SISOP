import java.util.LinkedList;

public class RoundRobinSemPrioridade extends Escalonador {
  private LinkedList<Processo> readyQueue;
  private LinkedList<Processo> blockedQueue;
  private Process running;
  RoundRobinSemPrioridade(LinkedList<Processo> readyQueue) {
    this.readyQueue = readyQueue;
    this.blockedQueue = new LinkedList<>();
    this.running = null;
  }

  public int run() { return(-1);  }
}
