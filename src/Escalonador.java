import java.util.List;

public abstract class Escalonador {
    private int time;
    private List<Processo> blockedQueue;
    private List<Processo> readyQueue;
    private List<Processo> notStartedQueue;
    private List<Processo> finishedQueue;
    private Processo runningProcess;
    private Parser parser;
    private boolean modoPassoAPasso;

    abstract List<Processo> run();
}
