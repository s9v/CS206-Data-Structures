package elice;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class PQDirect<E> {
    public Node head, tail, cursor;  // Do not erase this
    public int manyNodes;  // Do not erase this
    public int highest; // Do not erase this

//    Node<E> head, tail;
//    E tmp;

    public PQDirect(int highest_priority) {
        if (highest_priority < 0)
            throw new IllegalArgumentException();

        highest = highest_priority;
        head = new Node<E>(null, 0);
        tail = new Node<E>(null, 0);

        head.setNext(tail);
        tail.setPrev(head);
    }

    public void add(E item, int priority) {
        if (priority < 0 || highest < priority)
            throw new IllegalArgumentException();

        tail.getPrev().addNodeAfter(item, priority);
    }

    public E remove() {
        if (head.getNext() == tail)
            throw new NoSuchElementException();

        E answer = null;
        int max_priority = 0;

        for (Node<E> cur = head.getNext(); cur != tail; cur = cur.getNext())
            max_priority = Math.max(max_priority, cur.getPriority());

        for (Node<E> cur = head.getNext(); cur != tail; cur = cur.getNext())
            if (cur.getPriority() == max_priority) {
                answer = cur.getValue();
                cur.remove();
                break;
            }

        return answer;
    }
}
