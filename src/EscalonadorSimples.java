import java.util.HashMap;
import java.util.LinkedList;

public class EscalonadorSimples extends Escalonador {
    class MemTuple {
        private int pc;
        private int acc;
    
        public MemTuple(int pc, int acc) {
            this.pc = pc;
            this.acc = acc;
        }
    
        public int getPc() {
            return pc;
        }
    
        public int getAcc() {
            return acc;
        }
    }
    
    private int time;
    private LinkedList<Processo> blockedQueue;
    private LinkedList<Processo> readyQueue;
    private Processo running;

    private HashMap<Processo, MemTuple> memory;
    
    public EscalonadorSimples() {
        this.blockedQueue = new LinkedList<>();
        this.readyQueue = new LinkedList<>();
        this.running = null;
    }

    public void escalonar() {
        // Escalonamento simples
        // O processo que chegar primeiro é o primeiro a ser executado
        // O proce
    }

    public void addProcesso(Processo p) {
        // Adiciona o processo na fila de prontos
    }

    public void blockProcesso(Processo p) {
        // Bloqueia o processo
    }

    @Override
    int run() {
        return 0;
    }
}