public class Processo {
  private String pid;
  private int acc;
  private int pc;
  private int startTime;
  private int quantum;
  private int priority;
  private int blockedTime;
  private Estado estado;

  public Processo(String pid, Estado estado, int startTime) {
    this.pid = pid;
    this.estado = estado;
    this.acc = 0;
    this.pc = 0;
    this.startTime = startTime;
    this.quantum = -1;
    this.priority = -1;
    this.blockedTime = 0;
  }

  public Processo(String pid, Estado estado, int startTime, int quantum, int priority) {
    this.pid = pid;
    this.estado = estado;
    this.acc = 0;
    this.pc = 0;
    this.startTime = startTime;
    this.quantum = quantum;
    this.priority = priority;
    this.blockedTime = 0;
  }

  public String getPid() {
    return pid;
  }

  public Estado getEstado() {
    return estado;
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

  public String toString() {
    return "pid: " + this.pid + " | state: " + this.estado + " | startTime: " + this.startTime;
  }
}
