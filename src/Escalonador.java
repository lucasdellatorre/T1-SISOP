import java.util.List;

public abstract class Escalonador {
    private List<Processo> blockedQueue;
    private List<Processo> readyQueue;
    private Processo running;

    abstract int run();
}