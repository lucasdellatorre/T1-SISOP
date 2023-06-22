class MemoryBuddy {
    private int size;
    private boolean isAllocated;
    private MemoryBuddy leftChild;
    private MemoryBuddy rightChild;

    public MemoryBuddy(int size) {
        this.size = size;
        this.isAllocated = false;
        this.leftChild = null;
        this.rightChild = null;
    }

    public boolean isAllocated() {
        return isAllocated;
    }

    public int getSize() {
        return size;
    }

    public MemoryBuddy getLeftChild() {
        return leftChild;
    }

    public MemoryBuddy getRightChild() {
        return rightChild;
    }

    public void setLeftChild(MemoryBuddy leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(MemoryBuddy rightChild) {
        this.rightChild = rightChild;
    }

    public void allocate() {
        this.isAllocated = true;
    }

    public void deallocate() {
        this.isAllocated = false;
    }
}
