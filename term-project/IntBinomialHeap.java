package elice;

import java.util.NoSuchElementException;

/**
 * A class representing a priority queue for storing integers using a binomial min-heap.
 *
 * Invariants:
 * 1. The internal linked list is either empty or stores one or more binomial trees in the order of
 *    increasing degrees.
 * 2. "manyItems" is the number of all integers stored in the
 .
 */
public class IntBinomialHeap {
    Node<IntBinomialTree> head;     // a linked list storing binomial trees (a forest)
    int manyItems;            // # of data items in the forest

    /**
     * The constructor.
     */
    public IntBinomialHeap() {
        head = null;
        manyItems = 0;
    }

    /**
     * Empties the queue.
     */
    public void clear() {
        head = null;
        manyItems = 0;
    }

    /**
     * Returns one of the minimum items in the queue.
     * @return a minimum item in the queue.
     * @precondition
     *  The queue is not empty.
     */
    public int peek() {
        if (head == null)
            throw new NoSuchElementException("Empty heap");
        return findMinTree().getKey();
    }

    /**
     * Adds a new key and value "_key" and "_value" to the queue.
     * @param _key  - a new key of element to be added to the queue
     * @param _value  - a new value of element to be added to the queue
     */
    public void add(int _key, String _value) {
        IntBinomialHeap queue = new IntBinomialHeap();
        IntBinomialTree tree = new IntBinomialTree(_key, _value);
        queue.appendTree (tree);
        merge(queue);
    }

    /**
     * Removes a minimum item from the queue.
     * @return a minimum item in the queue
     * @precondition
     *  The queue is not empty.
     */
    public int remove() {
        if (head == null)
            throw new NoSuchElementException("Empty heap");
        IntBinomialTree minTree = removeMinTree();
        merge(minTree.split());
        return minTree.getKey();
    }

    /**
     * Merges a given queue "addend" with this queue.
     * @param addend - a queue to be merged with this queue.
     * @precondition
     *  The argument "addend" is not null.
     */
    public void merge(IntBinomialHeap addend) {
        IntBinomialHeap newheap = new IntBinomialHeap();
        IntBinomialTree carry = null;
        Node<IntBinomialTree> cur1 = head;
        Node<IntBinomialTree> cur2 = addend.head;

        while (cur1 != null && cur2 != null) {
            IntBinomialTree tree1 = cur1.getData();
            IntBinomialTree tree2 = cur2.getData();

            if (tree1.getDegree() == tree2.getDegree()) {
                if (carry != null) {
                    newheap.appendTree(carry);
                    carry = null;
                }

                tree1.merge(tree2);
                carry = tree1;

                cur1 = cur1.getLink();
                cur2 = cur2.getLink();
            }
            else if (tree1.getDegree() < tree2.getDegree()) {
                if (carry != null) {
                    if (carry.getDegree() == tree1.getDegree())
                        carry.merge(tree1);
                    else {
                        newheap.appendTree(carry);
                        carry = null;
                        newheap.appendTree(tree1);
                    }
                }
                else
                    newheap.appendTree(tree1);

                cur1 = cur1.getLink();
            }
            else if (tree1.getDegree() > tree2.getDegree()) {
                if (carry != null) {
                    if (carry.getDegree() == tree2.getDegree())
                        carry.merge(tree2);
                    else {
                        newheap.appendTree(carry);
                        carry = null;
                        newheap.appendTree(tree2);
                    }
                }
                else
                    newheap.appendTree(tree2);

                cur2 = cur2.getLink();
            }
        }

        while (cur1 != null) {
            IntBinomialTree tree1 = cur1.getData();

            if (carry != null) {
                if (carry.getDegree() == tree1.getDegree())
                    carry.merge(tree1);
                else {
                    newheap.appendTree(carry);
                    carry = null;
                    newheap.appendTree(tree1);
                }
            }
            else
                newheap.appendTree(tree1);

            cur1 = cur1.getLink();
        }

        while (cur2 != null) {
            IntBinomialTree tree2 = cur2.getData();

            if (carry != null) {
                if (carry.getDegree() == tree2.getDegree())
                    carry.merge(tree2);
                else {
                    newheap.appendTree(carry);
                    carry = null;
                    newheap.appendTree(tree2);
                }
            }
            else
                newheap.appendTree(tree2);

            cur2 = cur2.getLink();
        }

        if (carry != null)
            newheap.appendTree(carry);

        head = newheap.head;
        manyItems = newheap.manyItems;
    }

    /**
     * Appends a given binomial tree "tree" to the forest.
     * @param tree - a binomial tree to be added to the forest
     * @precondition
     *  The degree of the given tree is larger than any of the trees in the forest.
     *  please refer to the "supplement.pdf": figure 11.10 (a)
     *
     */
    void appendTree(IntBinomialTree tree) {
        if (head == null)
            head = new Node<IntBinomialTree>(tree, null);
        else {
            Node<IntBinomialTree> tail = head;

            while (tail.getLink() != null)
                tail = tail.getLink();

            tail.addNodeAfter(tree);
        }

        manyItems += tree.size();
    }

    /**
     * Removes one of the minimum trees from the forest.
     * (A minimum tree means a tree containing a minimum element.)
     * @precondition
     *  This forest is not empty.
     */
    IntBinomialTree removeMinTree() {
        Node<IntBinomialTree> mintree_node = null;

        for (Node<IntBinomialTree> cur = head; cur != null; cur = cur.getLink())
            if (mintree_node == null || mintree_node.getData().getKey() > cur.getData().getKey())
                mintree_node = cur;

        if (mintree_node == head)
            head = head.getLink();
        else {
            for (Node<IntBinomialTree> cur = head; cur != null; cur = cur.getLink())
                if (cur.getLink() == mintree_node) {
                    cur.removeNodeAfter();
                    break;
                }
        }

        manyItems -= mintree_node.getData().size();

        return mintree_node.getData();
    }

    /**
     * Finds and returns one of the minimum trees from the heap, if any.
     * (A minimum tree means a tree containing a minimum element.)
     * @return - one of the minimum trees from the forest, if any.
     */
    IntBinomialTree findMinTree() {
        Node<IntBinomialTree> mintree_node = null;

        for (Node<IntBinomialTree> cur = head; cur != null; cur = cur.getLink())
            if (mintree_node == null || mintree_node.getData().getKey() > cur.getData().getKey())
                mintree_node = cur;

        return mintree_node.getData();
    }

    /**
     * Prints the content of the queue (for debugging).
     * You are free to modify or remove this method.
     */
    public void print(){
        System.out.printf("[IntBinomialTree with %d items]\n", manyItems);
        Node<IntBinomialTree> cur = head;
        while(cur != null){
            IntBinomialTree tree = cur.getData();
            tree.print(0);
            cur = cur.getLink();
        }
    }
}
