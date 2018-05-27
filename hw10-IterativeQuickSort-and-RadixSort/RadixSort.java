package elice;

import java.util.LinkedList;

public class RadixSort {
  //Do not change the name
  LinkedList<Integer> list0, list1;
  final int MAX_ITERATIONS = 31;
  
  public RadixSort(){
    list0 = new LinkedList<Integer>();
    list1 = new LinkedList<Integer>();
  }

  public void radixSort(int[] data){
      int divisor = 1;

        for (int i = 0; i < MAX_ITERATIONS; i++) {
      for (int j = 0; j < data.length; j++) {
//        if (((data[j] >> i) & 1) == 0)
        if ((data[j]/divisor) % 2 == 0)
          list0.add(data[j]);
        else
          list1.add(data[j]);
      }

      divisor *= 2;

      for (int j = 0; j < data.length; j++) {
        if ( ! list0.isEmpty())
          data[j] = list0.removeFirst();
        else
          data[j] = list1.removeFirst();
      }
    }
  }
  
  //Print method for your help
  public void print( int[] data, int n )
    {
        int i;
        for ( i = 0; i < n; ++i )
            System.out.print(data[i]+" ");
    }
}
