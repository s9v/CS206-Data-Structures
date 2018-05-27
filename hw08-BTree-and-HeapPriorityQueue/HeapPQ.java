package elice;

public class HeapPQ<E>
{
    /*
     *  root node should always be root of the tree.
     */
    // DO NOT CHANGE PRAMETER NAME
    private int[] priorities;
    private E[] data;
    private int[] entered;
    public int numItems;
    private int capacity;
    private int numAdds;
    private final int INITIAL_CAPACITY = 10;

    public HeapPQ()
    {
        numItems = 0;
        capacity = INITIAL_CAPACITY;

        priorities = new int[INITIAL_CAPACITY];
        data = (E[]) new Object[INITIAL_CAPACITY];
        entered = new int[INITIAL_CAPACITY];
    }

    public void add(E itemData, int itemPriority)
    {
        ensureCapacity(numItems+1);

        data[numItems] = itemData;
        priorities[numItems] = itemPriority;
        entered[numItems] = numAdds++;
        numItems++;

        int idx = numItems-1;
        while (idx > 0 && hasHeigherPriority(idx, (idx-1)/2)) {
            swapItems(idx, (idx-1)/2);
            idx = (idx-1)/2;
        }
    }

    public E remove()
    {
        E answer = data[0];

        priorities[0] = priorities[numItems-1];
        data[0] = data[numItems-1];
        entered[0] = entered[numItems-1];
        numItems--;

        int idx = 0;
        while ((2*idx+1 < numItems && hasHeigherPriority(2*idx+1, idx))
                || (2*idx+2 < numItems && hasHeigherPriority(2*idx+2, idx))) {
            int childIdx = 2*idx+1;

            if (hasHeigherPriority(2*idx+2, 2*idx+1))
                childIdx = 2*idx+2;

            swapItems(childIdx, idx);
            idx = childIdx;
        }

        return answer;
    }

    private boolean hasHeigherPriority(int childIdx, int parentIdx) {
        return priorities[childIdx] > priorities[parentIdx]
                || (priorities[childIdx] == priorities[parentIdx] && entered[childIdx] < entered[parentIdx]);
    }

    private void swapItems(int childIdx, int parentIdx) {
        int temp;

        temp = priorities[childIdx];
        priorities[childIdx] = priorities[parentIdx];
        priorities[parentIdx] = temp;

        temp = entered[childIdx];
        entered[childIdx] = entered[parentIdx];
        entered[parentIdx] = temp;

        E tempE;

        tempE = data[childIdx];
        data[childIdx] = data[parentIdx];
        data[parentIdx] = tempE;
    }

    private void ensureCapacity(int requiredCapacity)
    {
        if (capacity < requiredCapacity) {
            capacity = 2*requiredCapacity + 1;

            int[] newPriorities = new int[capacity];
            E[] newData = (E[]) new Object[capacity];
            int[] newEntered = new int[capacity];

            System.arraycopy(priorities, 0, newPriorities, 0, numItems);
            System.arraycopy(data, 0, newData, 0, numItems);
            System.arraycopy(entered, 0, newEntered, 0, numItems);

            priorities = newPriorities;
            data = newData;
            entered = newEntered;
        }
    }

    public int[] getpriorities()
    {
        // Do NOT MODIFY THIS METHOD.
        // THE TEST USES THIS METHOD.
        return priorities;
    }

    public E[] getData()
    {
        // Do NOT MODIFY THIS METHOD.
        // THE TEST USES THIS METHOD.
        return data;
    }

    public int[] getEntered()
    {
        // Do NOT MODIFY THIS METHOD.
        // THE TEST USES THIS METHOD.
        return entered;
    }

    public void print() {
        for (int i = 0; i < numItems; i++)
            System.out.print(data[i] + " ");
        System.out.println();
    }
}