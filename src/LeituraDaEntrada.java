import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LeituraDaEntrada {
    private List<String> instrucoes = new ArrayList<>();
    String[] vetor;

    public LeituraDaEntrada() {
        leArquivo(Paths.get("src/examples/prog1.asm"));
    }


    public void leArquivo(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line = br.readLine();

            while (line != null) {
                vetor = line.split("\\t", 2);
                List<String> fields = Arrays.asList(vetor[0]);
                for(String s : fields)
                    instrucoes.add(s);
                line = br.readLine();

            }

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
    }

    public List<String> getInstrucoes() {
        return instrucoes;
    }

    public static void main(String[] args) {
        LeituraDaEntrada la = new LeituraDaEntrada();
        for(String s : la.getInstrucoes()) {
            System.out.println(s);
        }

    }
}