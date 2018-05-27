package elice;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;

public class CHTable<K, E> {
    // Invariant of the CHTable class:
    // For index i, the Object in index i of table is a linked list
    // of all the elements for which hash(key) is i.
    private Vector<LinkedList<HashPair<K, E>>> table;

    // DON'T ERASE THIS. WE USE THIS VARIABLE FOR THE TEST.
    public int visits = 0;

    /**
     * Initialize an empty CHTable with a specified table size.
     *
     * @param tableSize
     *            the table size for this new chained hash
     * @Precondition <CODE>tableSize > 0</CODE>
     * @Postcondition This CHTable is empty and has the specified table size.
     * @exception OutOfMemoryError
     *                Indicates insufficient memory for the specified table
     *                size.
     * @exception IllegalArgumentException
     *                Indicates that tableSize is not positive.
     **/
    public CHTable(int tableSize) {
        int i;

        if (tableSize <= 0)
            throw new IllegalArgumentException("Table size must be positive.");

        // Allocate the table which is initially all empty linked lists:
        table = new Vector<LinkedList<HashPair<K, E>>>(tableSize);
        table.setSize(tableSize);
        for (i = 0; i < tableSize; i++) {
            table.set(i, new LinkedList<HashPair<K, E>>());
        }
    }

    /**
     * Determines whether a specified key is in this CHTable.
     *
     * @param key
     *            the non-null key to look for
     * @Precondition <CODE>key</CODE> cannot be null.
     * @return <CODE>true</CODE> (if this CHTable contains an object with the
     *         specified key); <CODE>false</CODE> otherwise. Note that <CODE>
     *         key.equals()</CODE> is used to compare the <CODE>key</CODE> to
     *         the keys that are in the CHTable.
     * @exception NullPointerException
     *                Indicates that <CODE>key</CODE> is null.
     **/
    public boolean containsKey(K key) {
        return findHashPair(key) != null;
    }

    private HashPair<K, E> findHashPair(K key)
    {
        int i = hash(key);

        for (HashPair<K, E> h: table.get(i))
        {
            visits++;

            if (h.key.equals(key))
                return h;
        }

        return null;
    }


    /**
     * Retrieves an object for a specified key.
     *
     * @param key
     *            the non-null key to look for
     * @Precondition <CODE>key</CODE> cannot be null.
     * @return a reference to the object with the specified <CODE>key</CODE (if
     *         this CHTable contains an such an object); null otherwise. Note
     *         that <CODE>key.equals( )</CODE> is used to compare the <CODE>key
     *         </CODE> to the keys that are in the CHTable.
     * @exception NullPointerException
     *                Indicates that <CODE>key</CODE> is null.
     **/
    public E get(K key) {
        HashPair<K, E> h = findHashPair(key);

        return h == null ? null : h.element;
    }

    private int hash(K key)
    {
      /*
        The return value is a valid index of the CHTable's table. The index is
        calculated as the remainder when the absolute value of the key's
        hash code is divided by the size of the CHTable's table.
      */

        return Math.abs(key.hashCode()) % table.size();
    }

    /**
     * Add a new element to this CHTable, using the specified key.
     *
     * @param key
     *            the non-null key to use for the new element
     * @param element
     *            the new element that's being added to this CHTable
     * @Precondition Neither <CODE>key</CODE> nor </CODE>element</CODE> is null.
     * @Postcondition If this CHTable already has an object with the specified
     *                <CODE>key</CODE>, then that object is linked to end of list which at the specified <CODE> key </CODE>
     *                </CODE>element</CODE>, and the return value is a reference
     *                to the object. Otherwise, the new
     *                </CODE>element</CODE> is added with the specified
     *                <CODE>key</CODE> and the return value is null.
     * @exception NullPointerException
     *                Indicates that <CODE>key</CODE> or <CODE>element</CODE> is
     *                null.
     **/
    public E put(K key, E element) {
        // Verify the precondition:
        if (key == null || element == null)
            throw new NullPointerException("null key or element in put");

        HashPair<K, E> h = findHashPair(key);
        E answer;

        if (h == null) {
            answer = null;

            int i = hash(key);
            table.get(i).add(new HashPair<K, E>(key, element));
        }
        else {
            answer = h.element;
            h.element = element;
        }

        return answer;
    }

    /**
     * Removes an object for a specified key.
     *
     * @param key
     *            the non-null key to look for
     * @Precondition <CODE>key</CODE> cannot be null.
     * @Postcondition If an object was found with the specified
     *                </CODE>key</CODE>, then that object has been removed from
     *                this CHTable and a copy of the removed object is
     *                returned; otherwise, this CHTable is unchanged and the
     *                null reference is returned. Note that
     *                <CODE>key.equals( )</CODE> is used to compare the
     *                <CODE>key</CODE> to the keys that are in the CHTable.
     * @exception NullPointerException
     *                Indicates that </CODE>key</CODE> is null.
     **/
    public E remove(K key) {
        HashPair<K, E> h = findHashPair(key);

        E answer;

        if (h != null) {
            answer = h.element;
            int i = hash(key);
            table.get(i).remove(h);
        }
        else
            answer = null;

        return answer;
    }

}

class HashPair<K, E> {
    K key;
    E element;

    HashPair(K initKey, E initElement) {
        key = initKey;
        element = initElement;
    }
}
