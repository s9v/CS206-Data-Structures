package elice;

import java.util.NoSuchElementException;

public class PQOrdinary<E> {
    private ArrayQueue<E>[] queues; // DO NOT ERASE THIS!
//    E tmp;
    private int highest;
    private int cur_highest;

    public ArrayQueue<E>[] getQueues() {
        /*
      DO NOT CHANGE THIS METHOD!
    */
        return queues;
    }

    public PQOrdinary(int highest) {
        if (highest < 0)
            throw new IllegalArgumentException();

        this.highest = highest;
        queues = new ArrayQueue[highest+1];

        for (int i = 0; i <= highest; i++)
            queues[i] = new ArrayQueue<E>();

        cur_highest = 0;
    }

    public void add(E item, int priority) {
        if (priority < 0 || highest < priority)
            throw new IllegalArgumentException();

        cur_highest = Math.max(cur_highest, priority);
        queues[priority].add(item);
    }

    public E remove() {
        while (cur_highest > 0 && queues[cur_highest].isEmpty())
            cur_highest--;

        if (queues[cur_highest].isEmpty())
            throw new NoSuchElementException();

        return queues[cur_highest].remove();
    }
}
