package elice;

/**
 * A class representing a binomial tree for storing integers
 */
public class IntBinomialTree{
    private int key;
    private String value;
    private int degree;           // the number of children
    private Node<IntBinomialTree> head;   // a linked list for implementing a general tree
    private int manyItems;          // the number of items in the tree

    /**
     * The constructor.
     * @param _data - the initial value of "data"
     */
    public IntBinomialTree(int _key, String _value){
        key = _key;
        value = _value;
        degree = 0;
        head = null;
        manyItems = 1;
    }

    /**
     * Takes an IntBinomialTree "tree" and attaches it to this node.
     * This is only permissible when both trees are of the same order.
     * @param tree - a tree to be added to the current tree
     * @precondition
     *  The given tree is of the same order as this tree.
     **/
    public void merge(IntBinomialTree tree){
        if (degree != tree.degree)
            throw new IllegalArgumentException ("Incompatible degrees");

        if (key > tree.key)
            swapContents(tree);

        if (head == null)
            head = new Node<IntBinomialTree>(tree, null);
        else {
            Node<IntBinomialTree> tail = head;

            while (tail.getLink() != null)
                tail = tail.getLink();

            tail.addNodeAfter(tree);
        }

        degree++;
        manyItems += tree.manyItems;
    }

    /**
     * Return child trees as separate binomial trees in a binomial heap.
     * @return - an IntBinomialHeap consisting of the children.
     */
    public IntBinomialHeap split(){
        IntBinomialHeap heap = new IntBinomialHeap();
        for(Node<IntBinomialTree> cur = head; cur != null; cur = cur.getLink())
            heap.appendTree(cur.getData());
        return heap;
    }

    public int getKey(){return key;}

    public String getValue(){return value;}

    public int getDegree(){return degree;}

    public int size(){return manyItems;}

    /**
     * Swaps the contents of this node with the given node "tree".
     * @param tree
     */
    void swapContents(IntBinomialTree tree){
        // swap data(key)
        int tmp = key;
        key = tree.key;
        tree.key = tmp;

        //swqp the value
        String tmp_str = value;
        value = tree.value;
        tree.value = tmp_str;

        // swap subtrees
        Node<IntBinomialTree> tmp2 = head;
        head = tree.head;
        tree.head = tmp2;

        // CAUTION: doesnt swap manyItems and degree
        //
        // Reason: degree assumed to be equal and
        // manyItems is completely dependent on degree
        
        if (degree != tree.degree)
            throw new Error("La bebe, degrees gotta be equal.");
    }

    /**
     * Prints the contents (for debugging).
     * You are free to modify or delete this method.
     * @param depth - the current indentation depth.
     */
    public void print(int depth){
        for (int i = 1; i <= depth; i++)
            System.out.print("  ");
        System.out.println(key);

        Node<IntBinomialTree> cursor = head;
        while(cursor != null){
            IntBinomialTree tree = cursor.getData();
            tree.print(depth + 1);
            cursor = cursor.getLink();
        }
    }

    //////////////////////////////////////////////
  /*
   * Below methods are used for grading.
   * Please don't be modify below codes
   */
    public Node getHead(){
        return head;
    }
}
