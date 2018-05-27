package elice;

import java.util.NoSuchElementException;

public class Deque<E> extends ArrayQueue<E> {
    /* Do not make the data, manyItem, front and rear for the Deque class.
    * Use the variable of ArrayQueue using 'super' keyword
    */
    E tmp; // This variable is used to compile the only initial skeleton code.

    public Deque() {
        super();
    }

    public Deque(int initialCapacity) {
        super(initialCapacity);
    }

    public void addFirst(E element) {
        if (manyItems == data.length) {
            // Double the capacity and add 1; this works even if manyItems is 0. However, in
            // case that manyItems*2 + 1 is beyond Integer.MAX_VALUE, there will be an
            // arithmetic overflow and the bag will fail.
            ensureCapacity(manyItems * 2 + 1);
        }

        if (manyItems == 0) {
            front = 0;
            rear = 0;
        } else
            front = prevIndex(front);

        data[front] = element;
        manyItems++;
    }

    public void addLast(E element) {
        super.add(element);
    }

    public E removeFirst() {
        return super.remove();
    }

    public E removeLast() {
        E answer;

        if (manyItems == 0)
            throw new NoSuchElementException("Queue underflow");
        answer = data[rear];
        rear = prevIndex(rear);
        manyItems--;
        return answer;
    }

    public int prevIndex(int i) {
        if (--i == -1)
            return data.length + i;
        else
            return i;
    }
}
