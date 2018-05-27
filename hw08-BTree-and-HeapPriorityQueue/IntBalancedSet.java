package elice;

public class IntBalancedSet implements Cloneable
{
    // Invariant of the IntBalancedSet class:
    //   1. The elements of the Set are stored in a B-tree, satisfying the six
    //      B-tree rules.
    //   2. The number of elements in the tree's root is in the instance
    //      variable dataCount, and the number of subtrees of the root is stored
    //      stored in the instance variable childCount.
    //   3. The root's elements are stored in data[0] through data[dataCount-1].
    //   4. If the root has subtrees, then subtree[0] through
    //      subtree[childCount-1] are references to these subtrees.
    private final int MINIMUM = 1; // DO NOT CHANGE
    private final int MAXIMUM = 2*MINIMUM; // DO NOT CHANGE
    int dataCount;
    int[ ] data = new int[MAXIMUM + 1];
    int childCount;
    IntBalancedSet[ ] subset = new IntBalancedSet[MAXIMUM + 2];

    /**
     * Initialize an empty set.
     * <b>Postcondition:</b>
     *   This set is empty.
     **/
    public IntBalancedSet( )
    {
        dataCount = 0;
        childCount = 0;
    }


    /**
     * Add a new element to this set.
     * @param  element
     *   the new element that is being added
     * <b>Postcondition:</b>
     *   If the element was already in this set, then there is no change.
     *   Otherwise, the element has been added to this set.
     **/
    public void add(int element)
    {
        looseAdd(element);

        if (dataCount > MAXIMUM) {
            int midData = data[MINIMUM];

            IntBalancedSet subsetL = new IntBalancedSet();
            IntBalancedSet subsetR = new IntBalancedSet();

            System.arraycopy(data, 0, subsetL.data, 0, MINIMUM);
            System.arraycopy(data, MINIMUM+1, subsetR.data, 0, MINIMUM);
            subsetL.dataCount = subsetR.dataCount = MINIMUM;

            if (isLeaf())
                subsetL.childCount = subsetR.childCount = 0;
            else {
                System.arraycopy(subset, 0, subsetL.subset, 0, MINIMUM + 1);
                System.arraycopy(subset, MINIMUM+1, subsetR.subset, 0, MINIMUM + 1);
                subsetL.childCount = subsetR.childCount = MINIMUM + 1;
            }


            for (int i = 0; i < dataCount; i++)
                data[i] = 0;

            for (int i = 0; i < childCount; i++)
                subset[i] = null;

            dataCount = 1;
            childCount = 2;

            data[0] = midData;

            subset[0] = subsetL;
            subset[1] = subsetR;
        }
    }

