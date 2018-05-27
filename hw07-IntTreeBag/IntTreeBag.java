// File: IntTreeBag.java from the package edu.colorado.collections

// The implementation of most methods in this file is left as a student
// exercise from Section 9.5 of "Data Structures and Other Objects Using Java"

// Check with your instructor to see whether you should put this class in
// a package. At the moment, it is declared as part of edu.colorado.collections:
package elice;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/******************************************************************************
 * This class is a homework assignment;
 * An <CODE>IntTreeBag</CODE> is a collection of int numbers.
 *
 * <b>Limitations:</b>
 *   Beyond <CODE>Integer.MAX_VALUE</CODE> elements, <CODE>countOccurrences</CODE>,
 *   and <CODE>size</CODE> are wrong.
 *
 * <b>Outline of Java Source Code for this class:</b>
 *   <A HREF="../../../../edu/colorado/collections/IntTreeBag.java">
 *   http://www.cs.colorado.edu/~main/edu/colorado/collections/IntTreeBag.java
 *   </A>
 *
 * <b>Note:</b>
 *   This file contains only blank implementations ("stubs")
 *   because this is a Programming Project for my students.
 *
 * @version Feb 10, 2016
 *
 * @see IntArrayBag
 * @see IntLinkedBag
 ******************************************************************************/
public class IntTreeBag implements Cloneable
{
    // Invariant of the IntTreeBag class:
    //   1. The elements in the bag are stored in a binary search tree.
    //   2. The instance variable root is a reference to the root of the
    //      binary search tree (or null for an empty tree).
    private IntBTNode root;

    /**
     *
     *  DO NOT CHANGE THIS METHOD.
     *  TEST CODE USES THIS CODE FOR TEST.
     *
     **/
    public IntBTNode getRoot()
    {
        return root;
    }

    /**
     * Insert a new element into this bag.
     * @param element
     *   the new element that is being inserted
     * <b>Postcondition:</b>
     *   A new copy of the element has been added to this bag.
     *
     **/
    public void add(int element) {
        if (root != null)
            addToSubtree(root, element);
        else {
            root = new IntBTNode(element, null, null);
        }
    }

    private void addToSubtree(IntBTNode root, int element) {
        if (element <= root.getData()) {
            if (root.getLeft() != null)
                addToSubtree(root.getLeft(), element);
            else
                root.setLeft(new IntBTNode(element, null, null));
        }
        else {
            if (root.getRight() != null)
                addToSubtree(root.getRight(), element);
            else
                root.setRight(new IntBTNode(element, null, null));
        }
    }

    public void addMany(int[] elements) {
        for (int i = 0; i < elements.length; i++)
            add(elements[i]);
    }

    /**
     * Add the contents of another bag to this bag.
     * @param addend
     *   a bag whose contents will be added to this bag
     * <b>Precondition:</b>
     *   The parameter, <CODE>addend</CODE>, is not null.
     * <b>Postcondition:</b>
     *   The elements from <CODE>addend</CODE> have been added to this bag.
     * @exception IllegalArgumentException
     *   Indicates that <CODE>addend</CODE> is null.
     *
     **/
    public void addAll(IntTreeBag addend)
    {
        if (addend == null)
            throw new IllegalArgumentException("addend is null.");

        if (this == addend) {
            addend = (IntTreeBag) addend.clone();
        }

        addAllOfSubtree(addend.root);
    }

    private void addAllOfSubtree(IntBTNode otherRoot) {
        if (otherRoot == null)
            return;

        add(otherRoot.getData());

        addAllOfSubtree(otherRoot.getLeft());
        addAllOfSubtree(otherRoot.getRight());
    }

