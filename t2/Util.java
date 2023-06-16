import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;

public class Util {
    public static ArrayList<Request> readFile(File file) {
        Scanner in = new Scanner(System.in);
        ArrayList<Request> requests = new ArrayList<>();

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine().strip();
                if (line.length() == 0) continue;

                String[] result = parseString(line);
                String command = result[0];
                String pid = result[1];
                int pSize = result[2] != null ? Integer.parseInt(result[2]) : 0;
                requests.add(new Request(command, pid, pSize));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return requests;
    }

    private static String[] parseString(String input) {
        String command;
        String[] result = new String[3];

        input = input.trim();

        if (input.startsWith("IN")) {
            command = "IN";
            String[] parts = input.substring(3, input.length() - 1).split(",");
            result[0] = command;
            result[1] = parts[0].trim();
            result[2] = parts[1].trim();
        } else if (input.startsWith("OUT")){
            command = "OUT";
            String pid = input.substring(4, input.length() - 1);
            result[0] = command;
            result[1] = pid;
            result[2] = null;
        }

        return result;
    }
}
