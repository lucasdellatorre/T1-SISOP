public class Buddy {
    private MemoryBuddy root;

    public Buddy(int totalSize) {
        root = new MemoryBuddy(totalSize);
    }

    public int allocate(int size) {
        if (!Util.isPowerOfTwo(size) || size > root.getSize() || root.isAllocated()) {
            System.out.println("Invalid allocation size");
            return -1; // Invalid allocation size or memory is fully allocated
        }

        MemoryBuddy block = findFreeBlock(root, size);
        if (block == null) {
            return -1; // No suitable block found
        }

        splitBlock(block, size);
        block.allocate();
        return block.getSize();
    }

    private MemoryBuddy findFreeBlock(MemoryBuddy node, int size) {
        if (node.getSize() == size && !node.isAllocated()) {
            return node; // Exact match found
        }

        if (node.getSize() < size || node.isAllocated()) {
            return null; // No suitable block found
        }

        MemoryBuddy leftChild = node.getLeftChild();
        MemoryBuddy rightChild = node.getRightChild();

        MemoryBuddy block = findFreeBlock(leftChild, size);
        return block != null ? block : findFreeBlock(rightChild, size);
    }

    private void splitBlock(MemoryBuddy block, int size) {
        while (block.getSize() > size * 2) {
            int newSize = block.getSize() / 2;

            MemoryBuddy leftChild = new MemoryBuddy(newSize);
            MemoryBuddy rightChild = new MemoryBuddy(newSize);

            block.setLeftChild(leftChild);
            block.setRightChild(rightChild);

            block = leftChild;
        }
    }
}
