import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Processo {
    private static int pidCounter = 0;

    private int pid;
    private int turnarround;
    private int processingTime;
    private int waitingTime;
    private int acc;
    private int pc;
    private int startTime;
    private int quantum;
    private int priority;
    private int blockedTime;
    private Estado estado;
    private ArrayList<String[]> instrucoes;
    private HashMap<String, Integer> data;
    private HashMap<String, Integer> labels;

    public Processo(int startTime, File file) {
        this.pid = pidCounter++;
        this.estado = Estado.READY;
        this.acc = 0;
        this.pc = 0;
        this.startTime = startTime;
        this.quantum = -1;
        this.priority = -1;
        this.blockedTime = 0;
        this.turnarround = 0;
        this.processingTime = 0;
        this.waitingTime = 0;
        leArquivo(file);
    }

    public Processo(int startTime, int quantum, int priority, File file) {
        this.estado = Estado.READY;
        this.acc = 0;
        this.pc = 0;
        this.startTime = startTime;
        this.quantum = quantum;
        this.priority = priority;
        this.blockedTime = 0;
        this.turnarround = 0;
        this.processingTime = 0;
        this.waitingTime = 0;
        leArquivo(file);
    }

    private void leArquivo(File file) {
        ArrayList<String> memory = new ArrayList<String>();
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine().strip();
                if (line.length() == 0) continue;
                memory.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        getInstrucoes(memory);
        getData(memory);
        // Util.printList(instrucoes);
    }

    private void getData(ArrayList<String> file) {
        this.data = new HashMap<String, Integer>();
        int startDataPos = file.indexOf(".data");
        int endDataPos = file.indexOf(".enddata");

        for (int i = startDataPos+1; i<endDataPos; i++) {
            String[] line = file.get(i).split("\\s+");
            // Util.printArray(line);
            String var = line[0];
            int value = Integer.parseInt(line[1]);
            this.data.put(var, value);   
        }
        // Util.printHashMap(this.data);
    }

    private void getInstrucoes(ArrayList<String> file) {
        this.labels = new HashMap<String, Integer>();
        this.instrucoes = new ArrayList<String[]>();
        int startDataPos = file.indexOf(".code");
        int endDataPos = file.indexOf(".endcode");
        
        int pc = 0;
        for (int i = startDataPos+1; i<endDataPos; i++) {
            String[] line = file.get(i).split("\\s+");
            int isLabel = 0;
            
            if (line[0].endsWith(":")) {
                String label = line[0].substring(0, line[0].length()-1);
                this.labels.put(label, pc + (line.length == 1 ? 0 : 1));
                isLabel = 1;
            } 
            if (line.length == 1) continue;
            String[] instruction = {line[0 + isLabel], line[1 + isLabel]};
            this.instrucoes.add(instruction);
            pc++;
        }
        // Util.printList(instrucoes);
        // System.out.println("==========================");
        // Util.printHashMap(labels);
        // System.out.println("==========================");
    }

    public String[] getInstrucao(int pos) {
        // Util.printList(instrucoes);
        return this.instrucoes.get(pos);
    }

    public int getIntuctionsSize() {
        return this.instrucoes.size();
    }

    public int getLabel(String label) {
        return this.labels.get(label);
    }

    public int getVar(String var) {
        return this.data.get(var);
    }

    public void setVar(String var, int value) {
        this.data.put(var, value);
    }

    public int getPid() {
        return this.pid;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getAcc() {
        return acc;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getQuantum() {
        return quantum;
    }

    public int getPriority() {
        return priority;
    }

    public int getBlockedTime() {
        return blockedTime;
    }

    public void setBlockedTime(int newBlockedTime) {
        this.blockedTime = newBlockedTime;
    }

    public int getTurnarround() {
        return this.turnarround;
    }
    
    public int getWaitingTime() {
        return this.waitingTime;
    }
    
    public int getProcessingTime() {
        return this.processingTime;
    }

    public void setTurnarround(int time) {
        this.turnarround = time; 
    }

    public void setWaitingTime() {
        this.waitingTime++; 
    }
    
    public void setProcessingTime() {
        this.processingTime++; 
    }
    
    public String toString() {
        return "pid: " + this.pid + " | state: " + this.estado + " | startTime: " + this.startTime;
    }
}
