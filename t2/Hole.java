public class Hole extends Partition {
    public int size;

    public Hole(int size) {
        this.size = size;
    }

    @Override
    public String toString () {
        return "Hole | size: " + size;
    }
}
