package elice;

public class Table<K, E> {
  // Invariant of the Table class:
    // 1. The number of items in the table is in the instance variable
    // manyItems.
    // 2. The preferred location for an element with a given key is at index
    // hash(key). If a collision occurs, then next-Index is used to search
    // forward to find the next open address. When an open address is found
    // at an index i, then the element itself is placed in data[i] and the
    // element's key is placed at keys[i].
    // 3. An index i that is not currently used has data[i] and key[i] set to
    // null.
    // 4. If an index i has been used at some point (now or in the past), then
    // hasBeenUsed[i] is true; otherwise it is false.
    private int manyItems;
    private Object[] keys;
    private Object[] data;
    private boolean[] hasBeenUsed;

    /**
     * Initialize an empty table with a specified capacity.
     * 
     * @param capacity
     *            the capacity for this new open-address hash table <dt>
     * @Postcondition This table is empty and has the specified capacity.
     * @exception OutOfMemoryError
     *                Indicates insufficient memory for the specified capacity.
     **/
    public Table(int capacity) {
      if (capacity <= 0)
        throw new IllegalArgumentException("Capacity is negative");
      keys = new Object[capacity];
      data = new Object[capacity];
      hasBeenUsed = new boolean[capacity];
    }

    /**
     * Determines whether a specified key is in this table. Note that
     * <CODE>key.equals( )</CODE> is used to compare the <CODE>key</CODE> to the
     * keys that are in the table.
     * 
     * @param key
     *            the non-null key to look for
     * @Precondition <CODE>key</CODE> cannot be null.
     * @return true if this table contains an object with the specified key;
     *         false otherwise.
     * @exception NullPointerException
     *                Indicates that <CODE>key</CODE> is null.
     **/
    public boolean containsKey(K key) {
      return findIndex(key) != -1;
    }

    // a temporary variable for counting the number of probings in findIndex().
    public int visits = 0;
    
    private int findIndex(K key)
    // Postcondition: If the specified key is found in the table, then the
    // return value is the index of the specified key. Otherwise, the return
    // value is -1.
    {
      int count = 0;
      int i = hash(key);

      while (count < data.length && hasBeenUsed[i]) {
        visits++;
        if (key.equals(keys[i]))
          return i;
        count++;
        i = nextIndex(i);
      }

      return -1;
    }

    /**
     * Retrieves an object for a specified key.
     * 
     * @param key
     *            the non-null key to look for
     * @Precondition <CODE>key</CODE> cannot be null.
     * @return a reference to the object with the specified <CODE>key</CODE> (if
     *         this table contains an such an object); null otherwise. Note that
     *         <CODE>key.equals( )</CODE> is used to compare the <CODE>key
     *         </CODE> to the keys that are in the table.
     * @exception NullPointerException
     *                Indicates that <CODE>key</CODE> is null.
     **/
    @SuppressWarnings("unchecked")
    public E get(K key) {
      int index = findIndex(key);

      if (index == -1)
        return null;
      else
        return (E) data[index];
    }

    private int hash(K key)
    {
      /*
        The return value is a valid index of the table's arrays. The index is
        calculated as the remainder when the absolute value of the key's
        hash code is divided by the size of the table's arrays.
      */
      return Math.abs(key.hashCode()) % data.length;
    }

    private int nextIndex(int i)
    // The return value is normally i+1. But if i+1 is data.length, then the
    // return value is zero instead.
    {
      if (i + 1 == data.length)
        return 0;
      else
        return i + 1;
    }

    /**
     * Add a new element to this table, using the specified key.
     * 
     * @param key
     *            the non-null key to use for the new element
     * @param element
     *            the new element that is being added to this table
     * @Precondition If there is not already an element with the specified
     *               <CODE>key</CODE>, then this table's size must be less than
     *               its capacity (i.e., <CODE>size() < capacity()</CODE>).
     *               Also, neither <CODE>key</CODE> nor </CODE>element</CODE> is
     *               null.
     * @Postcondition If this table already has an object with the specified
     *                <CODE>key</CODE>, then that object is replaced by
     *                </CODE>element</CODE>, and the return value is a reference
     *                to the replaced object. Otherwise, the new
     *                </CODE>element</CODE> is added with the specified
     *                <CODE>key</CODE> and the return value is null.
     * @exception IllegalStateException
     *                Indicates that there is no room for a new object in this
     *                table.
     * @exception NullPointerException
     *                Indicates that <CODE>key</CODE> or <CODE>element</CODE> is
     *                null.
     **/
    @SuppressWarnings("unchecked")
    public E put(K key, E element) {
      int index = findIndex(key);
      E answer;

      if (index != -1) { // The key is already in the table.
        answer = (E) data[index];
        data[index] = element;
        return answer;
      } else if (manyItems < data.length) { // The key is not yet in this
                          // Table.
        index = hash(key);
        while (keys[index] != null)
          index = nextIndex(index);
        keys[index] = key;
        data[index] = element;
        hasBeenUsed[index] = true;
        manyItems++;
        return null;
      } else { // The table is full.
        throw new IllegalStateException("Table is full.");
      }
    }

    /**
     * Removes an object for a specified key.
     * 
     * @param key
     *            the non-null key to look for
     * @Precondition <CODE>key</CODE> cannot be null.
     * @Postcondition If an object was found with the specified
     *                </CODE>key</CODE>, then that object has been removed from
     *                this table and a copy of the removed object is returned;
     *                otherwise, this table is unchanged and the null reference
     *                is returned. Note that <CODE>key.equals( )</CODE> is used
     *                to compare the <CODE>key</CODE> to the keys that are in
     *                the table.
     * @exception NullPointerException
     *                Indicates that </CODE>key</CODE> is null.
     **/
    @SuppressWarnings("unchecked")
    public E remove(K key) {
      int index = findIndex(key);
      E answer = null;

      if (index != -1) {
        answer = (E) data[index];
        keys[index] = null;
        data[index] = null;
        manyItems--;
      }

      return answer;
    }
}
