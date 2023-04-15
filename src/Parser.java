import java.util.Scanner;

public class Parser {
    
    private Processo process;
    private Scanner scan;

    public Parser(Processo process) {
        this.process = process;
        this.scan = new Scanner(System.in);
    }

    public void setProcess(Processo process) {
        this.process = process;
    }

    public int parseNextLine() {
        int pc = this.process.getPc();
        String[] instruction = this.process.getInstrucao(pc);
        String op = instruction[0];
        String value = instruction[1];
        int status = doOperation(Operacoes.valueOf(op.toUpperCase()), value);
        this.process.setPc(++pc);
        // System.out.println("PARSING -> " + pc + " " + this.process.getAcc());
        return status;
    }

    // 0  -> Continua Mesmo estado
    // 1  -> Bloqueia
    // -1 -> Acaba
    private int doOperation(Operacoes op, String value) {
        // System.out.println(op + " " + value);
        int acc = this.process.getAcc();
        Processo process = this.process;

        boolean isImediate = false;
        if (value.startsWith("#")) isImediate = true;
        int number;
        switch (op) {
            case ADD:
                number = isImediate ? Integer.parseInt(value.substring(1)) : process.getVar(value);
                process.setAcc(acc + number);
            break;
                case SUB:
                number = isImediate ? Integer.parseInt(value.substring(1)) : process.getVar(value);
                process.setAcc(acc - number);
                break;
            case MULT:
                number = isImediate ? Integer.parseInt(value.substring(1)) : process.getVar(value);
                process.setAcc(acc * number);
                break;
            case DIV:
                number = isImediate ? Integer.parseInt(value.substring(1)) : process.getVar(value);
                process.setAcc(acc / number);
                break;
            case LOAD:
                process.setAcc(process.getVar(value));
                break;
            case STORE:
                process.setVar(value, acc);
                break;
            case BRANY:
                process.setPc(process.getLabel(value));
                break;
            case BRPOS:
                if (acc > 0) process.setPc(process.getLabel(value));
                break;
            case BRZERO:
                if (acc == 0) process.setPc(process.getLabel(value));
                break;
            case BRNEG:
                if (acc < 0) process.setPc(process.getLabel(value));
                break;
            case SYSCALL:
                int val = Integer.parseInt(value);
                if (val == 0) {
                    System.out.println("SYSCALL 0 -> " + process.getPid());
                    return -1;
                }
                else if (val == 1) {
                    System.out.println("SYSCALL 1 -> " + acc);
                    return 1;
                } else if (val == 2) {
                    process.setAcc(Integer.parseInt(scan.nextLine()));
                    return 1;
                }
                break;
            default:
                break;
        }

        return 0;
    }
}