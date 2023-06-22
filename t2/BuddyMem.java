public class BuddyMem {
  private static class Node {
    int size;
    int ocupationSize;
    boolean isEmpty;
    Node esq, dir;
    String pid;
  
    public Node(int size, int ocupationSize) {
      this.ocupationSize = ocupationSize;
      this.size = size;
      esq = dir = null;
      isEmpty = true;
    }
  }

  private Node root;

  private int fragInternaTotal;

  public BuddyMem(int size) {
    root = new Node(size, 0);
    fragInternaTotal = 0;
  }

  public void insert( int size, String pid ) {
    Node node = insert( root, size, pid );
    if (node == null) {
      System.err.println("Memory: EMPTY PARTITION");
    }
  }

  private Node insert( Node n, int size, String pid ) {
    if (n == null) {
        return null;
    }
    if(n.size > (2*size - 1) && n.isEmpty) {
      n.esq = new Node(n.size/2, 0);
      n.dir = new Node(n.size/2, 0);
      n.isEmpty = false;
      n.ocupationSize += size;
      return insert(n.esq, size, pid);
    } else if(n.size <= (2*size - 1) && n.isEmpty) {
      n.isEmpty = false;
      n.ocupationSize += size;
      n.pid = pid;
      fragInternaTotal += n.size - size;
      return n;
    } else if(n.size >= 2*size && !n.isEmpty) {
      Node aux = insert(n.esq, size, pid);
      if (aux == null) {
        aux = insert(n.dir, size, pid);
      }
      if (aux != null) {
        n.ocupationSize += size;
      }
      return aux;
    } else {
      return null;
    }
  }

  public void remove( String pid ) {
    Node node = remove( root, pid );
    if (node == null) {
      System.err.println("Memory: Process not found");
    }
  }

  private Node remove(Node n, String pid) {
    if (n == null) {
        return null;
    }
    if(n.isEmpty) {
      return null;
    } else if(n.pid != null && (n.pid).equals(pid)) {
      n.isEmpty = true;
      n.pid = null;
      fragInternaTotal -= n.size - n.ocupationSize;
      n.ocupationSize = 0;
      return n;
    } else {
      if (n.esq == null && n.dir == null) {
        return null;
      }
      Node aux = remove(n.esq, pid);
      if (aux == null) {
        aux = remove(n.dir, pid);
      }

      if (aux != null) {
        if (n.esq.isEmpty && n.dir.isEmpty) {
          n.isEmpty = true;
          n.ocupationSize = 0;
          n.dir = n.esq = null;
        } else if (n.esq.isEmpty) {
          n.ocupationSize = n.dir.ocupationSize;
        } else {
          n.ocupationSize = n.esq.ocupationSize;
        }
      }
      return aux;
    }
  }

  public void print( )  { 
    print( root, "" ); 
    System.out.println("Fragmentacao Interna: " + fragInternaTotal + "\n");
  }

  private void print( Node n, String s ) {
    if ( n == null ) return;
    print( n.dir, s + "    ");
    System.out.print( s + n.size + "-" + n.isEmpty + "(" + n.pid + "-" + n.ocupationSize + ")" + "\n" );
    print( n.esq, s + "    " );
  }
}