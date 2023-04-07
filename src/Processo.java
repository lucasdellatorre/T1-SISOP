public enum Estado {
  RUNNING,
  BLOCKED,
  READY,
  FINISHED
}

public class Processo {

  private int pid;
  private Estado estado;

  Processo(int pid, Estado estado) {
    this.pid = pid;
    this.estado = estado;
  }

  public int getPid() {
    return pid;
  }

  public Estado getEstado() {
    return estado;
  }
}
