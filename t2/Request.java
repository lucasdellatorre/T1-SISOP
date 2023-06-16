public class Request {
    private String command;
    private String pid;
    private int pSize;

    public Request(String command, String pid, int pSize) {
        this.command = command;
        this.pid = pid;
        this.pSize = pSize;
    }

    public String toString() {
        String aux = "";
        if (command == "IN") aux = ", " + pSize;
        return command + "(" + pid + aux + ")";
    }

    public String getCommand() {
        return this.command;
    }

    public String getPid() {
        return this.pid;
    }

    public int getPSize() {
        return this.pSize;
    }
}