    /**
     * Generate a copy of this bag.
     * @return
     *   The return value is a copy of this bag. Subsequent changes to the
     *   copy will not affect the original, nor vice versa. Note that the return
     *   value must be type cast to an <CODE>IntTreeBag</CODE> before it can be used.
     * @exception OutOfMemoryError
     *   Indicates insufficient memory for creating the clone.
     **/
    public Object clone( )
    {  // Clone an IntTreeBag object.
        IntTreeBag copy = null;

        try {
            copy = (IntTreeBag) super.clone();
            copy.root = IntBTNode.treeCopy(copy.root);
        }
        catch (OutOfMemoryError e) {
            throw e;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return copy;
    }


    /**
     * Accessor method to count the number of occurrences of a particular element
     * in this bag.
     * @param target
     *   the element that needs to be counted
     * @return
     *   the number of times that <CODE>target</CODE> occurs in this bag
     **/
    public int countOccurrences(int target)
    {
        // Student will replace this return statement with their own code:
        return countOccurrencesSubtree(root, target);
    }

    private int countOccurrencesSubtree(IntBTNode root, int target) {
        if (root == null)
            return 0;

        return (root.getData() == target ?1 :0)
                + countOccurrencesSubtree(root.getLeft(), target)
                + countOccurrencesSubtree(root.getRight(), target);
    }


    /**
     * Remove one copy of a specified element from this bag.
     * If there are dulplicates remove lower one first.
     * @param target
     *   the element to remove from the bag
     * <b>Postcondition:</b>
     *   If <CODE>target</CODE> was found in the bag, then one copy of
     *   <CODE>target</CODE> has been removed and the method returns true.
     *   Otherwise the bag remains unchanged and the method returns false.
     **/
    public boolean remove(int target) {
        return removeSubtree(root, null, target);
    }

    private boolean removeSubtree(IntBTNode root, IntBTNode parentOfRoot, int target) {
        if (root == null)
            return false;

        if (target < root.getData())
            return removeSubtree(root.getLeft(), root, target);
        else if (root.getData() < target)
            return removeSubtree(root.getRight(), root, target);
        else {
            int nChildren = 0;
            nChildren += (root.getLeft() != null ?1 :0);
            nChildren += (root.getRight() != null ?1 :0);

            if (nChildren == 2) {
                root.setData(root.getLeft().getRightmostData());
                root.setLeft(root.getLeft().removeRightmost());
            }
            else {
                IntBTNode theOnlyChild = null; // null if no child

                if (root.getLeft() != null)
                    theOnlyChild = root.getLeft();
                else
                    theOnlyChild = root.getRight();

                if (parentOfRoot == null)
                    this.root = theOnlyChild;
                else {
                    if (parentOfRoot.getLeft() == root)
                        parentOfRoot.setLeft(theOnlyChild);
                    else
                        parentOfRoot.setRight(theOnlyChild);
                }
            }

            return true;
        }
    }


    /**
     * Determine the number of elements in this bag.
     * @return
     *   the number of elements in this bag
     **/
    public int size( ) {
        return IntBTNode.treeSize(root);
    }

    /**
     * Find target's level in the tree bag. Root's level is 0. If there are duplicates, return level of lower one(closer to root).
     * @param target
     *   The target want to find in the bag.
     *
     * <b>Precondition:</b>
     * @return
     *   The level of target. If there is no target in the bag, return -1
     *
     **/
    public int search(int target) {
        return searchSubtree(root, target, 0);
    }

    private int searchSubtree(IntBTNode root, int target, int depth) {
        if (root == null)
            return -1;

        if (root.getData() == target)
            return depth;
        else if (target < root.getData())
            return searchSubtree(root.getLeft(), target, depth+1);
        else
            return searchSubtree(root.getRight(), target, depth+1);
    }

    /**
     * Create a new bag that contains all the elements from two other bags.
     * @param b1
     *   the first of two bags
     * @param b2
     *   the second of two bags
     * <b>Precondition:</b>
     *   Neither b1 nor b2 is null.
     * @return
     *   the union of b1 and b2
     * @exception IllegalArgumentException
     *   Indicates that one of the arguments is null.
     *
     **/
    public static IntTreeBag union(IntTreeBag b1, IntTreeBag b2)
    {
        IntTreeBag all = new IntTreeBag();
        all.addAll(b1);
        all.addAll(b2);
        return all;
    }


    /**
     * Split the bag into two bags.
     * @param b1
     *   the bag
     * @param t
     *   the target
     * <b>Precondition:</b>
     *   Neither b1 nor b2 is null.
     * @return
     *   Two bags- All elements in the first bag are smaller than "target" and all elements in the second bag are equal or smaller than "target"
     * @exception IllegalArgumentException
     *   Indicates that the bag is null.
     *
     **/
    public static IntTreeBag[] split(IntTreeBag bag, int target)
    {
        IntTreeBag leftBag = new IntTreeBag();
        IntTreeBag rightBag = new IntTreeBag();

        splitSubtree(bag.root, leftBag, rightBag, target);

        return new IntTreeBag[]{leftBag, rightBag};
    }

    private static void splitSubtree(IntBTNode root, IntTreeBag leftBag, IntTreeBag rightBag, int target) {
        if (root == null)
            return;

        if (root.getData() < target)
            leftBag.add(root.getData());
        else
            rightBag.add(root.getData());

        splitSubtree(root.getLeft(), leftBag, rightBag, target);
        splitSubtree(root.getRight(), leftBag, rightBag, target);
    }

    /**
     * A histogram is a graphical representation of the distribution of numerical data.
     * getHistogram method return the array of the number of cases in each bin
     * @param bin
     *   range of value, bin is should be larger than zero.
     * @return
     *   Array of the number of cases in each bin.
     *   Length of the array must be smallest. Please see a given document for this homework.
     *   The array must be sorted in ascending order of represented value of bin.
     * @exception IllegalArgumentException
     *   Indicates that "bin" is equal or smaller than zero
     *
     **/

    public int[] getHistogram(int bin) {
        if (bin <= 0)
            throw new IllegalArgumentException();

        int maxData = root.getRightmostData();
        int minData = root.getLeftmostData();
        int size = (maxData - minData + 1 + bin - 1)/bin;
        int[] hist = new int[size];
        updateHistogram(root, bin, hist, minData);

        return hist;
    }

    private void updateHistogram(IntBTNode root, int bin, int[] hist, int minData) {
        if (root == null)
            return;

        hist[(root.getData() - minData) / bin]++;
        updateHistogram(root.getLeft(), bin, hist, minData);
        updateHistogram(root.getRight(), bin, hist, minData);
    }

    /*
     * LevelOrderIterator is an external iterator for IntTreeBag which can traverse IntTreeBag in level order (Breadth First Traversal).
     * You should implement hasNext() and next() methods in this class
     */
    private class LevelOrderIterator implements Iterator<Integer> {
        private Queue<IntBTNode> queue;

        LevelOrderIterator() {
            queue = new LinkedList<IntBTNode>();
            if (root != null)
                queue.add(root);
        }

        @Override
        public boolean hasNext() {
            return ! queue.isEmpty();
        }

        @Override
        public Integer next() {
            if (queue.peek().getLeft() != null)
                queue.add(queue.peek().getLeft());

            if (queue.peek().getRight() != null)
                queue.add(queue.peek().getRight());

            return queue.remove().getData();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    //DO NOT MODIFY BELOW METHOD
    public Iterator<Integer> getLevelOrderIterator(){
        return new LevelOrderIterator();
    }

}