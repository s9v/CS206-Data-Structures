package cs206b;

public class Node<T> implements Cloneable {
    private T data;
    private Node<T> prev;
    private Node<T> next;
    
    Node() {
        data = null;
        prev = null;
        next = null;
    }
    
    Node(T t, Node<T> prev, Node<T> next) {
        data = t;
        this.prev = prev;
        this.next = next;
    }
    
    T getData() {
        return data;
    }
    
    Node<T> getPrev() {
        return prev;
    }
    
    Node<T> getNext() {
        return next;
    }
    
    void setData(T t) {
        data = t;
    }
    
    void setPrev(Node<T> prev) {
        this.prev = prev;
    }
    
    void setNext(Node<T> next) {
        this.next = next;
    }
    /*
    public static DoubleNode listCopy(DoubleNode source)
    {
        DoubleNode copyHead;
        DoubleNode copyTail;

        // Handle the special case of the empty list.
        if (source == null)
            return null;

        // Make the first node for the newly created list.
        copyHead = new Node(source.data, null, null);
        copyTail = copyHead;

        // Make the rest of the nodes for the newly created list.
        while (source.link != null)
        {
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }

        // Return the head reference for the new list.
        return copyHead;
    }
    */
    
   // Please inmplement Node class.
   // Reference DoubleNode in previous homework, and change it to generic version.
   // You don't have to impelemnt every method in DoubleNode class. Just make sure that DoublyLinkedSseq works well.
}