    /**
     * Generate a copy of this set.
     * @return
     *   The return value is a copy of this set. Subsequent changes to the
     *   copy will not affect the original, nor vice versa. Note that the return
     *   value must be type cast to an <CODE>IntBalancedSet</CODE> before it
     *   can be used.
     **/
    public Object clone( )
    {  // Clone a IntBalancedSet object.
        IntBalancedSet copy = null;

        try {
            copy = (IntBalancedSet) super.clone();
            copy.data = copy.data.clone();
            copy.subset = copy.subset.clone();

            for (int i = 0; i < childCount; i++)
                copy.subset[i] = (IntBalancedSet) copy.subset[i].clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return copy;
    }


    /**
     * Accessor method to determine whether a particular element is in this set.
     * @param target
     *   an element that may or may not be in this set
     * @return
     *   <CODE>true</CODE> if this set contains <CODE>target</CODE>;
     *   otherwise <CODE>false</CODE>
     **/
    public boolean contains(int target)
    {
        int idx = firstGE(target);

        if (idx < dataCount && data[idx] == target)
            return true;
        else if (isLeaf())
            return false;
        else
            return subset[idx].contains(target);
    }


    /**
     * Remove a specified element from this set.
     * @param target
     *   the element to remove from this set
     * @return
     *   if <CODE>target</CODE> was found in this set, then it has been removed
     *   and the method returns <CODE>true</CODE>. Otherwise this set remains
     *   unchanged and the method returns <CODE>false</CODE>.
     **/
    public boolean remove(int target)
    {
        boolean answer = looseRemove(target);

        if (dataCount == 0 && childCount == 1) {
            dataCount = subset[0].dataCount;
            childCount = subset[0].childCount;
            data = subset[0].data;
            subset = subset[0].subset;
        }

        return answer;
    }


    public void print(int indent)
    // Print a representation of this set's B-tree, useful during debugging.
    {
        final int EXTRA_INDENTATION = 4;
        int i;
        int space;

        // Print the indentation and the data from this node
        for (space = 0; space < indent; space++)
            System.out.print(" ");
        for (i = 0; i < dataCount; i++)
            System.out.print(data[i] + " ");
        System.out.println( );

        // Print the subtrees
        for (i = 0; i < childCount; i++)
            subset[i].print(indent + EXTRA_INDENTATION);
    }


    // PRIVATE HELPER METHODS
    // The helper methods are below with precondition/postcondition contracts.
    // Students should implement these methods to help with the other methods.

    private int deleteData(int removeIndex)
    // Precondition: 0 <= removeIndex < dataCount.
    // Postcondition: The element at data[removeIndex] has been removed and
    // subsequent elements shifted over to close the gap. Also, dataCount has
    // been decremented by one, and the return value is a copy of the
    // removed element.
    {
        int removedData = data[removeIndex];
        System.arraycopy(data, removeIndex+1, data, removeIndex, dataCount - (removeIndex+1));
        data[dataCount-1] = 0;
        dataCount--;

        return removedData;
    }


    private IntBalancedSet deleteSubset(int removeIndex)
    // Precondition: 0 <= removeIndex < childCount.
    // Postcondition: The element at subset[removeIndex] has been removed and
    // subsequent elements shifted over to close the gap. Also, childCount has
    // been decremented by one, and the return value is a copy of the
    // removed element.
    {
        IntBalancedSet removedSubset = subset[removeIndex];
        System.arraycopy(subset, removeIndex+1, subset, removeIndex, childCount-(removeIndex+1));
        subset[childCount-1] = null;
        childCount--;

        return removedSubset;
    }

    private int firstGE(int target)
    // Postcondition: The return value, x, is the first location in the root
    // such that data[x] >= target. If there is no such location, then the
    // return value is dataCount.
    {
        int idx = 0;
        for (; data[idx] < target && idx < dataCount; idx++);

        return idx;
    }


    private void fixExcess(int i)
    // Precondition:
    //   (i < childCount) and the entire B-tree is valid EXCEPT that
    //   subset[i] has MAXIMUM + 1 entries. Also, the root is allowed to have
    //   zero entries and one child.
    // Postcondition:
    //   The tree has been rearranged so that the entire B-tree is valid EXCEPT
    //   that the number of entries in the root of this set might be one more than
    //   the allowed maximum.
    {
        IntBalancedSet oldSubset = deleteSubset(i);

        int midData = oldSubset.data[MINIMUM];
        insertData(i, midData);

        IntBalancedSet subsetL = new IntBalancedSet();
        IntBalancedSet subsetR = new IntBalancedSet();

        System.arraycopy(oldSubset.data, 0, subsetL.data, 0, MINIMUM);
        System.arraycopy(oldSubset.data, MINIMUM+1, subsetR.data, 0, MINIMUM);
        subsetL.dataCount = subsetR.dataCount = MINIMUM;

        if (oldSubset.isLeaf())
            subsetL.childCount = subsetR.childCount = 0;
        else {
            System.arraycopy(oldSubset.subset, 0, subsetL.subset, 0, MINIMUM + 1);
            System.arraycopy(oldSubset.subset, MINIMUM + 1, subsetR.subset, 0, MINIMUM + 1);
            subsetL.childCount = subsetR.childCount = MINIMUM + 1;
        }

        insertSubset(i, subsetL);
        insertSubset(i+1, subsetR);
    }


    private void fixShortage(int i)
    // Precondition:
    //   (i < childCount) and the entire B-tree is valid EXCEPT that
    //   subset[i] has only MINIMUM - 1 entries.
    // Postcondition:
    //   The tree has been rearranged so that the entire B-tree is valid EXCEPT
    //   that the number of entries in the root of this set might be one less than
    //   the allowed minimum.
    {
        if (i-1 >= 0 && subset[i-1].dataCount > MINIMUM) {
            transferRight(i-1);
        }
        else if (i+1 < childCount && subset[i+1].dataCount > MINIMUM) {
            transferLeft(i+1);
        }
        else if (i-1 >= 0 && subset[i-1].dataCount == MINIMUM) {
            mergeWithNextSubset(i-1);
        }
        else if (i+1 < childCount && subset[i+1].dataCount == MINIMUM) {
            mergeWithNextSubset(i);
        }
    }


    private void insertData(int insertIndex, int entry)
    // Precondition: 0 <= insertIndex <= dataCount <= MAXIMUM.
    // Postcondition: The entry has been inserted at data[insertIndex] with
    // subsequent elements shifted right to make room. Also, dataCount has
    // been incremented by one.
    {
        System.arraycopy(data, insertIndex, data, insertIndex+1, dataCount-insertIndex);
        data[insertIndex] = entry;
        dataCount++;
    }


    private void insertSubset(int insertIndex, IntBalancedSet set)
    // Precondition: 0 <= insertIndex <= childCount <= MAXIMUM+1.
    // Postcondition: The set has been inserted at subset[insertIndex] with
    // subsequent elements shifted right to make room. Also, childCount has
    // been incremented by one.
    {
        System.arraycopy(subset, insertIndex, subset, insertIndex+1, childCount-insertIndex);
        subset[insertIndex] = set;
        childCount++;
    }


    private boolean isLeaf( )
    // Return value is true if and only if the B-tree has only a root.
    {
        return (childCount == 0);
    }


    private void looseAdd(int entry)
    // Precondition:
    //   The entire B-tree is valid.
    // Postcondition:
    //   If entry was already in the set, then the set is unchanged. Otherwise,
    //   entry has been added to the set, and the entire B-tree is still valid
    //   EXCEPT that the number of entries in the root of this set might be one
    //   more than the allowed maximum.
    {
        int idx = firstGE(entry);

        if (idx < dataCount  && data[idx] == entry)
            return;
        else if (isLeaf())
            insertData(idx, entry);
        else {
            subset[idx].looseAdd(entry);

            if (subset[idx].dataCount > MAXIMUM)
                fixExcess(idx);
        }
    }


    private boolean looseRemove(int target)
    // Precondition:
    //   The entire B-tree is valid.
    // Postcondition:
    //   If target was in the set, then it has been removed from the set and the
    //   method returns true; otherwise the set is unchanged and the method
    //   returns false. The entire B-tree is still valid EXCEPT that the
    //   number of entries in the root of this set might be one less than the
    //   allowed minimum.
    {
        int idx = firstGE(target);

        if (isLeaf()) {
            if (idx == dataCount || data[idx] != target)
                return false;
            else {
                deleteData(idx);
                return true;
            }
        }
        else {
            if (idx == dataCount || data[idx] != target) {
                boolean answer = subset[idx].looseRemove(target);

                if (subset[idx].dataCount < MINIMUM)
                    fixShortage(idx);

                return answer;
            }
            else {
                data[idx] = subset[idx].removeBiggest();

                if (subset[idx].dataCount < MINIMUM)
                    fixShortage(idx);

                return true;
            }
        }
    }


    private void mergeWithNextSubset(int i)
    // Precondition:
    //   (i+1 < childCount) and the entire B-tree is valid EXCEPT that the total
    //   number of entries in subset[i] and subset[i+1] is 2*MINIMUM - 1.
    // Postcondition:
    //   subset[i] and subset[i+1] have been merged into one subset (now at
    //   subset[i]), and data[i] has been passed down to be the median entry of the
    //   new subset[i]. As a result, the entire B-tree is valid EXCEPT that the
    //   number of entries in the root of this set might be one less than the
    //   allowed minimum.
    {
        // delete data[i] and union subset[i] and subset[i+1]
        IntBalancedSet left = subset[i];
        IntBalancedSet right = deleteSubset(i+1);

        // subsetL = union(subsetL, subsetR)
        left.data[ left.dataCount++ ] = deleteData(i);
        for (int j = 0; j < right.dataCount; j++)
            left.data[ left.dataCount++ ] = right.data[j];

        for (int j = 0; j < right.childCount; j++)
            left.subset[ left.childCount++ ] = right.subset[j];
    }

    private int removeBiggest( )
    // Precondition:
    //   (dataCount > 0) and the entire B-tree is valid.
    // Postcondition:
    //   The largest item in the set has been removed, and the value of this
    //   item is the return value. The B-tree is still valid EXCEPT
    //   that the number of entries in the root of this set might be one less than
    //   the allowed minimum.
    {
        if (isLeaf())
            return data[--dataCount];
        else
            return subset[childCount-1].removeBiggest();
    }


    private void transferLeft(int i)
    // Precondition:
    //   (0 < i < childCount) and (subset[i]->dataCount > MINIMUM)
    //   and the entire B-tree is valid EXCEPT that
    //   subset[i-1] has only MINIMUM - 1 entries.
    // Postcondition:
    //   One entry has been shifted from the front of subset[i] up to
    //   data[i-1], and the original data[i-1] has been shifted down to the last
    //   entry of subset[i-1]. Also, if subset[i] is not a leaf, then its first
    //   subset has been transfered over to be the last subset of subset[i-1].
    //   As a result, the entire B-tree is now valid.
    {
        subset[i-1].insertData(subset[i-1].dataCount, data[i-1]);
        int firstData = subset[i].deleteData(0);
        data[i-1] = firstData;

        if ( ! subset[i].isLeaf()) {
            IntBalancedSet firstChild = subset[i].deleteSubset(0);
            subset[i - 1].insertSubset(subset[i - 1].childCount, firstChild);
        }
    }


    private void transferRight(int i)
    // Precondition:
    //   (i+1 < childCount) and (subset[i]->dataCount > MINIMUM)
    //   and the entire B-tree is valid EXCEPT that
    //   subset[i] has only MINIMUM - 1 entries.
    // Postcondition: One entry has been shifted from the end of subset[i] up to
    //   data[i], and the original data[i] has been shifted down to the first entry
    //   of subset[i+1]. Also, if subset[i] is not a leaf, then its last subset has
    //   been transfered over to be the first subset of subset[i+1].
    //   As a result, the entire B-tree is now valid.
    {
        subset[i+1].insertData(0, data[i]);
        int lastData = subset[i].deleteData(subset[i].dataCount - 1);
        data[i] = lastData;

        if ( ! subset[i].isLeaf()) {
            IntBalancedSet lastChild = subset[i].deleteSubset(subset[i].childCount - 1);
            subset[i + 1].insertSubset(0, lastChild);
        }
    }

}
