package elice;

public class QPTable<K, E> {

    private int manyItems;
    private Object[] keys;
    private Object[] data;
    private boolean[] hasBeenUsed;

    public QPTable(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity is negative");
        keys = new Object[capacity];
        data = new Object[capacity];
        hasBeenUsed = new boolean[capacity];
    }

    public boolean containsKey(K key) {
        return findIndex(key) != -1;
    }

    // DON'T ERASE THIS. WE USE THIS VARIABLE FOR THE TEST.
    public int visits = 0;

    private int findIndex(K key)
    // Postcondition: If the specified key is found in the table, then the
    // return value is the index of the specified key. Otherwise, the return
    // value is -1.
    {
        int count = 0;
        int i = hash(key);
        int step = 1;

        while (count < data.length && hasBeenUsed[i]) {
            visits++;
            if (key.equals(keys[i]))
                return i;
            count++;
            i = nextIndex(i, step++);
        }

        return -1;
    }

    private int nextIndex(int i, int step)
    {
        return (i + step) % data.length;
    }

    @SuppressWarnings("unchecked")
    public E get(K key) {
        int index = findIndex(key);

        if (index == -1)
            return null;
        else
            return (E) data[index];
    }

    private int hash(K key)
    {
    /*
      The return value is a valid index of the table's arrays. The index is
      calculated as the remainder when the absolute value of the key's
      hash code is divided by the size of the table's arrays.
    */

        return Math.abs(key.hashCode()) % data.length;
    }

    public E put(K key, E element) {
        int index = findIndex(key);
        E answer;

        if (index != -1) { // The key is already in the table.
            answer = (E) data[index];
            data[index] = element;
            return answer;
        } else if (manyItems < data.length) { // The key is not yet in this
            // Table.
            index = hash(key);
            int step = 1;
            while (keys[index] != null)
                index = nextIndex(index, step++);
            keys[index] = key;
            data[index] = element;
            hasBeenUsed[index] = true;
            manyItems++;
            return null;
        } else { // The table is full.
            throw new IllegalStateException("Table is full.");
        }
    }

    public E remove(K key) {
        int index = findIndex(key);
        E answer = null;

        if (index != -1) {
            answer = (E) data[index];
            keys[index] = null;
            data[index] = null;
            manyItems--;
        }

        return answer;
    }
}
