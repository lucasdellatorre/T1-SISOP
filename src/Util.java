import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    public static void printHashMap(HashMap<?, ?> map) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public static void printList(List<?> list) {
        for (Object element : list) {
            if (element instanceof String[]) {
                printArray((Object[]) element);
            } else {
                System.out.println(element);
            }
        }
    }

    public static void printArray(Object[] array) {
        System.out.print("[");
        int size = array.length;
        int i = 0;
        for (Object element : array) {
            System.out.print(element);
            i++;
            if (i != size) System.out.print(", ");
        }
        System.out.println("]");
    }
}
