package cs206b;

public class DoubleArraySeq implements Cloneable {
  private static final int INITIAL_CAPACITY = 10;
  private int size;
  private int capacity;
  private double[] data;
  private int current;

  public DoubleArraySeq() {
    this(INITIAL_CAPACITY);
  }

  public DoubleArraySeq(int initialCapacity) {
    if (initialCapacity < 0)
      throw new IllegalArgumentException();

    size = 0;
    capacity = initialCapacity;
    data = new double[capacity];
    current = -1;
  }

  public void addAfter(double element) {
    ensureCapacity(size + 1);

    if ( ! isCurrent())
      current = size-1;

    for (int i = size-1; i > current; i--)
      data[i+1] = data[i];

    current++;
    data[current] = element;

    size++;
  }

  public void addBefore(double element) {
    ensureCapacity(size + 1);

    if ( ! isCurrent())
      current = 0;

    for (int i = size-1; i >= current; i--)
      data[i+1] = data[i];

    data[current] = element;

    size++;
  }

  public void addAll(DoubleArraySeq addend) {
    if (addend == null)
      throw new NullPointerException();

    ensureCapacity(size + addend.size);

    System.arraycopy(addend.data, 0, data, size, addend.size);
    size += addend.size;
  }

  public void advance() {
    if ( ! isCurrent())
      throw new IllegalStateException();

    current++;
        
        if (current == size)
            current = -1;
  }

  public DoubleArraySeq clone() {
    DoubleArraySeq result = null;

    try {
      result = (DoubleArraySeq) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }

    result.data = data.clone();
    return result;
  }

  public static DoubleArraySeq concatenation(DoubleArraySeq s1, DoubleArraySeq s2) {
    DoubleArraySeq result = new DoubleArraySeq(s1.size + s2.size);

    result.addAll(s1);
    result.addAll(s2);

    return result;
  }

  public void ensureCapacity(int minimumCapacity) {
    if (capacity < minimumCapacity) {
      capacity = 2*minimumCapacity; // @TODO Decide: minimumCapacity or 2*minimumCapacity?
      double[] data2 = new double[capacity];
      System.arraycopy(data, 0, data2, 0, size);
      data = data2;
    }
  }

  public int getCapacity() {
    return capacity;
  }

  public double getCurrent() {
    if ( ! isCurrent())
      throw new IllegalStateException();

    return data[current];
  }

  public boolean isCurrent() {
    return current != -1;
  }

  public void removeCurrent() {
    if ( ! isCurrent())
      throw new IllegalStateException();

    for (int i = current + 1; i < size; i++)
      data[i-1] = data[i];

    size--;
  }

  public int size() {
    return size;
  }

  public void start() {
    current = 0;
  }

  public void trimToSize() {
    capacity = size;
    double[] data2 = new double[capacity];
    System.arraycopy(data, 0, data2, 0, size);
    data = data2;
  }
  public static DoubleArraySeq reverse(DoubleArraySeq seq) {
    DoubleArraySeq result = seq.clone();

    for (int i = 0; i < result.size - i - 1; i++) {
      double temp = result.data[i];
      result.data[i] = result.data[result.size - i - 1];
      result.data[result.size - i - 1] = temp;
    }

    return result;
  }
  public double getMax() {
    if (size == 0)
      throw new IllegalStateException();

    double max = data[0];
    for (int i = 1; i < size; i++)
      if (max < data[i])
        max = data[i];

    return max;
  }
  public double getMin() {
    if (size == 0)
      throw new IllegalStateException();

    double min = data[0];
    for (int i = 1; i < size; i++)
      if (min > data[i])
        min = data[i];

    return min;
  }

  public DoubleArraySeq insertSeqAt(DoubleArraySeq seq, int index) {
    if (index < 1 || size < index)
      throw new IllegalArgumentException();

    index--;

    DoubleArraySeq result = this.clone();
    result.ensureCapacity(result.size + seq.size);

    // shift
    for (int i = result.size-1; i >= index; i--)
      result.data[i + seq.size] = result.data[i];

    // insert
    for (int i = 0; i < seq.size; i++)
      result.data[index + i] = seq.data[i];

    result.size += seq.size;

    return result;
  }
}
