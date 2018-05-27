package elice;

/**
 * A class representing a node in an AVL tree for storing integers.
 *
 */
public class ATNode
{
    String key;     // data to be stored in the node
    String value;
    ATNode left;    // a reference to the left child
    ATNode right; // a reference to the right child
    int height;     // the height of this tree (The height of a leaf node is 0.)

    /**
     * The constructor.
     * @param _key - the initial value of "key"
     * @param _left - the initial value of "left"
     * @param _right - the initial value of "right"
     */
    public ATNode(String _key, String _value, ATNode _left, ATNode _right){
        key = _key;
        value = _value;
        left = _left;
        right = _right;
        height = 0;   // assuming that this node is a leaf node initially
    }

    /**
     * Indicates if this node is a leaf node.
     * @return - true if this node is a leaf node, and false otherwise.
     */
    public boolean isLeaf(){
        return (left == null) && (right == null);
    }

    public String getKey() { return key; }

    public String getValue() { return value; }

    public int getHeight() { return height; }

    /**
     * Updates the height of this node based on the heights of the children.
     * @precondition
     *  The heigkeyata of the children are up-to-date and valid.
     */
    void updateHeight()
    {
        int leftHeight = -1;
        if(left != null) leftHeight = left.getHeight();
        int rightHeight = -1;
        if(right != null) rightHeight = right.getHeight();
        height = 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Calculates the balance factor of this node based on the heights of the children.
     * @return - the balance factor of this node
     * @precondition
     *  The height data of the children are up-to-date and valid.
     */
    int getBalanceFactor()
    {
        int leftHeight = -1;
        if(left != null) leftHeight = left.getHeight();
        int rightHeight = -1;
        if(right != null) rightHeight = right.getHeight();
        return leftHeight - rightHeight;
    }

    /**
     * Returns the data of the leftmost node of the tree starting from this node.
     * @return - the data from the leftmost node that can be reached from this node.
     */
    public String getLeftmostData( )
    {
        if (left == null)
            return key;
        else
            return left.getLeftmostData( );
    }

    private ATNode getLeftmostNode( )
    {
        if (left == null)
            return this;
        else
            return left.getLeftmostNode( );
    }

    /**
     * Returns the data of the rightmost node of the tree starting from this node.
     * @return - the data from the rightmost node that can be reached from this node.
     */
    public String getRightmostData( )
    {
        if (right == null)
            return key;
        else
            return right.getRightmostData( );
    }

    /**
     * Removes the leftmost node of the tree starting from this node.
     * @return - the root node of the tree after the leftmost node is removed.
     */
    public ATNode removeLeftmost( )
    {
        if (left == null)
            return right;
        else
        {
            left = left.removeLeftmost( );
            balance();
            return this;
        }
    }

    /**
     * Removes the rightmost node of the tree starting from this node.
     * @return - the root node of the tree after the rightmost node is removed.
     */
    public ATNode removeRightmost( )
    {
        if (right == null)
            return left;
        else {
            right = right.removeRightmost( );
            balance();
            return this;
        }
    }

    /**
     * Adds a new item "object" to the tree starting from this node.
     * @param object - a new item to be added
     * @precondition
     *  The tree starting from this node is an AVL tree.
     * @postcondition
     *  The tree starting from this node is an AVL tree.
     */
    public void add(String object, String value)
    {
        if (compareWords(object, key)==0 || compareWords(object, key) == 1) {
            if(left == null)
                left = new ATNode(object, value, null, null);
            else
                left.add(object, value);
        } else {
            if(right == null)
                right = new ATNode(object, value, null, null);
            else
                right.add(object, value);
        }
        balance();    // in any case
    }

    /**
     * Indicates if a given item "object" is in the tree starting from this node.
     * @param object - a target item to search for
     * @return - true if the given item is in the tree, and false otherwise.
     */
    public boolean has(String object)
    {
        if (compareWords(object, key) == 0)
            return true;
        else if (compareWords(object, key) == 1 && left != null)
            return left.has(object);
        else if (compareWords(object, key) == -1 && right != null)
            return right.has(object);
        else
            return false;
    }

    /**
     * Removes a given item "object" from the tree starting from this node.
     * @param object - an item to be removed from the tree.
     * @precondition
     *  The given item "object" is already in the tree.
     */
    public ATNode remove(String object)
    {
        if (compareWords(object, key) == 0) {
            if (left == null && right == null)
                return null;
            else if (left == null)
                return right;
            else if (right == null)
                return left;
            else {
                ATNode node = right.getLeftmostNode();
                key = node.key;
                value = node.value;
                right = right.removeLeftmost();
                balance();
                return this;
            }
        }
        else if (compareWords(object, key) == 1 && left != null) {
            left = left.remove(object);
            balance();
            return this;
        }
        else if (compareWords(object, key) == -1 && right != null) {
            right = right.remove(object);
            balance();
            return this;
        }
        else
            throw new IllegalArgumentException("Can't find given object.");
    }

    /**
     * Restore the balance condition of an AVL tree after add or remove operation.
     */
    void balance()
    {
        updateHeight();
        if (getBalanceFactor() > 1){
            if (left.getBalanceFactor() > 0)
                doLLRotation();
            else
                doLRRotation();
        }
        else if (getBalanceFactor() < -1) {
            if (right.getBalanceFactor() < 0)
                doRRRotation();
            else
                doRLRotation();
        }
    }

    /**
     * Performs an LL-rotation.
     */
    void doLLRotation()
    {
        ATNode Z = left;
        ATNode A = left.left;
        ATNode B = left.right;
        ATNode C = right;

        left = A;
        right = Z;
        Z.left = B;
        Z.right = C;

        String temp;

        temp = key;
        key = Z.key;
        Z.key = temp;

        temp = value;
        value = Z.value;
        Z.value = temp;

        Z.updateHeight();
        updateHeight();
    }

    /**
     * Performs an RR-rotation.
     */
    void doRRRotation()
    {
        ATNode Z = right;
        ATNode A = left;
        ATNode B = right.left;
        ATNode C = right.right;

        left = Z;
        right = C;
        Z.left = A;
        Z.right = B;

        String temp;

        temp = key;
        key = Z.key;
        Z.key = temp;

        temp = value;
        value = Z.value;
        Z.value = temp;

        Z.updateHeight();
        updateHeight();
    }

    /**
     * Performs an LR-rotation.
     */
    void doLRRotation()
    {
        left.doRRRotation();
        doLLRotation();
    }

    /**
     * Performs an RL-rotation.
     */
    void doRLRotation()
    {
        right.doLLRotation();
        doRRRotation();
    }

    /**
     * It compares two words in dictionary order. If the first word precedes the second word in alphabetical order return 1;
     * Unless, return -1;
     * It is used to construct the tree in dictionary order.
     * NOTE1 : If even one chracter of arguments is not alphabet, it throws an IllegalArgumentException.
     * That means two parameters,word1, word2 should not contain numeric character, symbol, and space.
     * NOTE2: If two parameters are same string, it returns 0;
     * NOTE3: It converts every characters of parameters to the lowercase.
     *       So we consider the uppercase and the lowercase are identical.
     */
    int compareWords(String word1, String word2)
    {
        for(char c1: word1.toCharArray())
        {
            if(!Character.isAlphabetic(c1))
                throw new IllegalArgumentException("It is not alphabetical word");
        }

        for(char c2: word2.toCharArray())
        {
            if(!Character.isAlphabetic(c2))
                throw new IllegalArgumentException("It is not alphabetical word");
        }
        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();

        int min_l = Math.min(word1.length(),word2.length());
        for(int i=0; i<min_l ; i++)
        {
            char c1 =word1.charAt(i);
            char c2 =word2.charAt(i);

            if(word1.equals(word2))
            {
                return 1;
            }
            if((int)c1 != (int)c2)
            {
                if((int)c1 < (int)c2)
                {
                    return 1;
                }
                else
                {
                    return -1;
                }
            }
        }

        if(word1.length() > word2.length() ){
            return -1;
        }
        else if(word1.length() < word2.length() ){
            return 1;
        }
        else
        {
            return 0 ; // same
        }
    }


    /**
     * Calculates the number of nodes in the tree starting from a given node "root".
     * @param root - the root of the tree
     * @return - the number of nodes in the tree starting from the given node "root"
     */
    public static int treeSize(ATNode root)
    {
        if (root == null)
            return 0;
        else
            return 1 + treeSize(root.left) + treeSize(root.right);
    }

    // Debug
    public void print(int indent, String label) {
        if (left != null)
            left.print(indent+1, "L");

        for (int i = 0; i < indent; i++)
            System.out.print("   ");
        System.out.println("(" + label + ") " + getBalanceFactor());

        if (right != null)
            right.print(indent+1, "R");
    }

    public boolean isbalanced() {
        if (getBalanceFactor() < -1 || 1 < getBalanceFactor())
            return false;
        if (left != null && !left.isbalanced())
            return false;
        if (right != null && !right.isbalanced())
            return false;
        return true;
    }
}
