public class Main {
    public static void main(String[] args) {
        new Main().run();
    }
    
    public void run() {
      try {
        new VariablePartition(4);
        something();
      } catch (InsufficientMemoryException e) {
        System.err.println(e.getMessage());
      }
    }

    public void something() throws InsufficientMemoryException {
      throw new InsufficientMemoryException();
  }
}
