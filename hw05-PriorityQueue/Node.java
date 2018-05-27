package elice;

public class Node<E> {
    // Please inmplement Node class.
    // Reference DoubleNode in previous homework, and change it to generic version.
    // You don't have to impelemnt every method in DoubleNode class. Just make sure that DoublyLinkedSseq works well.
    private E value;
    private int priority;
    private Node<E> next;
    private Node<E> prev;

    Node(E initial_value, int initial_priority) {
        value = initial_value;
        priority = initial_priority;
    }

    void addNodeAfter(E value, int priority) {
        Node<E> node = new Node<E>(value, priority);
        node.prev = this;
        node.next = next;
        next.prev = node;
        next = node;
    }

    void remove() {
        prev.next = next;
        next.prev = prev;
        prev = next = null;
    }

    Node<E> getNext() {
        return next;
    }

    void setNext(Node<E> node) {
        next = node;
    }

    Node<E> getPrev() {
        return prev;
    }

    void setPrev(Node<E> node) {
        prev = node;
    }

    E getValue() {
        return value;
    }

    void setValue(E v) {
        value = v;
    }

    int getPriority() {
        return priority;
    }

    void setPriority(int p) {
        priority = p;
    }
}
