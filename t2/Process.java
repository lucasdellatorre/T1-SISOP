public class Process extends Partition {
    public String pid;
    public int size;

    public Process(String pid, int size) {
        this.pid = pid;
        this.size = size;
    }

    @Override
    public String toString () {
        return "Process | pid: " + pid + " | size: " + size;
    }
}
